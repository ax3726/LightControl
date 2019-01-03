package com.mf.lightcontrol.model.control;

/**
 * Created by LiMing
 * Date 2019/1/3
 */
public class SubmitModel {
    private int CommType;
    private int Mode;//模式
    private String Color;//颜色
    private int Lum;//亮度
    private int Speed;//速度
    private String Product;
    private int AtuoOffTime;//自动关闭时间范围
    private int TotalLenth;//灯珠总个数
    private int RunlLenth;//运行的灯珠总个数

    public int getCommType() {
        return CommType;
    }

    public void setCommType(int commType) {
        CommType = commType;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getLum() {
        return Lum;
    }

    public void setLum(int lum) {
        Lum = lum;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public int getAtuoOffTime() {
        return AtuoOffTime;
    }

    public void setAtuoOffTime(int atuoOffTime) {
        AtuoOffTime = atuoOffTime;
    }

    public int getTotalLenth() {
        return TotalLenth;
    }

    public void setTotalLenth(int totalLenth) {
        TotalLenth = totalLenth;
    }

    public int getRunlLenth() {
        return RunlLenth;
    }

    public void setRunlLenth(int runlLenth) {
        RunlLenth = runlLenth;
    }
}
