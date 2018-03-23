package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestRuleVideoAdapter;
import com.leyuan.aidong.entity.campaign.ContestRuleBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestRuleView;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.aidong.widget.richtext.RichWebView;

/**
 * Created by user on 2018/3/19.
 */
public class ContestRuleActivity extends BaseActivity implements ContestRuleView {

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestRuleActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);
    }

    private CommonTitleLayout layoutTitle;
    private RecyclerView recyclerView;
    private TextView txtContestTime;
    private TextView txtAddress;
    private RichWebView richWebView;

    ContestPresentImpl contestPresent;
    private String contestId;
    ContestRuleVideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contest_rule);

        contestId = getIntent().getStringExtra("contestId");

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        txtContestTime = (TextView) findViewById(R.id.txt_contest_time);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        richWebView = (RichWebView) findViewById(R.id.rich_web_view);
//        Color.parseColor("#111111")
        richWebView.setBackgroundColor(0); // 设置背景色
        richWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoAdapter = new ContestRuleVideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);

        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestRuleView(this);
        contestPresent.getContestRule(contestId);

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onGetContestRuleData(ContestRuleBean rule) {
        videoAdapter.setData(rule.videos);
        txtContestTime.setText(rule.date);
        txtAddress.setText(rule.address);
        richWebView.setRichText(rule.introduce);
    }
}
