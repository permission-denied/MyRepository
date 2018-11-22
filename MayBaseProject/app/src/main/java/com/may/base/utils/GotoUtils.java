package com.may.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;


public class GotoUtils {

    public static void gotoActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void gotoActivityForResult(Context context, Class<?> cls, int requestCode) {
        Intent intent = new Intent(context, cls);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void gotoActivityForResultWithBundle(Context context, Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void gotoActivityWithBundle(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        //setExtrasData(context, bundle);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void gotoActivityWithIntent(Context context, Class<?> cls, Intent intent) {
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    public static void gotoActivities(Context context, Intent[] intents) {
        context.startActivities(intents);
    }

    public static void gotoActivityClearTop(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 相当于Androidmanifest里配置的singleTask
     *
     * @param context
     * @param cls
     */
    public static void gotoActivitySingleTask(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    /**
     * 前往登录页面
     *
     * @param context
     */
    public static void gotoLogin(Context context) {
        //gotoActivitySingleTask(context, LoginActivity.class);
    }

    /**
     * 前往MainActivity
     *
     * @param context
     */
    public static void gotoMain(Context context) {
        //gotoActivitySingleTask(context, MainActivity.class);
    }

    public static void gotoWebsite(Context context, Uri webUri) {
        if (webUri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, webUri);
        context.startActivity(intent);
    }

    /**
     * 跳转到拨号页面
     *
     * @param context
     * @param number
     */
    public static void gotoPhone(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     */
    public static void gotoCall(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }


    public static void gotoSms(Context context, String number, String content) {
        Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + number));
        localIntent.putExtra("sms_body", content);
        context.startActivity(localIntent);
    }

    public static void gotoEmail(Context context, String toEmail, String title, String content) {//模拟器有bug
        try {
            Intent localIntent = new Intent("android.intent.action.SEND");
            localIntent.setType("plain/text");
            localIntent.putExtra("android.intent.extra.EMAIL", toEmail);
            localIntent.putExtra("android.intent.extra.SUBJECT", title);
            localIntent.putExtra("android.intent.extra.TEXT", content);
            context.startActivity(localIntent);
        } catch (Exception e) {

        }
    }

    public static void gotoGallery(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivity(intent);
    }

    /**
     * 跳转到浏览文件App
     *
     * @param context
     * @param file     文件
     * @param fileType 文件类型(支持pdf, word, ppt, xlsx等4种格式)
     */
    public static void gotoReadFile(Context context, File file, String fileType) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);

            if (".pdf".equals(fileType)) {
                intent.setDataAndType(uri, "application/pdf");
            } else if (".doc".equals(fileType) || ".docx".equals(fileType)) {
                //打开word
                intent.setDataAndType(uri, "application/msword");
            } else if (".ppt".equals(fileType) || ".PPT".equals(fileType)) {
                //打开ppt
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (".xlsx".equals(fileType)) {
                //打开表格
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else {
                Toast.makeText(context, "不支持阅览当前文件类型", Toast.LENGTH_SHORT).show();
                return;
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "未找到可打开文件的应用", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转到qq聊天页面
     *
     * @param context
     * @param qq      qq号码
     */
    public static void gotoQQ(Context context, String qq) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("mqqwpa://im/chat?chat_type=wpa&uin=%s&version=1", qq))));
    }

    /**
     * 跳转到web页面
     *
     * @param context
     * @param url
     * @param title
     */
    public static void goToWebUrl(Context context, String url, String title) {
        goToWebUrl(context, url, title, true);
    }

    public static void goToWebUrl(Context context, String url, String title, boolean needToolbar) {
        /*Intent intent = new Intent(context, WebUrlActivity.class);
        intent.putExtra(Constants.IntentExtraKey.WEB_URL, url);
        intent.putExtra(Constants.IntentExtraKey.WEB_TITLE, title);
        intent.putExtra(Constants.IntentExtraKey.PAGE_SIMPLE_NAME, getFromPageName(context));
        intent.putExtra(Constants.IntentExtraKey.WEB_NEED_TOOLBAR, needToolbar);
        context.startActivity(intent);*/
    }


}

