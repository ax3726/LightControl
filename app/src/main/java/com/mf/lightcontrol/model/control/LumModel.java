package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class LumModel {
    private int CommType;
    private int Lum;

    public LumModel(int Lum) {
        CommType = 2;
        this.Lum = Lum;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public int getLum() {
        return Lum;
    }

    public void setLum(int lum) {
        Lum = lum;
    }
}
