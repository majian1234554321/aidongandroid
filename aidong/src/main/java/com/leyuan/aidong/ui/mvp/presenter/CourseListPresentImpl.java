package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.course.CourseDataNew;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.CourseModelNewImpl;
import com.leyuan.aidong.ui.mvp.view.CourseListView;
import com.leyuan.aidong.utils.RequestResponseCount;

/**
 * Created by user on 2017/11/21.
 */
public class CourseListPresentImpl {
    private Context context;

    CourseModelNewImpl courseModel;
    private RequestResponseCount requestResponse;
    CourseListView listener;

    public CourseListPresentImpl(Context context, CourseListView listener) {
        this.context = context;
        courseModel = new CourseModelNewImpl(context);
        this.listener = listener;
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.requestResponse = requestResponse;
    }

    private void getCourseList( String store, String course, String time, String date, String page) {
        courseModel.getCourseList(new BaseSubscriber<CourseDataNew>(context) {
            @Override
            public void onNext(CourseDataNew courseDataNew) {
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, store, course, time, date, page);
    }

    public void pullRefreshCourseList( String store, String course, String time, String date) {
        courseModel.getCourseList(new BaseSubscriber<CourseDataNew>(context) {
            @Override
            public void onNext(CourseDataNew courseDataNew) {
                if (courseDataNew != null) {
                    listener.onGetRefreshCourseList(courseDataNew.getTimetable());
                } else {
                    listener.onGetRefreshCourseList(null);
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetRefreshCourseList(null);
            }
        }, store, course, time, date, "1");
    }

    public void loadMoreCourseList( String store, String course, String time, String date, String page) {
        courseModel.getCourseList(new BaseSubscriber<CourseDataNew>(context) {
            @Override
            public void onNext(CourseDataNew courseDataNew) {
                if (courseDataNew != null) {
                    listener.onGetMoreCourseList(courseDataNew.getTimetable());
                } else {
                    listener.onGetMoreCourseList(null);
                }

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                listener.onGetMoreCourseList(null);
            }
        },  store, course, time, date, page);
    }


}
