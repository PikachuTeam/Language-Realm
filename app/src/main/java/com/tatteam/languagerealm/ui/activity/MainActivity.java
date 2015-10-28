package com.tatteam.languagerealm.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.tatteam.languagerealm.app.BaseActivity;
import com.tatteam.languagerealm.app.BasePhraseFragment;
import com.tatteam.languagerealm.ui.module.phrasemodule.phrase.SlangFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected BasePhraseFragment getFragmentContent() {
        return new SlangFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void addFragmentContent() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() == 0) {
            super.addFragmentContent();
        } else {
            manager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
