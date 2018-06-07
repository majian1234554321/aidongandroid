package com.example.aidong.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.DYNAMIC_FIVE_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_FOUR_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_NONE;
import static com.example.aidong .utils.Constant.DYNAMIC_ONE_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_SIX_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_THREE_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_TWO_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_VIDEO;

/**
 * 动态类型
 * Created by song on 2017/2/16.
 */
@IntDef({
        DYNAMIC_VIDEO,
        DYNAMIC_ONE_IMAGE,
        DYNAMIC_TWO_IMAGE,
        DYNAMIC_THREE_IMAGE,
        DYNAMIC_FOUR_IMAGE,
        DYNAMIC_FIVE_IMAGE,
        DYNAMIC_SIX_IMAGE,
        DYNAMIC_MULTI_IMAGE,
        DYNAMIC_NONE
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.METHOD})
public @interface DynamicType {

}
