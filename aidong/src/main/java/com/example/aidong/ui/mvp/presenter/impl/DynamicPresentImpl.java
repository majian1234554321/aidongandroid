package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.DynamicBean;
import com.example.aidong .entity.data.CommentData;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .entity.data.DynamicsSingleData;
import com.example.aidong .entity.data.LikeData;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong.http.subscriber.Progress2Subscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .ui.mvp.model.FollowModel;
import com.example.aidong .ui.mvp.model.impl.DynamicModelImpl;
import com.example.aidong .ui.mvp.model.impl.FollowModelImpl;
import com.example.aidong .ui.mvp.presenter.DynamicPresent;
import com.example.aidong .ui.mvp.view.DynamicDetailActivityView;
import com.example.aidong .ui.mvp.view.DynamicParseUserView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .ui.mvp.view.PublishDynamicActivityView;
import com.example.aidong .ui.mvp.view.SportCircleFragmentExtendView;
import com.example.aidong .ui.mvp.view.SportCircleFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class DynamicPresentImpl implements DynamicPresent {
    private DynamicParseUserView dynamicParseUserView;
    private Context context;
    private DynamicModelImpl dynamicModel;
    private FollowModel followModel;

    private SportCircleFragmentView sportCircleFragmentView;
    private SportCircleFragmentExtendView sportCircleFragmentExtendView;
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






    public DynamicPresentImpl(Context context, SportCircleFragmentView view, SportCircleFragmentExtendView sportCircleFragmentExtendView) {
        this.context = context;
        this.sportCircleFragmentView = view;
        this.sportCircleFragmentExtendView = sportCircleFragmentExtendView;
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

    EmptyView emptyView;
    public DynamicPresentImpl(Context context, DynamicDetailActivityView view,EmptyView emptyView) {
        this.context = context;
        this.dynamicDetailActivityView = view;
        this.emptyView = emptyView;
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
        dynamicModel.getDynamics(new CommonSubscriber<DynamicsData>(context, switcherLayout) {
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

    //    @Override
//    public void commonLoadDataFollow(final SwitcherLayout switcherLayout) {
//        dynamicModel.getDynamicsFollow(new CommonSubscriber<DynamicsData>(context, switcherLayout) {
//            @Override
//            public void onNext(DynamicsData dynamicsData) {
//                if (dynamicsData != null) {
//                    dynamicBeanList = dynamicsData.getDynamic();
//                }
//                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
//                    switcherLayout.showContentLayout();
//                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
//                } else {
//                    switcherLayout.showEmptyLayout();
//                }
//            }
//        }, Constant.PAGE_FIRST);
//    }
    @Override
    public void commonLoadDataFollow(final SwitcherLayout switcherLayout) {
        dynamicModel.getDynamicsFollow(new CommonSubscriber<DynamicsData>(context, switcherLayout) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                switcherLayout.showContentLayout();
                sportCircleFragmentView.updateRecyclerView(dynamicBeanList);

            }

            @Override
            public void onError(Throwable e) {
//                super.onError(e);
                switcherLayout.showContentLayout();
                sportCircleFragmentView.updateRecyclerView(null);
            }
        }, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshDataFollow() {
        dynamicModel.getDynamicsFollow(new RefreshSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
            }

            @Override
            public void onError(Throwable e) {
                sportCircleFragmentView.updateRecyclerView(null);
            }

        }, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreDataFollow(RecyclerView recyclerView, final int size, int page) {
        dynamicModel.getDynamicsFollow(new RequestMoreSubscriber<DynamicsData>(context, recyclerView, size) {
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

    public void pullToRefreshRelativeDynamics(String type, String link_id) {
        dynamicModel.getRelativeDynamics(new RefreshSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (dynamicsData != null) {
                    dynamicBeanList = dynamicsData.getDynamic();
                }
                if (dynamicBeanList != null && !dynamicBeanList.isEmpty()) {
                    sportCircleFragmentView.updateRecyclerView(dynamicBeanList);
                } else {
                    if (sportCircleFragmentExtendView != null)
                        sportCircleFragmentExtendView.noRelevantData();
                }


            }
        }, type, link_id, Constant.PAGE_FIRST);
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

    public void requestMoreRelativeData(RecyclerView recyclerView, final int size, int page, String type, String link_id) {
        dynamicModel.getRelativeDynamics(new RequestMoreSubscriber<DynamicsData>(context, recyclerView, size) {
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
        }, type, link_id, page);
    }


    @Override
    public void getDynamicDetail(String id) {
        dynamicModel.getDynamicDetail(new BaseSubscriber<DynamicsSingleData>(context) {
            @Override
            public void onNext(DynamicsSingleData dynamicBean) {
                if (dynamicBean != null&&dynamicBean.getDynamic()!=null) {
                    dynamicDetailActivityView.onGetDynamicDetail(dynamicBean.getDynamic());
                } else {
                    if(emptyView!=null){
                        emptyView.showEmptyView();
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(emptyView!=null){
                    emptyView.showEmptyView();
                }
            }
        }, id);

    }

//    @Override
//    public void postDynamic(boolean isPhoto, String content, String type,
//                            String link_id,
//                            String position_name, String latitude, String longitude, String... media) {
//        if (isPhoto) {
//            postImageDynamic(content, type, link_id, position_name, latitude, longitude, media);
//        } else {
//            postVideoDynamic(content, type, link_id, position_name, latitude, longitude, media[0]);
//        }
//    }


    @Override
    public void postDynamic(boolean isPhoto, String content, String type, String link_id,
                            String position_name, String latitude, String longitude,
                            Map<String, String> itUser, String... media) {
        ArrayList<String> images = new ArrayList<>();
        Collections.addAll(images, media);
        if (isPhoto) {

            dynamicModel.postDynamic(new ProgressSubscriber<DynamicsData>(context, false) {
                @Override
                public void onNext(DynamicsData baseBean) {
                    publishDynamicActivityView.publishDynamicResult(baseBean);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    publishDynamicActivityView.publishDynamicResult(null);
                }
            }, null, content, type, link_id, position_name, latitude, longitude, images, itUser);

        } else {
            dynamicModel.postDynamic(new ProgressSubscriber<DynamicsData>(context, false) {
                @Override
                public void onNext(DynamicsData baseBean) {
                    publishDynamicActivityView.publishDynamicResult(baseBean);
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    publishDynamicActivityView.publishDynamicResult(null);
                }

            }, media[0], content, type, link_id, position_name, latitude, longitude, null, itUser);

        }
    }


//    private void postImageDynamic(String content, String type,
//                                  String link_id,
//                                  String position_name, String latitude, String longitude, String... image) {
//        dynamicModel.postDynamic(new ProgressSubscriber<BaseBean>(context, false) {
//            @Override
//            public void onNext(BaseBean baseBean) {
//                publishDynamicActivityView.publishDynamicResult(baseBean);
//            }
//        }, content, null, type, link_id, position_name, latitude, longitude, image);
//    }
//
//    private void postVideoDynamic(String content, String type,
//                                  String link_id,
//                                  String position_name, String latitude, String longitude, String video) {
//        dynamicModel.postDynamic(new ProgressSubscriber<BaseBean>(context, false) {
//            @Override
//            public void onNext(BaseBean baseBean) {
//                publishDynamicActivityView.publishDynamicResult(baseBean);
//            }
//        }, content, video, type, link_id, position_name, latitude, longitude, new String[]{});
//    }

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
    public void addComment(String id, String content, Map<String, String> itUser, Map<String, String> replyUserMap) {

        Map<String, String> allUser = new HashMap<>();
        allUser.putAll(itUser);
        allUser.putAll(replyUserMap);

        dynamicModel.addComment(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {

                dynamicDetailActivityView.addCommentsResult(baseBean);
            }
        }, id, content, allUser);
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
        dynamicModel.addLike(new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (sportCircleFragmentView != null) {
                    sportCircleFragmentView.addLikeResult(position, baseBean);
                }
                if (dynamicDetailActivityView != null) {
                    dynamicDetailActivityView.addLikeResult(position, baseBean);
                }
            }
        }, id);
    }

    @Override
    public void cancelLike(String id, final int position) {
        dynamicModel.cancelLike(new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (sportCircleFragmentView != null) {
                    sportCircleFragmentView.cancelLikeResult(position, baseBean);
                }
                if (dynamicDetailActivityView != null) {
                    dynamicDetailActivityView.cancelLikeResult(position, baseBean);
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
        }, id, type);
    }

    @Override
    public void cancelFollow(String id, String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    dynamicDetailActivityView.cancelFollowResult(baseBean);
                }
            }
        }, id, type);
    }

    @Override
    public void addFollow(String id, String type) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new Progress2Subscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    dynamicDetailActivityView.addFollowResult(baseBean);
                }
            }
        }, id, type);
    }

    @Override
    public void deleteDynamic(String id) {
        dynamicModel.deleteDynamic(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                dynamicDetailActivityView.deleteDynamicResult(baseBean);
            }
        }, id);
    }
}
