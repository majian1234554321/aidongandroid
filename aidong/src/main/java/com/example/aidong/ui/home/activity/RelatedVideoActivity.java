package com.example.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.video.MoreVideoAdapter;
import com.example.aidong .entity.CourseVideoBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.CoursePresent;
import com.example.aidong .ui.mvp.presenter.impl.CoursePresentImpl;
import com.example.aidong .ui.mvp.view.RelatedVideoActivityView;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 相关视频
 * Created by song on 2017/4/21.
 */
public class RelatedVideoActivity extends BaseActivity implements RelatedVideoActivityView,
    MoreVideoAdapter.OnItemClickListener, OnRefreshListener {
    private TextView tvTitle;
    private ImageView ivBack;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private MoreVideoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapper;
    private List<CourseVideoBean> data = new ArrayList<>();
    private CoursePresent coursePresent;
    private String id;
    private String videoId;
    private String title;
    private int currPage = 1;

    public static void start(Context context,String id,String title,String videoId) {
        Intent starter = new Intent(context, RelatedVideoActivity.class);
        starter.putExtra("id",id);
        starter.putExtra("videoId",videoId);
        starter.putExtra("title",title);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_video);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            videoId = getIntent().getStringExtra("videoId");
        }
        coursePresent = new CoursePresentImpl(this,this);
        initView();
        setListener();
        coursePresent.pullToRefreshVideo(id,videoId);
    }

    private void initView(){
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_video);
        adapter = new MoreVideoAdapter(this);
        wrapper = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(wrapper);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        tvTitle.setText(title);
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
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullToRefreshVideo(id,videoId);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.loadMoreVideo(id,videoId,recyclerView,pageSize,currPage);
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
        View view = View.inflate(this,R.layout.empty_relate_video,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onItemClick(String videoId) {
        CourseVideoDetailActivity.start(this,id,videoId);
    }
}
