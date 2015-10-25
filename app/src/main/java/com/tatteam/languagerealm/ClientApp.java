package com.tatteam.languagerealm;

import android.app.Application;

import com.tatteam.languagerealm.database.DataSource;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.util.AppSpeaker;

/**
 * Created by ThanhNH-Mac on 10/23/15.
 */
public class ClientApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataSource.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        AppCommon.getInstance().destroy();
        AppSpeaker.getInstance().destroy();
        super.onTerminate();
    }
}
