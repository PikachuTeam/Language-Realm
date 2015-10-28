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


public class MyViewPagerAdapter extends PagerAdapter {
    private RecyclerView rvPhrase, rvLetter;
    private PhraseInQuickModeAdapter adapterPhrase;
    private RecyclerView.LayoutManager lmPhrase, lmLetter;
    private BasePhraseFragment fragment;
    private BaseActivity activity;
    private PhraseQuickModePage phraseQuickModePage;
    private PhraseFullModePage phraseFullModePage;

    public MyViewPagerAdapter(BaseActivity activity, BasePhraseFragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View layout = null;
        if (position == 0) {
            if (phraseQuickModePage == null)
                phraseQuickModePage = new PhraseQuickModePage(activity, fragment);
            layout = phraseQuickModePage.getContent();
        } else {
            if (phraseFullModePage == null)
                phraseFullModePage = new PhraseFullModePage(activity, fragment);
            layout = phraseFullModePage.getContent();
        }
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        if (position == 0) {
            phraseQuickModePage.destroy();
        } else {
            phraseFullModePage.destroy();
        }
    }


}