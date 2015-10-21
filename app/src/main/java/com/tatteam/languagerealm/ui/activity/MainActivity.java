package com.tatteam.languagerealm.ui.activity;

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
}
