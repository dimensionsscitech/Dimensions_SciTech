package com.wd.common.mvp.persenter;

import android.content.Context;

import java.util.Map;

public interface ImplPersenter {

    void showGetReguest(String url, Class aClass);
    void showPostReguest(String url, Map<String, String> map, Map<String, String> headMap, Class aClass);
}
