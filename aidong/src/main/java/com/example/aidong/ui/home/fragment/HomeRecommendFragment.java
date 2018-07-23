package com.example.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.CoachAttentionAdapter;
import com.example.aidong .adapter.home.HomeRecommendActivityAdapter;
import com.example.aidong .adapter.home.HomeRecommendCourseAdapter;
import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .entity.data.HomeData;

import com.example.aidong .ui.BaseFragment;
import com.example.aidong.ui.DisplayActivity;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.course.CourseCircleDetailActivity;
import com.example.aidong .ui.home.activity.CircleListActivity;

import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.HomeRecommendPresentImpl;
import com.example.aidong .ui.mvp.view.FollowView;
import com.example.aidong .ui.mvp.view.HomeRecommendView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.SystemInfoUtils;

import com.example.aidong .widget.SwitcherLayout;

import com.example.aidong .widget.card.OverLayCardLayoutManager;
import com.example.aidong .widget.card.RenRenCallback;


import com.example.aidong .widget.card.UniversalAdapter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static android.app.Activity.RESULT_OK;
import static com.example.aidong.ui.App.context;

/**
 * Created by user on 2017/12/28.
 */
public class HomeRecommendFragment extends BaseFragment implements View.OnClickListener, HomeRecommendView, UniversalAdapter.OnItemClickListener, FollowView {


    private CustomRefreshLayout refreshLayout;


    private BGABanner banner;




    private LinearLayout llSelectionActivity;


    private RecyclerView rvActivity;
    private LinearLayout llStarCoach;


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
    private RenRenCallback callback;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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


    private RecyclerView mActivity_review;
    private UniversalAdapter mAdatper;


    ArrayList<CourseBeanNew> list = new ArrayList<>();

    ArrayList<CourseBeanNew> templist = new ArrayList<>();

    private void setData(final ArrayList<CourseBeanNew> courseBeanNews) {
        list.clear();
        templist.clear();

        templist.addAll(courseBeanNews);



        Collections.reverse(templist);

        list.addAll(templist);


        mAdatper = new UniversalAdapter(list, getContext());
        mAdatper.setOnItemClickListener(this);
        mActivity_review.setAdapter(mAdatper);

        callback.setSwipeListener(new RenRenCallback.OnSwipeListener() {
            @Override
            public void onSwiped(int adapterPosition, int direction) {
                if (direction == ItemTouchHelper.DOWN || direction == ItemTouchHelper.UP) {
                    list.add(0, list.remove(adapterPosition));
                    mActivity_review.getAdapter().notifyDataSetChanged();
                } else {

//                    if (list.size() > 0) {
//                        list.add(list.get(0));
//                        list.remove(0);
//
//
//                        if (flag){
//                        Collections.reverse(list);
//                        flag = false;
//                        }
//                    }

                    list.add(0, list.remove(adapterPosition));


                }


                mAdatper.notifyDataSetChanged();
               // Collections.reverse(list);

            }

            @Override
            public void onSwipeTo(RecyclerView.ViewHolder viewHolder, float offset) {

            }
        });
        new ItemTouchHelper(callback).attachToRecyclerView(mActivity_review);

    }


    public boolean flag = true;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mActivity_review = (RecyclerView) view.findViewById(R.id.activity_review);

        mActivity_review.setLayoutManager(new OverLayCardLayoutManager(getContext()));


        mActivity_review.setNestedScrollingEnabled(false);



        callback = new RenRenCallback();


        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        // initSwitcherLayout();


        banner = (BGABanner) view.findViewById(R.id.banner);




        llSelectionActivity = (LinearLayout) view.findViewById(R.id.ll_selection_activity);


        rvActivity = (RecyclerView) view.findViewById(R.id.rv_activity);
        llStarCoach = (LinearLayout) view.findViewById(R.id.ll_star_coach);


        rvStarCoach = (RecyclerView) view.findViewById(R.id.rv_star_coach);
        txt_check_all_course = (TextView) view.findViewById(R.id.txt_check_all_course);
        txt_check_all_coach = (TextView) view.findViewById(R.id.txt_check_all_coach);
        txtCheckAllActivity = (TextView) view.findViewById(R.id.txt_check_all_activity);

        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage2(((BannerBean) model).getImage(), (ImageView) view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                ((MainActivity) getActivity()).toTargetActivity((BannerBean) model);
//                if ("23".equals(((BannerBean) model).getType())){
//                    Intent intent = new Intent(context,DisplayActivity.class);
//                    intent.putExtra("TYPE","DetailsActivityH5Fragment");
//                    intent.putExtra("id",((BannerBean) model).campaign_detail);
//                    context.startActivity(intent);
//                }else{
//                    ((MainActivity) getActivity()).toTargetActivity((BannerBean) model);
//                }


            }
        });


        rvActivity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvStarCoach.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        rvActivity.setNestedScrollingEnabled(false);
        rvStarCoach.setNestedScrollingEnabled(false);

        courseAdapter = new HomeRecommendCourseAdapter(getActivity());
        activityAdapter = new HomeRecommendActivityAdapter(getActivity());
        coachAdapter = new CoachAttentionAdapter(getActivity());



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
            setData(homeData.getCourse());


            //llSelectionCourse.setVisibility(View.VISIBLE);

        } else {
            //llSelectionCourse.setVisibility(View.GONE);
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

//    @Override
//    public void onCourseAttentionClick(String id, int position, boolean followed) {
//
//        if (!App.getInstance().isLogin()) {
//            UiManager.activityJump(getActivity(), LoginActivity.class);
//            return;
//        }
//
//        if (followed) {
//            followPresent.cancelFollow(id, Constant.COURSE);
//        } else {
//            followPresent.addFollow(id, Constant.COURSE);
//        }
//
//    }

    @Override
    public void onItemClick(String id, int position,View view ) {
        itemClickedPosition = position;

        CourseCircleDetailActivity.startForResult(this, id, Constant.REQUEST_COURSE_DETAIL);


//        startActivity( new  Intent(getActivity(), CourseCircleDetailActivity.class).putExtra("id", id),
//                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity() ,view,"share").toBundle());


//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, OPTION_IMAGE);
//        ActivityCompat.startActivity(activity, intent, options.toBundle());



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

                    if (homeData.getCourse() != null) {
                        homeData.getCourse().get(itemClickedPosition).setFollowed(data.getBooleanExtra(Constant.FOLLOW, false));
                        Logger.i("follow", "onActivityResult follow = " + homeData.getCourse().get(itemClickedPosition).isFollowed());
                        courseAdapter.notifyItemChanged(itemClickedPosition);
                    }


                    break;
            }
        }

    }

}
