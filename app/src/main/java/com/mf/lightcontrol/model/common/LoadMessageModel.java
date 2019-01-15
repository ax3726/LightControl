package com.mf.lightcontrol.model.common;

/**
 * Created by LiMing
 * Date 2019/1/15
 */
public class LoadMessageModel {
    private int CommType;
    private String Request;

    public LoadMessageModel() {
        CommType = 3;
        Request = "Get Parameters";
    }
}
