package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;

import rx.Subscriber;

/**
 * photos
 * Created by song on 2017/2/20.
 */
public interface PhotoWallModel {

    /**
     * add photos to photo wall
     * @param subscriber Subscriber
     * @param photos
     */
    void addPhotos(Subscriber<BaseBean> subscriber, String... photos);


    /**
     * delete photos from photo wall
     * @param subscriber Subscriber
     * @param id
     */
    void deletePhotos(Subscriber<BaseBean> subscriber, String id);
}
