package com.wd.tech;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Environment;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.wd.common.utils.ContextUtils;

/**
 * @author : FangShiKang
 * @date : 2019/02/27.
 * email : fangshikang@outlook.com
 * desc :   1、全局的屏幕适配 , 沉浸式状态栏
 *          2、ARouter  路由调用
 */
public class DimensionsSciTechApplication extends Application {

    //绘制页面时参照的设计图宽度
    public final static float DESIGN_WIDTH = 750;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        ContextUtils.setApplication(this);
        //Fresco:
        //设置磁盘缓存（可以不要）
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                //设置缓存的目录名字
                .setBaseDirectoryName("images")
                //设置缓存的路径
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置，生成配置未见
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

//必须！！！！
        Fresco.initialize(this, config);

    }

    //全局屏幕适配
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();//这个方法重写也是很有必要的
    }

    public void resetDensity() {
        Point size = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }

}
