package com.tatteam.languagerealm.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.entity.PhraseEntity;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.app_common.ui.drawable.RippleEffectLight;


public class PhraseInQuickModeAdapter extends RecyclerView.Adapter<PhraseInQuickModeAdapter.ViewHolder> {

    private clickListener mlisListener;
    private int KIND_PHRASE_ID;
    private BaseActivity activity;
    private List<PhraseEntity> listPhraseEntity;

    public PhraseInQuickModeAdapter(BaseActivity activity, List<PhraseEntity> listPhraseEntity, int kind) {

        super();
        this.activity = activity;
        this.KIND_PHRASE_ID = kind;
        this.listPhraseEntity = new ArrayList<>();
        this.listPhraseEntity = listPhraseEntity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_phrase_in_quick_mode, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvPhrase.setText(Html.fromHtml(listPhraseEntity.get(i).phrase));

        switch (KIND_PHRASE_ID) {
            case R.string.slang:
                viewHolder.bgPhrase.setCardBackgroundColor(activity.getResources().getColor(R.color.slang_Primary));
                break;
            case R.string.idiom:
                viewHolder.bgPhrase.setCardBackgroundColor(activity.getResources().getColor(R.color.idiom_Primary));
                break;
            case R.string.proverb:
                viewHolder.bgPhrase.setCardBackgroundColor(activity.getResources().getColor(R.color.proverb_Primary));
                break;
        }
    }

    @Override
    public int getItemCount() {

        return listPhraseEntity.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, RippleView.OnRippleCompleteListener {
        public TextView tvPhrase;
        public CardView bgPhrase;
        public RippleEffectLight rippleView;


        public ViewHolder(View itemView) {
            super(itemView);

            rippleView = (RippleEffectLight) itemView.findViewById(R.id.rippleView);
            tvPhrase = (TextView) itemView.findViewById(R.id.tv_phrase);
            bgPhrase = (CardView) itemView.findViewById(R.id.bg_phrase);
                        rippleView.setOnRippleCompleteListener(this);
        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public void onComplete(RippleView rippleView) {
            if (mlisListener != null)
                mlisListener.onPhraseClick(getAdapterPosition());
        }
    }

    public void setListPhraseEntity(List<PhraseEntity> listPhraseEntity) {
        this.listPhraseEntity = listPhraseEntity;
    }

    public interface clickListener {
        public void onPhraseClick(int position);

    }

    public void setmListener(clickListener mlisListener) {
        this.mlisListener = mlisListener;
    }
}