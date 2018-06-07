package com.example.aidong.ui.mvp.model.impl;

import com.example.aidong .entity.video.CommentsVideoResult;
import com.example.aidong .entity.video.LiveHomeResult;
import com.example.aidong .entity.video.VideoDetailResult;
import com.example.aidong .entity.video.VideoListResult;
import com.example.aidong .entity.video.VideoRelationResult;
import com.example.aidong .http.RetrofitHelper;
import com.example.aidong .http.RxHelper;
import com.example.aidong .http.api.LiveVideoService;
import com.example.aidong .utils.Constant;

import rx.Observer;

import static com.example.aidong .http.RxHelper.transform;

/**
 * Created by user on 2017/3/2.
 */

public class VideoModelImpl {

    LiveVideoService service;

    public VideoModelImpl() {
        service = RetrofitHelper.createApi(LiveVideoService.class);
    }

    public void getHomeLives(Observer<LiveHomeResult.LiveHome> subscriber) {
        service.getHomeLives()
                .compose(RxHelper.<LiveHomeResult.LiveHome>transform())
                .subscribe(subscriber);
    }

    public void videoStatistics(Observer<Object> subscriber, String id, String type) {
        service.videoStatistics(id, type)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    public void videoPlayStatistics(Observer<Object> subscriber, String id) {
        service.videoStatistics(id, Constant.VIDEO)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    public void videoCoursePlayStatistics(Observer<Object> subscriber, String id) {
        service.videoStatistics(id, Constant.CATEGORY)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    public void livePlayStatistics(Observer<Object> subscriber, String id) {
        service.videoStatistics(id, Constant.LIVE)
                .compose(RxHelper.transform())
                .subscribe(subscriber);
    }

    public void getVideoList(Observer<VideoListResult> subscriber, String page, String type) {
        service.getVideoList(page, type)
                .compose(RxHelper.<VideoListResult>transform())
                .subscribe(subscriber);
    }

    public void getVideoDetail(Observer<VideoDetailResult> subscriber, String series_id) {
        service.getVideoDetail(series_id)
                .compose(RxHelper.<VideoDetailResult>transform())
                .subscribe(subscriber);
    }

    public void getVideoRelation(Observer<VideoRelationResult.VideoRelation> subscriber, String id, String page) {
        service.getVideoRelation(id, page)
                .compose(RxHelper.<VideoRelationResult.VideoRelation>transform())
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

    public void deleteLikes(Observer<Object> subscriber, String series_id, String video_id) {
        service.deleteLikes(series_id, video_id)
                .compose(transform())
                .subscribe(subscriber);
    }

    public void getComments(Observer<CommentsVideoResult> subscriber, String series_id, String phase, String page) {
        service.getComments(series_id, phase, page)
                .compose(RxHelper.<CommentsVideoResult>transform())
                .subscribe(subscriber);
    }

}
