package com.coolweather.app.util;

/**
 * Created by apple on 16/1/7.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
