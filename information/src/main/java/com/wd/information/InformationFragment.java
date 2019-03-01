package com.wd.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.wd.common.baseFragment.BaseFragment;
import com.wd.common.utils.ToastUtil;
import com.wd.information.activity.FicationActivity;
import com.wd.information.apis.Apis;
import com.wd.information.apis.StringUntils;
import com.wd.information.bean.XBannerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wd.information.apis.StringUntils.object_error;
import static com.wd.information.apis.StringUntils.object_status;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :资讯模板
 */

@Route(path = "/information/InformationFragment")
public class InformationFragment extends BaseFragment {
    @BindView(R2.id.message_classification)
    Button mMessageClassification;
    @BindView(R2.id.message_search)
    Button mMessageSearch;
    @BindView(R2.id.message_xbanner)
    XBanner mMessageXbanner;
    @BindView(R2.id.message_xrecy)
    XRecyclerView mMessageXrecy;
    private View view;
    private Unbinder unbinder;



    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void initData() {
        unbinder = ButterKnife.bind(this, getActivity());

        //轮播图
        doGetData(Apis.information_XBanner,XBannerBean.class);
        //兴趣分类的点击事件
        mMessageClassification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFication();
            }
        });
        //XRecyclerVeiw
        initRecycler();

    }

    /**
     * XRecyclerView
     */
    private void initRecycler() {
        //设置可刷新
        mMessageXrecy.setPullRefreshEnabled(true);
        //设置可加载
        mMessageXrecy.setLoadingMoreEnabled(true);
        //设置起始展示为列表格式
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        mMessageXrecy.setLayoutManager(manager);
        //添加适配器

    }

    /**
     * 兴趣分类的点击事件
     */
    private void initFication() {
        Intent intent = new Intent(getActivity(), FicationActivity.class);
        startActivity(intent);
    }

    /**
     * 得到数据
     * @param object
     */
    @Override
    protected void netSuccess(Object object) {
        //轮播图
        if (object instanceof XBannerBean){
            final List<String> bannerList=new ArrayList<>();
            List<String> bannerTitle=new ArrayList<>();
            XBannerBean xBannerBean=(XBannerBean)object;
            //请求成功
            if (xBannerBean.getStatus().equals(object_status)){
                List<XBannerBean.ResultBean> result = xBannerBean.getResult();
                int size = result.size();
                for (int i = 0; i <size ; i++) {
                    bannerList.add(result.get(i).getImageUrl());
                    bannerTitle.add(result.get(i).getTitle());
                }
                //添加数据
              mMessageXbanner.setData(bannerList,bannerTitle);
              mMessageXbanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getActivity()).load(bannerList.get(position)).into((ImageView) view);
                    }
                });
              //左右间距
              mMessageXbanner.setViewPagerMargin(10);
                //横向移动
                mMessageXbanner.setPageTransformer(Transformer.Default);
                mMessageXbanner.setPageChangeDuration(0);

            }else {
                ToastUtil.showToast(getActivity(),object_error+xBannerBean.getMessage());
            }
        }
    }

    /**
     * 出错
     * @param s
     */
    @Override
    protected void netFailed(Object s) {
       ToastUtil.showToast(getActivity(),StringUntils.object_error+s);
    }

    @Override
    public void onResume() {
        super.onResume();
        unbinder = ButterKnife.bind(this, getActivity());
        mMessageXbanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMessageXbanner.stopAutoPlay();
        unbinder.unbind();
    }

}
