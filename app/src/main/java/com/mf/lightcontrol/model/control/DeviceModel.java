package com.mf.lightcontrol.model.control;

public class DeviceModel {
    private String name;
    private String ip;
    private String state;



    public DeviceModel(String name, String ip, String state) {
        this.name = name;
        this.ip = ip;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
