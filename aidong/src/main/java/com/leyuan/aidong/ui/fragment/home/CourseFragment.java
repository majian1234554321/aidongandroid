package com.leyuan.aidong.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.home.adapter.CourseAdapter;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程列表
 * Created by song on 2016/11/1.
 */
public class CourseFragment extends BaseFragment implements CourserFragmentView, SwipeRefreshLayout.OnRefreshListener {
    private static final int HIDE_THRESHOLD = 80;
    private int scrolledDistance = 0;
    private boolean filterViewVisible = true;

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private CourseAdapter courseAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<CourseBean> data = new ArrayList<>();

    private String date;            //日期
    private String category;        //分类
    private String landmark;        //商圈

    private CoursePresent coursePresent;
    private AnimationListener animationListener;

    private TextView tvNoContent;

    public static CourseFragment newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date",date);
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageSize = 10;
        if(getArguments()!=null){
            date = getArguments().getString("date");
        }
        coursePresent = new CoursePresentImpl(getContext(),this);
        initRefreshLayout(view);
        initRecyclerView(view);
        coursePresent.commendLoadData(switcherLayout,date,category,landmark);
    }

    private void initRefreshLayout(View view) {
        tvNoContent = (TextView) view.findViewById(R.id.tv_no_content);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setProgressViewOffset(true,100,250);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_course);
        data = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(courseAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(getContext(),"refresh",Toast.LENGTH_LONG).show();
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullToRefreshData(date,category,landmark);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
               coursePresent.requestMoreData(recyclerView,pageSize,date,category,landmark,currPage);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD  && filterViewVisible) {           //手指向上滑动
                if(animationListener != null){
                    animationListener.onAnimatedHide();
                }
                filterViewVisible = false;
                scrolledDistance = 0;

            } else if (scrolledDistance < -HIDE_THRESHOLD && !filterViewVisible) {   //手指向下滑动
                if(animationListener != null){
                    animationListener.onAnimatedShow();
                }
                scrolledDistance = 0;
                filterViewVisible = true;
            }
            if ((filterViewVisible && dy > 0) || (!filterViewVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CourseBean> courseList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(courseList);
        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {

    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    public void scrollToTop(){
        filterViewVisible = true;
        recyclerView.scrollToPosition(0);
    }

    public void refreshCategory(final String category) {
        this.category = category;
        this.onRefresh();
    }

    public void refreshCircle(String businessCircle) {
        this.landmark = businessCircle;
        this.onRefresh();
    }

    public void setAnimationListener(AnimationListener listener) {
        this.animationListener = listener;
    }

    public interface AnimationListener{
        void onAnimatedShow();
        void onAnimatedHide();
    }
}
