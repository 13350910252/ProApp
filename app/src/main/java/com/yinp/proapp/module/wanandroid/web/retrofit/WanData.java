package com.yinp.proapp.module.wanandroid.web.retrofit;

import com.google.gson.JsonElement;
import com.yinp.proapp.web.retrofit.BaseRetrofitData;

import java.io.Serializable;

public class WanData{
    private JsonElement data;
    private int errorCode;
    private String errorMsg;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
