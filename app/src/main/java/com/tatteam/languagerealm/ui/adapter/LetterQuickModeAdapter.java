package com.tatteam.languagerealm.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.entity.LetterEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hoaba on 9/8/2015.
 */
public class LetterQuickModeAdapter extends RecyclerView.Adapter<LetterQuickModeAdapter.ViewHolder> {

    private BaseActivity activity;
    private ClickListener mlistener;
    private List<LetterEntity> listLetterEntity;
    private int KIND_LETTER;

    public LetterQuickModeAdapter(BaseActivity activity, List<LetterEntity> listLetterEntity, int kind) {
        super();
        this.activity = activity;
        this.KIND_LETTER = kind;
        this.listLetterEntity = new ArrayList<>();
        this.listLetterEntity = listLetterEntity;
    }

    public void setListLetterEntity(List<LetterEntity> listLetterEntity) {
        this.listLetterEntity = listLetterEntity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_letter_quick_mode, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvCharacter.setText(listLetterEntity.get(i).letter.toUpperCase());
        switch (KIND_LETTER) {
            case R.string.slang:
                if (listLetterEntity.get(i).selected) {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.slang_Primary));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.slang_Primary));
                }
                break;
            case R.string.idiom:
                if (listLetterEntity.get(i).selected) {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.idiom_Primary));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.idiom_Primary));
                }
                break;
            case R.string.proverb:
                if (listLetterEntity.get(i).selected) {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.proverb_Primary));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.white));
                } else {
                    viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
                    viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.proverb_Primary));
                }
                break;
        }

    }

    @Override
    public int getItemCount() {

        return listLetterEntity.size();
    }

    public void setmListener(ClickListener listener) {
        this.mlistener = listener;
    }

    public interface ClickListener {
        public void onLetterClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCharacter;
        public CardView cvCharacter;
        public RelativeLayout rippleView;

        public ViewHolder(View itemView) {
            super(itemView);

            cvCharacter = (CardView) itemView.findViewById(R.id.background_letter);
            tvCharacter = (TextView) itemView.findViewById(R.id.tv_letter);
            rippleView = (RelativeLayout) itemView.findViewById(R.id.rippleView);
            rippleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mlistener.onLetterClick(getAdapterPosition());

                }
            });


        }


    }
}
