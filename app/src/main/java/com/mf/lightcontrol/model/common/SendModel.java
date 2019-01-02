package com.mf.lightcontrol.model.common;

/**
 * Created by LiMing
 * Date 2018/12/18
 */
public class SendModel {
    private int CommType;
    private String Request;

    public SendModel(int commType, String request) {
        CommType = commType;
        Request = request;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }
}
