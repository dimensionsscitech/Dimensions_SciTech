package com.wd.common.mvp.persenter;


import com.wd.common.mvp.callback.MyCallBack;
import com.wd.common.mvp.model.Model;
import com.wd.common.mvp.view.IView;

import java.util.Map;

public class IPersenter implements ImplPersenter{

    Model IModel;
    IView Iview;

    public IPersenter(IView iView){
        Iview = iView;
        IModel = new Model();
    }

    @Override
    public void showGetReguest(String url, Class aClass) {
        IModel.ReguestGet(url, aClass, new MyCallBack() {
            @Override
            public void success(Object o) {
                Iview.successRequest(o);
            }

            @Override
            public void error(Object error) {
                Iview.errorRequest(error);
            }
        });
    }

    @Override
    public void showPostReguest(String url, Map<String, String> map, Map<String, String> headMap, Class aClass) {
        IModel.ReguestPost(url, map, headMap, aClass, new MyCallBack() {
            @Override
            public void success(Object o) {

            }

            @Override
            public void error(Object error) {

            }
        });
    }

    //解决内存泄漏
    public void onDostry(){
        if(IModel!=null){
            IModel=null;
        }
        if(Iview!=null){
            Iview=null;
        }
    }
}
