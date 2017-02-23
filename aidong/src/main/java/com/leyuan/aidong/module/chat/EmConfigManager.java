package com.leyuan.aidong.module.chat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.leyuan.aidong.utils.Logger;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by user on 2016/12/15.
 */

public class EmConfigManager {


    public static void initialize(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        // options.setAcceptInvitationAlways(false);
        //自动登录属性默认是 true 打开的，如果不需要自动登录,设置为 false 关闭。
        //  options.setAutoLogin(false);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid,context);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(context.getPackageName())) {
            Logger.e("","enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private static String  getAppName(int pID,Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Logger.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
