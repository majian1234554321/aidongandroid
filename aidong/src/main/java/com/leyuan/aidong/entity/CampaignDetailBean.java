package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动详情实体
 * Created by song on 2016/8/18.
 */
public class CampaignDetailBean implements Parcelable {
    private String id;                      //活动编号
    private String name;                    //活动名称
    private List<String> image;             //活动封面图
    private String landmark;                //活动地标
    private String start_time;              //活动开始时间
    private String end_time;                //活动结束时间
    private String address;                 //活动举办地址
    private String organizer;               //活动举办者
    private String place;                   //活动名额
    private String introduce;               //活动介绍
    private String price;                   //活动价格
    private String market_price;            //活动指导价
    private String entry_start_time;       //报名开始时间
    private String entry_end_time;         //报名截至时间
    private List<UserBean> appicant;        //报名的人

    private String status = "";
    private CoordinateBean coordinate;
    private String view_count;

    private String created_at;              //订单生成时间

    public String simple_intro;//文字介绍
    public int applied_count;// 已报名人数,
    public String skucode;//选中的规格的skucode
    public String amount;//选择的数量
    public String skuPrice;

    public GoodsSpecBean spec;//规格
    public boolean followed;



    public String getViewCount() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public CoordinateBean getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateBean coordinate) {
        this.coordinate = coordinate;
    }

    public String getCampaignId() {
        return id;
    }

    public void setCampaign_id(String campaign_id) {
        this.id = campaign_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public String getEntry_start_time() {
        return entry_start_time;
    }

    public void setEntry_start_time(String entry_start_time) {
        this.entry_start_time = entry_start_time;
    }

    public String getEntry_end_time() {
        return entry_end_time;
    }

    public void setEntry_end_time(String entry_end_time) {
        this.entry_end_time = entry_end_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserBean> getApplicant() {
        return appicant;
    }

    public void setApplicant(ArrayList<UserBean> applicant) {
        this.appicant = applicant;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public class Applicant {
        private String idong_id;
        private String name;
        private String avatar;

        public String getIdong_id() {
            return idong_id;
        }

        public void setIdong_id(String idong_id) {
            this.idong_id = idong_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.image);
        dest.writeString(this.landmark);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.address);
        dest.writeString(this.organizer);
        dest.writeString(this.place);
        dest.writeString(this.introduce);
        dest.writeString(this.price);
        dest.writeString(this.market_price);
        dest.writeString(this.entry_start_time);
        dest.writeString(this.entry_end_time);
        dest.writeTypedList(this.appicant);
        dest.writeString(this.status);
        dest.writeParcelable(this.coordinate, flags);
        dest.writeString(this.view_count);
        dest.writeString(this.created_at);
        dest.writeString(this.simple_intro);
        dest.writeInt(this.applied_count);
        dest.writeString(this.skucode);
        dest.writeString(this.amount);
        dest.writeString(this.skuPrice);
    }

    public CampaignDetailBean() {
    }

    protected CampaignDetailBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.image = in.createStringArrayList();
        this.landmark = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.address = in.readString();
        this.organizer = in.readString();
        this.place = in.readString();
        this.introduce = in.readString();
        this.price = in.readString();
        this.market_price = in.readString();
        this.entry_start_time = in.readString();
        this.entry_end_time = in.readString();
        this.appicant = in.createTypedArrayList(UserBean.CREATOR);
        this.status = in.readString();
        this.coordinate = in.readParcelable(CoordinateBean.class.getClassLoader());
        this.view_count = in.readString();
        this.created_at = in.readString();
        this.simple_intro = in.readString();
        this.applied_count = in.readInt();
        this.skucode = in.readString();
        this.amount = in.readString();
        this.skuPrice = in.readString();
    }

    public static final Creator<CampaignDetailBean> CREATOR = new Creator<CampaignDetailBean>() {
        @Override
        public CampaignDetailBean createFromParcel(Parcel source) {
            return new CampaignDetailBean(source);
        }

        @Override
        public CampaignDetailBean[] newArray(int size) {
            return new CampaignDetailBean[size];
        }
    };
}
