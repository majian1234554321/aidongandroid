package com.leyuan.custompullrefresh.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by user on 2017/7/6.
 */
public class DensityUtil {
    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
