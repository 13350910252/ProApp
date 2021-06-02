package com.yinp.proapp.module.wanandroid.web.retrofit;

import com.google.gson.JsonParseException;
import com.yinp.proapp.web.retrofit.BaseObserver;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

public abstract class WanObserver2<T> extends BaseObserver<T> {
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;

    @Override
    public void onNext(@NotNull T o) {
        try {
            WanData2 model = (WanData2) o;
            if (model.getErrorCode() == 0) {
                onSuccess(o);
            } else {
                onCodeFail("加载失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
        }

    }

    @Override
    public void onError(@NotNull Throwable e) {
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR);
        } else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError("未知错误");
            }
        }

    }

    private void onException(int unknownError) {
        switch (unknownError) {
            case CONNECT_ERROR:
                onError("连接错误");
                break;

            case CONNECT_TIMEOUT:
                onError("连接超时");
                break;

            case BAD_NETWORK:
                onError("网络问题");
                break;

            case PARSE_ERROR:
                onError("解析数据失败");
                break;

            default:
                break;
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T o);

    public abstract void onError(String msg);

    public abstract void onCodeFail(String msg);
}
