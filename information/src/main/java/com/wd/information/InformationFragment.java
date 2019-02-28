package com.wd.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.stx.xhb.xbanner.XBanner;
import com.wd.common.baseFragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(Object s) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(getActivity(), R.layout.fragment_information, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
