package com.example.aidong.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.example.aidong .entity.PushExtroInfo;
import com.example.aidong .ui.App;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .ui.mine.activity.AppointCampaignDetailActivity;
import com.example.aidong .ui.mine.activity.AppointDetailCourseNewActivity;
import com.example.aidong .ui.mine.activity.OrderDetailMultiplePackagesActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.LogAidong;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by user on 2016/12/15.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogAidong.i(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogAidong.i(TAG, "[MyReceiver] 接收 注册Registration Id : " + regId);

            App.getInstance().saveJpushId(regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogAidong.i(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " +
                    bundle.getString(JPushInterface.EXTRA_MESSAGE) + ", bike id = " + value);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);
            LogAidong.i(TAG, "[MyReceiver] 接收到推送下来的通知  value = " + value);
            PushExtroInfo info = new Gson().fromJson(value, PushExtroInfo.class);
            if (info != null) {
                context.sendBroadcast(new Intent(NewPushMessageReceiver.ACTION));
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogAidong.i(TAG, "[MyReceiver] 用户点击打开了通知");
            String value = bundle.getString(JPushInterface.EXTRA_EXTRA);

            PushExtroInfo info = new Gson().fromJson(value, PushExtroInfo.class);
            if (info == null) return;
            //if (App.getInstance().isForeground) return;

            if (App.getInstance().getActivityStack().isEmpty()) {
                startAppActivityByType(context, info);
            } else {
                startActivityByType(context, info);
            }


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            LogAidong.i(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogAidong.i(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            LogAidong.i(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }


    private void startAppActivityByType(Context context, PushExtroInfo info) {
        Intent intentMain = new Intent(context, MainActivity.class);
        intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent = getIntentByType(context, info);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivities(new Intent[]{intentMain, intent});
        }
    }

    private void startActivityByType(Context context, PushExtroInfo info) {
        Intent intent = getIntentByType(context, info);
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    private Intent getIntentByType(Context context, PushExtroInfo info) {
        Intent intent = null;
        switch (info.getType()) {
            case Constant.CAMPAIGN:
                LogAidong.i(TAG, "[MyReceiver] 用户点击打开了通知类型: CAMPAIGN 跳转");
                intent = new Intent(context, AppointCampaignDetailActivity.class);
                intent.putExtra("orderId", info.getLink_id());
                break;
            case Constant.ORDER:
                intent = new Intent(context, OrderDetailMultiplePackagesActivity.class);
                intent.putExtra("orderId", info.getLink_id());

                break;
            case Constant.COURSE:
//                AppointDetailCourseNewActivity.appointStart(context,info.getLink_id());
//                intent = new Intent(context, AppointDetailCourseNewActivity.class);
//                intent.putExtra("orderId", info.getLink_id());

                intent = new Intent(context, AppointDetailCourseNewActivity.class);
                intent.putExtra("appointId", info.getLink_id());
                intent.putExtra("type", "appoint");

                break;
            case Constant.PUSH_CAMPAIGN:
                intent = new Intent(context, ActivityCircleDetailActivity.class);
                intent.putExtra("id", info.getLink_id());

                break;
        }
        return intent;
    }


    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    LogAidong.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogAidong.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
