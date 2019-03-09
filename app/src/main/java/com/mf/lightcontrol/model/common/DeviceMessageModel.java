package com.mf.lightcontrol.model.common;

import java.io.Serializable;
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
    private int RunLenth;
    private String TotalONOFFStatus;
    private String Signal;
    private String TransDirDetecColor;
    private int TransAdvanceShow;
    private int DetecStopTime;
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

    public String getTotalONOFFStatus() {
        return TotalONOFFStatus;
    }

    public void setTotalONOFFStatus(String totalONOFFStatus) {
        TotalONOFFStatus = totalONOFFStatus;
    }

    public String getSignal() {
        return Signal;
    }

    public void setSignal(String signal) {
        Signal = signal;
    }

    public String getTransDirDetecColor() {
        return TransDirDetecColor;
    }

    public void setTransDirDetecColor(String transDirDetecColor) {
        TransDirDetecColor = transDirDetecColor;
    }

    public int getTransAdvanceShow() {
        return TransAdvanceShow;
    }

    public void setTransAdvanceShow(int transAdvanceShow) {
        TransAdvanceShow = transAdvanceShow;
    }

    public int getDetecStopTime() {
        return DetecStopTime;
    }

    public void setDetecStopTime(int detecStopTime) {
        DetecStopTime = detecStopTime;
    }
}
