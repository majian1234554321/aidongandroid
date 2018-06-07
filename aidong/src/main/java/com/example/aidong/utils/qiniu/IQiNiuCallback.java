package com.example.aidong.utils.qiniu;

import java.util.List;

/**
 * 上传七牛回调
 * Created by song on 2017/2/13.
 */
public interface IQiNiuCallback {
    /**
     * Successfully upload;
     * @param urls the media urls generate by qiNiu
     */
    void onSuccess(List<String> urls);

    /**
     * Error happened when upload to qiNiu;
     */
    void onFail();
}
