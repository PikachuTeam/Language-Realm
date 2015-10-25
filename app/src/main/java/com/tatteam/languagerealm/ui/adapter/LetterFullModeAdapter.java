package com.tatteam.languagerealm.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.andexert.library.RippleView;
import com.tatteam.languagerealm.R;

import java.util.ArrayList;

import tatteam.com.app_common.ui.drawable.RippleEffectLight;


/**
 * Created by hoaba on 9/8/2015.
 */
public class LetterFullModeAdapter extends RecyclerView.Adapter<LetterFullModeAdapter.ViewHolder> {


    private ArrayList<String> listLetter = null;
    private OnClickLetter mListener;

    public LetterFullModeAdapter(ArrayList<String> listLetter) {
        super();
        this.listLetter = listLetter;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_letter_full_mode, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String character = listLetter.get(i).toUpperCase();
        viewHolder.tvCharacter.setText(character);
    }

    @Override
    public int getItemCount() {

        return listLetter.size();
    }

    ////////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvCharacter;
        public CardView cvCharacter;
        public RippleEffectLight item;
        public ViewHolder(View itemView) {
            super(itemView);

            cvCharacter = (CardView) itemView.findViewById(R.id.card_view);
            tvCharacter = (TextView) itemView.findViewById(R.id.tvCharacter);
            item = (RippleEffectLight) itemView.findViewById(R.id.grid);
            item.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    if (mListener != null) {
                        mListener.onLetterClick(getAdapterPosition());
                    }
                }
            });
        }

        public void setItem(String s) {
            tvCharacter.setText(s);

        }


    }

    public interface OnClickLetter {
        public void onLetterClick(int position);
    }

    public void setLetterClickListener(OnClickLetter mlisListener) {
        this.mListener = mlisListener;
    }
}