package com.wd.common.utils;

import android.app.Application;
import android.content.Context;

public class ContextUtils {

    private static Context mContext;

    public static void setApplication(Application context) {
        mContext = context.getApplicationContext();
    }

    public static Context getApplication() {
        return mContext;
    }
}
