package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/15
 */
public class ControlModel {
    private int CommType;
    private String para;
    private String data;


    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
