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

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestSemiFinalEnrolAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestBean;
import com.leyuan.aidong.entity.campaign.ContestScheduleBean;
import com.leyuan.aidong.entity.data.ContestSchedulesDateData;
import com.leyuan.aidong.ui.App;
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

import static com.example.aidong.R.id.txt_right;

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

    ContestBean contestBean;
    String contestId;
    private String city;
    private ArrayList<String> allCity;

    public static void start(Context context, String contestId, ContestBean contest) {

        Intent intent = new Intent(context, ContestQuarterFinalEnrolActivity.class);
        intent.putExtra("contestId", contestId);
        intent.putExtra("contest", contest);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contestId = getIntent().getStringExtra("contestId");
        contestBean = getIntent().getParcelableExtra("contest");
        setContentView(R.layout.activity_contest_quarter_final_enrol);

        relTitle = (RelativeLayout) findViewById(R.id.rel_title);
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        txtRight = (TextView) findViewById(txt_right);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_order);

        txtRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContestQuarterFinalEnroledRecordActivity.start(ContestQuarterFinalEnrolActivity.this, contestId);
            }
        });

        city = App.getInstance().getSelectedCity();
        allCity = contestBean.getAllCity();
        tvLocation.setText(city);
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHeightDialog();
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
        coursePresent.getContestSchedules(contestId, city, currPage);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showHeightDialog() {
        new MaterialDialog.Builder(this).title("请选择城市")
                .items(allCity)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        city = text.toString();
                        onRefresh();
                        tvLocation.setText(city);

                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.getContestSchedules(contestId, city, currPage);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.getContestSchedules(contestId, city, currPage);
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
            ToastGlobal.showLongConsecutive("报名成功，请提早15分钟进入赛场");
            onRefresh();

        } else {
            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void onScheduleCancelResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            ToastGlobal.showLongConsecutive("取消报名成功");
            onRefresh();
        } else {
            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void onGetContestSchedulesRecordData(ArrayList<ContestScheduleBean> record) {

    }
}
