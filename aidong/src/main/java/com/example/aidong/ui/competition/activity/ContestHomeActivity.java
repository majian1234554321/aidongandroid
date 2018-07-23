package com.example.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer.util.Util;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong .entity.campaign.ContestBean;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.ContestPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.ContestEnrolView;
import com.example.aidong .ui.mvp.view.ContestHomeView;
import com.example.aidong .ui.mvp.view.FollowView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .utils.Constant;

import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;

import java.util.ArrayList;

import static com.example.aidong.R.id.img_post_or_enrol;
import static com.example.aidong .utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_PHOTO;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/2/6.
 * 精选活动-赛事
 */

public class ContestHomeActivity extends BaseActivity implements View.OnClickListener, ContestHomeView, ContestEnrolView, FollowView {


    private RelativeLayout relTitle;
    private ImageView imgBack;
    private TextView txtTitle;
    private ImageView imgBg;
    private ImageView imgPostOrEnrol;
    private LinearLayout layoutEnd;
    private ImageView imgEndCover, img_bg;
    private TextView txtRelateDynamic;
    private TextView txtOfficialInfo;
    private TextView txtRank;
    private ImageButton img_attention;

    private FrameLayout layoutInvitation;

    private String contestId;
    CampaignBean campaignBean;

    ContestPresentImpl contestPresent;
    private ContestBean contest;
    private String invitationCode;

    private SharePopupWindow sharePopupWindow;
    private ArrayList<BaseMedia> selectedMedia;
    private FollowPresentImpl present;


    public static void start(Context context, String contestId) {
        Intent intent = new Intent(context, ContestHomeActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);
    }

    public static void start(Context context, CampaignBean campaignBean) {
        Intent intent = new Intent(context, ContestHomeActivity.class);
        intent.putExtra("campaignBean", campaignBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_competition_home);

        txtTitle = (TextView) findViewById(R.id.txt_title);

        img_bg = (ImageView) findViewById(R.id.img_bg);
        imgPostOrEnrol = (ImageView) findViewById(img_post_or_enrol);
        layoutEnd = (LinearLayout) findViewById(R.id.layout_end);
        imgEndCover = (ImageView) findViewById(R.id.img_end_cover);

        txtRelateDynamic = (TextView) findViewById(R.id.txt_relate_dynamic);
        txtOfficialInfo = (TextView) findViewById(R.id.txt_official_info);
        txtRank = (TextView) findViewById(R.id.txt_rank);

        layoutInvitation = (FrameLayout) findViewById(R.id.layout_invitation);
        layoutInvitation.setVisibility(View.GONE);

        img_attention = (ImageButton) findViewById(R.id.img_attention);

        contestId = getIntent().getStringExtra("contestId");
        campaignBean = getIntent().getParcelableExtra("campaignBean");

        if (campaignBean != null) {
            contestId = campaignBean.id;
            txtTitle.setText(campaignBean.name);
        }
        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestHomeView(this);
        contestPresent.setContestEnrolView(this);


        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.bt_end_play).setOnClickListener(this);
        findViewById(R.id.img_share).setOnClickListener(this);
        findViewById(R.id.img_attention).setOnClickListener(this);
        findViewById(R.id.bt_enrol_semi_final).setOnClickListener(this);
        findViewById(R.id.bt_close_invitation).setOnClickListener(this);


        imgEndCover.setOnClickListener(this);
        imgPostOrEnrol.setOnClickListener(this);
        findViewById(R.id.bt_rule).setOnClickListener(this);
        txtRelateDynamic.setOnClickListener(this);
        txtOfficialInfo.setOnClickListener(this);
        txtRank.setOnClickListener(this);

        sharePopupWindow = new SharePopupWindow(this);

        present = new FollowPresentImpl(this);
        present.setFollowListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        contestPresent.getContestDetail(contestId);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.img_back:

                finish();
                break;

            case R.id.img_share:

                if (contest == null) return;


                String value = "";











                if(contest.introduce!=null) {
                    value = contest.introduce;
                    if (value.contains("<p>")){
                        value = value.replace("<p>","");
                    }
                    if (value.length()>=30){
                        value = value.substring(0,30);

                    }

                }



                sharePopupWindow.showAtBottom(contest.name + Constant.I_DONG_FITNESS, value,
                        contest.background, contest.share_url);

                break;

            case R.id.img_attention:

                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(this, LoginActivity.class);
                    return;
                }

                if (contest == null) return;
                if (contest.followed) {
                    present.cancelFollow(contestId, contest.type);
                } else {
                    present.addFollow(contestId, contest.type);
                }

                break;

            case R.id.img_end_cover:
            case R.id.bt_end_play:
                if (contest == null) return;
                Intent intent = new Intent(this, PlayerActivity.class)
                        .setData(Uri.parse(contest.video))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
                startActivity(intent);

                break;
            case img_post_or_enrol:
                if (contest == null) return;
                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(this, LoginActivity.class);
                } else if ("preliminary".equals(contest.status)) {
                    if (contest.joined) {
                        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
                        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
                    } else {
                        ContestEnrolmentInfoActivity.start(this, contestId, contest.name, contest.start_date + "~" + contest.end_date, contest);
                    }

                } else if ("semi_finals".equals(contest.status)) {
                    if (contest.joined) {
                        //已参加，有复赛资格
                        ContestQuarterFinalEnrolActivity.start(this, contestId, contest);

                    } else {
                        layoutInvitation.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastGlobal.showLongConsecutive("海选尚未开始，发布动态请前往爱动广场");
                }

//                ContestQuarterFinalEnrolActivity.start(this, contestId);

                break;

            case R.id.txt_relate_dynamic:

                ContestDynamicActivity.start(this, contestId);

                break;

            case R.id.txt_official_info:

                ContestOfficialInformationActivity.start(this, contestId);

                break;
            case R.id.txt_rank:

                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(this, LoginActivity.class);
                    return;
                }

                if (contest != null) {
                    ContestRankingListActivity.start(this, contest);
                }

                break;

            case R.id.bt_rule:
                //h5

                ContestRuleActivity.start(this, contestId);

                break;

            case R.id.bt_close_invitation:
                getEditInvitationCode().setText("");
                getEditInvitationCode().setHint("请输入邀请码");
                layoutInvitation.setVisibility(View.GONE);



                break;

            case R.id.bt_enrol_semi_final:
                invitationCode = getEditInvitationCode().getText().toString().trim();
                if (TextUtils.isEmpty(invitationCode)) {
                    ToastGlobal.showShortConsecutive("请输入邀请码");
                } else {
                    contestPresent.checkInvitationCode(contestId, invitationCode);
                }

                break;
        }
    }

    private EditText getEditInvitationCode() {
        return (EditText) findViewById(R.id.edit_invitation_code);
    }


    @Override
    public void onGetContestDetailData(ContestBean contest) {
        this.contest = contest;
        txtTitle.setText(contest.name);
        GlideLoader.getInstance().displayImage(contest.background, img_bg);
        img_attention.setImageResource(contest.followed ? R.drawable.icon_contest_parsed : R.drawable.icon_contest_parse_not);

        if ("preliminary".equals(contest.status)) {

            imgPostOrEnrol.setImageResource(R.drawable.post_video);

        } else if ("semi_finals".equals(contest.status)) {
            imgPostOrEnrol.setImageResource(R.drawable.contest_semi_final_enrol);
        } else if ("finals".equals(contest.status)) {
            imgPostOrEnrol.setVisibility(View.GONE);
            layoutEnd.setVisibility(View.VISIBLE);
            GlideLoader.getInstance().displayImage(contest.cover, imgEndCover);
        } else if ("pending".equals(contest.status)) {
            imgPostOrEnrol.setImageResource(R.drawable.post_video);
        } else {

            imgPostOrEnrol.setVisibility(View.GONE);
            layoutEnd.setVisibility(View.VISIBLE);
            GlideLoader.getInstance().displayImage(contest.cover, imgEndCover);

        }

    }

    @Override
    public void onCheckInvitationCodeResult(boolean registed) {

        layoutInvitation.setVisibility(View.GONE);

        ContestSemiFinalEnrolmentActivity.start(this, contestId, campaignBean.name, campaignBean.start, contest, invitationCode);

//        if (registed) {
//            ContestQuarterFinalEnrolActivity.start(this, contestId);
//        } else {
//
//        }
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

                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {

                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
//
                    PublishContestDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC, contestId);

//                    uploadVideo();


                }
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
//                if (urls != null && urls.size() > 0){
//                    DialogUtils.showDialog(ContestHomeActivity.this,"",true);
//                    contestPresent.postVideo(contestId, urls.get(0));
//                }
//
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

    @Override
    public void onContestEnrolResult(BaseBean baseBean) {

    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {

        ToastGlobal.showLong("视频上传成功");
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {

        if (baseBean.getStatus() == Constant.OK) {
            contest.followed = true;
            img_attention.setImageResource(R.drawable.icon_contest_parsed);

        } else {
            Toast.makeText(this, R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            contest.followed = false;
            img_attention.setImageResource(R.drawable.icon_contest_parse_not);

        } else {
            Toast.makeText(this, R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
