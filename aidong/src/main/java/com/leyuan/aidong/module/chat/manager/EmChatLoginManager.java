package com.leyuan.aidong.module.chat.manager;

import android.os.Handler;
import android.os.Message;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.leyuan.aidong.R;
import com.leyuan.aidong.module.chat.db.DemoDBManager;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.Logger;

/**
 * Created by user on 2016/12/14.
 */

public class EmChatLoginManager {

    //    首次登录成功后，不需要再次调用登录方法，在下次 APP 启动时，SDK 会自动为您登录。并且如果您自动登录失败
// ，也可以读取到之前的会话信息（以上情况是在未调用登出的情况下实现的）。
//
//    SDK 中自动登录属性默认是 true 打开的，如果不需要自动登录，在初始化 SDK 初始化的时候，
//    调用options.setAutoLogin(false);设置为 false 关闭。
//
//    自动登录在以下几种情况下会被取消：
//
//    用户调用了 SDK 的登出动作;
//    用户在别的设备上更改了密码，
// ；
//    用户的账号被从服务器端删除；
//    用户从另一个设备登录，把当前设备上登录的用户踢出。
    private static final String PASSWORD = "123456";
    private OnLoginListner listner;

    private static final int LOGIN_SUCCESS = 1;
    private static final int LOGIN_FAILE = 2;
    private static final int NEED_REGISTER = 3;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    listner.onChatLogin(true);
                    break;
                case LOGIN_FAILE:
                    listner.onChatLogin(false);
                    break;
                case NEED_REGISTER:
                    if (msg.obj != null)
                        listner.onNeedRegister((String) msg.obj);
            }
        }
    };


    public EmChatLoginManager(OnLoginListner listner) {
        this.listner = listner;
    }


    public void login(final String userName) {
        EMClient.getInstance().login(userName, PASSWORD, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Logger.d("EMCHAT", "登录聊天服务器成功！");
                handler.sendEmptyMessage(LOGIN_SUCCESS);

                if (App.getInstance().isLogin()) {
                    EaseUser user = new EaseUser("admin");
                    user.setAvatar("" + R.drawable.sysytem_message);
                    user.setNickname("系统消息");
                    DemoDBManager.getInstance().saveContact(user);
                }

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Logger.d("EMCHAT", "登录聊天服务器失败！ error code = " + code);
                if (code == EMError.USER_NOT_FOUND) {
                    Message msg = Message.obtain();
                    msg.what = NEED_REGISTER;
                    msg.obj = userName;
                    handler.sendMessage(msg);
                } else {
                    handler.sendEmptyMessage(LOGIN_FAILE);
                }
            }
        });
    }

    public interface OnLoginListner {
        void onChatLogin(boolean result);

        void onNeedRegister(String userName);

    }

    public static void loginOut() {

        //方法里第一个参数需要设为true，这样退出的时候会解绑设备token，否则可能会出现退出了，还能收到消息的现象。
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }

    public void release() {
        handler.removeCallbacksAndMessages(null);
        listner = null;
    }


}
