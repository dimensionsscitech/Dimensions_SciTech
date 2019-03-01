package com.wd.information;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @author: pengbo
 * @date:2019/2/28 desc:全局
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
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
}
