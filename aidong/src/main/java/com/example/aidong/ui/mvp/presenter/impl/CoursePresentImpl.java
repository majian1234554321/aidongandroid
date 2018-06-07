package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CourseDetailData;
import com.example.aidong .entity.CourseVideoBean;
import com.example.aidong .entity.PayOrderBean;
import com.example.aidong .entity.ShareData;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .entity.course.CourseDataNew;
import com.example.aidong .entity.data.CouponData;
import com.example.aidong .entity.data.CourseVideoData;
import com.example.aidong .entity.data.PayOrderData;
import com.example.aidong .http.subscriber.CommonSubscriber;
import com.example.aidong .http.subscriber.IsLoginSubscriber;
import com.example.aidong .http.subscriber.ProgressSubscriber;
import com.example.aidong .http.subscriber.RefreshSubscriber;
import com.example.aidong .http.subscriber.RequestMoreSubscriber;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.PayUtils;
import com.example.aidong .ui.mvp.model.CouponModel;
import com.example.aidong .ui.mvp.model.CourseModel;
import com.example.aidong .ui.mvp.model.FollowModel;
import com.example.aidong .ui.mvp.model.impl.CouponModelImpl;
import com.example.aidong .ui.mvp.model.impl.CourseModelImpl;
import com.example.aidong .ui.mvp.model.impl.FollowModelImpl;
import com.example.aidong .ui.mvp.presenter.CoursePresent;
import com.example.aidong .ui.mvp.view.AppointCourseActivityView;
import com.example.aidong .ui.mvp.view.CourseActivityView;
import com.example.aidong .ui.mvp.view.CourseDetailActivityView;
import com.example.aidong .ui.mvp.view.CourseVideoDetailActivityView;
import com.example.aidong .ui.mvp.view.CourseVideoView;
import com.example.aidong .ui.mvp.view.CourserFragmentView;
import com.example.aidong .ui.mvp.view.RelatedVideoActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
@Deprecated
public class CoursePresentImpl implements CoursePresent {
    private Context context;
    private CourseModel courseModel;
    private FollowModel followModel;
    private CouponModel couponModel;

    private ArrayList<CourseBeanNew> courseBeanList;
    private CourserFragmentView courserFragmentView;                //课程列表View层对象
    private CourseActivityView coursesActivityView;                 //课程列表View层对象
    private CourseDetailActivityView courseDetailActivityView;      //课程详情View层对象
    private AppointCourseActivityView appointCourseActivityView;    //预约课程View层对象
    private CourseVideoDetailActivityView videoDetailActivityView;
    private RelatedVideoActivityView relatedVideoActivityView;
    private ShareData.ShareCouponInfo shareCouponInfo = new ShareData().new ShareCouponInfo();
    private CourseVideoView courseVideoView;


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
        courseModel.getCourses(new CommonSubscriber<CourseDataNew>(context, switcherLayout) {
            @Override
            public void onNext(CourseDataNew courseData) {
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
        courseModel.getCourses(new RefreshSubscriber<CourseDataNew>(context) {
            @Override
            public void onNext(CourseDataNew courseData) {
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
        courseModel.getCourses(new RequestMoreSubscriber<CourseDataNew>(context, recyclerView, page) {
            @Override
            public void onNext(CourseDataNew courseData) {
                if (courseData != null && !courseData.getCourse().isEmpty()) {
                    courseBeanList = courseData.getCourse();
                }
                if (courseBeanList != null && !courseBeanList.isEmpty()) {
                    courserFragmentView.loadMoreRecyclerViewData(courseBeanList);
                }
                //没有更多数据了显示到底提示
                if (courseBeanList != null && courseBeanList.size() < pageSize) {
                    courserFragmentView.showEndFooterView();
                }
            }
        }, day, category, landmark, page);
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
    public void getCourseDetail(String id) {
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        courseModel.getCourseDetail(new ProgressSubscriber<CourseDetailData>(context) {

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(CourseDetailData courseDetailData) {
                if (courseDetailData != null) {
                    courseDetailActivityView.setCourseDetail(courseDetailData.getCourse());
                } else {
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
        }, id,Constant.COURSE);
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
        }, id,Constant.COURSE);
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
        courseModel.getCourses(new RefreshSubscriber<CourseDataNew>(context) {
            @Override
            public void onNext(CourseDataNew courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    if (coursesActivityView != null) {
//                        coursesActivityView.setScrollPosition(courseData.getDate());
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
                if (videoDetailActivityView != null)
                    videoDetailActivityView.showLoadingView();
            }


            @Override
            public void onNext(CourseVideoData courseVideoData) {
                if (courseVideoData != null && !courseVideoData.getVideos().isEmpty()) {
                    if (videoDetailActivityView != null)
                        videoDetailActivityView.updateRelateVideo(courseVideoData.getTitle(), courseVideoData.getVideos());
                }

                if (videoDetailActivityView != null)
                    videoDetailActivityView.showContentView();

                if(courseVideoView != null){
                    courseVideoView.updateRelateVideo(courseVideoData.getTitle(), courseVideoData.getVideos());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (videoDetailActivityView != null)
                    videoDetailActivityView.showErrorView();
            }

        }, "relation", id, 1, videoId);
    }

    @Override
    public void pullToRefreshVideo(String id, String videoId) {
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
        }, "all", id, 1, videoId);
    }

    @Override
    public void loadMoreVideo(String id, String videoId, RecyclerView recyclerView, final int pageSize, int page) {
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
        }, "all", id, page, videoId);
    }

    public void setCourseVideoViewListener(CourseVideoView courseVideoView) {
        this.courseVideoView = courseVideoView;
    }
}
