/*
 *  Copyright 2016 Jeroen Mols
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.leyuan.aidong.module.photopicker.boxing_impl.record.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing_impl.record.preview.CapturePreview;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.Logger;


public class VideoCaptureView extends FrameLayout implements OnClickListener, RecordButton.RecordButtonListener {
    private RecordButton recordButton;
    private ImageView mDeclineBtnIv;
    private ImageView mAcceptBtnIv;
    private ImageView mRecordBtnIv;
    private ImageView mChangeCameraIv;

    private SurfaceView mSurfaceView;
    private ImageView mThumbnailIv;
    private TextView mTimerTv;
    private Handler customHandler = new Handler();
    private long startTime = 0L;
    private TextView tvTip;
    private ImageView ivBack;

    private RecordingButtonInterface mRecordingInterface;
    private boolean mShowTimer;
    private boolean isFrontCameraEnabled;
    private boolean isCameraSwitchingEnabled;

    private long timeInMilliseconds;

    public VideoCaptureView(Context context) {
        super(context);
        initialize(context);
    }

    public VideoCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VideoCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        final View videoCapture = View.inflate(context, R.layout.view_videocapture, this);
        recordButton = (RecordButton) videoCapture.findViewById(R.id.record_view);
        mRecordBtnIv = (ImageView) videoCapture.findViewById(R.id.videocapture_recordbtn_iv);
        mAcceptBtnIv = (ImageView) videoCapture.findViewById(R.id.videocapture_acceptbtn_iv);
        mDeclineBtnIv = (ImageView) videoCapture.findViewById(R.id.videocapture_declinebtn_iv);
        mChangeCameraIv = (ImageView) videoCapture.findViewById(R.id.change_camera_iv);

        mRecordBtnIv.setOnClickListener(this);
        mAcceptBtnIv.setOnClickListener(this);
        mDeclineBtnIv.setOnClickListener(this);
        mChangeCameraIv.setOnClickListener(this);
        recordButton.setRecordListener(this);


        mThumbnailIv = (ImageView) videoCapture.findViewById(R.id.videocapture_preview_iv);
        mSurfaceView = (SurfaceView) videoCapture.findViewById(R.id.videocapture_preview_sv);

        mTimerTv = (TextView) videoCapture.findViewById(R.id.videocapture_timer_tv);
        tvTip = (TextView) videoCapture.findViewById(R.id.tv_tip);
        ivBack = (ImageView) videoCapture.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

    }

    public void setRecordingButtonInterface(RecordingButtonInterface mBtnInterface) {
        this.mRecordingInterface = mBtnInterface;
    }

    public void setCameraSwitchingEnabled(boolean isCameraSwitchingEnabled) {
        this.isCameraSwitchingEnabled = isCameraSwitchingEnabled;
        mChangeCameraIv.setVisibility(isCameraSwitchingEnabled ? View.VISIBLE : View.INVISIBLE);
    }

    public void setCameraFacing(boolean isFrontFacing) {
        if (!isCameraSwitchingEnabled) return;
        isFrontCameraEnabled = isFrontFacing;
        mChangeCameraIv.setImageResource(isFrontCameraEnabled ?
                R.drawable.ic_change_camera :
                R.drawable.ic_change_camera);
    }

    public SurfaceHolder getPreviewSurfaceHolder() {
        return mSurfaceView.getHolder();
    }

    public void updateUINotRecording() {
        mRecordBtnIv.setSelected(false);
        mRecordBtnIv.animate().scaleX(0.8f).scaleY(0.8f).setDuration(300).start();
        mChangeCameraIv.setVisibility(allowCameraSwitching() ? VISIBLE : INVISIBLE);
        mRecordBtnIv.setVisibility(View.VISIBLE);
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
        recordButton.setVisibility(VISIBLE);
    }


    public void updateUINotRecordingReset() {
        customHandler.removeCallbacks(updateTimerThread);
        updateUINotRecording();
        mTimerTv.setText("");
        ivBack.setVisibility(View.VISIBLE);
        tvTip.setVisibility(View.VISIBLE);
        mChangeCameraIv.setVisibility(VISIBLE);
        recordButton.setProgress(0);

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {

            Logger.i("video", "timeInMilliseconds = " + timeInMilliseconds);
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            updateRecordingTime(seconds, minutes);
            customHandler.postDelayed(this, 200);
        }
    };

    public void updateUIRecordingOngoing() {
        mRecordBtnIv.setSelected(true);
        mRecordBtnIv.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        mRecordBtnIv.setVisibility(View.VISIBLE);
        mChangeCameraIv.setVisibility(View.INVISIBLE);
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
        recordButton.setVisibility(VISIBLE);
        if (mShowTimer) {
            mTimerTv.setVisibility(View.VISIBLE);
            startTime = SystemClock.uptimeMillis();
            updateRecordingTime(0, 0);
            customHandler.postDelayed(updateTimerThread, 200);
        }
    }

    public void updateUIRecordingFinished(Bitmap videoThumbnail) {
        mRecordBtnIv.setVisibility(View.INVISIBLE);
        mAcceptBtnIv.setVisibility(View.VISIBLE);
        mChangeCameraIv.setVisibility(View.INVISIBLE);
        mDeclineBtnIv.setVisibility(View.VISIBLE);
        mThumbnailIv.setVisibility(View.VISIBLE);
        mSurfaceView.setVisibility(View.VISIBLE);
        recordButton.setVisibility(GONE);
        int translationByX = DensityUtil.dp2px(getContext(), 100);
        mAcceptBtnIv.animate().translationXBy(translationByX).setInterpolator
                (new AccelerateDecelerateInterpolator()).setDuration(300).start();
        mDeclineBtnIv.animate().translationXBy(-translationByX).setInterpolator
                (new AccelerateDecelerateInterpolator()).setDuration(300).start();

        if (videoThumbnail != null) {
            mThumbnailIv.setScaleType(ScaleType.CENTER_CROP);
            mThumbnailIv.setImageBitmap(videoThumbnail);
        }
        customHandler.removeCallbacks(updateTimerThread);
    }

    @Override
    public void onClick(View v) {
        if (mRecordingInterface == null) return;

        if (v.getId() == mRecordBtnIv.getId()) {
            mRecordingInterface.onRecordButtonClicked();
        } else if (v.getId() == mAcceptBtnIv.getId()) {
            mRecordingInterface.onAcceptButtonClicked();
        } else if (v.getId() == mDeclineBtnIv.getId()) {
            mRecordingInterface.onDeclineButtonClicked();
        } else if (v.getId() == mChangeCameraIv.getId()) {
            isFrontCameraEnabled = !isFrontCameraEnabled;
            mChangeCameraIv.setImageResource(isFrontCameraEnabled ?
                    R.drawable.ic_change_camera : R.drawable.ic_change_camera);
            mRecordingInterface.onSwitchCamera(isFrontCameraEnabled);
        } else if (v.getId() == ivBack.getId()) {
            mRecordingInterface.onBack();
        }
    }

    public void showTimer(boolean showTimer) {
        this.mShowTimer = showTimer;
    }

    private void updateRecordingTime(int seconds, int minutes) {
        recordButton.setProgress(timeInMilliseconds / 100);
        mTimerTv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    private boolean allowCameraSwitching() {
        return CapturePreview.isFrontCameraAvailable() && isCameraSwitchingEnabled;
    }

    @Override
    public void onPressRecordButton() {
        tvTip.setVisibility(GONE);
        ivBack.setVisibility(GONE);
        mRecordingInterface.onRecordButtonClicked();
    }

    @Override
    public void onReleaseRecordButton() {

        if (timeInMilliseconds < 5000) {
            mRecordingInterface.onRecordTooShort();
        } else {
            mRecordingInterface.onRecordButtonClicked();
        }

    }

    @Override
    public void onProgressMax() {
        mRecordingInterface.onRecordButtonClicked();
    }

}
