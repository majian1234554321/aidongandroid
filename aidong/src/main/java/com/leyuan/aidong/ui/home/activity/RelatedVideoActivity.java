package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.MoreVideoAdapter;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.RelatedVideoActivityView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 相关视频
 * Created by song on 2017/4/21.
 */
public class RelatedVideoActivity extends BaseActivity implements RelatedVideoActivityView, SwipeRefreshLayout.OnRefreshListener {
    private ImageView ivBack;
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MoreVideoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapper;
    private List<CourseVideoBean> data = new ArrayList<>();
    private CoursePresent coursePresent;
    private String id;
    private int currPage = 1;

    public static void start(Context context,String id) {
        Intent starter = new Intent(context, RelatedVideoActivity.class);
        starter.putExtra("id",id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_video);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
        }
        coursePresent = new CoursePresentImpl(this,this);
        initView();
        setListener();
        coursePresent.pullToRefreshVideo(id);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_video);
        adapter = new MoreVideoAdapter(this);
        wrapper = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(wrapper);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullToRefreshVideo(id);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.loadMoreVideo(id,recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecycler(List<CourseVideoBean> courseVideoBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(courseVideoBeanList);
        adapter.setData(data);
        wrapper.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
