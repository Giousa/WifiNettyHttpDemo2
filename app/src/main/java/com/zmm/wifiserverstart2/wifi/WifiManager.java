package com.zmm.wifiserverstart2.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/26
 * Time:上午10:33
 */

public class WifiManager implements WifiTools.WifiStartUpListener {

    private WifiTools sWifiTools;
    private WifiStartListener mWifiStartListener;

    public interface WifiStartListener{
        void wifiStartListener();
    }

    public void setWifiStartListener(WifiStartListener wifiStartListener) {
        mWifiStartListener = wifiStartListener;
    }

    public void start(Context context, String wifiName){

        sWifiTools = WifiTools.getInstance(context);
        sWifiTools.setWifiStartUpListener(this);
        WifiConfiguration wifiConfiguration;
        wifiConfiguration = sWifiTools.createWifiInfo(wifiName, "12345678");
        sWifiTools.createHotSpot(wifiConfiguration);

    }

    @Override
    public void startUpListener() {
        System.out.println("================");
        if(mWifiStartListener != null){
            mWifiStartListener.wifiStartListener();
        }
    }
}
