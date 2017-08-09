package com.leyuan.aidong.utils.qiniu;


import com.leyuan.aidong.utils.qiniu.token.Auth;
import com.leyuan.aidong.utils.qiniu.token.StringMap;
import com.qiniu.android.utils.UrlSafeBase64;

/**
 * 生成七牛上传Token
 * Created by song on 2017/2/13.
 */
public class QiNiuTokenUtils {
    public static String ACCESS_KEY = "SOMBFhoOmUgnKcmLYPoYlZxUa4E7E5sJp7nW5YDU";
    public static String SECRET_KEY = "be2JUix4G-CayPHwXa6Wz4TD9E13Ffjc6QQH9qzn";
    public static String bucketName = "adong";//要上传的空间

    private static StringMap policy = new StringMap().put("persistentPipeline", "aidong_video");

    public static String getQiNiuToken() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);    //密钥配置
        return auth.uploadToken(bucketName);
    }

    public static String getQiNiuVideoAvthumbToken(String expectKey) {

        String base64key = UrlSafeBase64.encodeToString(bucketName +":"+expectKey);
//        StringMap policy = new StringMap().put("persistentOps", "avthumb/m3u8/noDomain/1/segtime/5|saveas/" + base64key)
//                .put("persistentPipeline", "aidong_video");
//
//        Logger.i("expectKey = " + expectKey + "   , key = " + base64key+"  ,  policy  persistentOps = " + policy.get("persistentOps")
//                + " ,persistentPipeline = " +  policy.get("persistentPipeline") );
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);    //密钥配置

        return auth.uploadToken(bucketName, expectKey, 3600, policy
                .put("persistentOps", "avthumb/m3u8/noDomain/1/segtime/5|saveas/" + base64key), true);
    }
}
