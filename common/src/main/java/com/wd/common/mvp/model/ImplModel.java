package com.wd.common.mvp.model;

import android.app.Application;
import android.content.Context;


import com.wd.common.mvp.callback.MyCallBack;

import java.util.Map;

public interface ImplModel {

    void ReguestGet(String url, Class aClass, MyCallBack myCallBack);
    void ReguestPost(String url, Map<String, String> map, Map<String, String> headerMap, Class aClass, MyCallBack myCallBack);
}
