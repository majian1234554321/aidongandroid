package com.leyuan.aidong.entity;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 活动实体
 * Created by song on 2016/8/18.
 */
public class CampaignBean {
    public String id;
    public String name;
    public String cover;
    public String start;
    public String landmark;
    public String follows_count;
    public String type;
    public ArrayList<String> image;
    public String slogan;
    public int hard_degree;
    public int strength;
    private ArrayList<String> tags;//  ["标签"]

    private StringBuffer tagString = new StringBuffer();

    public StringBuffer getTagString() {
        if (tagString == null) {
            tagString = new StringBuffer();
        }
        if (tagString.length() == 0) {
            for (int i = 0; i < tags.size(); i++) {
                if (i < tags.size() - 1) {
                    tagString.append(tags.get(i)).append(" | ");
                } else {
                    tagString.append(tags.get(i));
                }

            }
        }
        return tagString;
    }


    public String getId() {
        return id;
    }

    public void setId(String campaign_id) {
        this.id = campaign_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getType() {
        return type;
    }

    public String getTypeCZ() {
        if (TextUtils.equals(type, "campaign")) {
            return "活动";
        } else if (TextUtils.equals(type, "contest")) {
            return "赛事";
        } else if (TextUtils.equals(type, "course")) {
            return "课程";
        }
        return type;
    }

    public ArrayList<String> getImage() {
        return image;
    }


    public String getFollows_count() {
        return follows_count;
    }

    public String getSlogan() {
        return slogan;
    }

    public boolean isCourse() {

        return TextUtils.equals(type, "course");
    }
}
