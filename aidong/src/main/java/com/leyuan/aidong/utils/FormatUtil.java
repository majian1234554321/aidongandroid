package com.leyuan.aidong.utils;


public class FormatUtil {

    /**
     * 将String转换成int
     * @param str String
     * @return  默认返回0
     */
    public static int parseInt(String str){
        int result = 0;
        try {
            result = Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将String转换成float
     * @param str String
     * @return -1.0表示转换失败
     */
    public static float parseFloat(String str){
        float result = -1;
        try {
            result = Float.parseFloat(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 将String转换成float
     * @param str String
     * @return -1.0表示转换失败
     */
    public static double parseDouble(String str){
        double result = -1;
        try {
            result = Double.parseDouble(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
