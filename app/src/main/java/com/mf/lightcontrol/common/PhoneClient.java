package com.mf.lightcontrol.common;

import android.text.TextUtils;
import android.util.Log;

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
    private static boolean isSearch = false;
    private Thread sendThread = null, receiveThread = null;
    private DatagramSocket sock = null;
    private InetAddress local = null, mSearchlocal = null;
    private DatagramPacket mSendPack;
    private int part = 1025;
    private int seq = 0;
    private UdpListener mUdpListener = null;
    private SearchListener mSearchListener = null;
    private static int REQUEST_INTERVEL_TIME = 3 * 1000;//5s

    public void setUdpListener(UdpListener mUdpListener) {
        this.mUdpListener = mUdpListener;
    }

    public void setSearchListener(SearchListener mSearchListener) {
        this.mSearchListener = mSearchListener;
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
    public synchronized void init() {

        try {
            sock = new DatagramSocket(1026);
            sock.setBroadcast(true);

        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            // 换成广播IP
            mSearchlocal = InetAddress.getByName("255.255.255.255");
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
     * 设置模块的IP地址
     *
     * @param ip
     */
    public void setSendIP(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return;
        }
        try {
            // 换成服务器端IP
            local = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            close();
        }
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
                    Log.e("msg", "发送的消息" + str_data);
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
     * 发送搜索请求，
     */
    public void sendSearch() {
        while (isSearch) {
            String messageByte = DemoUtils.packData();
            if (messageByte == null) {
                return;
            }
            mSendPack = new DatagramPacket(messageByte.getBytes(), messageByte.length(), mSearchlocal,
                    part);

            try {
                sock.send(mSendPack);
                Log.e("msg", "发送的消息" + seq + messageByte.toString());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            try {
                Thread.sleep(REQUEST_INTERVEL_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seq++;
        }


    }

    /**
     * 实现收到server返回设备信息，并解析数据
     */
    private void receive() {
        if (sock == null || sock.isClosed()) {
            return;
        }
        byte buf[] = new byte[512];

        while (isOpen) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                sock.receive(packet);
                Log.e("msg", "收到的消息" + new String(packet.getData(), "UTF-8"));

                ReceiverModel receiverModel = DemoUtils.parseDeviceUserData(packet.getData());
                if (receiverModel != null) {
                    if (receiverModel.getRecCommType() == 0) {//设置参数应答
                        if (mUdpListener != null)
                            mUdpListener.onSetting();
                    } else if (receiverModel.getRecCommType() == 1) {//搜索设备应答
                        if (mSearchListener != null)
                            mSearchListener.onDevice(packet.getAddress().getHostAddress());
                    } else if (receiverModel.getRecCommType() == 3) {//设置红外传感器映射参数
                        if (mUdpListener != null)
                            mUdpListener.onRed();
                    } else if (receiverModel.getRecCommType() == 4) {//设置长度参数
                        if (mUdpListener != null)
                            mUdpListener.onLenth();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public synchronized void startSearch() {
        isSearch = true;
        sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sendSearch();
            }
        });
        sendThread.start();
    }

    public synchronized void stopSearch() {
        isSearch = false;
        if (sendThread != null) {
            sendThread.interrupt();
        }
    }

    /**
     * 关闭搜索设备，释放资源等
     */
    public synchronized void close() {
        isOpen = false;
        isSearch = false;
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

        void onLenth();
    }

    public interface SearchListener {
        void onDevice(String ip);
    }
}
