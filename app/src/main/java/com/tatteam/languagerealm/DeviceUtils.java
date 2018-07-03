package com.tatteam.languagerealm;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceUtils {
    private String mDeviceId;
    private int mScreenWidth;
    private int mScreenHeight;

    private static DeviceUtils sInstance;

    private DeviceUtils() {
    }

    public static DeviceUtils getInstance() {
        if (sInstance == null) {
            synchronized (DeviceUtils.class) {
                if (sInstance == null) {
                    sInstance = new DeviceUtils();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        return mScreenWidth;
    }

    public int getScreenHeight() {
        return mScreenHeight;
    }

    public String getDeviceId(Context context) {
        if (!TextUtils.isEmpty(mDeviceId))
            return mDeviceId;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                mDeviceId = telephonyManager.getImei();
            else
                mDeviceId = telephonyManager.getDeviceId();
        }

        if (!TextUtils.isEmpty(mDeviceId))
            return mDeviceId;

        mDeviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (TextUtils.isEmpty(mDeviceId))
            mDeviceId = "00000000";
        return mDeviceId;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }

    public static boolean isAppRunning(Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
