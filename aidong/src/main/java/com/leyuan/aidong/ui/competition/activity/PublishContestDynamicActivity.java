package com.leyuan.aidong.ui.competition.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadToQiNiuManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/3/14.
 */
public class PublishContestDynamicActivity extends PublishDynamicActivity implements ContestEnrolView {


    private String contestId;
    ContestPresentImpl contestPresent;

    public static void startForResult(Fragment fragment, boolean isPhoto, ArrayList<BaseMedia> selectedMedia, int requestCode, String contestId) {
        Intent starter = new Intent(fragment.getContext(), PublishContestDynamicActivity.class);
        starter.putExtra("isPhoto", isPhoto);
        starter.putExtra("contestId", contestId);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        fragment.startActivityForResult(starter, requestCode);
    }

    public static void startForResult(Activity activity, boolean isPhoto, ArrayList<BaseMedia> selectedMedia, int requestCode, String contestId) {
        Intent starter = new Intent(activity, PublishContestDynamicActivity.class);
        starter.putExtra("isPhoto", isPhoto);
        starter.putExtra("contestId", contestId);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.layout_add_circle_location).setVisibility(View.GONE);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        contestId = getIntent().getStringExtra("contestId");
        contestPresent = new ContestPresentImpl(this);
        contestPresent.setContestEnrolView(this);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FormatUtil.parseInt(tvContentCount.getText().toString()) > MAX_TEXT_COUNT) {
                    ToastGlobal.showLong(String.format(getString(R.string.too_many_text), MAX_TEXT_COUNT));
                    return;
                }

                uploadVideo();
            }
        });


    }


    private void uploadVideo() {
        DialogUtils.showDialog(PublishContestDynamicActivity.this,"",true);
        Logger.i("contest video ", "uploadVideo");
        UploadToQiNiuManager.getInstance().uploadMediaVideo(selectedMedia, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                DialogUtils.dismissDialog();
                Logger.i("contest video ", "uploadVideo  onSuccess urls .size = " + urls.size());

                if (urls != null && urls.size() > 0){
                    DialogUtils.showDialog(PublishContestDynamicActivity.this,"",true);
                    contestPresent.postVideo(contestId, urls.get(0),etContent.getText().toString().trim());
                }


            }

            @Override
            public void onFail() {
                DialogUtils.dismissDialog();
                ToastGlobal.showLong("上传失败");
            }
        });
    }

    @Override
    protected void uploadToServer(List<String> urls) {
//        super.uploadToServer(qiNiuMediaUrls);
        if (urls != null && urls.size() > 0) {
            contestPresent.postVideo(contestId, urls.get(0),etContent.getText().toString().trim());
        }

    }

    @Override
    public void onContestEnrolResult(BaseBean baseBean) {

    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {
        DialogUtils.dismissDialog();
        ToastGlobal.showLong("视频上传成功");
        finish();
    }
}
