package com.tatteam.languagerealm.app;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BasePhraseFragment;

/**
 * Created by ThanhNH on 9/11/2015.
 */
public abstract class BasePage {
    protected BasePhraseFragment fragment;
    protected BaseActivity activity;
    protected View content;

    protected abstract int getContentId();


    public BasePage(BasePhraseFragment fragment, BaseActivity activity) {
        this(activity, fragment, null);
    }

    public BasePage(BaseActivity activity, BasePhraseFragment fragment, ViewGroup parent) {
        this.fragment = fragment;

        this.activity = activity;
        if (parent != null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            content = inflater.inflate(getContentId(), parent, false);
        } else {
            content = View.inflate(activity, getContentId(), null);
        }

    }

    public View getContent() {
        return content;
    }

    public void destroy() {
    }

}
