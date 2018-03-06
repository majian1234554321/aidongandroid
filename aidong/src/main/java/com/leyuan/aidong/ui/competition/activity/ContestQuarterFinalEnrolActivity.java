package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestSemiFinalEnrolAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestScheduleBean;
import com.leyuan.aidong.entity.data.ContestSchedulesDateData;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestSchedulesView;
import com.leyuan.aidong.ui.mvp.view.ContestSemiFinalEnrolClickListener;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastGlobal;
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
public class ContestQuarterFinalEnrolActivity extends BaseActivity implements OnRefreshListener, ContestSchedulesView, ContestSemiFinalEnrolClickListener {
    private RelativeLayout relTitle;
    private ImageView imgLeft;
    private TextView txtTitle;
    private TextView tvLocation;
    private TextView txtRight;


    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage = 1;
    private ContestSemiFinalEnrolAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private ContestPresentImpl coursePresent;

    private ArrayList<ContestSchedulesDateData> data = new ArrayList<>();

    FollowPresentImpl followPresent;
    private int clickedFollowPosition;
    String contestId;

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestQuarterFinalEnrolActivity.class);
        intent.putExtra("contestId", contestId);

        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contestId = getIntent().getStringExtra("contestId");
        setContentView(R.layout.activity_contest_quarter_final_enrol);

        relTitle = (RelativeLayout) findViewById(R.id.rel_title);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        txtRight = (TextView) findViewById(R.id.txt_right);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_order);

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContestQuarterFinalEnroledRecordActivity.start(ContestQuarterFinalEnrolActivity.this,contestId);
            }
        });

        refreshLayout.setOnRefreshListener(this);
        adapter = new ContestSemiFinalEnrolAdapter(this);
        adapter.setListener(this);

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

        coursePresent = new ContestPresentImpl(this);
        coursePresent.setContestSchedulesView(this);
        coursePresent.getContestSchedules(contestId, currPage);

        imgLeft.setOnClickListener(new View.OnClickListener() {
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
        coursePresent.getContestSchedules(contestId, currPage);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.getContestSchedules(contestId, currPage);
            }

        }
    };

    @Override
    public void onGetContestSchedulesData(ArrayList<ContestSchedulesDateData> schedule) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data = schedule;
        adapter.setData(data);

    }


    @Override
    public void onSemiFinalEnrolClick(String id, boolean appointed) {
        if (appointed) {
            coursePresent.scheduleCancel(contestId, id);
        } else {
            coursePresent.scheduleEnrol(contestId, id);
        }
    }


    @Override
    public void onScheduleEnrol(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            coursePresent.getContestSchedules(contestId, currPage);
        } else {
            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void onScheduleCancelResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            coursePresent.getContestSchedules(contestId, currPage);
        } else {
            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void onGetContestSchedulesRecordData(ArrayList<ContestScheduleBean> record) {

    }
}
