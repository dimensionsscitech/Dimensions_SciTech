package com.wd.message;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wd.common.baseFragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :
 */

@Route(path = "/message/MessageFragment")
public class MessageFragment extends BaseFragment {


    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
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
