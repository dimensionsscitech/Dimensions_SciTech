package com.wd.common.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 自定义拦截器
 */
public class CustomIntercept implements Interceptor {

    private Context mContext;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");
        String sessionId = sharedPreferences.getString("sessionId", "");

        Request.Builder newBuilder = original.newBuilder();
        newBuilder.method(original.method(), original.body());
        newBuilder.addHeader("ak","0110010010000");
        newBuilder.addHeader("Content-Type","application/x-www-form-urlencoded");
        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)) {
            newBuilder.addHeader("userId", userId);
            newBuilder.addHeader("sessionId", sessionId);
        }
        Request request = newBuilder.build();
        return chain.proceed(request);
    }
}
