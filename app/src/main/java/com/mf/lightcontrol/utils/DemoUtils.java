package com.mf.lightcontrol.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.lm.lib_common.utils.ParseJsonUtils;
import com.mf.lightcontrol.model.common.DeviceMessageModel;
import com.mf.lightcontrol.model.common.ReceiverModel;
import com.mf.lightcontrol.model.common.SendModel;

/**
 * Created by LiMing
 * Date 2019/1/2
 */
public class DemoUtils {
    public static String packData() {
        return ParseJsonUtils.getjsonStr(new SendModel(0x1, "FIND=1"));
    }
    public static ReceiverModel parseDeviceUserData(byte[] data) {
        ReceiverModel bean = null;
        try {
            bean = ParseJsonUtils.getBean(bufferToStr(data), ReceiverModel.class);
        } catch (Exception ex) {

        }
        return bean;
    }
    public static DeviceMessageModel parseDeviceMessgaeData(byte[] data) {
        DeviceMessageModel bean = null;
        try {
            bean = ParseJsonUtils.getBean(bufferToStr(data), DeviceMessageModel.class);
        } catch (Exception ex) {

        }
        return bean;
    }




    public static String bufferToStr(byte[] buffer) {
        try {
            int length = 0;
            for (int i = 0; i < buffer.length; ++i) {
                if (buffer[i] == 0) {
                    length = i;
                    break;
                }
            }
            String str = new String(buffer, 0, length, "UTF-8");
            str = str.substring(0, str.indexOf("}") + 1);
            Log.e("mssg","截取后的消息"+str);
            return str;
        } catch (Exception e) {
            return "";
        }
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
