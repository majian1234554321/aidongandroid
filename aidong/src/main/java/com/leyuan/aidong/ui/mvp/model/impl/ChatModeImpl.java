package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.user.ProfileBeanResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.ChatService;

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

    public void getUserInfo(Observer<ProfileBeanResult> subscribe, List<String> ids) {
        chatService.getUserInfo(ids)
                .compose(RxHelper.<ProfileBeanResult>transform())
                .subscribe(subscribe);
    }
}
