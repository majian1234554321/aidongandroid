package com.leyuan.aidong.entity;

import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Md5Utils;

import java.io.Serializable;

/**
 * Created by user on 2017/5/27.
 */
public class ShareData implements Serializable {
    ShareCouponInfo share_coupons;

    public ShareCouponInfo getShare_coupons() {
        return share_coupons;
    }

    public void setShare_coupons(ShareCouponInfo share_coupons) {
        this.share_coupons = share_coupons;
    }


    public class ShareCouponInfo implements Serializable {
        String createdAt;
        String no;
        String coupons;// 优惠券,
        String title;//分享标题,
        String image;// 分享图片,
        String content;//  分享内容
        String shareUrl;

        public String getCoupons() {
            return coupons;
        }

        public void setCoupons(String coupons) {
            this.coupons = coupons;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getShareUrl() {
            if (App.getInstance().isLogin()) {
                return ConstantUrl.URL_SHARE_COUPON + DateUtils.dateToLongTime(createdAt) + "&iorder="
                        + no + "&icoupon=" + coupons + "&iuser=" + Md5Utils.createMd("ad|" + App.getInstance().getUser().getId());
            } else {
                return ConstantUrl.URL_SHARE_COUPON + DateUtils.dateToLongTime(createdAt) + "&iorder="
                        + no + "&icoupon=" + coupons + "&iuser=0";
            }
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        @Override
        public String toString() {
            return "ShareCouponInfo{" +
                    "createdAt='" + createdAt + '\'' +
                    ", no='" + no + '\'' +
                    ", coupons='" + coupons + '\'' +
                    ", title='" + title + '\'' +
                    ", image='" + image + '\'' +
                    ", content='" + content + '\'' +
                    ", shareUrl='" + shareUrl + '\'' +
                    '}';
        }
    }


}
