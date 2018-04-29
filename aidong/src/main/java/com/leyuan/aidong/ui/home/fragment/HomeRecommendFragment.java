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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.CoachAttentionAdapter;
import com.leyuan.aidong.adapter.home.HomeRecommendActivityAdapter;
import com.leyuan.aidong.adapter.home.HomeRecommendCourseAdapter;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.HomeData;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.course.CourseCircleDetailActivity;
import com.leyuan.aidong.ui.home.activity.CircleListActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomeRecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.ui.mvp.view.HomeRecommendView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 2017/12/28.
 */
public class HomeRecommendFragment extends BaseFragment implements View.OnClickListener, HomeRecommendView, HomeRecommendCourseAdapter.OnAttentionClickListener, FollowView {


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
    HomeRecommendPresentImpl homePresent;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {
                initHomeBanner();
                refreshData();
            } else {
                refreshData();
            }

        }
    };

    private HomeRecommendCourseAdapter courseAdapter;
    private HomeRecommendActivityAdapter activityAdapter;
    private CoachAttentionAdapter coachAdapter;
    FollowPresentImpl followPresent;
    private int itemClickedPosition;
    private HomeData homeData;
    private SwitcherLayout switcherLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_recommend, container, false);
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
       // initSwitcherLayout();
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

        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((BannerBean) model).getImage(), (ImageView) view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                ((MainActivity) getActivity()).toTargetActivity((BannerBean) model);
            }
        });

        rvCourse.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvActivity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvStarCoach.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvCourse.setNestedScrollingEnabled(false);
        rvActivity.setNestedScrollingEnabled(false);
        rvStarCoach.setNestedScrollingEnabled(false);

        courseAdapter = new HomeRecommendCourseAdapter(getActivity());
        activityAdapter = new HomeRecommendActivityAdapter(getActivity());
        coachAdapter = new CoachAttentionAdapter(getActivity());
        courseAdapter.setOnAttentionClickListener(this);


        rvCourse.setAdapter(courseAdapter);
        rvActivity.setAdapter(activityAdapter);
        rvStarCoach.setAdapter(coachAdapter);

        txt_check_all_course.setOnClickListener(this);
        txt_check_all_coach.setOnClickListener(this);
        txtCheckAllActivity.setOnClickListener(this);


        homePresent = new HomeRecommendPresentImpl(getActivity(), this);
        homePresent.getRecommendList2();
        initHomeBanner();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    private void initHomeBanner() {
        List<BannerBean> bannerBeanList = SystemInfoUtils.getHomeBanner(getActivity());

        if (bannerBeanList != null && !bannerBeanList.isEmpty()) {
            banner.setVisibility(View.VISIBLE);
            banner.setAutoPlayAble(bannerBeanList.size() > 1);
            banner.setData(bannerBeanList, null);
        } else {
            banner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        followPresent = new FollowPresentImpl(getActivity());
        followPresent.setFollowListener(this);
    }

    private void refreshData() {
        refreshLayout.setRefreshing(true);
        homePresent.getRecommendList2();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_check_all_course:
                CircleListActivity.start(getActivity(), 0);

                break;
            case R.id.txt_check_all_activity:
                CircleListActivity.start(getActivity(), 1);
                break;
            case R.id.txt_check_all_coach:
                CircleListActivity.start(getActivity(), 2);
                break;
        }
    }

    @Override
    public void onGetData(HomeData homeData) {
        this.homeData = homeData;
        refreshLayout.setRefreshing(false);
        if (homeData == null) return;

        if (homeData.getCourse() != null && !homeData.getCourse().isEmpty()) {
            courseAdapter.setData(homeData.getCourse());



            llSelectionCourse.setVisibility(View.VISIBLE);

        } else {
            llSelectionCourse.setVisibility(View.GONE);
        }

        if (homeData.getCampaign() != null && !homeData.getCampaign().isEmpty()) {
            activityAdapter.setData(homeData.getCampaign());
            llSelectionActivity.setVisibility(View.VISIBLE);
        } else {
            llSelectionActivity.setVisibility(View.GONE);
        }

        if (homeData.getCoach() != null && !homeData.getCoach().isEmpty()) {
            coachAdapter.setData(homeData.getCoach());
            llStarCoach.setVisibility(View.VISIBLE);
        } else {
            llStarCoach.setVisibility(View.GONE);
        }

    }


    @Override
    public void addFollowResult(BaseBean baseBean) {

        if (baseBean.getStatus() == 1) {
            homePresent.getRecommendList2();
        }

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {

        if (baseBean.getStatus() == 1) {
            homePresent.getRecommendList2();
        }
    }

    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {

        if (!App.getInstance().isLogin()) {
            UiManager.activityJump(getActivity(), LoginActivity.class);
            return;
        }

        if (followed) {
            followPresent.cancelFollow(id, Constant.COURSE);
        } else {
            followPresent.addFollow(id, Constant.COURSE);
        }

    }

    @Override
    public void onItemClick(String id, int position) {
        itemClickedPosition = position;

        CourseCircleDetailActivity.startForResult(this, id,Constant.REQUEST_COURSE_DETAIL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_COURSE_DETAIL:

                    if (homeData.getCourse()!=null){
                        homeData.getCourse().get(itemClickedPosition).setFollowed(data.getBooleanExtra(Constant.FOLLOW,false));
                        Logger.i("follow", "onActivityResult follow = " +  homeData.getCourse().get(itemClickedPosition).isFollowed());
                        courseAdapter.notifyItemChanged(itemClickedPosition);
                    }



                    break;
            }
        }

    }

}
