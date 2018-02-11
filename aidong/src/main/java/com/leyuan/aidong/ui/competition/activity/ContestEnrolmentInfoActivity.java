package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iknow.android.TrimmerActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.CommonTitleLayout;

import java.util.ArrayList;

import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/2/6.
 */
public class ContestEnrolmentInfoActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleLayout titleLayout;
    private TextView txtContestName;
    private TextView txtContestTime;
    private RelativeLayout layoutUserName;
    private TextView txtUserName;
    private RelativeLayout layoutSelectCity;
    private TextView txtCity;
    private RelativeLayout layoutContestArea;
    private TextView txtContestArea;
    private RelativeLayout layoutSelectGroup;
    private TextView txtBelongGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_enroll_info);

        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        txtContestName = (TextView) findViewById(R.id.txt_contest_name);
        txtContestTime = (TextView) findViewById(R.id.txt_contest_time);
        layoutUserName = (RelativeLayout) findViewById(R.id.layout_user_name);
        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        layoutSelectCity = (RelativeLayout) findViewById(R.id.layout_select_city);
        txtCity = (TextView) findViewById(R.id.txt_city);
        findViewById(R.id.bt_select_city).setOnClickListener(this);
        layoutContestArea = (RelativeLayout) findViewById(R.id.layout_contest_area);
        txtContestArea = (TextView) findViewById(R.id.txt_contest_area);
        layoutSelectGroup = (RelativeLayout) findViewById(R.id.layout_select_group);
        txtBelongGroup = (TextView) findViewById(R.id.txt_belong_group);
        findViewById(R.id.bt_select_group).setOnClickListener(this);
        findViewById(R.id.bt_enrol_post_video).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_select_city:
                break;
            case R.id.bt_select_group:
                break;
            case R.id.bt_enrol_post_video:
                BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
                Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);

                break;
        }
    }

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestEnrolmentInfoActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if ( requestCode == REQUEST_SELECT_VIDEO) {
                ArrayList<BaseMedia> selectedMedia =  Boxing.getResult(data);
                if(selectedMedia != null && selectedMedia.size() > 0){
                    TrimmerActivity.go(this,selectedMedia.get(0).getPath());
                }


//                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
//                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);

            }
        }
    }
}
