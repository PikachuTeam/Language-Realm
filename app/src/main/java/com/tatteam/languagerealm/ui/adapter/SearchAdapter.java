package com.tatteam.languagerealm.ui.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.entity.PhraseEntity;

import java.util.List;

/**
 * Created by Shu on 10/17/2015.
 */
public class SearchAdapter extends BaseAdapter {
    private List<PhraseEntity> list;
    private ClickListener mlisListener;

    private Context context;

    private static LayoutInflater inflater = null;

    public SearchAdapter(BaseActivity mainActivity, List<PhraseEntity> list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView tittle;
        ImageView icon;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.item_phrase_search, null);
        holder.tittle = (TextView) rowView.findViewById(R.id.tvTittleSearch);
        holder.icon = (ImageView) rowView.findViewById(R.id.icon_phrase);
        holder.tittle.setText(Html.fromHtml(list.get(position).phrase));

        switch (list.get(position).kind_ID) {
            case R.string.slang:
                holder.icon.setBackgroundResource(R.drawable.ic_slang);
                break;
            case R.string.idiom:
                holder.icon.setBackgroundResource(R.drawable.ic_idiom);
                break;
            case R.string.proverb:
                holder.icon.setBackgroundResource(R.drawable.ic_proverb);
                break;
        }


        return rowView;
    }

    public interface ClickListener {
        void onPhraseClick(int position);


    }

    public void setmLislistener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;
    }
}
