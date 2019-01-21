package com.mf.lightcontrol.model.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiMing
 * Date 2019/1/15
 */
public class DeviceMessageModel implements Serializable {

    /**
     * RecCommType : 3
     * ONOFFStatus : Power
     * Mode : 4
     * Color : 16711680
     * Lum : 50
     * Speed : 50
     * AtuoOffTime : 0
     * TotalLenth : 1024
     * RunLenth : 1024
     * IRMapping : [[0,10],[1,20],[3,35],[4,40]]
     */

    private int RecCommType;
    private String ONOFFStatus;
    private int Mode;
    private int Color;
    private int Lum;
    private int Speed;
    private int AtuoOffTime;
    private int TotalLenth;
    private int RunLenth;
    private List<List<Integer>> IRMapping;

    public int getRecCommType() {
        return RecCommType;
    }

    public void setRecCommType(int RecCommType) {
        this.RecCommType = RecCommType;
    }

    public String getONOFFStatus() {
        return ONOFFStatus;
    }

    public void setONOFFStatus(String ONOFFStatus) {
        this.ONOFFStatus = ONOFFStatus;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int Mode) {
        this.Mode = Mode;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int Color) {
        this.Color = Color;
    }

    public int getLum() {
        return Lum;
    }

    public void setLum(int Lum) {
        this.Lum = Lum;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int Speed) {
        this.Speed = Speed;
    }

    public int getAtuoOffTime() {
        return AtuoOffTime;
    }

    public void setAtuoOffTime(int AtuoOffTime) {
        this.AtuoOffTime = AtuoOffTime;
    }

    public int getTotalLenth() {
        return TotalLenth;
    }

    public void setTotalLenth(int TotalLenth) {
        this.TotalLenth = TotalLenth;
    }

    public int getRunLenth() {
        return RunLenth;
    }

    public void setRunLenth(int RunLenth) {
        this.RunLenth = RunLenth;
    }

    public List<List<Integer>> getIRMapping() {
        return IRMapping;
    }

    public void setIRMapping(List<List<Integer>> IRMapping) {
        this.IRMapping = IRMapping;
    }


}
