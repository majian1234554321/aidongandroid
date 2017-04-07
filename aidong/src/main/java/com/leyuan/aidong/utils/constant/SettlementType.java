package com.leyuan.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.leyuan.aidong.utils.Constant.SETTLEMENT_CART;
import static com.leyuan.aidong.utils.Constant.SETTLEMENT_EQUIPMENT_IMMEDIATELY;
import static com.leyuan.aidong.utils.Constant.SETTLEMENT_NURTURE_IMMEDIATELY;

/**
 * 结算类型:购物车,立即购买营养品,立即购买装备
 * Created by song on 2017/3/6.
 */
@StringDef({
        SETTLEMENT_CART,
        SETTLEMENT_NURTURE_IMMEDIATELY,
        SETTLEMENT_EQUIPMENT_IMMEDIATELY
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
public @interface SettlementType {

}
