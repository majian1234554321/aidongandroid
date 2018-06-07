package com.example.aidong.ui.mvp.presenter;

/**
 * photo wall
 * Created by song on 2017/2/20.
 */
public interface PhotoWallPresent {

    /**
     *  add photos to photo wall
     * @param photos the url of photos
     */
    void addPhotos(String... photos);

    /**
     * delete photo from photo wall
     * @param id the id of photo
     */
    void deletePhotos(String id,int position);
}
