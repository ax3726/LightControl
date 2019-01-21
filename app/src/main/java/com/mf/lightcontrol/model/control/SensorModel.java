package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2018/12/20
 */
public class SensorModel {
    int position=1;
    int id=0;

    public SensorModel(int id) {
        this.id = id;
    }

    public SensorModel(int position, int id) {
        this.position = position;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
