package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.PhotoWallModel;
import com.leyuan.aidong.ui.mvp.model.impl.PhotoWallModelIMpl;
import com.leyuan.aidong.ui.mvp.presenter.PhotoWallPresent;
import com.leyuan.aidong.ui.mvp.view.UpdatePhotoWallActivityView;

/**
 * photo wall
 * Created by song on 2017/2/20.
 */
public class PhotoWallPresentImpl implements PhotoWallPresent{
    private Context context;
    private PhotoWallModel photoWallModel;
    private UpdatePhotoWallActivityView photoWallActivityView;

    public PhotoWallPresentImpl(Context context, UpdatePhotoWallActivityView view) {
        this.context = context;
        this.photoWallActivityView = view;
        photoWallModel = new PhotoWallModelIMpl();
    }

    @Override
    public void addPhotos(String... photos) {
        photoWallModel.addPhotos(new ProgressSubscriber<BaseBean>(context,false) {
            @Override
            public void onNext(BaseBean baseBean) {
                photoWallActivityView.addPhotosResult(baseBean);
            }
        },photos);
    }

    @Override
    public void deletePhotos(String id,final int position) {
        photoWallModel.deletePhotos(new ProgressSubscriber<BaseBean>(context,true) {
            @Override
            public void onNext(BaseBean baseBean) {
                photoWallActivityView.deletePhotoResult(baseBean,position);
            }
        },id);
    }
}
