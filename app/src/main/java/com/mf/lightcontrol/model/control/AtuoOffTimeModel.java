package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class AtuoOffTimeModel {
    private int CommType;
    private String AtuoOffTime;

    public AtuoOffTimeModel(String AtuoOffTime) {
        CommType = 2;
        AtuoOffTime = AtuoOffTime;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getAtuoOffTime() {
        return AtuoOffTime;
    }

    public void setAtuoOffTime(String atuoOffTime) {
        AtuoOffTime = atuoOffTime;
    }
}
