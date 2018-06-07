package com.leyuan.aidong.module;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.aidong.R;
import com.leyuan.aidong.module.chat.manager.EmChatLoginManager;
import com.leyuan.aidong.module.chat.manager.EmChatRegisterManager;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;

/**
 * Created by user on 2017/5/18.
 */

public class ChatLoginService extends Service {

    private static final java.lang.String TAG = "ChatLoginService";
    private EmChatRegisterManager.OnChatRegisterCallback charRegisterListener = new EmChatRegisterManager.OnChatRegisterCallback() {
        @Override
        public void onChatRegisterResult(boolean success, String userName) {
            if (success && userName != null) {
                chatLoginManager.login(userName);
            } else {
                ToastGlobal.showLong("聊天服务注册失败");
                stopSelf();
            }
        }
    };
    private EmChatLoginManager.OnLoginListner loginListner = new EmChatLoginManager.OnLoginListner() {
        @Override
        public void onChatLogin(boolean result) {
            if (result) {
//                ToastGlobal.showLong(R.string.login_chat_success);
            } else {
                ToastGlobal.showLong(R.string.login_chat_faile);
            }
            stopSelf();

        }

        @Override
        public void onNeedRegister(String userName) {
            EmChatRegisterManager chatRegisterManager = new EmChatRegisterManager(charRegisterListener);
            chatRegisterManager.registerEmChat(userName);
        }
    };
    private EmChatLoginManager chatLoginManager;

    public static void startService(Context context, String userName) {
        Intent intent = new Intent(context, ChatLoginService.class);
//        intent.putExtra("userName", userName);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, "onCreate()");
        chatLoginManager = new EmChatLoginManager(loginListner);
        if (App.getInstance().isLogin()) {
            String userName = App.getInstance().getUser().getId() + "";
            if (userName != null) {
                chatLoginManager.login(userName);
            }
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestroy()");

        super.onDestroy();
        chatLoginManager.release();
//        if (chatRegisterManager != null)
//            chatRegisterManager.release();
    }

}
