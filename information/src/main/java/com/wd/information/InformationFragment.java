package com.wd.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.wd.information.apis.Apis;
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
 * desc :
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
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
            //轮播图
        doGetData(Apis.information_XBanner,XBannerBean.class);
    }

    @Override
    protected void netSuccess(Object object) {
        //轮播图
        if (object instanceof XBannerBean){
            final List<String> bannerList=new ArrayList<>();
            List<String> bannerTitle=new ArrayList<>();
            XBannerBean xBannerBean=(XBannerBean)object;
            Log.i("TAG","玩儿法规和刚发的"+xBannerBean);
            //请求成功
            if (xBannerBean.getStatus().equals(object_status)){
                Log.i("TAG","电饭煲");
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
                mMessageXbanner.setPageTransformer(Transformer.Zoom);  //本页刚左移，下页就在后面
                mMessageXbanner.setPageChangeDuration(0);

            }else {
                ToastUtil.showToast(getActivity(),object_error+xBannerBean.getMessage());
            }
        }
    }

    @Override
    protected void netFailed(Object s) {
        ToastUtil.showToast(getActivity(),object_error+s);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
