package com.leyuan.aidong.utils.qiniu;

/**
 * 七牛图片处理
 * Created by song on 2017/2/14.
 */
public class QiNiuImageProcessUtils {

    public static String minWidthScale(String url,int width){
        StringBuilder result = new StringBuilder(url);
        result.append("?imageView2").append("/").append(1).append("/").append("w").append("/").append(width);
        return result.toString();
    }
}
