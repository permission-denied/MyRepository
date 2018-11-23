package com.may.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.may.base.utils.AppUtils;
import com.may.base.utils.LogUtils;
import com.may.base.utils.manager.ActivityStackManager;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

/**
 * Created by may on 2018/8/15.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtils.init(this);

        LogUtils.d("dev  branch");

        LogUtils.d("tev branch");

        LogUtils.d("v10 branch add");

        //初始化Logger
        Logger.init(Constants.LOG_TAG_NAME).hideThreadInfo().methodCount(0);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().addActivity(new WeakReference<>(activity));
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().removeActivity(new WeakReference<>(activity));
            }
        });
    }


}
