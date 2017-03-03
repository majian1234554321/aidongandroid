package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.video.CommentsVideoResult;
import com.leyuan.aidong.entity.video.LiveHomeResult;
import com.leyuan.aidong.entity.video.VideoDetailResult;
import com.leyuan.aidong.entity.video.VideoListResult;
import com.leyuan.aidong.entity.video.VideoRelationResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.LiveVideoService;

import rx.Observer;

import static com.leyuan.aidong.http.RxHelper.transform;

/**
 * Created by user on 2017/3/2.
 */

public class VideoModelImpl {

    LiveVideoService service;

    public VideoModelImpl() {
        service = RetrofitHelper.createApi(LiveVideoService.class);
    }

    public void getHomeLives(Observer<LiveHomeResult> subscriber) {
        service.getHomeLives()
                .compose(RxHelper.<LiveHomeResult>transform())
                .subscribe(subscriber);
    }

    public void getVideoList(Observer<VideoListResult> subscriber, String page, String type) {
        service.getVideoList(page, type)
                .compose(RxHelper.<VideoListResult>transform())
                .subscribe(subscriber);
    }

    public void getVideoDetail(Observer<VideoDetailResult> subscriber, String id) {
        service.getVideoDetail(id)
                .compose(RxHelper.<VideoDetailResult>transform())
                .subscribe(subscriber);
    }

    public void getVideoRelation(Observer<VideoRelationResult> subscriber, String id, String page) {
        service.getVideoRelation(id, page)
                .compose(RxHelper.<VideoRelationResult>transform())
                .subscribe(subscriber);
    }

    public void addCommont(Observer<Object> subscriber, String content, String series_id, String video_id) {
        service.addCommont(series_id, content, video_id)
                .compose(transform())
                .subscribe(subscriber);
    }

    public void addLikes(Observer<Object> subscriber, String series_id, String video_id) {
        service.addLikes(series_id, video_id)
                .compose(transform())
                .subscribe(subscriber);
    }

    public void getComments(Observer<CommentsVideoResult> subscriber, String series_id, String phase, String page) {
        service.getComments(series_id, phase, page)
                .compose(RxHelper.<CommentsVideoResult>transform())
                .subscribe(subscriber);
    }

}
