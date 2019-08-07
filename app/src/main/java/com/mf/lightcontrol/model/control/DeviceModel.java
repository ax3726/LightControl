package com.mf.lightcontrol.model.control;

public class DeviceModel {
    private String name;
    private String ip;
    private String state;
    private String version;



    public DeviceModel(String name, String ip, String state, String version) {
        this.name = name;
        this.ip = ip;
        this.state = state;
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
