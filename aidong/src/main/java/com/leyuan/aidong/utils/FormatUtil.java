package com.leyuan.aidong.utils;


public class FormatUtil {

    private static final int HUNDRED_KILOMETER = 100 * 1000;

    /**
     * 将String转换成int
     *
     * @param str String
     * @return 默认返回0
     */
    public static int parseInt(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将String转换成int
     *
     * @param str String
     * @return 默认返回0
     */
    public static long parseLong(String str) {
        long result = 0;
        try {
            result = Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将String转换成float
     *
     * @param str String
     * @return -1.0表示转换失败
     */
    public static float parseFloat(String str) {
        float result = -1;
        try {
            result = Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 将String转换成float
     *
     * @param str String
     * @return -1.0表示转换失败
     */
    public static double parseDouble(String str) {
        double result = -1;
        try {
            result = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatDistance(double distance) {
        if (distance < 100) {
            return "<100m";
        } else if (distance < 1000) {
            return (int) distance + "m";
        } else if (distance < HUNDRED_KILOMETER) {
            return String.format("%.2f", (distance / 1000)) + "km";
        } else {
            return ">100km";
        }
    }

    public static String formatDistanceStore(double distance) {
        if (distance < 100) {
            return "<100m";
        } else if (distance < 1000) {
            return "<" + (int) distance + "m";
        } else if (distance < HUNDRED_KILOMETER) {
            return "<" + String.format("%.2f", (distance / 1000)) + "km";
        } else {
            return ">100km";
        }
    }
}
