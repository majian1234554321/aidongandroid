package com.example.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;

/**
 * 商品类型:营养品,健康餐饮,装备
 * Created by song on 2017/2/16.
 */
@StringDef({
        GOODS_EQUIPMENT,
        GOODS_NUTRITION,
        GOODS_FOODS,
        GOODS_TICKET

})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.METHOD})
public @interface GoodsType {

}
