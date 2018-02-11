package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.UiManager;

/**
 * Created by user on 2018/2/6.
 */

public class ContestHomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView txtTitle;
    private ImageView imgPostOrEnrol;
    private LinearLayout layoutEnd;
    private ImageView imgEndCover;
    private TextView txtRelateDynamic;
    private TextView txtOfficialInfo;
    private TextView txtRank;
    private String contestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contestId = getIntent().getStringExtra("contestId");

        setContentView(R.layout.activity_competition_home);

        txtTitle = (TextView) findViewById(R.id.txt_title);

        imgPostOrEnrol = (ImageView) findViewById(R.id.img_post_or_enrol);
        layoutEnd = (LinearLayout) findViewById(R.id.layout_end);
        imgEndCover = (ImageView) findViewById(R.id.img_end_cover);

        txtRelateDynamic = (TextView) findViewById(R.id.txt_relate_dynamic);
        txtOfficialInfo = (TextView) findViewById(R.id.txt_official_info);
        txtRank = (TextView) findViewById(R.id.txt_rank);


        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.bt_end_play).setOnClickListener(this);
        findViewById(R.id.img_share).setOnClickListener(this);
        findViewById(R.id.img_attention).setOnClickListener(this);
        imgPostOrEnrol.setOnClickListener(this);
        findViewById(R.id.bt_rule).setOnClickListener(this);
        txtRelateDynamic.setOnClickListener(this);
        txtOfficialInfo.setOnClickListener(this);
        txtRank.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_back:

                finish();
                break;
            case R.id.img_share:

                break;
            case R.id.img_attention:

                break;
            case R.id.bt_end_play:

                break;
            case R.id.img_post_or_enrol:

                ContestEnrolmentInfoActivity.start(this, contestId);

                break;

            case R.id.txt_relate_dynamic:

                UiManager.activityJump(this, ContestDynamicActivity.class);

                break;
            case R.id.txt_official_info:

                ContestOfficialInformationActivity.start(this, contestId);

                break;
            case R.id.txt_rank:
                UiManager.activityJump(this, ContestRankingListActivity.class);

                break;
            case R.id.bt_rule:
                //h5
                break;
        }
    }

    public static void start(Context context, String contestId) {
        Intent intent = new Intent(context, ContestHomeActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);
    }
}
