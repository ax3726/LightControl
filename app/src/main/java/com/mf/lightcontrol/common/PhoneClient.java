package com.mf.lightcontrol.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;


import com.lm.lib_common.utils.Utils;
import com.mf.lightcontrol.model.common.ReceiverModel;
import com.mf.lightcontrol.utils.DemoUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by LiMing
 * Date 2018/12/19
 */
public class PhoneClient {

    private static boolean isOpen = false;
    private Thread sendThread = null, receiveThread = null;
    DatagramSocket sock = null;
    InetAddress local = null;
    private DatagramPacket mSendPack;
    private int part = 1025;
    private UdpListener mUdpListener = null;
    private String mWifiAddress = "";

    public void setUdpListener(UdpListener mUdpListener) {
        this.mUdpListener = mUdpListener;
    }

    private static PhoneClient mPhoneClient = null;

    private PhoneClient() {

    }

    public static PhoneClient getIntance() {
        if (mPhoneClient == null) {
            mPhoneClient = new PhoneClient();
        }
        return mPhoneClient;
    }

    /**
     * 完成初始化
     *
     * @return
     */
    public synchronized void init(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return;
        }
        mWifiAddress = ip;
        try {
            sock = new DatagramSocket();
            sock.setBroadcast(true);

        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            // 换成服务器端IP
            local = InetAddress.getByName(mWifiAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            close();
        }


        receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receive();
            }
        });

        isOpen = true;
        receiveThread.start();

    }


    /**
     * 发送搜索请求，并能指定想要发现的是支持哪种功能
     */
    public synchronized void send(String str_data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isOpen) {
                    if (str_data == null) {
                        return;
                    }
                    mSendPack = new DatagramPacket(str_data.getBytes(), str_data.length(), local,
                            part);

                    try {
                        sock.send(mSendPack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

    }

    /**
     * 实现收到server返回设备信息，并解析数据
     */
    private void receive() {
        if (sock == null || sock.isClosed()) {
            return;
        }
        byte buf[] = new byte[1024];

        while (isOpen) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                Log.e("msg", "收到的消息" + new String(packet.getData(), "UTF-8"));
                ReceiverModel receiverModel = DemoUtils.parseDeviceUserData(packet.getData());
                if (receiverModel != null && mUdpListener != null) {
                    if (receiverModel.getCommType() == 0) {//设置参数应答
                        mUdpListener.onSetting();
                    } else if (receiverModel.getCommType() == 3) {//红外参数应答
                        mUdpListener.onRed();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    /**
     * 关闭搜索设备，释放资源等
     */
    public synchronized void close() {
        isOpen = false;
        if (sendThread != null) {
            sendThread.interrupt();
        }
        if (receiveThread != null) {
            receiveThread.interrupt();
        }
        if (sock != null) {

            sock.close();
            sock = null;

        }
    }

    public interface UdpListener {
        void onSetting();

        void onRed();
    }
}
