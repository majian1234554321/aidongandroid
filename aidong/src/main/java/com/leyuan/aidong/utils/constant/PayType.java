package com.leyuan.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import static com.leyuan.aidong.utils.Constant.PAY_ALI;
import static com.leyuan.aidong.utils.Constant.PAY_WEIXIN;

/**
 * 支付类型
 * Created by song on 2017/2/16.
 */
@StringDef({
        PAY_ALI,
        PAY_WEIXIN
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
public @interface PayType {

}
