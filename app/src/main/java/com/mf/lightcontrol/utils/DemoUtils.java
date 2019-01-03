package com.mf.lightcontrol.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.lm.lib_common.utils.ParseJsonUtils;
import com.mf.lightcontrol.model.common.ReceiverModel;

/**
 * Created by LiMing
 * Date 2019/1/2
 */
public class DemoUtils {

    public static ReceiverModel parseDeviceUserData(byte[] data) {

        ReceiverModel bean = null;
        try {
            String str = new String(data, "UTF-8");
            bean = ParseJsonUtils.getBean(str, ReceiverModel.class);
        } catch (Exception ex) {

        }
        return bean;

    }

    /**
     * 获取wifiI地址
     *
     * @param context
     * @return
     */
    public static String getWifiIpAddress(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

}
