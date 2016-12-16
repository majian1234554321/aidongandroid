package com.leyuan.aidong.module.chat;

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
    private OnRigsterCallback callback ;

    public EmChatRegisterManager(){

    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REGISTER_SUCCESS:
                    if(callback != null)
                        callback.onRigster(true);
                    break;
                case REGISTER_FAILE:
                    if(callback != null)
                        callback.onRigster(false);
                    break;
            }
        }
    };


    public void registerEmChat(String username,OnRigsterCallback callback){
        this.callback = callback;
        register(username,PASSWORD);
    }


    /**
     * 注册用户名会自动转为小写字母，所以建议用户名均以小写注册。
     * @param username
     * @param pwd
     */
    private void register(final String username, final String pwd){
           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       EMClient.getInstance().createAccount(username, pwd);
                       //注册成功
                       handler.sendEmptyMessage(REGISTER_SUCCESS);

                   } catch (HyphenateException e) {
                       e.printStackTrace();
                       handler.sendEmptyMessage(REGISTER_FAILE);
                       //注册失败
                   }

               }
           }).start();
    }

public  interface   OnRigsterCallback{
    void onRigster(boolean back);
}

}
