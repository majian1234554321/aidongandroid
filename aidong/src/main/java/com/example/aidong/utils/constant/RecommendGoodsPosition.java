package com.example.aidong.utils.constant;

import android.support.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.RECOMMEND_CART;
import static com.example.aidong .utils.Constant.RECOMMEND_EQUIPMENT;
import static com.example.aidong .utils.Constant.RECOMMEND_NUTRITION;
import static com.example.aidong .utils.Constant.RECOMMEND_ORDER;

/**
 * 推荐商品位置 : 营养品模块,装备模块 ,购物车模块
 * Created by song on 2017/3/6.
 */
@StringDef({
        RECOMMEND_EQUIPMENT,
        RECOMMEND_NUTRITION,
        RECOMMEND_CART,
        RECOMMEND_ORDER
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.FIELD,ElementType.METHOD})
public @interface RecommendGoodsPosition {

}


