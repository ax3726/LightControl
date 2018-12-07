package com.lm.lib_common.model;

import java.io.Serializable;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class ResultResponse implements Serializable {

    private String errorInfo;
    private int errorNo;

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
}
