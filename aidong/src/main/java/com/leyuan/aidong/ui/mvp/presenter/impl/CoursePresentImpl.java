package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.entity.CourseDetailData;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.AliPay;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.WeiXinPay;
import com.leyuan.aidong.ui.mvp.model.CourseModel;
import com.leyuan.aidong.ui.mvp.model.FollowModel;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.FollowModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.view.AppointmentInfoActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;

import rx.Subscriber;

/**
 * 课程
 * Created by song on 2016/9/21.
 */
public class CoursePresentImpl implements CoursePresent{
    private Context context;
    private CourseModel courseModel;
    private FollowModel followModel;

    private List<CourseBean> courseBeanList;
    private CourserFragmentView courserFragmentView;                //课程列表View层对象
    private CourseActivityView coursesActivityView;                 //课程列表View层对象
    private CourseDetailActivityView courseDetailActivityView;      //课程详情View层对象
    private AppointmentInfoActivityView appointActivityView;        //预约课程View层对象

    public CoursePresentImpl(Context context, CourseDetailActivityView view) {
        this.context = context;
        this.courseDetailActivityView = view;
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
        if(followModel == null){
            followModel = new FollowModelImpl();
        }
    }

    public CoursePresentImpl(Context context, CourserFragmentView view) {
        this.context = context;
        this.courserFragmentView = view;
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
    }

    public CoursePresentImpl(Context context, CourseActivityView view) {
        this.context = context;
        this.coursesActivityView = view;
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
    }

    public CoursePresentImpl(Context context) {
        this.context = context;
        if (courseModel == null) {
            courseModel = new CourseModelImpl(context);
        }
    }

    @Override
    public void getCategory() {
        coursesActivityView.setCategory(courseModel.getCategory());
    }

    @Override
    public void getBusinessCircle() {
        coursesActivityView.setBusinessCircle(courseModel.getBusinessCircle());
    }

    @Override
    public void commendLoadData(final SwitcherLayout switcherLayout, String day, String category, String landmark) {
        courseModel.getCourses(new CommonSubscriber<CourseData>(switcherLayout) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    switcherLayout.showContentLayout();
                    courserFragmentView.updateRecyclerView(courseData.getCourse());
                } else {
                    switcherLayout.showEmptyLayout();
                }
            }
        }, day, category, landmark, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String day, String category, String landmark) {
        courseModel.getCourses(new RefreshSubscriber<CourseData>(context) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
                    courserFragmentView.updateRecyclerView(courseData.getCourse());
                } else {
                    courserFragmentView.showEmptyView();
                }
            }
        }, day, category, landmark, Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, String day, String category, String landmark, int page) {
        courseModel.getCourses(new RequestMoreSubscriber<CourseData>(context, recyclerView, page) {
            @Override
            public void onNext(CourseData courseData) {
                if (courseData != null && !courseData.getCourse().isEmpty()) {
                    courseBeanList = courseData.getCourse();
                }
                if (!courseBeanList.isEmpty()) {
                    courserFragmentView.updateRecyclerView(courseBeanList);
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
    public void buyCourse(String id, String couponId, String integral, String payType,
                          String contactName, String contactMobile, final PayInterface.PayListener listener) {
        courseModel.buyCourse(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                String payType = payOrderData.getOrder().getPayType();
                PayInterface payInterface = "alipay".equals(payType) ? new AliPay(context,listener)
                        : new WeiXinPay(context,listener);
                payInterface.payOrder(payOrderData.getOrder());
            }
        }, id, couponId, integral, payType, contactName, contactMobile);
    }

    @Override
    public void addFollow(String id) {
        followModel.addFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    courseDetailActivityView.addFollow(baseBean);
                }
            }
        },id);
    }

    @Override
    public void cancelFollow(String id) {
        followModel.cancelFollow(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if(baseBean != null){
                    courseDetailActivityView.addFollow(baseBean);
                }
            }
        },id);
    }
}
