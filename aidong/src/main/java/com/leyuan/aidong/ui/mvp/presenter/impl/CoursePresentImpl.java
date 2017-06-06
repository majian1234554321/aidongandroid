package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.CourseVideoData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.CourseModel;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.view.AppointCourseActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseVideoDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.ui.mvp.view.RelatedVideoActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CoursePresentImpl implements CoursePresent {
    private Context context;
    private CourseModel courseModel;
    private FollowModel followModel;
    private CouponModel couponModel;

    private List<CourseBean> courseBeanList;
    private CourserFragmentView courserFragmentView;                //课程列表View层对象
    private CourseActivityView coursesActivityView;                 //课程列表View层对象
    private CourseDetailActivityView courseDetailActivityView;      //课程详情View层对象
    private AppointCourseActivityView appointCourseActivityView;    //预约课程View层对象
    private CourseVideoDetailActivityView videoDetailActivityView;
    private RelatedVideoActivityView relatedVideoActivityView;
    private ShareData.ShareCouponInfo shareCouponInfo = new ShareData().new ShareCouponInfo();


    public CoursePresentImpl(Context context, RelatedVideoActivityView view) {
        this.context = context;
        this.relatedVideoActivityView = view;
    }

    public CoursePresentImpl(Context context, CourseVideoDetailActivityView view) {
        this.context = context;
        this.videoDetailActivityView = view;
    }

    public CoursePresentImpl(Context context, CourseDetailActivityView view) {
        this.context = context;
        this.courseDetailActivityView = view;
    }

    public CoursePresentImpl(Context context, CourserFragmentView view) {
        this.context = context;
        this.courserFragmentView = view;
    }

    public CoursePresentImpl(Context context, CourseActivityView view) {
        this.context = context;
        this.coursesActivityView = view;
    }

    public CoursePresentImpl(Context context, AppointCourseActivityView view) {
        this.context = context;
        this.appointCourseActivityView = view;
    }

    @Override
    public void getCategory() {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        coursesActivityView.setCategory(courseModel.getCategory());
    }

    @Override
    public void getBusinessCircle() {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        coursesActivityView.setBusinessCircle(courseModel.getBusinessCircle());
    }

    @Override
    public void commendLoadData(final SwitcherLayout switcherLayout, String day, String category, String landmark) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourses(new CommonSubscriber<CourseData>(context, switcherLayout) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    switcherLayout.showContentLayout();
                    courserFragmentView.refreshRecyclerViewData(courseData.getCourse());
                } else {
                    courserFragmentView.showEmptyView();
                }
            }
        }, day, category, landmark, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String day, String category, String landmark) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourses(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    courserFragmentView.refreshRecyclerViewData(courseData.getCourse());
                } else {
                    courserFragmentView.showEmptyView();
                }
            }
        }, day, category, landmark, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String day, String category, String landmark, int page) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourses(new RequestMoreSubscriber<CourseData>(context, recyclerView, page) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && !courseData.getCourse().isEmpty()) {
                    courseBeanList = courseData.getCourse();
                }
                if (!courseBeanList.isEmpty()) {
                    courserFragmentView.loadMoreRecyclerViewData(courseBeanList);
                }
                //没有更多数据了显示到底提示
                if (courseBeanList != null && courseBeanList.size() < pageSize) {
                    courserFragmentView.showEndFooterView();
                }
            }
        }, day, category, landmark, pageSize);
    }

    @Override
    public void getCourseDetail(final SwitcherLayout switcherLayout, String id) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourseDetail(new Subscriber<CourseDetailData>() {
            @Override
            public void onStart() {
                switcherLayout.showLoadingLayout();
            }

            @Override
            public void onCompleted() {
                switcherLayout.showContentLayout();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                switcherLayout.showExceptionLayout();
            }

            @Override
            public void onNext(CourseDetailData courseDetailData) {
                if (courseDetailData != null) {
                    switcherLayout.showContentLayout();
                    courseDetailActivityView.setCourseDetail(courseDetailData.getCourse());
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, id);
    }

    @Override
    public void buyCourse(String id, @Nullable String couponId, @Nullable String integral, String payType,
                          String contactName, String contactMobile, final PayInterface.PayListener listener, String isVip) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.buyCourse(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                createShareBeanByOrder(payOrderData);
                PayUtils.pay(context, payOrderData.getOrder(), listener);


            }
        }, id, couponId, integral, payType, contactName, contactMobile, isVip);
    }

    private void createShareBeanByOrder(PayOrderData payOrderData) {
        if (payOrderData.getOrder() != null) {
            PayOrderBean payOrderBean = payOrderData.getOrder();
            shareCouponInfo.setCreatedAt(payOrderBean.getCreatedAt());
            shareCouponInfo.setNo(payOrderBean.getId());
        }
    }

    @Override
    public ShareData.ShareCouponInfo getShareInfo() {
        return shareCouponInfo;
    }

    @Override
    public void addFollow(String id) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    courseDetailActivityView.addFollowResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void cancelFollow(String id) {
        if (followModel == null) {
            followModel = new FollowModelImpl();
        }
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    courseDetailActivityView.cancelFollowResult(baseBean);
                }
            }
        }, id);
    }

    @Override
    public void getSpecifyCourseCoupon(String id) {
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }

        couponModel.getGoodsAvailableCoupon(new ProgressSubscriber<CouponData>(context, false) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    appointCourseActivityView.setCourseCouponResult(couponData.getCoupon());//maybe null
                }
            }
        }, Constant.COUPON_COURSE + "_" + id + "_1");
    }

    @Override
    public void getScrollDate(String day, String category) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourses(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    if (coursesActivityView != null) {
                        coursesActivityView.setScrollPosition(courseData.getDate());
                    }
                }
            }
        }, day, category, null, Constant.PAGE_FIRST);
    }


    @Override
    public void getRelateCourseVideo(String id, String videoId) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourseVideo(new IsLoginSubscriber<CourseVideoData>(context) {

            @Override
            public void onStart() {
                super.onStart();
                videoDetailActivityView.showLoadingView();
            }


            @Override
            public void onNext(CourseVideoData courseVideoData) {
                if (courseVideoData != null && !courseVideoData.getVideos().isEmpty()) {
                    videoDetailActivityView.updateRelateVideo(courseVideoData.getTitle(),courseVideoData.getVideos());
                }

                videoDetailActivityView.showContentView();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                videoDetailActivityView.showErrorView();
            }

        }, "relation", id, 1, videoId);
    }

    @Override
    public void pullToRefreshVideo(String id) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourseVideo(new IsLoginSubscriber<CourseVideoData>(context) {
            @Override
            public void onNext(CourseVideoData courseVideoData) {
                List<CourseVideoBean> courseVideoBeanList = new ArrayList<>();
                if (courseVideoData != null) {
                    courseVideoBeanList = courseVideoData.getVideos();
                }
                if (!courseVideoBeanList.isEmpty()) {
                    relatedVideoActivityView.updateRecycler(courseVideoBeanList);
                } else {
                    relatedVideoActivityView.showEmptyView();
                }
            }
        }, "all", id, 1, "");
    }

    @Override
    public void loadMoreVideo(String id, RecyclerView recyclerView, final int pageSize, int page) {
        courseModel.getCourseVideo(new RequestMoreSubscriber<CourseVideoData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(CourseVideoData courseVideoData) {
                List<CourseVideoBean> courseVideoBeanList = new ArrayList<>();
                if (courseVideoData != null) {
                    courseVideoBeanList = courseVideoData.getVideos();
                }
                if (!courseVideoBeanList.isEmpty()) {
                    relatedVideoActivityView.updateRecycler(courseVideoBeanList);
                }
                //没有更多数据了显示到底提示
                if (courseVideoBeanList.size() < pageSize) {
                    relatedVideoActivityView.showEndFooterView();
                }
            }
        }, "all", id, page, "");
    }

}
