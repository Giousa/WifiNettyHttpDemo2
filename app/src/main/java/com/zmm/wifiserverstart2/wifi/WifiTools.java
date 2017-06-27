package com.zmm.wifiserverstart2.wifi;

import android.content.Context;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class WifiTools {

    private static WifiTools wifiTools = null;
    private static final String SETUP_WIFIAP_METHOD = "setWifiApEnabled";
    private static final String ACTIVITY_TAG = "WifiTools";
    private static final String DEFAULT_AP_PASSWORD = "12345678";


    //监听wifi热点的状态变化
    public static final int WIFI_AP_STATE_ENABLED = 13;


    public WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private TelephonyManager telephonyManager;

    private ConnectivityManager connectivityManager;
    private WifiStartUpListener mWifiStartUpListener;

    public interface WifiStartUpListener{
        void startUpListener();
    }

    public void setWifiStartUpListener(WifiStartUpListener wifiStartUpListener) {
        mWifiStartUpListener = wifiStartUpListener;
    }

    public static WifiTools getInstance(Context context) {

        if (wifiTools == null) {
            wifiTools = new WifiTools(context);

        }
        return wifiTools;
    }

    public WifiTools(Context context) {

        //get system Wifi service WIFI_SERVICE
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //get connection info
        this.wifiInfo = this.wifiManager.getConnectionInfo();
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }


    /**
     * 创建热点
     * @param wifiConfiguration wifi配置信息
     * @return
     */
    public boolean createHotSpot(WifiConfiguration wifiConfiguration) {
        try {
            closeWifi();
            closeHotSpot();
            Method setupMethod = wifiManager.getClass().getMethod(SETUP_WIFIAP_METHOD, WifiConfiguration.class, boolean.class);
            setupMethod.invoke(wifiManager, wifiConfiguration, true);

            Log.i(ACTIVITY_TAG, "热点创建成功");

            if(mWifiStartUpListener != null){
                mWifiStartUpListener.startUpListener();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭热点
     *
     * @return
     */
    public boolean closeHotSpot() {
        Log.i(ACTIVITY_TAG, "closeHotSpot---" + getWifiApState());

        while (getWifiApState() == WIFI_AP_STATE_ENABLED) {
            Log.i(ACTIVITY_TAG, "AP state---" + getWifiApState() + "");

            try {
                Method method1 = wifiManager.getClass().getMethod("setWifiApEnabled",
                        WifiConfiguration.class, boolean.class);
                method1.invoke(wifiManager, null, false);

            } catch (Exception e) {
                e.printStackTrace();
//                Log.e(TAG, e.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * 构造wifi信息
     *
     * @param ssid
     * @param password
     * @return
     */
    public WifiConfiguration createWifiInfo(String ssid, String password) {
        //配置网络信息类
        WifiConfiguration wifiConfiguration = new WifiConfiguration();

        Log.i(ACTIVITY_TAG, "创建热点配置信息");
        wifiConfiguration.preSharedKey = password;
        wifiConfiguration.SSID = ssid;

        //设置网络属性
//        wifiConfiguration.allowedAuthAlgorithms.clear();
//        wifiConfiguration.allowedGroupCiphers.clear();
//        wifiConfiguration.allowedKeyManagement.clear();
//        wifiConfiguration.allowedPairwiseCiphers.clear();
//        wifiConfiguration.allowedProtocols.clear();

        wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        wifiConfiguration.preSharedKey = password;

        return wifiConfiguration;
    }

    /**
     * 关闭 wifi
     *
     * @return
     */
    public boolean closeWifi() {
        if (wifiManager.isWifiEnabled()) { //the current wifi is enabled
            wifiManager.setWifiEnabled(false);
            Log.i(ACTIVITY_TAG, "wifi closed!");
            return true;
        } else {
            Log.i(ACTIVITY_TAG, "wifi had been closed!");
        }
        return false;
    }

    /**
     * 获取热点创建状态
     **/
    public int getWifiApState() {
        try {
            int i = ((Integer) this.wifiManager.getClass()
                    .getMethod("getWifiApState", new Class[0])
                    .invoke(this.wifiManager, new Object[0])).intValue();
            return i;
        } catch (Exception localException) {
        }
        return 4;   //未知wifi网卡状态
    }

}