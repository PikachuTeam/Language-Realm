package com.tatteam.languagerealm;

import android.text.TextUtils;

public class CampaignInfo {
    public String name;
    public String source;
    public String medium;

    public boolean isEmpty() {
        return TextUtils.isEmpty(name) && TextUtils.isEmpty(source) && TextUtils.isEmpty(medium);
    }

    @Override
    public String toString() {
        return "name: " + name + ", source: " + source + ", medium: " + medium;
    }
}
