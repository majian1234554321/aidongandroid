package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.video.CommentsVideoResult;
import com.leyuan.aidong.entity.video.VideoDetailResult;
import com.leyuan.aidong.entity.video.VideoListResult;
import com.leyuan.aidong.entity.video.VideoRelationResult;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.VideoModelImpl;
import com.leyuan.aidong.ui.mvp.view.VideoCommentView;
import com.leyuan.aidong.ui.mvp.view.VideoDetailView;
import com.leyuan.aidong.ui.mvp.view.VideoListViewLister;
import com.leyuan.aidong.ui.mvp.view.VideoRelationView;

/**
 * Created by user on 2017/3/2.
 */

public class VideoPresenterImpl {
    public static final String family = "topic";
    public static final String professional = "depth";
    public static final String celebrity = "celebrity";
    Context context;
    VideoModelImpl videoModel;
    VideoListViewLister viewListener;
    VideoDetailView videoDetailView;
    VideoRelationView videoRelationView;
    VideoCommentView videoCommentView;


    public VideoPresenterImpl(Context context) {
        this.context = context;
        videoModel = new VideoModelImpl();
    }


    public void setVideoCommentView(VideoCommentView videoCommentView) {
        this.videoCommentView = videoCommentView;
    }

    public void setVideoListViewListener(VideoListViewLister listener) {
        this.viewListener = listener;
    }

    public void setVideoDetailView(VideoDetailView videoDetailView) {
        this.videoDetailView = videoDetailView;
    }

    public void setVideoRelationView(VideoRelationView videoRelationView) {
        this.videoRelationView = videoRelationView;
    }

    public void getVideoList(String page, String type) {
        videoModel.getVideoList(new ProgressSubscriber<VideoListResult>(context) {
            @Override
            public void onNext(VideoListResult videoListResult) {
                if (viewListener != null)
                    viewListener.onGetVideoList(videoListResult.getVideo());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (viewListener != null)
                    viewListener.onGetVideoList(null);
            }
        }, page, type);
    }


    public void getMoreVideoList(String page, String type) {
        videoModel.getVideoList(new ProgressSubscriber<VideoListResult>(context) {
            @Override
            public void onNext(VideoListResult videoListResult) {
                if (viewListener != null)
                    viewListener.onGetMoreVideoList(videoListResult.getVideo());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (viewListener != null)
                    viewListener.onGetMoreVideoList(null);
            }
        }, page, type);
    }


    public void getVideoDetail(String series_id) {
        videoModel.getVideoDetail(new ProgressSubscriber<VideoDetailResult>(context) {
            @Override
            public void onNext(VideoDetailResult videoDetailResult) {
                if (videoDetailView != null) {
                    videoDetailView.onGetVideoDetailList(videoDetailResult.getVideo());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoDetailView != null) {
                    videoDetailView.onGetVideoDetailList(null);
                }
            }
        }, series_id);
    }

    public void likeVideo(String series_id, String video_id, final int currentItem) {
        videoModel.addLikes(new ProgressSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                if (videoDetailView != null) {
                    videoDetailView.onLikesResult(true, currentItem);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoDetailView != null) {
                    videoDetailView.onLikesResult(false, currentItem);
                }
            }
        }, series_id, video_id);
    }

    public void deleteLikeVideo(String series_id, String video_id, final int currentItem) {
        videoModel.deleteLikes(new ProgressSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                if (videoDetailView != null) {
                    videoDetailView.onDeleteLikesResult(true, currentItem);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoDetailView != null) {
                    videoDetailView.onDeleteLikesResult(false, currentItem);
                }
            }
        }, series_id, video_id);
    }


    public void getComments(String series_id, String phase, String page) {
        videoModel.getComments(new ProgressSubscriber<CommentsVideoResult>(context) {
            @Override
            public void onNext(CommentsVideoResult commentsVideoResult) {
                if (videoCommentView != null) {
                    videoCommentView.onGetCommentList(commentsVideoResult.getComment());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoCommentView != null) {
                    videoCommentView.onGetCommentList(null);
                }
            }
        }, series_id, phase, page);
    }

    public void commentVideo(String content, String series_id, String video_id) {
        videoModel.addCommont(new ProgressSubscriber<Object>(context) {
            @Override
            public void onNext(Object o) {
                if (videoCommentView != null)
                    videoCommentView.onPostCommentResult(true);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoCommentView != null)
                    videoCommentView.onPostCommentResult(false);
            }
        }, content, series_id, video_id);
    }

    public void getVideoRelation(String id, String page) {
        videoModel.getVideoRelation(new ProgressSubscriber<VideoRelationResult.VideoRelation>(context) {
            @Override
            public void onNext(VideoRelationResult.VideoRelation videoRelation) {
                if (videoRelationView != null)
                    videoRelationView.onGetRelation(videoRelation);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoRelationView != null)
                    videoRelationView.onGetRelation(null);
            }
        }, id, page);
    }

}
