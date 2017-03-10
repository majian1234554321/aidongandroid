package com.leyuan.aidong.entity;

import static com.leyuan.aidong.R.string.start_time;

/**
 * 活动实体
 * Created by song on 2016/8/18.
 */
public class CampaignBean {
    private String id;
    private String name;
    private String cover;
    private String start;
    private String landmark;
    
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


}
