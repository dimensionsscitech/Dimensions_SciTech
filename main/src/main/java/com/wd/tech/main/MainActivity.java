package com.wd.tech.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjm.bottomtabbar.BottomTabBar;
import com.wd.common.baseActivity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R2.id.bottom_tab_bar)
    BottomTabBar mBottomTabBar;
    @BindView(R2.id.layout_main)
    RelativeLayout layout_main;
    private Fragment mCommunityFragment;
    private Fragment mInformationFragment;
    private Fragment mMessageFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mInformationFragment = (Fragment) ARouter.getInstance().build("/information/InformationFragment").navigation();
        mMessageFragment = (Fragment) ARouter.getInstance().build("/message/MessageFragment").navigation();
        mCommunityFragment = (Fragment) ARouter.getInstance().build("/community/CommunityFragment").navigation();
    }

    @Override
    protected void initData() {

        //底部导航栏
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(70, 70)
                .setFontSize(14)
                .setChangeColor(Color.BLACK, Color.GRAY)
                //前面第一个是点击后的图片，第二个是点击前的图片
                .addTabItem("资讯", R.mipmap.common_tab_informatiion_s, R.mipmap.common_tab_information_n, mInformationFragment.getClass())
                .addTabItem("消息", R.mipmap.common_tab_message_s, R.mipmap.common_tab_message_n, mMessageFragment.getClass())
                .addTabItem("社区", R.mipmap.common_tab_community_s, R.mipmap.common_tab_community_n, mCommunityFragment.getClass())
                .isShowDivider(true);
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(Object s) {

    }
}
