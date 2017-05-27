package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.constant.DynamicType;

import java.util.List;

import static com.leyuan.aidong.utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public class DynamicBean implements Parcelable {
    public String id;
    public String content;
    public List<String> image;
    public DynamicVideoBean videos;
    public UserBean publisher;
    public String published_at;
    public LikeUserListBean like;
    public CommentListBean comment;

    public boolean isLiked = false; //标记是否点赞

    @DynamicType
    public  int getDynamicType(){
        if(image != null && !image.isEmpty()){
            /*if(image.size() == 1){
                return DYNAMIC_ONE_IMAGE;
            }else if(image.size() == 2){
                return DYNAMIC_TWO_IMAGE;
            }else if(image.size() == 3){
                return DYNAMIC_THREE_IMAGE;
            }else if(image.size() == 4){
                return DYNAMIC_FOUR_IMAGE;
            }else if(image.size() == 5){
                return DYNAMIC_FIVE_IMAGE;
            }else if(image.size() == 6){
                return DYNAMIC_SIX_IMAGE;
            }else {
                return DYNAMIC_NONE;
            }*/
            return DYNAMIC_MULTI_IMAGE;
        }else {
            return DYNAMIC_VIDEO;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeStringList(this.image);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.publisher, flags);
        dest.writeString(this.published_at);
        dest.writeParcelable(this.like, flags);
        dest.writeParcelable(this.comment, flags);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
    }

    public DynamicBean() {
    }

    protected DynamicBean(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.image = in.createStringArrayList();
        this.videos = in.readParcelable(DynamicVideoBean.class.getClassLoader());
        this.publisher = in.readParcelable(UserBean.class.getClassLoader());
        this.published_at = in.readString();
        this.like = in.readParcelable(LikeUserListBean.class.getClassLoader());
        this.comment = in.readParcelable(CommentListBean.class.getClassLoader());
        this.isLiked = in.readByte() != 0;
    }

    public static final Creator<DynamicBean> CREATOR = new Creator<DynamicBean>() {
        @Override
        public DynamicBean createFromParcel(Parcel source) {
            return new DynamicBean(source);
        }

        @Override
        public DynamicBean[] newArray(int size) {
            return new DynamicBean[size];
        }
    };
}
