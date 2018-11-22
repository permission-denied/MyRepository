package com.may.base.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;

    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(int resId) { //R.string.**
        getSingleToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            getSingleToast(text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showSingleToastCenter(String text) {
        getSingleToastCenter(text, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleLongToast(int resId) {
        getSingleToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showSingleLongToast(String text) {
        getSingleToast(text, Toast.LENGTH_LONG).show();
    }

    public static Toast getSingleToast(int resId, int duration) { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(AppUtils.getContext().getResources().getText(resId).toString(), duration);
    }

    public static Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(AppUtils.getContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    public static Toast getSingleToastCenter(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(AppUtils.getContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        return mToast;
    }

    /*********************** 连续弹出的Toast ************************/
    public static void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            getToast(text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToastCenter(String text) {
        getToastCenter(text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    public static Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(AppUtils.getContext().getResources().getText(resId).toString(), duration);
    }

    public static Toast getToast(String text, int duration) {
        return Toast.makeText(AppUtils.getContext(), text, duration);
    }


    public static Toast getToastCenter(String text, int duration) {
        Toast toast = Toast.makeText(AppUtils.getContext(), text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        return toast;
    }

    public static Toast getToastTop(String text, int duration) {
        int height = ScreenUtils.getScreenHeight();
        Toast toast = Toast.makeText(AppUtils.getContext(), text, duration);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, height / 4);
        return toast;
    }
}
