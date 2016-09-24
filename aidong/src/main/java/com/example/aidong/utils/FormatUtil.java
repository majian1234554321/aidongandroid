package com.example.aidong.utils;

/**
 * Created by song on 2016/8/24.
 */
public class FormatUtil {

    /**
     * 将String转换成int
     * @param str String
     * @return -1表示转换失败
     */
    public static int toParseInt(String str){
        int result = -1;
        try {
            result = Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
