package com.iknow.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.iknow.android.interfaces.OnTrimVideoListener;
import com.iknow.android.utils.TrimVideoUtil;
import com.iknow.android.view.VideoTrimmerView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;

import java.io.File;

public class TrimmerActivity extends BaseActivity implements OnTrimVideoListener, ContestEnrolView {

    private static final String TAG = "jason";
    private static final String STATE_IS_PAUSED = "isPaused";
    public static final int VIDEO_TRIM_REQUEST_CODE = 0x001;

    private File tempFile;
    VideoTrimmerView trimmerView;
    private int duration;
//    private ContestPresentImpl contestPresent;

//    public static void startForResult(Activity from, String videoPath, int request_code) {
//        if (!TextUtils.isEmpty(videoPath)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("path", videoPath);
//            Intent intent = new Intent(from, TrimmerActivity.class);
//            intent.putExtras(bundle);
//            from.startActivityForResult(intent, request_code);
//        }
//    }

    public static void startForResult(Activity from, String videoPath, int duration, int request_code) {
        if (!TextUtils.isEmpty(videoPath)) {
            Bundle bundle = new Bundle();
            bundle.putString("path", videoPath);
            bundle.putInt("duration", duration);
            Intent intent = new Intent(from, TrimmerActivity.class);
            intent.putExtras(bundle);
            from.startActivityForResult(intent, request_code);
        } else {
            ToastGlobal.showLongConsecutive("获取视频失败，请检查该视频是否存在");
        }
    }

    public static void startForResult(Fragment from, String videoPath, int duration, int request_code) {
        if (!TextUtils.isEmpty(videoPath)) {
            Bundle bundle = new Bundle();
            bundle.putString("path", videoPath);
            bundle.putInt("duration", duration);
            Intent intent = new Intent(from.getActivity(), TrimmerActivity.class);
            intent.putExtras(bundle);
            from.startActivityForResult(intent, request_code);
        } else {
            ToastGlobal.showLongConsecutive("获取视频失败，请检查该视频是否存在");
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_trimmer);
        Bundle bd = getIntent().getExtras();
        String path = "";
        if (bd != null) {
            path = bd.getString("path");
            duration = bd.getInt("duration", TrimVideoUtil.VIDEO_MAX_DURATION);
            Logger.i("TrimmerActivity", "bd.getInt(\"duration\" durantion = " + duration);
        }
        if (duration <= 3 || duration > 60) {
            duration = 60;
        }

        trimmerView = (VideoTrimmerView) findViewById(R.id.trimmer_view);
        trimmerView.setMaxDuration(duration);
        trimmerView.setOnTrimVideoListener(this);
        trimmerView.setVideoURI(duration, Uri.parse(path));

//        contestPresent = new ContestPresentImpl(this);
//        contestPresent.setContestEnrolView(this);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        trimmerView.onPause();
        trimmerView.setRestoreState(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trimmerView.destroy();
    }

    @Override
    public void onStartTrim() {

        DialogUtils.showDialog(this, "", true);
    }

    @Override
    public void onFinishTrim(String path) {
        Logger.i("contest video ", "onFinishTrim  path = " + path);
        DialogUtils.dismissDialog();
        Intent intent = new Intent();
        intent.putExtra(Constant.VIDEO_PATH, path);
        setResult(RESULT_OK, intent);
        finish();


//        ArrayList<BaseMedia> selectedMedia = new ArrayList<>();
//        BaseMedia baseMedia = new BaseMedia() {
//            @Override
//            public TYPE getType() {
//                return TYPE.VIDEO;
//            }
//        };
//        baseMedia.setPath(path);
//        selectedMedia.add(baseMedia);
//
//        showProgressDialog();
//
//        UploadToQiNiuManager.getInstance().uploadMediaVideo(selectedMedia, new IQiNiuCallback() {
//            @Override
//            public void onSuccess(List<String> urls) {
//            }
//
//            @Override
//            public void onFail() {
//                dismissProgressDialog();
//                ToastGlobal.showLong("上传失败");
//            }
//        });


    }


    @Override
    public void onCancel() {
        trimmerView.destroy();
        finish();
    }

    @Override
    public void onContestEnrolResult(BaseBean baseBean) {

    }

    @Override
    public void onPostVideoResult(BaseBean baseBean) {

    }
}
