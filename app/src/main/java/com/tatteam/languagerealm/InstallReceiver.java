package com.tatteam.languagerealm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        if (!TextUtils.isEmpty(referrer)) {
            CampaignInfo campaignInfo = new CampaignInfo();

            String[] data = referrer.split("%26");
            if (data.length > 1) {
                for (String aData : data) {
                    String[] items = aData.split("%3D");
                    if (items.length == 2) {
                        if (items[0].equals("utm_source")) {
                            campaignInfo.source = items[1];
                        } else if (items[0].equals("utm_medium")) {
                            campaignInfo.medium = items[1];
                        } else if (items[0].equals("utm_campaign")) {
                            campaignInfo.name = items[1];
                        }
                    }
                }
            } else {
                data = referrer.split("&");
                if (data.length > 1) {
                    for (String aData : data) {
                        String[] items = aData.split("=");
                        if (items.length == 2) {
                            if (items[0].equals("utm_source")) {
                                campaignInfo.source = items[1];
                            } else if (items[0].equals("utm_medium")) {
                                campaignInfo.medium = items[1];
                            } else if (items[0].equals("utm_campaign")) {
                                campaignInfo.name = items[1];
                            }
                        }
                    }
                }
            }

            if (!campaignInfo.isEmpty()) {
                String deviceId = DeviceUtils.getInstance().getDeviceId(context);

                Calendar now = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");
                String dateTime = simpleDateFormat.format(now.getTime());

                HashMap<String, Object> toPushData = new HashMap<>();
                toPushData.put("/" + deviceId + "/" + dateTime, campaignInfo.toString());

                FirebaseDatabase.getInstance().getReference().updateChildren(toPushData);
            }
        }
    }
}
