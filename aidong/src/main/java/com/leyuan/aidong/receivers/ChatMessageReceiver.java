package com.example.aidong.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by user on 2017/3/9.
 */
public class ChatMessageReceiver extends BroadcastReceiver {
    private final View img_new_message;

    public ChatMessageReceiver(View img_new_message) {
        this.img_new_message = img_new_message;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (img_new_message != null)
            img_new_message.setVisibility(View.VISIBLE);
    }
}
