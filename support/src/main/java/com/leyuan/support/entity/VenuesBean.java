package com.leyuan.support.entity;

/**
 * 场馆实体
 * Created by pc on 2016/8/2.
 */
public class VenuesBean {
    private String gym_id;          //场馆编号
    private String name;            //场馆名字
    private String logo;            //场馆封面
    private String address;         //场馆地址
    private String distance;        //距离
    private String price;           //价格

    public String getGym_id() {
        return gym_id;
    }

    public void setGym_id(String gym_id) {
        this.gym_id = gym_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "VenuesBean{" +
                "gym_id='" + gym_id + '\'' +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
