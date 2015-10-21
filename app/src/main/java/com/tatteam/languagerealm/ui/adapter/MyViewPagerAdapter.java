package com.tatteam.languagerealm.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BasePage;
import com.tatteam.languagerealm.app.BasePhraseFragment;
import com.tatteam.languagerealm.ui.module.phrasemodule.PhraseFullModePage;
import com.tatteam.languagerealm.ui.module.phrasemodule.PhraseQuickModePage;


public  class MyViewPagerAdapter extends PagerAdapter {
    private RecyclerView rvPhrase, rvLetter;
    private PhraseInQuickModeAdapter adapterPhrase;
    private RecyclerView.LayoutManager lmPhrase, lmLetter;
    private BasePhraseFragment fragment;
    private BaseActivity activity;

    public BasePage[] myPages;

    public MyViewPagerAdapter(BaseActivity activity, BasePhraseFragment fragment) {
        this.activity = activity;
        this.fragment =fragment;
        myPages = new BasePage[]{new PhraseQuickModePage(activity,fragment), new PhraseFullModePage(activity,fragment)};
    }

    @Override
    public int getCount() {
        return myPages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout = myPages[position].getContent();
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        BasePage page = myPages[position];
        page.destroy();
    }




}