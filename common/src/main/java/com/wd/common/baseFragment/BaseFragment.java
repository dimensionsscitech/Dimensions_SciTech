package com.wd.common.baseFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wd.common.R;
import com.wd.common.mvp.persenter.IPersenter;
import com.wd.common.mvp.view.IView;
import com.wd.common.utils.NetWorkUtils;
import com.wd.common.utils.ToastUtil;

import java.util.Map;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :
 */
public abstract class BaseFragment extends Fragment implements IView {

    private IPersenter mMyPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=View.inflate(getActivity(), R.layout.fragment_base,null);
        //获取的是子Fragment的layout
        View view1=View.inflate(getActivity(),getLayoutId(),null);
        RelativeLayout mLayout=(RelativeLayout) view.findViewById(R.id.fragment_layout);
        initView(view);
        mLayout.addView(view1);
        mMyPresenter = new IPersenter(this);
        return view;
    }


    //子Fragment实现的获取Layout
    public abstract int getLayoutId();

    //初始化控件
    protected abstract void initView(View view);

    //实现方法,初始化数据
    public abstract void initData();

    //成功
    protected abstract void netSuccess(Object object);
    //失败
    protected abstract void netFailed(Object s);


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    //访问接口成功
    @Override
    public void successRequest(Object data) {
        if (!NetWorkUtils.hasNetwork(getActivity())){
            ToastUtil.showToast(getActivity(),"无可用网络，请检查网络是否连接");
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mMyPresenter != null) {
            mMyPresenter.onDostry();
        }
    }
}
