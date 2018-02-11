package com.iknow.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.iknow.android.interfaces.OnTrimVideoListener;
import com.iknow.android.utils.TrimVideoUtil;
import com.iknow.android.view.VideoTrimmerView;
import com.leyuan.aidong.R;

import java.io.File;

public class TrimmerActivity extends AppCompatActivity implements OnTrimVideoListener {

    private static final String TAG = "jason";
    private static final String STATE_IS_PAUSED = "isPaused";
    public static final int VIDEO_TRIM_REQUEST_CODE = 0x001;

    private File tempFile;
    VideoTrimmerView trimmerView;


    public static void go(FragmentActivity from, String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) {
            Bundle bundle = new Bundle();
            bundle.putString("path", videoPath);
            Intent intent = new Intent(from, TrimmerActivity.class);
            intent.putExtras(bundle);
            from.startActivityForResult(intent, VIDEO_TRIM_REQUEST_CODE);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_trimmer);
        Bundle bd = getIntent().getExtras();
        String path = "";
        if (bd != null)
            path = bd.getString("path");

        trimmerView = (VideoTrimmerView) findViewById(R.id.trimmer_view);
        trimmerView.setMaxDuration(TrimVideoUtil.VIDEO_MAX_DURATION);
        trimmerView.setOnTrimVideoListener(this);
        trimmerView.setVideoURI(Uri.parse(path));
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
    }

    @Override
    public void onFinishTrim(Uri uri) {
//        Looper.prepare();
        finish();
    }

    @Override
    public void onCancel() {
        trimmerView.destroy();
        finish();
    }
}
