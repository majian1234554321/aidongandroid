package com.leyuan.aidong.entity.campaign;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.leyuan.aidong.utils.Logger;

/**
 * Created by user on 2018/2/6.
 */
public class ContestBean implements Parcelable {

    public String id;//: 赛事编号,
    public String name;//: "赛事名称",
    public String background;//: "背景图",
    public String theme_color;//: "主题色调",

    public boolean joined;
    public String video;//: "赛事介绍视频",
    public String status;//: '赛事状态' #preliminary-海选 semi_finals-复赛 finals-决赛,
    public CompetitionAreaBean[] division_info;//: ["赛区-1","赛区-2"]

    protected ContestBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        background = in.readString();
        theme_color = in.readString();
        joined = in.readByte() != 0;
        video = in.readString();
        status = in.readString();
        division_info = in.createTypedArray(CompetitionAreaBean.CREATOR);
    }

    public static final Creator<ContestBean> CREATOR = new Creator<ContestBean>() {
        @Override
        public ContestBean createFromParcel(Parcel in) {
            return new ContestBean(in);
        }

        @Override
        public ContestBean[] newArray(int size) {
            return new ContestBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(background);
        dest.writeString(theme_color);
        dest.writeByte((byte) (joined ? 1 : 0));
        dest.writeString(video);
        dest.writeString(status);
        dest.writeTypedArray(division_info, flags);
    }

    public String getAreaByCity(String city) {

        Logger.i("contest area city", " getAreaByCity ");
        if (city == null || division_info == null) return null;
        for (CompetitionAreaBean areaBean : division_info) {


            for (String s : areaBean.city) {
                Logger.i("contest area city", " areaBean city   = " + s);

                if (TextUtils.equals(s, city)) {
                    return areaBean.name;
                }
            }
        }
        return null;
    }
}
