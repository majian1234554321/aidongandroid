package com.example.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.COUPON_CAMPAIGN;
import static com.example.aidong .utils.Constant.COUPON_CART;
import static com.example.aidong .utils.Constant.COUPON_COURSE;
import static com.example.aidong .utils.Constant.COUPON_EQUIPMENT;
import static com.example.aidong .utils.Constant.COUPON_NUTRITION;

/**
 * 获取优惠券的模块
 * from-商品类型(equipment|food|nutrition|course|campaign|cart)
 * Created by song on 2017/3/9.
 */
@StringDef({
        COUPON_EQUIPMENT,
        COUPON_NUTRITION,
        COUPON_COURSE,
        COUPON_CAMPAIGN,
        COUPON_CART
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
public @interface CouponPosition {
}
