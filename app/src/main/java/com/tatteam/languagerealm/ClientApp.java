package com.tatteam.languagerealm;

import android.app.Application;

import com.tatteam.languagerealm.database.DataSource;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.util.AppSpeaker;

/**
 * Created by ThanhNH-Mac on 10/23/15.
 */
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        DatabaseLoader.getInstance().restoreState(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
