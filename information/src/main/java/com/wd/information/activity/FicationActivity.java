package com.wd.information.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wd.common.baseActivity.BaseActivity;
import com.wd.common.utils.ToastUtil;
import com.wd.information.R;
import com.wd.information.R2;
import com.wd.information.activity.adapter.FicationAdapter;
import com.wd.information.activity.bean.FicationBean;
import com.wd.information.apis.Apis;
import com.wd.information.apis.StringUntils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lenovo
 * @date : 2019/02/28.
 * desc : 兴趣分类
 */
public class FicationActivity extends BaseActivity {

    @BindView(R2.id.ficationRecycler)
    XRecyclerView mFicationRecycler;
    private FicationAdapter mFicationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fication;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFicationAdapter = new FicationAdapter(this);
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        initRecycler();

    }

    /**
     * get请求，XRecyclerView
     */
    private void initRecycler() {
        //设置可刷新
        mFicationRecycler.setPullRefreshEnabled(false);
        //设置可加载
        mFicationRecycler.setLoadingMoreEnabled(false);
        //设置起始展示为列表格式
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        mFicationRecycler.setLayoutManager(manager);
        //添加适配器
        mFicationRecycler.setAdapter(mFicationAdapter);
        mFicationRecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //数据加载
                initRecyclerData();
            }

            @Override
            public void onLoadMore() {
                initRecyclerData();
            }
        });
        initRecyclerData();

    }

    /**
     * XRecyclerView网络数据请求加载
     */
    private void initRecyclerData() {
            doGetData(Apis.fication_xrecycler,FicationBean.class);
    }

    /**
     * 获取数据成功
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {
            if (object instanceof FicationBean){
                FicationBean ficationBean=(FicationBean)object;
                //判断网络结果
                if (ficationBean.getStatus().equals(StringUntils.object_status)){
                    List<FicationBean.ResultBean> result = ficationBean.getResult();
                    mFicationAdapter.setMjihe(result);
                }else {
                    ToastUtil.showToast(this,StringUntils.object_error+ficationBean.getMessage());
                }
            }
    }

    /**
     *获取数据失败
     * @param s
     */
    @Override
    protected void netFailed(Object s) {
        ToastUtil.showToast(this,StringUntils.object_error+s);
    }



}

