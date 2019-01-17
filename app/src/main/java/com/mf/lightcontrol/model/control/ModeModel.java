package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class ModeModel {
    private int CommType;
    private String Mode;

    public ModeModel( ) {
        CommType = 2;

    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }
}
