package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.user.UserListResult;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .ui.mvp.model.impl.ChatModeImpl;
import com.example.aidong .ui.mvp.view.EmChatView;

import java.util.List;

/**
 * Created by user on 2017/3/4.
 */

public class ChatPresentImpl {

    private Context context;
    private EmChatView emChatView;
    private ChatModeImpl mode;

    public ChatPresentImpl(Context context, EmChatView emChatView) {
        this.context = context;
        this.emChatView = emChatView;
        mode = new ChatModeImpl();
    }

    public void getUserInfo(List<String> ids) {
        mode.getUserInfo(new IsLoginSubscriber<UserListResult>(context) {
            @Override
            public void onNext(UserListResult userInfoData) {
                 emChatView.onGetUserInfo(userInfoData.getProfile());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                emChatView.onGetUserInfo(null);
            }
        }, ids);
    }
}
