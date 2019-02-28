package com.wd.common.utils;

import android.content.Context;
import android.widget.Toast;


/**
 * Toast工具类
 */

public class ToastUtil {
    private static Toast toast;
    /**
     * 强大的吐司，能够连续弹的吐司
     * @param text
     */
    public static void showToast(Context context,String text){
        if(toast==null){
        //如果等于null，则创建
            toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
        }else {
            //如果不等于空，则直接将text设置给toast
            toast.setText(text);
        }
        toast.show();
    }
}
