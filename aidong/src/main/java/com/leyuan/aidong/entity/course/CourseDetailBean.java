package com.leyuan.aidong.entity.course;

import com.leyuan.aidong.entity.UserBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/30.
 */
public class CourseDetailBean {
    String id;//: "课程编号"
    String category;
    String name;//: "名字",
    ArrayList<String> image;//: "封面图",
    ArrayList<String> tags;//: "标签",
    int strength;//: "难度系数"，

    String introduce;//: 课程简介
    String video;//: 课程视频
    String instrument;//: "",
    String caution;//: "",
    String equipment;//: "",
    String tip;//: "",
    String frequency;//: "",
    String collocation;//: "",
    String crowd;//: ""
    String diet;

    boolean followed;//: “是否关注”,
    ArrayList<UserBean> followers;//
    int follows_count;
    String video_cover;

    private StringBuffer tagString = new StringBuffer();

    public String getTagString() {
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
        return tagString.toString();
    }


    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public int getStrength() {
        return strength;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getInstrument() {
        return instrument;
    }

    public String getVideo() {
        return video;
    }

    public String getCaution() {
        return caution;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getTip() {
        return tip;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getCollocation() {
        return collocation;
    }

    public String getDiet() {
        return diet;
    }

    public String getCrowd() {
        return crowd;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public ArrayList<UserBean> getFollowers() {
        return followers;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public int getFollows_count() {
        return follows_count;
    }

    public void setFollows_count(int follows_count) {
        this.follows_count = follows_count;
    }
}
