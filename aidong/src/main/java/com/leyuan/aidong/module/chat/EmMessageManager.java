package com.leyuan.aidong.module.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * Created by user on 2016/12/15.
 */

public class EmMessageManager {

    public static void sendTxtMessage(String content, String id){
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, id);
//如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

}
