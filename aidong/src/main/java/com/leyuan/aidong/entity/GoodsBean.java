package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 商品实体 当前包含营养品、 健康餐饮、 装备
 * Created by song on 2016/7/14.
 */
public class GoodsBean implements Parcelable {
    private  String sku_code;      //商品编码
    private  String id;
    private  String code;
    private  String cover;         //商品封面
    private  String name;          //商品名字
    private  String price;         //商品售价
    private  String market_price;  //商品市场价
    private  String type;

    /******订单商品中需要用到的字段******/
    private ArrayList<String> spec_name;
    private ArrayList<String> spec_value;
    private String amount;                  //商品数量
    private String recommend_coach_id;      //推荐码
    private DeliveryBean deliveryBean;      //商品默认取货方式
    private boolean checked = false;        //标记商品是否被选中

    public String getSkuCode() {
        return sku_code;
    }

    public void setSkuCode(String sku_code) {
        this.sku_code = sku_code;
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

    public ArrayList<String> getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(ArrayList<String> spec_name) {
        this.spec_name = spec_name;
    }

    public ArrayList<String> getSpecValue() {
        return spec_value;
    }

    public void setSpec_value(ArrayList<String> spec_value) {
        this.spec_value = spec_value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecommendCode() {
        return recommend_coach_id;
    }

    public void setRecommend_code(String recommend_code) {
        this.recommend_coach_id = recommend_code;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "sku_code='" + sku_code + '\'' +
                ", id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", cover='" + cover + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", market_price='" + market_price + '\'' +
                ", spec_name=" + spec_name +
                ", spec_value=" + spec_value +
                ", amount='" + amount + '\'' +
                ", checked=" + checked +
                '}';
    }

    public GoodsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sku_code);
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.cover);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.market_price);
        dest.writeString(this.type);
        dest.writeStringList(this.spec_name);
        dest.writeStringList(this.spec_value);
        dest.writeString(this.amount);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
    }

    protected GoodsBean(Parcel in) {
        this.sku_code = in.readString();
        this.id = in.readString();
        this.code = in.readString();
        this.cover = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.market_price = in.readString();
        this.type = in.readString();
        this.spec_name = in.createStringArrayList();
        this.spec_value = in.createStringArrayList();
        this.amount = in.readString();
        this.checked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
