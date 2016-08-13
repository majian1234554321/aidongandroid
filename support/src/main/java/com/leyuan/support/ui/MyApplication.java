package com.leyuan.support.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.leyuan.support.util.LogUtil;
import com.leyuan.support.util.listener.SimpleActivityLifecycleCallbacks;

import java.util.LinkedList;
import java.util.List;

/**
 * Application
 * Created by song on 2016/8/10.
 */
public class MyApplication extends Application{

    private List<Activity> activityList = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        Fresco.initialize(getApplicationContext());
    }

    private class ActivityLifecycle extends SimpleActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            super.onActivityCreated(activity, savedInstanceState);
            activityList.add(activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            super.onActivityDestroyed(activity);
            activityList.remove(activity);
        }
    }

    public void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                LogUtil.d("Finish ALl = " + activity.getLocalClassName());
                activity.finish();
            }
        }
    }
}
