package com.tatteam.languagerealm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.entity.NavEntity;


/**
 * Created by Shu on 10/9/2015.
 */
public class NavAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    NavEntity[] list;
    Context context;

    public NavAdapter(BaseActivity mainActivity, NavEntity[] list) {
        // TODO Auto-generated constructor stub
        this.list = list;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.length;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();

        View rowView;
        rowView = inflater.inflate(R.layout.item_navigation, null);
        holder.tv_tittle = (TextView) rowView.findViewById(R.id.tittle_nav);
        holder.iv_icon = (ImageView) rowView.findViewById(R.id.icon_nav);
        holder.bg_item = (LinearLayout) rowView.findViewById(R.id.bg_item);
        holder.line = (LinearLayout) rowView.findViewById(R.id.line);
        holder.tv_tittle.setText(context.getResources().getString(list[position].tittle_id));
        holder.iv_icon.setImageResource(list[position].icon_id);
        if (list[position].tittle_id == R.string.favorite || list[position].tittle_id == R.string.recent || list[position].tittle_id == R.string.more_app)
            holder.iv_icon.setColorFilter(context.getResources().getColor(R.color.colorPrimaryLight));
        if (list[position].selected) {
            holder.bg_item.setBackgroundColor(context.getResources().getColor(R.color.nav_selected));
            holder.tv_tittle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.bg_item.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tv_tittle.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        }
        if (list[position].tittle_id == R.string.more_app) holder.line.setVisibility(View.VISIBLE);
        return rowView;
    }

    public class Holder {
        LinearLayout bg_item;
        LinearLayout line;
        TextView tv_tittle;
        ImageView iv_icon;
    }

}
