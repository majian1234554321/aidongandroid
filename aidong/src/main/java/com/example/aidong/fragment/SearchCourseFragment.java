package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.CourseAdapter;
import com.leyuan.support.entity.CourseBean;
import com.leyuan.support.mvp.presenter.SearchPresent;
import com.leyuan.support.mvp.presenter.impl.SearchCourseFragmentPresentImpl;
import com.leyuan.support.mvp.view.SearchCourseFragmentView;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程搜索结果
 * Created by song on 2016/9/12.
 */
public class SearchCourseFragment extends BaseFragment implements SearchCourseFragmentView {

    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<CourseBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CourseAdapter courseAdapter;

    private SearchPresent present;
    private String  keyword;

    public static SearchCourseFragment newInstance(String searchContent){
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchContent);
        SearchCourseFragment fragment = new SearchCourseFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
        }
        pageSize = 20;
        present = new SearchCourseFragmentPresentImpl(getContext(),this);
        return inflater.inflate(R.layout.fragment_result,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadData(switcherLayout,keyword);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(keyword);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout,keyword);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
        data = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreData(recyclerView,keyword,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CourseBean> courseBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(courseBeanList);
        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
