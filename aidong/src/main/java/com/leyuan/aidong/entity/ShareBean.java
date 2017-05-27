package com.leyuan.aidong.entity;

import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Md5Utils;

import java.io.Serializable;

/**
 * Created by user on 2017/5/26.
 */
public class ShareBean implements Serializable {

    private String title;
    private String content;
    private String imageUrl;
    private String webUrl;
    private String share_coupon;

    public ShareBean() {
    }

    public ShareBean(String title, String content, String imageUrl, String webUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getShare_coupon() {
        return share_coupon;
    }

    public void setShare_coupon(String share_coupon) {
        this.share_coupon = share_coupon;
    }

    public static String createCouponShare(String createdAt, String no, String share_coupons) {
        if (App.getInstance().isLogin()) {
            return ConstantUrl.URL_SHARE_COUPON + DateUtils.dateToLongTime(createdAt) + "&iorder="
                    + no + "&icoupon=" + share_coupons + "&iuser=" + Md5Utils.createMd("ad|" + App.getInstance().getUser().getId());
        } else {
            return ConstantUrl.URL_SHARE_COUPON + DateUtils.dateToLongTime(createdAt) + "&iorder="
                    + no + "&icoupon=" + share_coupons + "&iuser=";
        }

    }
}
