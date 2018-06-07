package com.example.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.contest.ContestRuleVideoAdapter;
import com.example.aidong .entity.campaign.ContestRuleBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.presenter.impl.ContestPresentImpl;
import com.example.aidong .ui.mvp.view.ContestRuleView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .widget.CommonTitleLayout;
import com.example.aidong .widget.richtext.RichWebView;

/**
 * Created by user on 2018/3/19.
 */
public class ContestRuleActivity extends BaseActivity implements ContestRuleView,EmptyView {

    private View view;
    private TextView tv;

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

        view = (View) findViewById(R.id.view);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        txtContestTime = (TextView) findViewById(R.id.txt_contest_time);
        tv = (TextView) findViewById(R.id.tv);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        richWebView = (RichWebView) findViewById(R.id.rich_web_view);
//        Color.parseColor("#111111")
        richWebView.setBackgroundColor(0); // 设置背景色
        richWebView.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoAdapter = new ContestRuleVideoAdapter(this);
        recyclerView.setAdapter(videoAdapter);

        contestPresent = new ContestPresentImpl(this,this);
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

        if (rule.videos!=null&&rule.videos.size()>0){
            videoAdapter.setData(rule.videos);
        }else {
            tv.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }
        txtContestTime.setText(rule.date);
        txtAddress.setText(rule.address);
        richWebView.setRichText(rule.introduce);
    }


    @Override
    public void showEmptyView() {

    }
}
