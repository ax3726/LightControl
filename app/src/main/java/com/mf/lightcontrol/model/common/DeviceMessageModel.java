package com.mf.lightcontrol.model.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiMing
 * Date 2019/1/15
 */
public class DeviceMessageModel implements Parcelable {
    private int RecCommType;
    private int Mode;
    private String ONOFFStatus;
    private String Color;
    private int Lum;
    private int Speed;
    private int AtuoOffTime;
    private int TotalLenth;
    private int RunLenth;

    public int getRecCommType() {
        return RecCommType;
    }

    public void setRecCommType(int recCommType) {
        RecCommType = recCommType;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public String getONOFFStatus() {
        return ONOFFStatus;
    }

    public void setONOFFStatus(String ONOFFStatus) {
        this.ONOFFStatus = ONOFFStatus;
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

    public int getRunLenth() {
        return RunLenth;
    }

    public void setRunLenth(int runLenth) {
        RunLenth = runLenth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.RecCommType);
        dest.writeInt(this.Mode);
        dest.writeString(this.ONOFFStatus);
        dest.writeString(this.Color);
        dest.writeInt(this.Lum);
        dest.writeInt(this.Speed);
        dest.writeInt(this.AtuoOffTime);
        dest.writeInt(this.TotalLenth);
        dest.writeInt(this.RunLenth);
    }

    public DeviceMessageModel() {
    }

    protected DeviceMessageModel(Parcel in) {
        this.RecCommType = in.readInt();
        this.Mode = in.readInt();
        this.ONOFFStatus = in.readString();
        this.Color = in.readString();
        this.Lum = in.readInt();
        this.Speed = in.readInt();
        this.AtuoOffTime = in.readInt();
        this.TotalLenth = in.readInt();
        this.RunLenth = in.readInt();
    }

    public static final Parcelable.Creator<DeviceMessageModel> CREATOR = new Parcelable.Creator<DeviceMessageModel>() {
        @Override
        public DeviceMessageModel createFromParcel(Parcel source) {
            return new DeviceMessageModel(source);
        }

        @Override
        public DeviceMessageModel[] newArray(int size) {
            return new DeviceMessageModel[size];
        }
    };
}
