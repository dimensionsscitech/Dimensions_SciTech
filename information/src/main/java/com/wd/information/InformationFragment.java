package com.wd.information;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wd.common.baseFragment.BaseFragment;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :
 */

@Route(path = "/information/InformationFragment")
public class InformationFragment extends BaseFragment {
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
}
