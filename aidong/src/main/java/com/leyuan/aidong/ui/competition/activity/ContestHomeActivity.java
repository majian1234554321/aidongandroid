package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer.util.Util;
import com.iknow.android.TrimmerActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.campaign.ContestBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.ui.mvp.view.ContestHomeView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadToQiNiuManager;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.img_post_or_enrol;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/2/6.
 */

public class ContestHomeActivity extends BaseActivity implements View.OnClickListener, ContestHomeView, ContestEnrolView {


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
    private FrameLayout layoutInvitation;

    private String contestId;
    CampaignBean campaignBean;

    ContestPresentImpl contestPresent;
    private ContestBean contest;
    private String invitationCode;
    private SharePopupWindow sharePopupWindow;
    private ArrayList<BaseMedia> selectedMedia;

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


        contestId = getIntent().getStringExtra("contestId");
        campaignBean = getIntent().getParcelableExtra("campaignBean");
        if (campaignBean != null) {
            contestId = campaignBean.id;
            txtTitle.setText(campaignBean.name);
        }
        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestHomeView(this);
        contestPresent.setContestEnrolView(this);
        contestPresent.getContestDetail(contestId);


        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.bt_end_play).setOnClickListener(this);
        findViewById(R.id.img_share).setOnClickListener(this);
        findViewById(R.id.img_attention).setOnClickListener(this);
        findViewById(R.id.bt_enrol_semi_final).setOnClickListener(this);
        findViewById(R.id.bt_close_invitation).setOnClickListener(this);


        imgPostOrEnrol.setOnClickListener(this);
        findViewById(R.id.bt_rule).setOnClickListener(this);
        txtRelateDynamic.setOnClickListener(this);
        txtOfficialInfo.setOnClickListener(this);
        txtRank.setOnClickListener(this);

        sharePopupWindow = new SharePopupWindow(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_back:

                finish();
                break;
            case R.id.img_share:
                sharePopupWindow.showAtBottom("我分享了赛事: " + contest.name + "，速速围观", contest.name,
                        contest.background, ConstantUrl.URL_SHARE_DYNAMIC + contest.id);

                break;

            case R.id.img_attention:
                ContestEnrolmentInfoActivity.start(this, contestId, campaignBean.name, campaignBean.start, contest);
                break;

            case R.id.bt_end_play:

                Intent intent = new Intent(this, PlayerActivity.class)
                        .setData(Uri.parse(contest.video))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_OTHER);
                startActivity(intent);

                break;
            case img_post_or_enrol:

                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(this, LoginActivity.class);
                } else if ("preliminary".equals(contest.status)) {
                    if(contest.joined){
                        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
                        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
                    }else {
                        ContestEnrolmentInfoActivity.start(this, contestId, campaignBean.name, campaignBean.start, contest);
                    }

                } else if ("semi_finals".equals(contest.status)) {
                    if (contest.joined) {
                        //已参加，有复赛资格
                        ContestQuarterFinalEnrolActivity.start(this, contestId);

                    } else {
                        layoutInvitation.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastGlobal.showLongConsecutive("海选尚未开始，发布动态请前往爱动广场");
                }

//                ContestQuarterFinalEnrolActivity.start(this, contestId);

                break;

            case R.id.txt_relate_dynamic:
                ContestDynamicActivity.start(this,contestId);
                break;

            case R.id.txt_official_info:

                ContestOfficialInformationActivity.start(this, contestId);

                break;
            case R.id.txt_rank:
                if (contest != null) {
                    ContestRankingListActivity.start(this, contest);
                }


                break;

            case R.id.bt_rule:
                //h5
                break;

            case R.id.bt_close_invitation:
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
    public void onGetContestDetailData(ContestBean contest) {
        this.contest = contest;

        GlideLoader.getInstance().displayImage(contest.background, img_bg);

        if ("preliminary".equals(contest.status)) {

            imgPostOrEnrol.setImageResource(R.drawable.post_video);

        } else if ("semi_finals".equals(contest.status)) {
            imgPostOrEnrol.setImageResource(R.drawable.contest_semi_final_enrol);
        } else if ("finals".equals(contest.status)) {
            imgPostOrEnrol.setVisibility(View.GONE);
            layoutEnd.setVisibility(View.VISIBLE);
        } else {
            imgPostOrEnrol.setImageResource(R.drawable.post_video);
        }


    }

    @Override
    public void onCheckInvitationCodeResult(boolean registed) {
        if (registed) {
            ContestQuarterFinalEnrolActivity.start(this, contestId);
        } else {
            ContestSemiFinalEnrolmentActivity.start(this, contestId, campaignBean.name, campaignBean.start, contest, invitationCode);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_VIDEO) {
                selectedMedia = Boxing.getResult(data);
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {

                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));

                    uploadVideo();


                }
            }
        }
    }

    private void uploadVideo() {

        Logger.i("contest video ", "uploadVideo");
        UploadToQiNiuManager.getInstance().uploadMediaVideo(selectedMedia, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                Logger.i("contest video ", "uploadVideo  onSuccess urls .size = " + urls.size());

                if (urls != null && urls.size() > 0){
                    DialogUtils.showDialog(ContestHomeActivity.this,"",true);
                    contestPresent.postVideo(contestId, urls.get(0));
                }


            }

            @Override
            public void onFail() {
                dismissProgressDialog();
                ToastGlobal.showLong("上传失败");
            }
        });
    }

    @Override
    public void onContestEnrolResult(BaseBean baseBean) {
        DialogUtils.dismissDialog();
    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {
        DialogUtils.dismissDialog();
        ToastGlobal.showLong("视频上传成功");
    }
}
