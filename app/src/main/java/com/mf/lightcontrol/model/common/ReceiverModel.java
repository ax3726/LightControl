package com.mf.lightcontrol.model.common;

/**
 * Created by LiMing
 * Date 2018/12/18
 * 0—接收到手机设置灯带参数
 * 1--- 接收到搜素信息
 * 3---接收到设置红外传感器映射参数
 * 4---接收到设置长度参数
 */
public class ReceiverModel {
    private int RecCommType;
    private String Product;
    private String Ack;
    private String IPADD;
    private String MACADD;
    private String SN;
    private String TotalONOFFStatus;
    private String Ver;

    public String getVer() {
        return Ver;
    }

    public void setVer(String Ver) {
        this.Ver = Ver;
    }

    public String getTotalONOFFStatus() {
        return TotalONOFFStatus;
    }

    public void setTotalONOFFStatus(String totalONOFFStatus) {
        TotalONOFFStatus = totalONOFFStatus;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public int getRecCommType() {
        return RecCommType;
    }

    public void setRecCommType(int recCommType) {
        RecCommType = recCommType;
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
