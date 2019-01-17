package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/17
 */
public class SpeedModel {
    private int CommType;
    private int Speed;

    public SpeedModel(int Speed) {
        CommType = 2;
        this.Speed = Speed;
    }

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }
}
