package com.example.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.course.CourseAppointListResult;
import com.example.aidong .entity.course.CourseAppointResult;
import com.example.aidong .entity.course.CourseQueueResult;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .module.pay.PayInterface;
import com.example.aidong .module.pay.PayUtils;
import com.example.aidong .ui.mvp.model.impl.CourseModelNewImpl;
import com.example.aidong .ui.mvp.view.AppointmentCourseDetailView;
import com.example.aidong .ui.mvp.view.AppointmentCourseListView;
import com.example.aidong .ui.mvp.view.CourseQueueView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .utils.constant.PayType;

/**
 * Created by user on 2017/11/23.
 */
public class AppointmentCoursePresentImpl {
    Context context;
    AppointmentCourseListView callback;
    CourseModelNewImpl courseModel;
    private CourseQueueView courseQueueCallback;
    AppointmentCourseDetailView callbackAppointDetail;

    public AppointmentCoursePresentImpl(Context context) {
        this.context = context;
        courseModel = new CourseModelNewImpl(context);
    }

    public void setCourseQueueViewCallback(CourseQueueView callback) {
        this.courseQueueCallback = callback;
    }

    public AppointmentCoursePresentImpl(Context context, AppointmentCourseListView callback, EmptyView emptyView) {
        this.context = context;
        this.callback = callback;
        this.emptyView = emptyView;
        courseModel = new CourseModelNewImpl(context);
    }

    public EmptyView emptyView;

    /*public AppointmentCoursePresentImpl(Context context, EmptyView emptyView) {
        this.context = context;
        this.emptyView = emptyView;
        courseModel = new CourseModelNewImpl(context);
    }*/


    public AppointmentCoursePresentImpl(Context context, AppointmentCourseDetailView callback) {
        this.context = context;
        this.callbackAppointDetail = callback;
        courseModel = new CourseModelNewImpl(context);
    }


    public void getCourseAppointDetail(String appointId) {
        courseModel.getCourseAppointDetail(new BaseSubscriber<CourseAppointResult>(context) {
            @Override
            public void onNext(CourseAppointResult courseAppointResult) {
                if (callbackAppointDetail != null)
                    callbackAppointDetail.onGetAppointDetailResult(courseAppointResult);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callbackAppointDetail != null)
                    callbackAppointDetail.onGetAppointDetailResult(null);
            }
        }, appointId);
    }

    public void checkCourseAppoint(String courseId) {
        courseModel.lookCourseAppoint(new BaseSubscriber<CourseAppointResult>(context) {
            @Override
            public void onNext(CourseAppointResult courseAppointResult) {
                if (callbackAppointDetail != null)
                    callbackAppointDetail.onGetAppointDetailResult(courseAppointResult);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callbackAppointDetail != null)
                    callbackAppointDetail.onGetAppointDetailResult(null);
            }
        }, courseId);
    }

    public void getFirstPageCourseAppointList(String list) {
        courseModel.getCourseAppointList(new BaseSubscriber<CourseAppointListResult>(context) {
            @Override
            public void onNext(CourseAppointListResult courseAppointListResult) {
                if (courseAppointListResult != null && courseAppointListResult.getAppointment().size() > 0)
                    callback.onFirstPageCourseAppointList(courseAppointListResult.getAppointment());
                else {
                    if (emptyView != null)
                        emptyView.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.onFirstPageCourseAppointList(null);
            }
        }, list, "1");
    }

    public void loadMoreCourseAppointList(String list, String page) {
        courseModel.getCourseAppointList(new BaseSubscriber<CourseAppointListResult>(context) {
            @Override
            public void onNext(CourseAppointListResult courseAppointListResult) {
                callback.onLoadMoreCourseAppointList(courseAppointListResult.getAppointment());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                callback.onLoadMoreCourseAppointList(null);
            }
        }, list, page);
    }

    public void cancelCourseAppoint(String appointId) {
        courseModel.cancelCourseAppoint(appointId, new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (callback != null)
                    callback.oncancelCourseAppointResult(baseBean);

                if (callbackAppointDetail != null)
                    callbackAppointDetail.oncancelCourseAppointResult(baseBean);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callback != null)
                    callback.oncancelCourseAppointResult(null);

                if (callbackAppointDetail != null)
                    callbackAppointDetail.oncancelCourseAppointResult(null);
            }
        });
    }

    public void deleteCourseAppoint(String appointId) {
        courseModel.deleteCourseAppoint(appointId, new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getStatus() == 1) {
                    if (callback != null)
                        callback.onDeleteCourseAppoint(true);

                    if (callbackAppointDetail != null)
                        callbackAppointDetail.onDeleteCourseAppoint(true);
                } else {
                    if (callback != null)
                        callback.onDeleteCourseAppoint(false);
                    if (callbackAppointDetail != null)
                        callbackAppointDetail.onDeleteCourseAppoint(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callback != null)
                    callback.onDeleteCourseAppoint(false);
                if (callbackAppointDetail != null)
                    callbackAppointDetail.onDeleteCourseAppoint(false);
            }
        });
    }


    public void cancelCourseQueue(String queueId) {
        courseModel.cancelCourseQueue(queueId, new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (callback != null)
                    callback.onCancelCourseQueue(baseBean);
                if (courseQueueCallback != null) {
                    courseQueueCallback.onCancelCourseQueue(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callback != null)
                    callback.onCancelCourseQueue(null);
                if (courseQueueCallback != null) {
                    courseQueueCallback.onCancelCourseQueue(null);
                }
            }
        });

    }


    public void deleteCourseQueue(String queueId) {
        courseModel.deleteCourseQueue(queueId, new BaseSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getStatus() == 1) {
                    if (callback != null) {
                        callback.onDeleteCourseQueue(true);
                    }

                    if (courseQueueCallback != null) {
                        courseQueueCallback.onDeleteCourseQueue(true);
                    }

                } else {
                    if (callback != null) {
                        callback.onDeleteCourseQueue(false);
                    }

                    if (courseQueueCallback != null) {
                        courseQueueCallback.onDeleteCourseQueue(false);
                    }

                }


            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callback != null) {
                    callback.onDeleteCourseQueue(false);
                }

                if (courseQueueCallback != null) {
                    courseQueueCallback.onDeleteCourseQueue(false);
                }
            }
        });

    }


    public void getCourseQueueDetailFromCourse(String courseId) {
        courseModel.getCourseQueueDetailFromCourse(new BaseSubscriber<CourseQueueResult>(context) {
            @Override
            public void onNext(CourseQueueResult courseQueueResult) {
                if (courseQueueCallback != null) {
                    courseQueueCallback.ongetCourseQueueDetail(courseQueueResult.getQueue());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (courseQueueCallback != null) {
                    courseQueueCallback.ongetCourseQueueDetail(null);
                }
            }
        }, courseId);
    }

    public void getCourseQueueDetailFromQueue(String queueId) {

        courseModel.getCourseQueueDetailFromQueue(new BaseSubscriber<CourseQueueResult>(context) {
            @Override
            public void onNext(CourseQueueResult courseQueueResult) {
                if (courseQueueCallback != null) {
                    courseQueueCallback.ongetCourseQueueDetail(courseQueueResult.getQueue());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (courseQueueCallback != null) {
                    courseQueueCallback.ongetCourseQueueDetail(null);
                }
            }
        }, queueId);
    }


    public void confirmAppointCourse(String courseId, String coupon_id, @PayType final String payType, final PayInterface.PayListener listener, String seat) {
        courseModel.confirmAppointCourse(
                new BaseSubscriber<CourseAppointResult>(context) {

                    @Override
                    public void onNext(CourseAppointResult courseAppointResult) {
//                        callback.onCourseAppointResult(courseAppointResult.getAppointment());
                        PayUtils.pay(context, payType, courseAppointResult.getPayment(), listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        listener.onFail("0", null);
//                        callback.onCourseAppointResult(null);
                    }
                }
                , courseId, coupon_id, seat);
    }

}
