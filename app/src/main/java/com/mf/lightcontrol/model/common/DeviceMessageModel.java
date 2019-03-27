package com.mf.lightcontrol.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiMing
 * Date 2019/1/15
 *  private List<List<Integer>> IRMapping;
 */
public class DeviceMessageModel implements Serializable {


    /**
     * RecCommType : 3
     * Signal : BRG
     * TransDirDetecColor : SolidColor
     * TransAdvanceShow : 3
     * DetecStopTime : 3
     * Mode : 2
     * Color : 16711680`
     * Lum : 50
     * Speed : 50
     * RunLenth : 15
     * IRMapping : []
     */

    private int RecCommType;
    private String Signal;
    private String TransDirDetecColor;
    private int TransAdvanceShow;
    private int DetecStopTime;
    private int Mode;
    private int Color;
    private int Lum;
    private int Speed;
    private int RunLenth;
    private List<List<Integer>> IRMapping;

    public int getRecCommType() {
        return RecCommType;
    }

    public void setRecCommType(int RecCommType) {
        this.RecCommType = RecCommType;
    }

    public String getSignal() {
        return Signal;
    }

    public void setSignal(String Signal) {
        this.Signal = Signal;
    }

    public String getTransDirDetecColor() {
        return TransDirDetecColor;
    }

    public void setTransDirDetecColor(String TransDirDetecColor) {
        this.TransDirDetecColor = TransDirDetecColor;
    }

    public int getTransAdvanceShow() {
        return TransAdvanceShow;
    }

    public void setTransAdvanceShow(int TransAdvanceShow) {
        this.TransAdvanceShow = TransAdvanceShow;
    }

    public int getDetecStopTime() {
        return DetecStopTime;
    }

    public void setDetecStopTime(int DetecStopTime) {
        this.DetecStopTime = DetecStopTime;
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
}
