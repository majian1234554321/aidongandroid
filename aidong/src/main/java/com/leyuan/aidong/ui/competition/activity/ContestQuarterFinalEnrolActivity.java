package com.leyuan.aidong.ui.competition.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.HomeRecommendCourseAdapter;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/22.
 */
public class ContestQuarterFinalEnrolActivity extends BaseActivity implements OnRefreshListener {
    private RelativeLayout relTitle;
    private ImageView imgLeft;
    private TextView txtTitle;
    private TextView tvLocation;
    private TextView txtRight;
    private RecyclerView rvOrder;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_quarter_final_enrol);

        relTitle = (RelativeLayout) findViewById(R.id.rel_title);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        txtRight = (TextView) findViewById(R.id.txt_right);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        rvOrder = (RecyclerView) findViewById(R.id.rv_order);


        refreshLayout.setOnRefreshListener(this);
        adapter = new HomeRecommendCourseAdapter(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);


        switcherLayout = new SwitcherLayout(this, refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

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

}
