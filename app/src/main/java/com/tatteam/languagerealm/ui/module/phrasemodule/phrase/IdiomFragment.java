package com.tatteam.languagerealm.ui.module.phrasemodule.phrase;

import android.os.Bundle;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.app.BasePhraseFragment;


/**
 * Created by Shu on 10/5/2015.
 */
public class IdiomFragment extends BasePhraseFragment {

    private int THEME_ID = R.style.IdiomTheme;
    private int FRAGMENT_NAME_ID = R.string.idiom;
    private int STATUS_BAR_COLOR_ID = R.color.idiom_PrimaryDark;
    private int BANNER_ID = R.drawable.idiom;

    private String SQL_TABLE_NAME = "idioms";

    @Override
    protected boolean isPhraseFragment() {
        return true;
    }

    @Override
    protected String getSqlTableName() {
        return SQL_TABLE_NAME;
    }

    @Override
    protected int getBannerID() {
        return BANNER_ID;
    }

    @Override
    protected int getThemeID() {
        return THEME_ID;
    }

    @Override
    protected int getFragmentNameID() {
        return FRAGMENT_NAME_ID;
    }

    @Override
    protected int getStatusBarColor() {
        return STATUS_BAR_COLOR_ID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNavHeaderColor(STATUS_BAR_ID);

    }
}
