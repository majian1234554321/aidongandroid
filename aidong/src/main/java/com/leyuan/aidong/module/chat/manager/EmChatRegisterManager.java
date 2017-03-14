package com.leyuan.aidong.module.chat.manager;

import android.os.Handler;
import android.os.Message;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by user on 2016/12/14.
 */

public class EmChatRegisterManager {

    private static final int REGISTER_SUCCESS = 1;
    private static final int REGISTER_FAILE = 2;
    private static final String PASSWORD = "123456";
    private OnChatRegisterCallback callback;

    public EmChatRegisterManager(OnChatRegisterCallback c) {
        this.callback = c;
//        Looper.prepare();
//
//        Looper.loop();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_SUCCESS:
                    callback.onChatRegisterResult(true, (String) msg.obj);
                    break;
                case REGISTER_FAILE:
                    callback.onChatRegisterResult(false, null);
                    break;
            }
        }
    };


    public void registerEmChat(String username) {
        register(username, PASSWORD);
    }


    /**
     * 注册用户名会自动转为小写字母，所以建议用户名均以小写注册。
     *
     * @param username
     * @param pwd
     */
    private void register(final String username, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username, pwd);
                    //注册成功
                    Message msg = Message.obtain();
                    msg.what = REGISTER_SUCCESS;
                    msg.obj = username;
                    handler.sendMessage(msg);
//                    handler.sendEmptyMessage(REGISTER_SUCCESS);

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(REGISTER_FAILE);
                    //注册失败
                }

            }
        }).start();
    }

    public interface OnChatRegisterCallback {
        void onChatRegisterResult(boolean success, String userName);
    }

    public void release() {
        handler.removeCallbacksAndMessages(null);
        callback = null;
    }


}
