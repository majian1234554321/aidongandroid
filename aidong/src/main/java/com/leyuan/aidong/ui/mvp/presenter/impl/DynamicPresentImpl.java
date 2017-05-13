package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.LikeData;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.DynamicModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.view.DynamicDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.DynamicParseUserView;
import com.leyuan.aidong.ui.mvp.view.PublishDynamicActivityView;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class DynamicPresentImpl implements DynamicPresent {
    private DynamicParseUserView dynamicParseUserView;
    private Context context;
    private DynamicModel dynamicModel;
    private FollowModel followModel;

    private SportCircleFragmentView sportCircleFragmentView;
    private PublishDynamicActivityView publishDynamicActivityView;
    private DynamicDetailActivityView dynamicDetailActivityView;
    private List<DynamicBean> dynamicBeanList = new ArrayList<>();
    private List<CommentBean> commentBeanList = new ArrayList<>();

    public DynamicPresentImpl(Context context, SportCircleFragmentView view) {
        this.context = context;
        this.sportCircleFragmentView = view;
        if (dynamicModel == null) {
            dynamicModel = new DynamicModelImpl();
        }
    }

    public DynamicPresentImpl(Context context, PublishDynamicActivityView view) {
        this.context = context;
        this.publishDynamicActivityView = view;
        if (dynamicModel == null) {
            dynamicModel = new DynamicModelImpl();
        }
    }

    public DynamicPresentImpl(Context context, DynamicDetailActivityView view) {
        this.context = context;
        this.dynamicDetailActivityView = view;
        if (dynamicModel == null) {
            dynamicModel = new DynamicModelImpl();
        }
    }

    public DynamicPresentImpl(Context context, DynamicParseUserView view) {
        this.context = context;
        this.dynamicParseUserView = view;
        if (dynamicModel == null) {
            dynamicModel = new DynamicModelImpl();
        }
    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout) {
        dynamicModel.getDynamics(new CommonSubscriber<DynamicsData>(switcherLayout) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData() {
        dynamicModel.getDynamics(new RefreshSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                }
            }
        }, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int size, int page) {
        dynamicModel.getDynamics(new RequestMoreSubscriber<DynamicsData>(context, recyclerView, size) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                }
                //没有更多数据了显示到底提示
                if (dynamicBeanList != null && dynamicBeanList.size() < size) {
                    sportCircleFragmentView.showEndFooterView();
                }
            }
        }, page);
    }

    @Override
    public void postDynamic(boolean isPhoto, String content, String... media) {
        if (isPhoto) {
            postImageDynamic(content, media);
        } else {
            postVideoDynamic(content, media[0]);
        }
    }

    private void postImageDynamic(String content, String... image) {
        dynamicModel.postDynamic(new ProgressSubscriber<BaseBean>(context, false) {
            @Override
            public void onNext(BaseBean baseBean) {
                publishDynamicActivityView.publishDynamicResult(baseBean);
            }
        }, content, null, image);
    }

    private void postVideoDynamic(String content, String video) {
        dynamicModel.postDynamic(new ProgressSubscriber<BaseBean>(context, false) {
            @Override
            public void onNext(BaseBean baseBean) {
                publishDynamicActivityView.publishDynamicResult(baseBean);
            }
        }, content, video, new String[]{});
    }

    @Override
    public void addComment(String id, String content) {
        dynamicModel.addComment(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                dynamicDetailActivityView.addCommentsResult(baseBean);
            }
        }, id, content);
    }

    @Override
    public void pullToRefreshComments(String id) {
        dynamicModel.getComments(new IsLoginSubscriber<CommentData>(context) {
            @Override
            public void onNext(CommentData commentData) {
                if (commentData != null && commentData.getComment() != null
                        && !commentData.getComment().isEmpty()) {
                    dynamicDetailActivityView.updateComments(commentData.getComment());
                } else {
                    dynamicDetailActivityView.showEmptyCommentView();
                }
            }
        }, id, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreComments(RecyclerView recyclerView, String id, int page, final int pageSize) {
        dynamicModel.getComments(new RequestMoreSubscriber<CommentData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(CommentData commentData) {
                if (commentData != null && commentData.getComment() != null) {
                    commentBeanList = commentData.getComment();
                }
                if (!commentBeanList.isEmpty()) {
                    dynamicDetailActivityView.updateComments(commentBeanList);
                }
                //没有更多数据了显示到底提示
                if (commentBeanList.size() < pageSize) {
                    dynamicDetailActivityView.showEndFooterView();
                }
            }
        }, id, page);
    }

    @Override
    public void addLike(String id, final int position) {
        dynamicModel.addLike(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (sportCircleFragmentView != null) {
                    sportCircleFragmentView.addLikeResult(position, baseBean);
                }
                if(dynamicDetailActivityView != null){
                    dynamicDetailActivityView.addLikeResult(position,baseBean);
                }
            }
        }, id);
    }

    @Override
    public void cancelLike(String id, final int position) {
        dynamicModel.cancelLike(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (sportCircleFragmentView != null) {
                    sportCircleFragmentView.cancelLikeResult(position, baseBean);
                }
                if(dynamicDetailActivityView != null){
                    dynamicDetailActivityView.cancelLikeResult(position,baseBean);
                }
            }
        }, id);
    }

    @Override
    public void getLikes(String dynamicId, final int page) {
        dynamicModel.getLikes(new ProgressSubscriber<LikeData>(context) {
            @Override
            public void onNext(LikeData likeData) {
                if (dynamicParseUserView != null) {
                    dynamicParseUserView.onGetUserData(likeData.getPublisher(), page);
                }
            }
        }, dynamicId, page);
    }

    @Override
    public void reportDynamic(String id, String type) {
        dynamicModel.reportDynamic(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                dynamicDetailActivityView.reportResult(baseBean);
            }
        },id,type);
    }

    @Override
    public void cancelFollow(String id) {
        if(followModel == null){
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    dynamicDetailActivityView.cancelFollowResult(baseBean);
                }
            }
        },id);
    }

    @Override
    public void addFollow(String id) {
        if(followModel == null){
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    dynamicDetailActivityView.addFollowResult(baseBean);
                }
            }
        },id);
    }

    @Override
    public void deleteDynamic(String id) {
        dynamicModel.deleteDynamic(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                dynamicDetailActivityView.deleteDynamicResult(baseBean);
            }
        },id);
    }
}
