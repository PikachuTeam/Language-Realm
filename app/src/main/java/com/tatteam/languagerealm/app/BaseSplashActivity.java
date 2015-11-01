package com.tatteam.languagerealm.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.tatteam.languagerealm.R;
import com.tatteam.languagerealm.database.DataSource;
import com.tatteam.languagerealm.ui.activity.MainActivity;

import java.util.Locale;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppSpeaker;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public abstract class BaseSplashActivity extends AppCompatActivity {
    private static final long SPLASH_DURATION = 2000;
    private android.os.Handler handler;
    private boolean isDatabaseImported = false;
    private boolean isWaitingInitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.slang_PrimaryDark));
        }
        importDatabase();
        initAppCommon();

        //for text to speech
        initAppSpeaker();

        handler = new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (isDatabaseImported) {
                    isDatabaseImported = false;
                    switchToMainActivity();
                } else {
                    isWaitingInitData = true;
                }
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0, SPLASH_DURATION);

    }

    private void initAppCommon() {
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        AppCommon.getInstance().increaseLaunchTime();
        AppCommon.getInstance().syncAdsSmallBannerIfNeeded(AppConstant.AdsType.SMALL_BANNER_LANGUAGE_LEARNING);
    }

    private void initAppSpeaker() {
        AppSpeaker.getInstance().initIfNeeded(getApplicationContext(), getLocale());
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
    protected abstract Locale getLocale();
    private void importDatabase() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                DataSource.getInstance().createDatabaseIfNeed();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                isDatabaseImported = true;
                if (isWaitingInitData) {
                    switchToMainActivity();
                }
            }
        };
        task.execute();
    }

    private void switchToMainActivity() {
        startActivity(new Intent(BaseSplashActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}