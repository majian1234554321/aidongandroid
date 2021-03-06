package com.example.aidong.ui.course.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .adapter.video.RelativeViedeoAdapter;
import com.example.aidong .entity.CourseVideoBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.impl.CoursePresentImpl;
import com.example.aidong .ui.mvp.view.RelatedVideoActivityView;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.List;

import static com.example.aidong .ui.App.context;

/**
 * Created by user on 2018/1/11.
 */
public class RelativeVideoListActivity extends BaseActivity implements RelatedVideoActivityView {
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private RelativeViedeoAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    String relativeId;
    private CoursePresentImpl coursePresent;
    private String title;
    private String videoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative_video);
        relativeId = getIntent().getStringExtra("relativeId");
        title = getIntent().getStringExtra("title");
        videoId = getIntent().getStringExtra("videoId");

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (!TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
        }

        initSwipeRefreshLayout();
        initRecyclerView();

        coursePresent = new CoursePresentImpl(this, this);

        coursePresent.pullToRefreshVideo(relativeId, videoId);

    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                coursePresent.pullToRefreshVideo(relativeId, null);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_news);
        adapter = new RelativeViedeoAdapter(this, relativeId);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            coursePresent.loadMoreVideo(relativeId, null, recyclerView, pageSize, currPage);
        }
    };

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, RelativeVideoListActivity.class);
        intent.putExtra("relativeId", id);
        context.startActivity(intent);
    }

    public static void start(Context context, String id, String title) {
        Intent intent = new Intent(context, RelativeVideoListActivity.class);
        intent.putExtra("relativeId", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    public static void start(Context context, String id, String title, String videoId) {
        Intent intent = new Intent(context, RelativeVideoListActivity.class);
        intent.putExtra("relativeId", id);
        intent.putExtra("title", title);
        intent.putExtra("videoId", videoId);
        context.startActivity(intent);
    }

    @Override
    public void updateRecycler(List<CourseVideoBean> courseVideoBeanList) {
        adapter.setData(courseVideoBeanList);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
