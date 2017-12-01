package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.course.AppointmentCourseListQueueAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseAppointBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.impl.AppointmentCoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointmentCourseListView;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/16.
 */
public class AppointmentFragmentCourseChildQueue extends BaseFragment implements AppointmentCourseListView {
    protected static final String TAG = "AppointmentFragmentEventChild";
    private CustomRefreshLayout refreshLayout;
    private RecyclerView rvAppointment;
    private RelativeLayout rlEmpty;
    private AppointmentCoursePresentImpl present;
    private int currPage;
    private AppointmentCourseListQueueAdapter appointmentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    RecyclerView recyclerView;
    RelativeLayout emptyLayout;
    String type = CourseAppointBean.HISTORY;
    private ArrayList<CourseAppointBean> appointData;


    public static AppointmentFragmentCourseChildQueue newInstance(String type) {
        AppointmentFragmentCourseChildQueue fragment = new AppointmentFragmentCourseChildQueue();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.i(TAG, "onActivityCreated");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_envent_child, null);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }

        initView(view);

        present = new AppointmentCoursePresentImpl(getContext(), this);
        present.getFirstPageCourseAppointList(type);
    }

    private void initView(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        rvAppointment = (RecyclerView) view.findViewById(R.id.rv_appointment);
        rlEmpty = (RelativeLayout) view.findViewById(R.id.rl_empty);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.getFirstPageCourseAppointList(type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_appointment);
        emptyLayout = (RelativeLayout) view.findViewById(R.id.rl_empty);

        appointmentAdapter = new AppointmentCourseListQueueAdapter(getContext(), type);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(appointmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        appointmentAdapter.setAppointmentListener(new AppointCallback());
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (appointData != null && appointData.size() >= pageSize) {
                present.loadMoreCourseAppointList(type, currPage + "");
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.i(TAG, "setUserVisibleHint = " + isVisibleToUser);
    }


    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG, "onResume = ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestroy");
    }

    @Override
    public void onFirstPageCourseAppointList(ArrayList<CourseAppointBean> appointment) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        appointData = appointment;
        appointmentAdapter.setData(appointData);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreCourseAppointList(ArrayList<CourseAppointBean> appointment) {
        appointData.addAll(appointment);
        appointmentAdapter.setData(appointData);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void oncancelCourseAppointResult(BaseBean courseAppointResult) {
        if (courseAppointResult != null) {
            present.getFirstPageCourseAppointList(type);
            Toast.makeText(getContext(), "取消成功", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteCourseAppoint(boolean courseAppointResult) {

    }

    @Override
    public void onCancelCourseQueue(BaseBean baseBean) {

    }

    @Override
    public void onDeleteCourseQueue(boolean courseQueueResult) {
        if (courseQueueResult) {
            present.getFirstPageCourseAppointList(type);
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_LONG).show();
        }
    }

    private class AppointCallback implements AppointmentCourseListQueueAdapter.AppointmentListener {
        @Override
        public void onPayOrder(String type, String id) {

        }

        @Override
        public void onDeleteOrder(String id) {
            present.deleteCourseQueue(id);
        }

        @Override
        public void onSignImmedialtely(int position) {

        }

        @Override
        public void onCancelQueue(int position) {
            CourseAppointBean courseAppointBean = appointData.get(position);
            present.cancelCourseQueue(courseAppointBean.getId());
        }

        @Override
        public void onCancelAppoint(String id) {

        }


        @Override
        public void onCountdownEnd(int position) {

        }
    }
}
