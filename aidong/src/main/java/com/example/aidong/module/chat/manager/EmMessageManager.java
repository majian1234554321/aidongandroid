package com.example.aidong.module.chat.manager;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.example.aidong .ui.App;

import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016/12/15.
 */

public class EmMessageManager {

    public static void sendTxtMessage(String content, String id) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(content, id);
//如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
//发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendVoiceMessage(String filePath, int length, String id) {
        //filePath为语音文件路径，length为录音时间(秒)
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, id);
//如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendImageMessage(String imagePath, String id) {
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imagePath, false, id);
//        如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendLocationMessage(double latitude, double longitude, String locationAddress, String id) {
        //latitude为纬度，longitude为经度，locationAddress为具体位置内容
        EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, id);
//如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public static void sendFileMessage(String filePath, String id) {
        EMMessage message = EMMessage.createFileSendMessage(filePath, id);
// 如果是群聊，设置chattype，默认是单聊
//        if (chatType == CHATTYPE_GROUP)
//            message.setChatType(ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    EMMessageListener msgListener = new EMMessageListener() {


        @Override
        public void onMessageReceived(List<EMMessage> list) {
            saveMessage(list);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    };

    public void registerMessageListener() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    public void releaseMessageListener() {
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }

    public void setMessageStatusCallback(EMMessage message) {

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    public static List<EMMessage> getAllMessages(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
//获取此会话的所有消息
        List<EMMessage> messages = conversation.getAllMessages();
//SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
//获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
//        建议初始化SDK的时候设置成每个会话默认load一条消息，节省加载会话的时间，方法为：options.setNumberOfMessagesLoaded(1);
        return messages;
    }

    public static int getUnReadMessageNum(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        if (conversation == null)
            return 0;
        return conversation.getUnreadMsgCount();
    }

    public static int getUnReadMessageNum() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    public static int getAllMessageNum(String username) {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
//获取此会话在本地的所有的消息数量
        conversation.getAllMsgCount();
//如果只是获取当前在内存的消息数量，调用
        return conversation.getAllMessages().size();
    }

    public static Map<String, EMConversation> getAllConversations() {
        //如果出现偶尔返回的conversations的sizi为0，
        // 那很有可能是没有调用EMClient.getInstance().chatManager().loadAllConversations()，
        // 或者调用顺序不对，具体用法请参考登录章节。
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        return conversations;
    }

    public static void removeMessage(String username) {
        //删除和某个user会话，如果需要保留聊天记录，传false
        EMClient.getInstance().chatManager().deleteConversation(username, true);
    }

    public static void removeMessage(String username, String msgId) {
//        删除当前会话的某条聊天记录
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
        conversation.removeMessage(msgId);
    }

    public void saveMessage(List<EMMessage> msgs) {
        EMClient.getInstance().chatManager().importMessages(msgs);
    }

    public static boolean isHaveUnreadMessage() {
        return App.mInstance.isLogin() && getUnReadMessageNum() > 0;
    }
}
