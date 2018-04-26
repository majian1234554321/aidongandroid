package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 活动实体
 * Created by song on 2016/8/18.
 */
public class CampaignBean implements Parcelable{
    public String id;
    public String name;
    public String cover;
    public String start;
    public String landmark;
    public String follows_count;
    public String end_time;
    public String start_time;
    public String type;
    public ArrayList<String> image;
    public String slogan;
    public int hard_degree;
    public int strength;
    private ArrayList<String> tags;//  ["标签"]

    private StringBuffer tagString = new StringBuffer();

    protected CampaignBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        cover = in.readString();
        start = in.readString();
        landmark = in.readString();
        follows_count = in.readString();
        type = in.readString();
        image = in.createStringArrayList();
        slogan = in.readString();
        hard_degree = in.readInt();
        strength = in.readInt();
        tags = in.createStringArrayList();
    }

    public static final Creator<CampaignBean> CREATOR = new Creator<CampaignBean>() {
        @Override
        public CampaignBean createFromParcel(Parcel in) {
            return new CampaignBean(in);
        }

        @Override
        public CampaignBean[] newArray(int size) {
            return new CampaignBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(cover);
        dest.writeString(start);
        dest.writeString(landmark);
        dest.writeString(follows_count);
        dest.writeString(type);
        dest.writeStringList(image);
        dest.writeString(slogan);
        dest.writeInt(hard_degree);
        dest.writeInt(strength);
        dest.writeStringList(tags);
    }
}
