package com.leyuan.aidong.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.HomeRecommendCourseAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.course.CourseCircleDetailActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 2018/1/5.
 */
public class CircleCourseListFragment extends BaseFragment implements CourserFragmentView, OnRefreshListener, FollowView, HomeRecommendCourseAdapter.OnAttentionClickListener {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private HomeRecommendCourseAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private CoursePresentImpl coursePresent;
    private ArrayList<CourseBeanNew> data = new ArrayList<>();
    FollowPresentImpl followPresent;
    private int clickedFollowPosition;
    private int itemClickedPosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();
        coursePresent = new CoursePresentImpl(getContext(), this);

        coursePresent.commendLoadData(switcherLayout, null, null, null);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        followPresent = new FollowPresentImpl(getActivity());
        followPresent.setFollowListener(this);
    }


    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        adapter = new HomeRecommendCourseAdapter(getActivity());
        adapter.setOnAttentionClickListener(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullToRefreshData(null, null, null);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.requestMoreData(recyclerView, pageSize, null, null, null, currPage);
            }

        }
    };


    @Override
    public void refreshRecyclerViewData(ArrayList<CourseBeanNew> courseList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(courseList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void loadMoreRecyclerViewData(ArrayList<CourseBeanNew> courseList) {
        data.addAll(courseList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        View view = View.inflate(getContext(), R.layout.empty_course, null);
        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {

        if(!App.getInstance().isLogin()){
            UiManager.activityJump(getActivity(), LoginActivity.class);
            return;
        }


        if (followed) {
            followPresent.cancelFollow(id, Constant.COURSE);
        } else {
            followPresent.addFollow(id, Constant.COURSE);
        }
        this.clickedFollowPosition = position;

    }

    @Override
    public void onItemClick(String id, int position) {

        itemClickedPosition = position;

        CourseCircleDetailActivity.startForResult(this, id,Constant.REQUEST_COURSE_DETAIL);
//        UserInfoActivity.startForResult(this, id, Constant.REQUEST_USER_INFO);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            if (clickedFollowPosition < data.size() ) {

                data.get(clickedFollowPosition).setFollowed(true);
                adapter.notifyItemChanged(clickedFollowPosition);


                ToastGlobal.showShortConsecutive(R.string.attention_success);
            }
        }else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            if (clickedFollowPosition < data.size() ) {
                data.get(clickedFollowPosition).setFollowed(false);
                adapter.notifyItemChanged(clickedFollowPosition);
                ToastGlobal.showShortConsecutive(R.string.attention_cancel_success);

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_COURSE_DETAIL:

                    CircleCourseListFragment.this.data.get(itemClickedPosition).setFollowed(data.getBooleanExtra(Constant.FOLLOW,false));
                    Logger.i("follow", "onActivityResult follow = " + CircleCourseListFragment.this.data.get(itemClickedPosition).isFollowed());
                    adapter.notifyItemChanged(itemClickedPosition);


                    break;
            }
        }

    }

}
