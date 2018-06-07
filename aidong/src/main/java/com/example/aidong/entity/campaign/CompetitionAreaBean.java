package com.example.aidong.entity.campaign;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 2018/3/1.
 */
public class CompetitionAreaBean implements Parcelable {

    public String name;

    public String[] city;

    protected CompetitionAreaBean(Parcel in) {
        name = in.readString();
        city = in.createStringArray();
    }

    public CompetitionAreaBean(String name) {
        this.name = name;
    }

    public static final Creator<CompetitionAreaBean> CREATOR = new Creator<CompetitionAreaBean>() {
        @Override
        public CompetitionAreaBean createFromParcel(Parcel in) {
            return new CompetitionAreaBean(in);
        }

        @Override
        public CompetitionAreaBean[] newArray(int size) {
            return new CompetitionAreaBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringArray(city);
    }
}
