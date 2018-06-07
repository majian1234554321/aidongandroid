package com.example.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.DELIVERY_EXPRESS;
import static com.example.aidong .utils.Constant.DELIVERY_SELF;

/**
 * 交货类型:快递,自提
 * Created by song on 2017/2/16.
 */
@StringDef({
        DELIVERY_EXPRESS,
        DELIVERY_SELF
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
public @interface DeliveryType {

}
