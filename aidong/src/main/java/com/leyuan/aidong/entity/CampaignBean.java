package com.leyuan.aidong.entity;

/**
 * 活动实体
 * Created by song on 2016/8/18.
 */
public class CampaignBean {
    private String campaign_id;
    private String name;
    private String cover;
    private String start_time;
    private String landmart;

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getLandmart() {
        return landmart;
    }

    public void setLandmart(String landmart) {
        this.landmart = landmart;
    }

    @Override
    public String toString() {
        return "CampaignBean{" +
                "campaign_id='" + campaign_id + '\'' +
                ", name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                ", start_time='" + start_time + '\'' +
                ", landmart='" + landmart + '\'' +
                '}';
    }
}
