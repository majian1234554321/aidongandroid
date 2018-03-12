package com.leyuan.aidong.module.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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

//        JsonObject element = new JsonObject();
//
//        JsonArray jsonArray = new JsonArray();
//
//        element.addProperty(Constant.kuser_id,"");
//        jsonArray.add(element);


        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }

    /**
     * @param toUserName
     * @param myAvatar
     * @param mName
     * @param dynamicId
     * @param content
     * @param imageUrl
     * @param commentType       0 评论，1 回复,2 点赞, 3 @;
     * @param msgId
     * @param dynamicType       0 图片 ，1 视频
     * @param replySiteNickname
     */
    public static void sendCMDMessage(String toUserName, String myAvatar, String mName,
                                      String dynamicId, String content, String imageUrl,
                                      int commentType, String msgId, int dynamicType, String replySiteNickname) {
        sendCMDMessage(toUserName, myAvatar, mName, dynamicId, DateUtils.getCurrentTime(), content, imageUrl, commentType, msgId, dynamicType, replySiteNickname);
    }


    public static void sendCMDMessageAite(String myAvatar, String mName, String dynamicId,
                                          String content, String dynamicCover, int commentType,
                                          String msgId, int dynamicType, String replySiteNickname,
                                          Map<String, String> itUser) {
        if (itUser != null) {
            for (Map.Entry<String, String> code : itUser.entrySet()) {

                Logger.i("chat itUser. arrayUser.put(userObj);  name =  " + code.getKey() + ", user_id = " + code.getValue());
                sendCMDMessageAite(code.getValue(), myAvatar, mName, dynamicId, DateUtils.getCurrentTime(),
                        content, dynamicCover, commentType, msgId, dynamicType, replySiteNickname, itUser, null);
            }
            Logger.i("chat root.put(\"extras\", arrayUser);");
        }

    }

    public static void sendCMDMessageReply(String myAvatar, String mName, String dynamicId,
                                           String content,String dynamicCover, int commentType,
                                           String msgId, int dynamicType, String replySiteNickname,
                                           Map<String, String> itUser, Map<String, String> replyUser) {
        if (itUser != null) {
            for (Map.Entry<String, String> code : itUser.entrySet()) {

                Logger.i(" itUser. arrayUser.put(userObj);  name =  " + code.getKey() + ", user_id = " + code.getValue());
                sendCMDMessageAite(code.getValue(), myAvatar, mName, dynamicId, DateUtils.getCurrentTime(),
                        content, dynamicCover, commentType, msgId, dynamicType, replySiteNickname, itUser, replyUser);

            }
            Logger.i("root.put(\"extras\", arrayUser);");
        }

    }


    private static void sendCMDMessageAite(String toUserName, String myAvatar, String mName,
                                           String dynamicId, String time, String content, String imageUrl,
                                           int commentType, String msgId, int dynamicType, String replySiteNickname,
                                           Map<String, String> itUser, Map<String, String> replyUser) {
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


        //@的人
        JSONArray jsonArrayIter = new JSONArray();
        if (itUser != null) {
            for (Map.Entry<String, String> code : itUser.entrySet()) {
                try {
                    JSONObject element = new JSONObject();
                    element.put(Constant.kuser_name, code.getKey());
                    element.put(Constant.kuser_id, code.getValue());
                    jsonArrayIter.put(element);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        //评论里回复的人
        JSONArray jsonArrayReply = new JSONArray();
        if (replyUser != null) {
            for (Map.Entry<String, String> code : replyUser.entrySet()) {
                try {
                    JSONObject element = new JSONObject();
                    element.put(Constant.kuser_name, code.getKey());
                    element.put(Constant.kuser_id, code.getValue());
                    jsonArrayReply.put(element);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        cmdMsg.setAttribute(Constant.kDAiteUser, jsonArrayIter);
        cmdMsg.setAttribute(Constant.kDNreplyUser, jsonArrayReply);


        EMClient.getInstance().chatManager().sendMessage(cmdMsg);
    }


}
