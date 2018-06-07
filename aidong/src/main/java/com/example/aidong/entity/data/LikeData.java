package com.example.aidong.entity.data;

import com.google.gson.annotations.SerializedName;
import com.example.aidong .entity.UserBean;

import java.util.ArrayList;

/**
 * 爱动圈点赞
 * Created by song on 2017/1/16.
 */
public class LikeData {

    @SerializedName("like")
    ArrayList<UserBean> publisher;

    public ArrayList<UserBean> getPublisher() {
        return publisher;
    }

    public void setPublisher(ArrayList<UserBean> publisher) {
        this.publisher = publisher;
    }
}
