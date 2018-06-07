package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.activity.SelectCityActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.CommonTitleLayout;

import java.util.ArrayList;

import static com.example.aidong.R.id.txt_city;
import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/2/6.
 */
public class ContestEnrolmentInfoActivity extends BaseActivity implements View.OnClickListener, ContestEnrolView {

    private static final java.lang.String TAG = "ContestEnrolmentInfoActivity";
    private CommonTitleLayout titleLayout;
    private TextView txtContestName;
    private TextView txtContestTime;
    private RelativeLayout layoutUserName;
    private TextView txtUserName;
    private RelativeLayout layoutSelectCity;
    private TextView txtCity;
    private RelativeLayout layoutContestArea;
    private TextView txtContestArea,tv_tips;
    private RelativeLayout layoutSelectGroup;
    private TextView txtBelongGroup;
    EditText edit_name;


    private String contestId;
    ContestBean contestBean;
    private String city;
    private String area;
    private String gender;

    ContestPresentImpl contestPresent;
    private ArrayList<BaseMedia> selectedMedia;
    private ArrayList<String> allCity;


    public static void start(Context context, String contestId, String name, String start, ContestBean contest) {

        Intent intent = new Intent(context, ContestEnrolmentInfoActivity.class);
        intent.putExtra("contestId", contestId);
        intent.putExtra("name", name);
        intent.putExtra("start", start);
        intent.putExtra("contest", contest);

        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_enroll_info);

        contestId = getIntent().getStringExtra("contestId");
        contestBean = getIntent().getParcelableExtra("contest");

        tv_tips = (TextView) findViewById(R.id.tv_tips);
        titleLayout = (CommonTitleLayout) findViewById(R.id.title_layout);
        txtContestName = (TextView) findViewById(R.id.txt_contest_name);
        txtContestTime = (TextView) findViewById(R.id.txt_contest_time);
        layoutUserName = (RelativeLayout) findViewById(R.id.layout_user_name);
        txtUserName = (TextView) findViewById(R.id.txt_user_name);
        layoutSelectCity = (RelativeLayout) findViewById(R.id.layout_select_city);
        txtCity = (TextView) findViewById(txt_city);
        edit_name = (EditText) findViewById(R.id.edit_name);
        tv_tips.setVisibility(View.GONE);

        findViewById(R.id.bt_select_city).setOnClickListener(this);
        layoutContestArea = (RelativeLayout) findViewById(R.id.layout_contest_area);
        txtContestArea = (TextView) findViewById(R.id.txt_contest_area);
        layoutSelectGroup = (RelativeLayout) findViewById(R.id.layout_select_group);
        txtBelongGroup = (TextView) findViewById(R.id.txt_belong_group);
        findViewById(R.id.bt_select_group).setOnClickListener(this);
        findViewById(R.id.bt_enrol_post_video).setOnClickListener(this);
        titleLayout.setLeftIconListener(this);

        txtContestName.setText(getIntent().getStringExtra("name"));
        txtContestTime.setText(getIntent().getStringExtra("start"));
//        txtUserName.setText("姓名: " + App.getInstance().getUser().getName());

//        this.city = App.getInstance().getLocationCity();
//        this.area = contestBean.getAreaByCity(city);
//        txtCity.setText("我的位置: " + city);
//        txtContestArea.setText("所属赛区: " + area);

        if (App.getInstance().getUser().getGender() == Constant.MAN_GENDER) {
            txtBelongGroup.setText("选择分组: 男子组");
            genderint = 0;
            gender = "男";
            genderint = 0;
        } else {
            txtBelongGroup.setText("选择分组: 女子组");
            genderint = 1;
            gender = "女";
            genderint = 1;
        }

        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestEnrolView(this);

        allCity = contestBean.getAllCity();

        this.city = App.getInstance().getLocationCity();
        this.area = contestBean.getAreaByCity(city);
        txtCity.setText("我的位置: " + city);
        txtContestArea.setText("所属赛区: " + area);

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

                SelectCityActivity.startForResult(this, Constant.REQUEST_CITY_CODE, city, allCity);

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

                contestPresent.contestEnrol(contestId, name, gender, area);


                break;
        }
    }
    int genderint = 0;

    private void showGenderDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.man_woman_group_select)
                .items(R.array.gender)
                .itemsCallbackSingleChoice(genderint, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        txtBelongGroup.setText((which == 0) ? "选择分组: 男子组" : "选择分组: 女子组");
                        genderint = which;
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

        } else {

            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }

        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);

    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {
        DialogUtils.dismissDialog();

        if (baseBean.getStatus() == 1) {
            ToastGlobal.showLongConsecutive("上传并报名成功");
            finish();
        } else {
            ToastGlobal.showLongConsecutive(baseBean.getMessage());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_VIDEO) {
                selectedMedia = Boxing.getResult(data);
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    int duration = TrimVideoUtil.VIDEO_MAX_DURATION;

                    if (selectedMedia.get(0) instanceof VideoMedia) {
                        VideoMedia media = (VideoMedia) selectedMedia.get(0);
                        duration = (int) (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1);
                        Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.getDuration());
                    }
                    Logger.i("TrimmerActivity", "onActivityResult  durantion = " + duration);

                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), duration, Constant.REQUEST_VIDEO_TRIMMER);

//                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));

                    PublishContestDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC,contestId);
                    finish();

//                    uploadVideo();

                }
            } else if (requestCode == Constant.REQUEST_CITY_CODE) {

                city = data.getStringExtra("selectedCity");
                Logger.i(TAG, "city = " + city);

                this.area = contestBean.getAreaByCity(city);
                txtCity.setText("我的位置: " + city);
                txtContestArea.setText("所属赛区: " + area);


            }
        }
    }

//    private void uploadVideo() {
//
//        Logger.i("contest video ", "uploadVideo");
//        UploadToQiNiuManager.getInstance().uploadMediaVideo(selectedMedia, new IQiNiuCallback() {
//            @Override
//            public void onSuccess(List<String> urls) {
//                Logger.i("contest video ", "uploadVideo  onSuccess urls .size = " + urls.size());
//
//                if (urls != null && urls.size() > 0)
//                    contestPresent.postVideo(contestId, urls.get(0));
//
//            }
//
//            @Override
//            public void onFail() {
//                dismissProgressDialog();
//                ToastGlobal.showLong("上传失败");
//            }
//        });
//    }

}
