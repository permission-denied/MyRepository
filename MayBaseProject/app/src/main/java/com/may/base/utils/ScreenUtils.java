package com.may.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by may on 2018/8/15.
 */

public class ScreenUtils {
    public static float dpToPx( float dp) {
        return dp * AppUtils.getContext().getResources().getDisplayMetrics().density;
    }

    public static float pxToDp( float px) {
        return px / AppUtils.getContext().getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt( float dp) {
        return (int) (dpToPx(dp) + 0.5f);
    }


    public static int pxToDpCeilInt( float px) {
        return (int) (pxToDp( px) + 0.5f);
    }


    public static int[] getScreenWidthAndHeight() {
        Resources resources = AppUtils.getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        return new int[]{width, height};
    }


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }


    /**
     * 获取屏幕的高度（单位：px）考虑到部分手机有虚拟按键，getScreenHeight方法获取的
     * 屏幕高度会比实际屏幕高度小（720x1280分辨率带虚拟按键的手机获取的高度是1184），
     * 该方法可以获取真实的屏幕高度。api17版本以后增加
     *
     * @return 屏幕高px
     */
    public static int getScreenHeightV17() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealMetrics(dm);
        } else {
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
        return dm.heightPixels;
    }
}
