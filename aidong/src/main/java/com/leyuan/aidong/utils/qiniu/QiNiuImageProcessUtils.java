package com.leyuan.aidong.utils.qiniu;

import android.content.Context;

import com.leyuan.aidong.utils.DensityUtil;

/**
 * 七牛图片处理
 * Created by song on 2017/2/14.
 */
public class QiNiuImageProcessUtils {

    public static String minWidthScale(Context context,String url, int width){
        width = (int) DensityUtil.px2dp(context,width);
        return new StringBuilder(url)
                .append("?imageView2")
                .append("/")
                .append(1)
                .append("/")
                .append("w")
                .append("/")
                .append(width)
                .toString();
    }
}
