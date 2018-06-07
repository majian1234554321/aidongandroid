package com.example.aidong.module.chat;

/**
 * Created by user on 2016/12/15.
 */

public class EmConnectStateListener {
    //当掉线时，Android SDK 会自动重连，无需进行任何操作，通过注册连接监听来知道连接状态。
    // 在聊天过程中难免会遇到网络问题，在此 SDK 为您提供了网络监听接口，实时监听
    // 可以根据 disconnect 返回的 error 判断原因。若服务器返回的参数值为EMError.USER_LOGIN_ANOTHER_DEVICE
    //则认为是有同一个账号异地登录；若服务器返回的参数值为EMError.USER_REMOVED，则是账号在后台被删除。
    //注册一个监听连接状态的listener
//    EMClient.getInstance().addConnectionListener(new MyConnectionListener());
//
    //实现ConnectionListener接口
//    private class MyConnectionListener implements EMConnectionListener {
//        @Override
//        public void onConnected() {
//        }
//        @Override
//        public void onDisconnected(final int error) {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    if(error == EMError.USER_REMOVED){
//                        // 显示帐号已经被移除
//                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                        // 显示帐号在其他设备登录
//                    } else {
//                        if (NetUtils.hasNetwork(MainActivity.this))
//                        //连接不到聊天服务器
//                        else
//                        //当前网络不可用，请检查网络设置
//                    }
//                }
//            });
//        }
//    }
}
