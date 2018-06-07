package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .ui.mvp.model.PhotoWallModel;
import com.example.aidong .ui.mvp.model.impl.PhotoWallModelIMpl;
import com.example.aidong .ui.mvp.presenter.PhotoWallPresent;
import com.example.aidong .ui.mvp.view.UpdatePhotoWallActivityView;

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
                photoWallActivityView.deleteNetPhotoResult(baseBean,position);
            }
        },id);
    }
}
