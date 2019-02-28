package com.wd.common.baseActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wd.common.mvp.persenter.IPersenter;
import com.wd.common.mvp.view.IView;
import com.wd.common.utils.NetWorkUtils;
import com.wd.common.utils.ToastUtil;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :   activity  基类抽取
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private IPersenter mMyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
        mMyPresenter = new IPersenter(this);
        initData();
    }

    //子类向父传递的layout
    protected abstract int getLayoutId();

    //初始化控件
    protected abstract void initView(Bundle savedInstanceState);

    //初始化数据
    protected abstract void initData();

    //成功
    protected abstract void netSuccess(Object object);
    //失败
    protected abstract void netFailed(Object s);

    //访问接口成功
    @Override
    public void successRequest(Object data) {
        if (!NetWorkUtils.hasNetwork(this)){
            ToastUtil.showToast(this,"无可用网络，请检查网络是否连接");
        }else {
            netSuccess(data);
        }
    }

    //访问接口失败
    @Override
    public void errorRequest(Object error) {
        netFailed(error);
    }

    /**
     * presenter层回调方法Post请求
     * @param url
     * @param map
     * @param clazz
     */
    protected void doPost(String url, Map<String, String> map, Map<String, String> headerMap, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.showPostReguest(url, map,headerMap, clazz);
        }
    }

    /**
     * presenter层回调方法Get请求
     * @param url
     * @param clazz
     */
    protected void doGetData(String url, Class clazz) {
        if (mMyPresenter != null) {
            mMyPresenter.showGetReguest(url, clazz);
        }
    }

    //解绑   防止内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyPresenter != null) {
            mMyPresenter.onDostry();
        }
    }

}
