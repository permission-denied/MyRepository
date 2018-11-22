package com.may.base.utils.manager;

import android.app.Activity;
import android.os.Build;
import android.os.Process;

import com.may.base.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Stack;

/**
 * Created by may on 2018/8/24.
 */

public class ActivityStackManager {
    private static final ActivityStackManager activityStackManager = new ActivityStackManager();

    private static Stack<WeakReference<Activity>> mActivityStack = new Stack<>();


    private ActivityStackManager() {

    }

    public static ActivityStackManager getInstance() {
        return activityStackManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(WeakReference<Activity> activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 删除ac
     *
     * @param activity 弱引用的ac
     */
    public void removeActivity(WeakReference<Activity> activity) {
        mActivityStack.remove(activity);
    }


    /***
     * 获取栈顶Activity（堆栈中最后一个压入的）
     *
     * @return Activity
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement().get();
        if (null == activity) {
            return null;
        } else {
            return activity;
        }
    }


    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        try {
            WeakReference<Activity> activity = mActivityStack.lastElement();
            killActivity(activity);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    /***
     * 结束指定的Activity
     *
     * @param activity
     */
    public void killActivity(WeakReference<Activity> activity) {
        try {
            Iterator<WeakReference<Activity>> iterator = mActivityStack.iterator();
            while (iterator.hasNext()) {
                WeakReference<Activity> stackActivity = iterator.next();
                if (stackActivity.get() == null) {
                    iterator.remove();
                    continue;
                }
                if (stackActivity.get().getClass().getName().equals(activity.get().getClass().getName())) {
                    iterator.remove();
                    stackActivity.get().finish();
                    break;
                }
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }


    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        try {
            ListIterator<WeakReference<Activity>> listIterator = mActivityStack.listIterator();
            while (listIterator.hasNext()) {
                Activity activity = listIterator.next().get();
                if (activity != null) {
                    activity.finish();
                }
                listIterator.remove();
            }
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        killAllActivity();
        Process.killProcess(Process.myPid());
    }


    /**
     * 当前activity是否可用
     * @param activity
     * @return
     */
    public static boolean isUsable(Activity activity) {
        if(activity ==null){
            return false;
        }

        if(activity.isFinishing()){
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()){
                return false;
            }
        }
        return true;
    }
}
