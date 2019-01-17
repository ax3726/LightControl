package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class ONOFFModel {
    private int CommType;
    private String ONOFF;

    public ONOFFModel(String ONOFF) {
        CommType = 2;
        ONOFF = ONOFF;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getONOFF() {
        return ONOFF;
    }

    public void setONOFF(String ONOFF) {
        this.ONOFF = ONOFF;
    }
}
