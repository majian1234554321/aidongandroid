package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.user.UserListResult;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.ChatService;

import java.util.List;

import rx.Observer;

/**
 * Created by user on 2017/3/4.
 */

public class ChatModeImpl {

    private ChatService chatService;

    public ChatModeImpl() {
        this.chatService = RetrofitHelper.createApi(ChatService.class);
    }

    public void getUserInfo(Observer<UserListResult> subscribe, List<String> ids) {
        chatService.getUserInfo(ids)
                .compose(RxHelper.<UserListResult>transform())
                .subscribe(subscribe);
    }
}
