package com.leyuan.aidong.entity;

/**
 * Created by user on 2017/7/24.
 */
public class CircleDynamicBean {
    public static final int PARSE = 1;
    int cmdMsgType;
    String fromAvatar;
    String fromName;
    String dynamicId;
    String time;
    String content;
    String imageUrl;
    int commentType;
    String KDNMSGID;
    int dynamicType;
    String replySiteNickname;

    public int getCmdMsgType() {
        return cmdMsgType;
    }

    public void setCmdMsgType(int cmdMsgType) {
        this.cmdMsgType = cmdMsgType;
    }

    public String getFromAvatar() {
        return fromAvatar;
    }

    public void setFromAvatar(String fromAvatar) {
        this.fromAvatar = fromAvatar;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getKDNMSGID() {
        return KDNMSGID;
    }

    public void setKDNMSGID(String KDNMSGID) {
        this.KDNMSGID = KDNMSGID;
    }

    public int getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(int dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getReplySiteNickname() {
        return replySiteNickname;
    }

    public void setReplySiteNickname(String replySiteNickname) {
        this.replySiteNickname = replySiteNickname;
    }

    @Override
    public String toString() {
        return "CircleDynamicBean{" +
                "cmdMsgType=" + cmdMsgType +
                ", avatar='" + fromAvatar + '\'' +
                ", mName='" + fromName + '\'' +
                ", dynamicId='" + dynamicId + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", commentType=" + commentType +
                ", KDNMSGID='" + KDNMSGID + '\'' +
                ", dynamicType=" + dynamicType +
                ", replySiteNickname='" + replySiteNickname + '\'' +
                '}';
    }

    public class ActionType {

        public static final int COMMENT = 0;
        public static final int REPLY = 1;
        public static final int PARSE = 2;
        public static final int AITER = 3;

    }

    public class DynamicType {
        public static final int IMAGE = 0;
    }
}
