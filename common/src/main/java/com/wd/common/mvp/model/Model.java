package com.wd.common.mvp.model;


import com.google.gson.Gson;
import com.wd.common.mvp.callback.MyCallBack;
import com.wd.common.netWork.RetrofitUtil;

import java.util.Map;

public class Model implements ImplModel{


    @Override
    public void ReguestGet(String url, final Class aClass, final MyCallBack myCallBack) {
        RetrofitUtil.getInstance().get(url, new RetrofitUtil.ICallBack() {
            @Override
            public void successData(String result) {
                try {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(result, aClass);
                    myCallBack.success(o);
                }catch (Exception e){
                    myCallBack.error(e.getMessage());
                }
            }
            @Override
            public void failureData(String error) {
                myCallBack.error(error);
            }
        });
    }

    @Override
    public void ReguestPost(String url, Map<String, String> map,Map<String, String> headerMap, final Class aClass,final MyCallBack myCallBack) {
            RetrofitUtil.getInstance().post(url, map, headerMap, new RetrofitUtil.ICallBack() {
                @Override
                public void successData(String result) {
                    try {
                        Gson gson = new Gson();
                        Object o = gson.fromJson(result, aClass);
                        myCallBack.success(o);
                    }catch (Exception e){
                        myCallBack.error(e.getMessage());
                    }
                }

                @Override
                public void failureData(String error) {
                    myCallBack.error(error);
                }
            });
    }
}
