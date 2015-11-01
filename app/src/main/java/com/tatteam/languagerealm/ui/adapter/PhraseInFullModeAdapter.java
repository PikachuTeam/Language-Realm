package com.tatteam.languagerealm.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.entity.PhraseEntity;

import java.util.List;

import tatteam.com.app_common.ui.drawable.RippleEffectDark;


/**
 * Created by hoaba on 9/8/2015.
 */
public class PhraseInFullModeAdapter extends RecyclerView.Adapter<PhraseInFullModeAdapter.ViewHolder> {
    public Context activity;
    public List<PhraseEntity> list;
    private ClickListener mlisListener;

    public PhraseInFullModeAdapter(BaseActivity activity, List<PhraseEntity> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_phrase_in_full_mode, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        viewHolder.phrase_item.setVisibility(View.VISIBLE);
        viewHolder.item_favorite_character.setVisibility(View.GONE);
        viewHolder.tvTitle.setText(Html.fromHtml(list.get(i).phrase));
        viewHolder.tvMeaning.setText(list.get(i).explanation);
        if (list.get(i).isFavorite > 0)
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_grade_red_24dp);
        else {
            viewHolder.imageView.setBackgroundResource(R.drawable.ic_grade_gray_24dp);
            viewHolder.imageView.setColorFilter(activity.getResources().getColor(R.color.idiom_Primary));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setmLislistener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;
    }

    public interface ClickListener {
        void onPhraseClick(int position);

        void onFavoriteChange(int position);

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvMeaning;
        public CardView item_favorite_character;
        public RippleEffectDark phrase_item;
        public RelativeLayout phrase;
        public ImageView imageView;
        public TextView tvFavoriteCharacter;
        public LinearLayout favorite_icon;


        public ViewHolder(View itemView) {
            super(itemView);
            phrase = (RelativeLayout) itemView.findViewById(R.id.phrase);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTittle_List);
            tvMeaning = (TextView) itemView.findViewById(R.id.tvMeanning_List);
            tvFavoriteCharacter = (TextView) itemView.findViewById(R.id.tvTittle_List_favorite);
            imageView = (ImageView) itemView.findViewById(R.id.favorite_icon);
            favorite_icon = (LinearLayout) itemView.findViewById(R.id.favorite_icon_layout);
            phrase_item = (RippleEffectDark) itemView.findViewById(R.id.card_view2);
            item_favorite_character = (CardView) itemView.findViewById(R.id.list_item_favorite_character);
            favorite_icon.setOnClickListener(this);
            phrase_item.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    if (rippleView == phrase_item)
                        mlisListener.onPhraseClick(getAdapterPosition());
                }
            });

        }


        @Override
        public void onClick(View v) {
            if (mlisListener != null) {
                if (v == favorite_icon)
                    mlisListener.onFavoriteChange(getAdapterPosition());


            }
        }
    }
}

