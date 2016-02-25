package com.tatteam.languagerealm.app;

import android.content.Intent;
import android.os.Build;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.ui.activity.MainActivity;

import java.util.Locale;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppSpeaker;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public abstract class BaseSplashActivity extends tatteam.com.app_common.ui.activity.BaseSplashActivity {
    protected abstract Locale getLocale();

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.slang_PrimaryDark));
        }
    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        AppCommon.getInstance().increaseLaunchTime();
        AppCommon.getInstance().syncAdsIfNeeded(AppConstant.AdsType.SMALL_BANNER_LANGUAGE_LEARNING, AppConstant.AdsType.BIG_BANNER_LANGUAGE_LEARNING);
        AppSpeaker.getInstance().initIfNeeded(getApplicationContext(), getLocale());

        DatabaseLoader.getInstance().createIfNeeded(getApplicationContext(), "phrase.db");
    }

    @Override
    protected void onFinishInitAppCommon() {
        startActivity(new Intent(BaseSplashActivity.this, MainActivity.class));
        this.finish();
    }
}