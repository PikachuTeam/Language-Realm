package com.tatteam.languagerealm.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tatteam.languagerealm.R;

import java.util.ArrayList;


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
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCharacter;
        public CardView cvCharacter;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            cvCharacter = (CardView) itemView.findViewById(R.id.card_view);
            tvCharacter = (TextView) itemView.findViewById(R.id.tvCharacter);
        }

        public void setItem(String s) {
            tvCharacter.setText(s);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
//                v.setClickable(false);
                mListener.onLetterClick(getAdapterPosition());
            }
        }
    }

    public interface OnClickLetter {
        public void onLetterClick(int position);
    }

    public void setLetterClickListener(OnClickLetter mlisListener) {
        this.mListener = mlisListener;
    }
}