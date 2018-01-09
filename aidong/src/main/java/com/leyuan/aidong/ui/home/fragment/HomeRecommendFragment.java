package com.leyuan.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.PersonHorizontalAdapter;
import com.leyuan.aidong.adapter.home.HomeRecommendActivityAdapter;
import com.leyuan.aidong.adapter.home.HomeRecommendCourseAdapter;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.home.activity.CircleListActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.custompullrefresh.CustomRefreshLayout;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/12/28.
 */
public class HomeRecommendFragment extends BaseFragment implements View.OnClickListener {


    private CustomRefreshLayout refreshLayout;
    private NestedScrollView scrollView;
    private LinearLayout llContent;
    private BGABanner banner;
    private LinearLayout llSelectionCourse;
    private TextView txtSelectionCourse;
    private TextView txtSelectionCourseHint;
    private RecyclerView rvCourse;
    private LinearLayout llSelectionActivity;
    private TextView txtSelectionActivity;
    private TextView txtSelectionActivityHint;
    private RecyclerView rvActivity;
    private LinearLayout llStarCoach;
    private TextView txtStarCoach;
    private TextView txtStarCoachHint;
    private RecyclerView rvStarCoach;
    private TextView txtCheckAllActivity, txt_check_all_coach, txt_check_all_course;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLayout.setRefreshing(true);

        }
    };
    private HomeRecommendCourseAdapter courseAdapter;
    private HomeRecommendActivityAdapter activityAdapter;
    private PersonHorizontalAdapter coachAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_recommend, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        scrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        llContent = (LinearLayout) view.findViewById(R.id.ll_content);
        banner = (BGABanner) view.findViewById(R.id.banner);
        llSelectionCourse = (LinearLayout) view.findViewById(R.id.ll_selection_course);
        txtSelectionCourse = (TextView) view.findViewById(R.id.txt_selection_course);
        txtSelectionCourseHint = (TextView) view.findViewById(R.id.txt_selection_course_hint);
        rvCourse = (RecyclerView) view.findViewById(R.id.rv_course);
        llSelectionActivity = (LinearLayout) view.findViewById(R.id.ll_selection_activity);
        txtSelectionActivity = (TextView) view.findViewById(R.id.txt_selection_activity);
        txtSelectionActivityHint = (TextView) view.findViewById(R.id.txt_selection_activity_hint);
        rvActivity = (RecyclerView) view.findViewById(R.id.rv_activity);
        llStarCoach = (LinearLayout) view.findViewById(R.id.ll_star_coach);
        txtStarCoach = (TextView) view.findViewById(R.id.txt_star_coach);
        txtStarCoachHint = (TextView) view.findViewById(R.id.txt_star_coach_hint);
        rvStarCoach = (RecyclerView) view.findViewById(R.id.rv_star_coach);
        txt_check_all_course = (TextView) view.findViewById(R.id.txt_check_all_course);
        txt_check_all_coach = (TextView) view.findViewById(R.id.txt_check_all_coach);
        txtCheckAllActivity = (TextView) view.findViewById(R.id.txt_check_all_activity);

        rvCourse.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvActivity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvStarCoach.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvCourse.setNestedScrollingEnabled(false);
        rvActivity.setNestedScrollingEnabled(false);
        rvStarCoach.setNestedScrollingEnabled(false);

        courseAdapter = new HomeRecommendCourseAdapter(getActivity());
        activityAdapter = new HomeRecommendActivityAdapter(getActivity());
        coachAdapter = new PersonHorizontalAdapter(getActivity());

        rvCourse.setAdapter(courseAdapter);
        rvActivity.setAdapter(activityAdapter);
        rvStarCoach.setAdapter(coachAdapter);

        txt_check_all_course.setOnClickListener(this);
        txt_check_all_coach.setOnClickListener(this);
        txtCheckAllActivity.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_check_all_course:
                startActivity(new Intent(getActivity(),CircleListActivity.class));
                break;
            case R.id.txt_check_all_activity:
                startActivity(new Intent(getActivity(),CircleListActivity.class));
                break;
            case R.id.txt_check_all_coach:
                startActivity(new Intent(getActivity(),CircleListActivity.class));
                break;
        }
    }
}
