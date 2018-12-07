package com.lm.lib_common.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/21.
 */

public class BaseBean<T> implements Serializable {

    private String errorInfo;
    private int errorNo;
    private T data;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
