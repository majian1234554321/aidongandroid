package com.example.aidong.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by user on 2017/2/15.
 */
public class NewPushMessageReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.leyuan.idong.newMessage";
    private View imgNewMessage;

    public NewPushMessageReceiver(View imgNewMessage) {
        this.imgNewMessage = imgNewMessage;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (imgNewMessage != null) {
            imgNewMessage.setVisibility(View.VISIBLE);
        }

    }
}
