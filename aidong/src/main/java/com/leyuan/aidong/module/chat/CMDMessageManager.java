package com.leyuan.aidong.module.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;

/**
 * Created by user on 2017/7/24.
 * 透传消息，爱动圈点赞评论通过透传消息发送给对方 接收方做相应逻辑
 */

public class CMDMessageManager {

    private static void sendCMDMessage(String toUserName, String myAvatar, String mName,
                                      String dynamicId, String time, String content, String imageUrl,
                                      int commentType, String msgId, int dynamicType, String replySiteNickname) {
        final EMMessage cmdMsg = EMMessage.createSendMessage(EMMessage.Type.CMD);
        cmdMsg.setChatType(EMMessage.ChatType.Chat);
        String action = Constant.CIRCLE; //action可以自定义
        EMCmdMessageBody cmdBody = new EMCmdMessageBody(action);
        cmdMsg.addBody(cmdBody);
        cmdMsg.setTo(toUserName);

        cmdMsg.setAttribute(Constant.KCMDMSGTYPE, 1);
        cmdMsg.setAttribute(Constant.KDNPRAISEAVATAR, myAvatar);
        cmdMsg.setAttribute(Constant.KDNUSERNAME, mName);
        cmdMsg.setAttribute(Constant.KDNID, dynamicId);
        cmdMsg.setAttribute(Constant.KDNOCCURTIME, time);
        cmdMsg.setAttribute(Constant.KDNCONTENT, content);
        cmdMsg.setAttribute(Constant.KDNCONTENTURL, imageUrl);
        cmdMsg.setAttribute(Constant.KDNCOMMENTTYPE, commentType);//0 评论，1 点赞
        cmdMsg.setAttribute(Constant.KDNMSGID, msgId);
        cmdMsg.setAttribute(Constant.KDNCONTENTTYPE, dynamicType);//0 图片 ，1 视频
        cmdMsg.setAttribute(Constant.KDNREPLYSITENICKNAME, replySiteNickname);

        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     *
     * @param toUserName
     * @param myAvatar
     * @param mName
     * @param dynamicId
     * @param content
     * @param imageUrl
     * @param commentType 0 评论，1 点赞
     * @param msgId
     * @param dynamicType 0 图片 ，1 视频
     * @param replySiteNickname
     */
    public static void sendCMDMessage(String toUserName, String myAvatar, String mName,
                                      String dynamicId, String content, String imageUrl,
                                      int commentType, String msgId, int dynamicType, String replySiteNickname) {
        sendCMDMessage(toUserName, myAvatar, mName, dynamicId, DateUtils.getCurrentTime(), content, imageUrl, commentType, msgId, dynamicType, replySiteNickname);
    }


}
