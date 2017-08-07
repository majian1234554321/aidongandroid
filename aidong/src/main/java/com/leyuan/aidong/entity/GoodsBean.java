package com.leyuan.aidong.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.leyuan.aidong.utils.constant.GoodsType;

import java.util.ArrayList;

/**
 * 商品实体 当前包含营养品、 健康餐饮、 装备
 * Created by song on 2016/7/14.
 */
public class GoodsBean  implements Parcelable {
    private static final int OUT_OF_STOCK = 1;
    private static final int SOLD_OUT = 2;
    private static final int ON_SALE = 3;
    private String id;             //购物车商品id
    private String code;           //商品编码
    private String cover;         //商品封面
    private String name;          //商品名字
    private String price;         //商品售价
    private String market_price;  //商品市场价
    private String type;
    private String floor_price;

    /******
     * 订单商品中需要用到的字段
     ******/
    private ArrayList<String> spec_name;
    private ArrayList<String> spec_value;
    private String amount;                  //商品数量
    private String recommend_coach_id;      //推荐码
    private DeliveryBean deliveryBean;      //商品默认取货方式
    private boolean checked = false;        //标记商品是否被选中
    private int can_return;                 //可退数量


    /**
     * 购物车中商品id为product_id type为product_type id为该商品在购物车中的排列标记
     */
    private String product_id;
    private String product_type;
    private boolean online;
    private int stock;
    private String item;//退换条目和数量
    private boolean is_send;    //该商品是否支持快递

    public int getSoldState() {
        if (!online) {
            return OUT_OF_STOCK;
        } else if (stock <= 0) {
            return SOLD_OUT;
        } else {
            return ON_SALE;
        }
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    @GoodsType
    public String getProductType() {
        return product_type;
    }

    public void setProductType(@GoodsType String product_type) {
        this.product_type = product_type;
    }



    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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

    public ArrayList<String> getSpecName() {
        return spec_name;
    }

    public void setSpec_name(ArrayList<String> spec_name) {
        this.spec_name = spec_name;
    }

    public ArrayList<String> getSpecValue() {
        return spec_value;
    }

    public void setSpecValue(ArrayList<String> spec_value) {
        this.spec_value = spec_value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getCan_return() {
        return can_return;
    }

    public void setCan_return(int can_return) {
        this.can_return = can_return;
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

    public void setRecommendCode(String recommend_code) {
        this.recommend_coach_id = recommend_code;
    }

    public String getFloor_price() {
        return floor_price;
    }

    public void setFloor_price(String floor_price) {
        this.floor_price = floor_price;
    }

    public boolean isSend() {
        return is_send;
    }

    public void setisSend(boolean is_send) {
        this.is_send = is_send;
    }

    public GoodsBean(String id, String name, String sku_code, String cover, String price,
                     String type, String amount, ArrayList<String> spec_value) {
        this.id = id;
        this.cover = cover;
        this.name = name;
        this.price = price;
        this.type = type;
        this.spec_value = spec_value;
        this.amount = amount;

    }

    @Override
    public String toString() {
        return "GoodsBean{" +
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

    public void setItem(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
        dest.writeString(this.recommend_coach_id);
        dest.writeParcelable(this.deliveryBean, flags);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.can_return);
        dest.writeString(this.product_id);
        dest.writeString(this.product_type);
        dest.writeByte(this.online ? (byte) 1 : (byte) 0);
        dest.writeInt(this.stock);
        dest.writeString(this.item);
        dest.writeByte(this.is_send ? (byte) 1 : (byte) 0);
    }

    protected GoodsBean(Parcel in) {
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
        this.recommend_coach_id = in.readString();
        this.deliveryBean = in.readParcelable(DeliveryBean.class.getClassLoader());
        this.checked = in.readByte() != 0;
        this.can_return = in.readInt();
        this.product_id = in.readString();
        this.product_type = in.readString();
        this.online = in.readByte() != 0;
        this.stock = in.readInt();
        this.item = in.readString();
        this.is_send = in.readByte() != 0;
    }

    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
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
