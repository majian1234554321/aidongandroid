package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestBean;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.LocationActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.CommonTitleLayout;

import java.util.ArrayList;

import static com.leyuan.aidong.R.id.txt_city;

/**
 * Created by user on 2018/2/6.
 */
public class ContestSemiFinalEnrolmentActivity extends BaseActivity implements View.OnClickListener, ContestEnrolView {

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
    private String contestId;
    EditText edit_name;
    ContestBean contestBean;

    private String city;
    private String area;
    private String gender;

    ContestPresentImpl contestPresent;
    private ArrayList<BaseMedia> selectedMedia;
    String invitationCode;

    public static void start(Context context, String contestId, String name, String start, ContestBean contest, String invitationCode) {

        Intent intent = new Intent(context, ContestSemiFinalEnrolmentActivity.class);
        intent.putExtra("contestId", contestId);
        intent.putExtra("name", name);
        intent.putExtra("start", start);
        intent.putExtra("invitationCode", invitationCode);
        intent.putExtra("contest", contest);


        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_enroll_info);

        contestId = getIntent().getStringExtra("contestId");
        invitationCode = getIntent().getStringExtra("invitationCode");
        contestBean = getIntent().getParcelableExtra("contest");

        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        txtContestName = (TextView) findViewById(R.id.txt_contest_name);
        txtContestTime = (TextView) findViewById(R.id.txt_contest_time);
        layoutUserName = (RelativeLayout) findViewById(R.id.layout_user_name);
        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        layoutSelectCity = (RelativeLayout) findViewById(R.id.layout_select_city);
        txtCity = (TextView) findViewById(txt_city);
        edit_name = (EditText) findViewById(R.id.edit_name);

        findViewById(R.id.bt_select_city).setOnClickListener(this);
        layoutContestArea = (RelativeLayout) findViewById(R.id.layout_contest_area);
        txtContestArea = (TextView) findViewById(R.id.txt_contest_area);
        layoutSelectGroup = (RelativeLayout) findViewById(R.id.layout_select_group);
        txtBelongGroup = (TextView) findViewById(R.id.txt_belong_group);
        titleLayout.setLeftIconListener(this);
        findViewById(R.id.bt_select_group).setOnClickListener(this);
        findViewById(R.id.bt_enrol_post_video).setOnClickListener(this);

        ((Button)findViewById(R.id.bt_enrol_post_video)).setText("确认报名");

        txtContestName.setText(getIntent().getStringExtra("name"));
        txtContestTime.setText(getIntent().getStringExtra("start"));
        txtUserName.setText("姓名: " + App.getInstance().getUser().getName());

        layoutSelectCity.setVisibility(View.GONE);
        layoutContestArea.setVisibility(View.GONE);

        if (App.getInstance().getUser().getGender() == Constant.MAN_GENDER) {
            txtBelongGroup.setText("选择分组: 男子组");
            gender = "男";
        } else {
            gender = "女";
            txtBelongGroup.setText("选择分组: 女子组");
        }

        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestEnrolView(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.bt_select_city:
                UiManager.activityJump(this, LocationActivity.class);

                break;
            case R.id.bt_select_group:
                showGenderDialog();

                break;
            case R.id.bt_enrol_post_video:
                String name = edit_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastGlobal.showShortConsecutive("请输入真实姓名");
                    return;
                }


                contestPresent.invitationCodeEnrol(contestId, name, gender, invitationCode);


                break;
        }
    }

    private void showGenderDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_gender)
                .items(R.array.gender)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        txtBelongGroup.setText((which == 0) ? "选择分组: 男子组" : "选择分组: 女子组");
                        gender = (which == 0) ? "男" : "女";
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }


    @Override
    public void onContestEnrolResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {

            ContestQuarterFinalEnrolActivity.start(this, contestId);

        } else {

            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }


    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {
    }


}
