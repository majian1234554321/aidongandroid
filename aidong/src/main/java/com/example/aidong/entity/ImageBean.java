package com.example.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**Image
 * Created by song on 2016/8/30.
 */
public class ImageBean implements Parcelable {
    public enum TYPE {
        NET_IMAGE, LOCAL_IMAGE
    }

    private String id;
    private String url;
    private String path;
    private TYPE type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public TYPE getType() {
        if(TextUtils.isEmpty(path) && !TextUtils.isEmpty(url)) {
            return TYPE.NET_IMAGE;
        }else {
            return TYPE.LOCAL_IMAGE;
        }
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.path);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
    }

    public ImageBean() {
    }

    protected ImageBean(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.path = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : TYPE.values()[tmpType];
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel source) {
            return new ImageBean(source);
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };
}
