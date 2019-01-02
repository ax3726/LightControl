package com.mf.lightcontrol.model.common;

/**
 * Created by LiMing
 * Date 2018/12/18
 */
public class ReceiverModel {
    private int CommType;
    private String Ack;
    private String IPADD;
    private String MACADD;
    private String SN;

    public ReceiverModel() {
        CommType = 2;
        Ack = "FIND=1";
        this.IPADD = "192.168.0.102";
        this.MACADD = "AA:BB:CC:DD:EE:FF";
        this.SN = "12345678";
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getAck() {
        return Ack;
    }

    public void setAck(String ack) {
        Ack = ack;
    }

    public String getIPADD() {
        return IPADD;
    }

    public void setIPADD(String IPADD) {
        this.IPADD = IPADD;
    }

    public String getMACADD() {
        return MACADD;
    }

    public void setMACADD(String MACADD) {
        this.MACADD = MACADD;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }
}
