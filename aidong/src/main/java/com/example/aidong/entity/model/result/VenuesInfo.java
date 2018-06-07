package com.example.aidong.entity.model.result;

import org.json.JSONObject;


public class VenuesInfo {

    private String chStoreName;
    private String address;
    private String distance;
    private String count;
    private String brandLogo;
    private String storeLogo;

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getChStoreName() {
        return chStoreName;
    }

    public void setChStoreName(String chStoreName) {
        this.chStoreName = chStoreName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String storeId;

    public void parseJson(JSONObject jsonObject) {

        setChStoreName(jsonObject.optString("chStoreName"));
        setStoreId(jsonObject.optString("storeId"));
        setCount(jsonObject.optString("count"));
        setAddress(jsonObject.optString("address"));
        setDistance(jsonObject.optString("distance"));
        setBrandLogo(jsonObject.optString("brandLogo"));
    }
}
