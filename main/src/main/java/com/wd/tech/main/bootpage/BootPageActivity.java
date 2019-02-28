package com.wd.tech.main.bootpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wd.common.baseActivity.BaseActivity;
import com.wd.common.utils.IntentUtils;
import com.wd.tech.main.MainActivity;
import com.wd.tech.main.R;

/**
 * 启动页
 */
public class BootPageActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_boot_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        /**
         * 通过handler进行延时跳转
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //进行跳转操作
                Intent intent = new Intent(BootPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000); //在欢迎界面停留2秒钟跳转主页面中
    }

    @Override
    protected void netSuccess(Object object) {

    }

    @Override
    protected void netFailed(Object s) {

    }
}
