package com.leyuan.aidong.utils.qiniu;


import com.leyuan.aidong.utils.qiniu.token.Auth;

/**
 * 生成七牛上传Token
 * Created by song on 2017/2/13.
 */
public class QiNiuTokenUtils {
    public static String ACCESS_KEY = "SOMBFhoOmUgnKcmLYPoYlZxUa4E7E5sJp7nW5YDU";
    public static String SECRET_KEY = "be2JUix4G-CayPHwXa6Wz4TD9E13Ffjc6QQH9qzn";
    public static String bucketName = "aidong";                        //要上传的空间

    public static String getQiNiuToken(){
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);    //密钥配置
        return auth.uploadToken(bucketName);
    }
}
