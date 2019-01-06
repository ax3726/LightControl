package com.mf.lightcontrol.model.control;

public class RedModel {
    private int CommType;
    private int SensorID ;
    private int SetType ;
    private int MappLignt ;

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public int getSensorID() {
        return SensorID;
    }

    public void setSensorID(int sensorID) {
        SensorID = sensorID;
    }

    public int getSetType() {
        return SetType;
    }

    public void setSetType(int setType) {
        SetType = setType;
    }

    public int getMappLignt() {
        return MappLignt;
    }

    public void setMappLignt(int mappLignt) {
        MappLignt = mappLignt;
    }
}
