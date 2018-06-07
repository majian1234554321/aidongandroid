package com.example.aidong.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.aidong .utils.Constant.HOME_IMAGE_AND_HORIZONTAL_LIST;
import static com.example.aidong .utils.Constant.HOME_TITLE_AND_VERTICAL_LIST;

/**
 * the item type of home pager
 * Created by song on 2017/2/21.
 */
@IntDef({
        HOME_IMAGE_AND_HORIZONTAL_LIST,
        HOME_TITLE_AND_VERTICAL_LIST
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER,ElementType.METHOD})
public @interface HomeItemType {

}
