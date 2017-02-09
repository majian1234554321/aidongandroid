package com.leyuan.aidong.ui.media;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.CommonUtils;
import com.leyuan.aidong.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class MyRecorderVideoActivity extends BaseActivity implements Callback,
		OnClickListener, OnInfoListener, OnErrorListener {
	private final static String CLASS_LABEL = "MyRecordActivity";
	private WakeLock mWakeLock;
	private VideoView mVideoView;
	private ImageView close;
	private ImageView convert_camera;
	private TextView close_flashlight;
	private TextView auto_flashlight;
	private TextView open_flashlight;
	private ProgressBar pb_recorder_time;
	private TextView text_recorder_alltime;
	private ImageView start_recorder;
	private SurfaceHolder mSurfaceHolder;
	private Camera mCamera;
	private MediaRecorder mediaRecorder;
	/**  0是后置摄像头，1是前置摄像头,默认为后摄像头*/
	private int frontCamera = 0;
	int defaultVideoFrameRate = -1;
	private int previewWidth = 480;
	private int previewHeight = 480;
	private String localPath;
	private TextView text_start_recorder;
	private ImageView stop_recorder;
	private Chronometer chronometer;
	private String currentTime;
	private int current = 1;
	/** 是否正在录制中*/
	private boolean isRecordering = false;
	/** 闪光灯状态，0关闭，1打开，2自动，默认关闭*/
	private int flash_mode = 0;
	private boolean isFromDynamin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFormat(PixelFormat.TRANSPARENT);
		setContentView(R.layout.layout_my_recorder_video_activity);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
				CLASS_LABEL);
		mWakeLock.acquire();
		isFromDynamin = getIntent().getBooleanExtra("isFromDynamin", false);
		initView();

	}

	private void initView() {
		close = (ImageView) findViewById(R.id.close);
		convert_camera = (ImageView) findViewById(R.id.convert_camera);
		close_flashlight = (TextView) findViewById(R.id.close_flashlight);
		auto_flashlight = (TextView) findViewById(R.id.auto_flashlight);
		open_flashlight = (TextView) findViewById(R.id.open_flashlight);
		pb_recorder_time = (ProgressBar) findViewById(R.id.pb_recorder_time);
		chronometer = (Chronometer) findViewById(R.id.chronometer);
		text_recorder_alltime = (TextView) findViewById(R.id.text_recorder_alltime);
		text_start_recorder = (TextView) findViewById(R.id.text_start_recorder);
		start_recorder = (ImageView) findViewById(R.id.start_recorder);
		stop_recorder = (ImageView) findViewById(R.id.stop_recorder);
		mVideoView = (VideoView) findViewById(R.id.videoView);
		mSurfaceHolder = mVideoView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setclick();

	}

	private void setclick() {
		close.setOnClickListener(this);
		convert_camera.setOnClickListener(this);
		close_flashlight.setOnClickListener(this);
		auto_flashlight.setOnClickListener(this);
		open_flashlight.setOnClickListener(this);
		start_recorder.setOnClickListener(this);
		stop_recorder.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.close:
			releaseRecorder();
			releaseCamera();
            finish();
			break;
		case R.id.convert_camera:
			switchCamera();
			break;
		case R.id.close_flashlight:
			if(flash_mode != 0)
			{
				auto_flashlight.setTextColor(Color.WHITE);
				open_flashlight.setTextColor(Color.WHITE);
				close_flashlight.setTextColor(Color.parseColor("#00cbf7"));
				
			  flash_mode = 0;
              turnLightOn(mCamera);
			}

			break;
		case R.id.auto_flashlight:
			if(flash_mode != 2)
			{
				close_flashlight.setTextColor(Color.WHITE);
				open_flashlight.setTextColor(Color.WHITE);
				auto_flashlight.setTextColor(Color.parseColor("#00cbf7"));
			  flash_mode = 2;
              turnLightOn(mCamera);
			}

			break;
		case R.id.open_flashlight:
			if(flash_mode != 1)
			{
				close_flashlight.setTextColor(Color.WHITE);
				auto_flashlight.setTextColor(Color.WHITE);
				open_flashlight.setTextColor(Color.parseColor("#00cbf7"));
			  flash_mode = 1;
              turnLightOn(mCamera);
			}
			break;
		case R.id.start_recorder:
			 if(!startRecording())
			        return;
			 isRecordering = true;
			 text_start_recorder.setText("点击停止拍摄");
			 start_recorder.setVisibility(View.INVISIBLE);
			 start_recorder.setEnabled(false);
			 stop_recorder.setVisibility(View.VISIBLE);
			 chronometer.setBase(SystemClock.elapsedRealtime());
			 chronometer.start();
			new Thread(new Runnable() {
				int i = 0;
				@Override
				public void run() {
					while(isRecordering)
					{
						try {
							Thread.sleep(100);
							i += 100;
							pb_recorder_time.setProgress(i);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
			break;
		case R.id.stop_recorder:
			currentTime = chronometer.getText().toString();
			try {
				current = Integer.parseInt(currentTime.substring(
						currentTime.length() -2,currentTime.length() -1));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			if(current < 1)
			{
				Toast.makeText(getApplicationContext(), "录制时间太短", Toast.LENGTH_SHORT).show();
			}
			else{
			completeRecorderVideo();
			}
			break;

		default:
			break;
		}

	}
	

	private void turnLightOn(Camera mCamera) {
		
		if(mCamera == null)
		{
			return;
		}
		Parameters parameters = mCamera.getParameters();
		if (parameters == null) {
			return;
		}
		List<String> flashModes = parameters.getSupportedFlashModes();
		if (flashModes == null) {
			return;
		}
		switch (flash_mode) {
		case 0:
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
			}
			else{
				Toast.makeText(this, "关闭闪光灯模式不支持", Toast.LENGTH_SHORT).show();
			}
			break;
		case 1 :
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
			}
			else{
				Toast.makeText(this, "开启闪光灯模式不支持", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case 2 :
			if (flashModes.contains(Parameters.FLASH_MODE_AUTO)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_AUTO);
				mCamera.setParameters(parameters);
			}
			else{
				Toast.makeText(this, "自动闪光灯模式不支持", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		
		
	}

	public void switchCamera() {
		if(!isRecordering)
		{
		if (mCamera == null) {
			return;
		}
		if (Camera.getNumberOfCameras() >= 2) {
			convert_camera.setEnabled(false);
			
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
           
			switch (frontCamera) {
			case 0:
				mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
				frontCamera = 1;
				break;
			case 1:
				mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
				frontCamera = 0;
				break;
			}
			
			try {
				mCamera.lock();
				mCamera.setDisplayOrientation(90);
				mCamera.setPreviewDisplay(mVideoView.getHolder());
				mCamera.startPreview();
			} catch (IOException e) {
				mCamera.release();
				mCamera = null;
			}
			
			
			convert_camera.setEnabled(true);

		}
		}
	}


	private void stopRecorder() {
             if(mediaRecorder != null)
             {
            	 mediaRecorder.setOnErrorListener(this);
            	 mediaRecorder.setOnInfoListener(this);
            	 try {
					mediaRecorder.stop();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
             }
             
             releaseRecorder();
             if(mCamera != null)
             {
            	 mCamera.stopPreview();
            	 releaseCamera();
             }
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mWakeLock == null) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
					CLASS_LABEL);

			mWakeLock.acquire();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mWakeLock != null) {
			mWakeLock.release();
			mWakeLock = null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseCamera();
		if (mWakeLock != null) {
			mWakeLock.release();
			mWakeLock = null;
		}

	}
	
	public boolean startRecording(){
		if (mediaRecorder == null){
			if(!initRecorder())
			    return false;
		}
		mediaRecorder.setOnInfoListener(this);
		mediaRecorder.setOnErrorListener(this);
		
		mediaRecorder.start();
		return true;
	}
	
	
	
	private boolean initRecorder() {
		  if(!CommonUtils.isExitsSdcard()){
		        showNoSDCardDialog();
		        return false;
		    }
		  if (mCamera == null) {
				if(!initCamera()){
				    showFailDialog();
				    return false;
				}
			}
			mCamera.stopPreview();
			mediaRecorder = new MediaRecorder();
			mCamera.unlock();
			mediaRecorder.setCamera(mCamera);
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
			// 设置录制视频源为Camera（相机）
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			if (frontCamera == 1) {
				mediaRecorder.setOrientationHint(270);
			} else {
				mediaRecorder.setOrientationHint(90);
			}
			// 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			// 设置录制的视频编码h263 h264
			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			// 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
			mediaRecorder.setVideoSize(previewWidth, previewHeight);
			// 设置视频的比特率
			mediaRecorder.setVideoEncodingBitRate(384 * 1024);
			// // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
			if (defaultVideoFrameRate != -1) {
				mediaRecorder.setVideoFrameRate(defaultVideoFrameRate);
			}
//			File path = MyRecorderVideoActivity.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//	 		Log.i("select", "====" + path.getPath());
	 		if(!path.exists())
	 		{
	 			if(!path.mkdirs())
	 				return false;
	 		}
			localPath = path.getPath() + "/"
					+ System.currentTimeMillis() + ".mp4";
			mediaRecorder.setOutputFile(localPath);
			mediaRecorder.setMaxDuration(30000);
			mediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
			try {
	            mediaRecorder.prepare();
	        } catch (IllegalStateException e) {
	            e.printStackTrace();
	            return false;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
			return true;
	}
	
	private void showNoSDCardDialog() {
	    new AlertDialog.Builder(this)
        .setTitle(R.string.prompt)
        .setMessage("No sd card!")
        .setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        finish();

                    }
                }).setCancelable(false).show();
	}


	@SuppressLint("NewApi")
	private boolean initCamera() {
		try {
			//这里要添加检查摄像头程序,后面加
			if (frontCamera == 0) {
				mCamera = Camera.open(CameraInfo.CAMERA_FACING_BACK);
			} else {
				mCamera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
			}
			Parameters camParams = mCamera.getParameters();
			mCamera.lock();
			mSurfaceHolder = mVideoView.getHolder();
			mSurfaceHolder.addCallback(this);
			mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			mCamera.setDisplayOrientation(90);

		} catch (RuntimeException ex) {
//			EMLog.e("video", "init Camera fail " + ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (mCamera == null){
			if(!initCamera()){
			    showFailDialog();
			    return;
			}
			
		}
		try {
			mCamera.setPreviewDisplay(mSurfaceHolder);
			mCamera.startPreview();
			handleSurfaceChanged();
		} catch (Exception e1) {
//			EMLog.e("video", "start preview fail " + e1.getMessage());
			showFailDialog();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		mSurfaceHolder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		completeRecorderVideo();

	}
	

	private void releaseRecorder() {
		if (mediaRecorder != null) {
			mediaRecorder.release();
			mediaRecorder = null;
		}
	}

	protected void releaseCamera() {
		try {
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
		} catch (Exception e) {
		}
	}
	/** 打开摄像头失败的log*/
	private void showFailDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.prompt)
				.setMessage(R.string.Open_the_equipment_failure)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();

							}
						}).setCancelable(false).show();

	}
	
	private void handleSurfaceChanged() {
		if (mCamera == null) {
			finish();
			return;
		}
		boolean hasSupportRate = false;
		List<Integer> supportedPreviewFrameRates = mCamera.getParameters()
				.getSupportedPreviewFrameRates();
		if (supportedPreviewFrameRates != null
				&& supportedPreviewFrameRates.size() > 0) {
			Collections.sort(supportedPreviewFrameRates);
			for (int i = 0; i < supportedPreviewFrameRates.size(); i++) {
				int supportRate = supportedPreviewFrameRates.get(i);

				if (supportRate == 15) {
					hasSupportRate = true;
				}

			}
			if (hasSupportRate) {
				defaultVideoFrameRate = 15;
			} else {
				defaultVideoFrameRate = supportedPreviewFrameRates.get(0);
			}

		}
		// 获取摄像头的所有支持的分辨率
		List<Size> resolutionList = Utils.getResolutionList(mCamera);
		if (resolutionList != null && resolutionList.size() > 0) {
			Collections.sort(resolutionList, new Utils.ResolutionComparator());
			Size previewSize = null;
			boolean hasSize = false;
			// 如果摄像头支持640*480，那么强制设为640*480
			for (int i = 0; i < resolutionList.size(); i++) {
				Size size = resolutionList.get(i);
				if (size != null && size.width == 640 && size.height == 480) {
					previewSize = size;
					previewWidth = previewSize.width;
					previewHeight = previewSize.height;
					hasSize = true;
					break;
				}
			}
			// 如果不支持设为中间的那个
			if (!hasSize) {
				int mediumResolution = resolutionList.size() / 2;
				if (mediumResolution >= resolutionList.size())
					mediumResolution = resolutionList.size() - 1;
				previewSize = resolutionList.get(mediumResolution);
				previewWidth = previewSize.width;
				previewHeight = previewSize.height;

			}

		}

	}
//	
//	/** 检查手机里是否有摄像头 */
//	private boolean checkCameraHardware(Context context) {
//	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//	        // this device has a camera
//	        return true;
//	    } else {
//	        // no camera on this device
//	        return false;
//	    }
//	}
//	
//	/** 
//	 * 检查前置摄像头
//	 * @return 如果有前置摄像头，返回前置摄像头id，否则返回-1
//	 */
//	 private int FindFrontCamera(){  
//	        int cameraCount = 0;  
//	        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
//	        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
//	                
//	        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
//	            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
//	            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_FRONT ) {   
//	                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
//	               return camIdx;  
//	            }  
//	        }  
//	        return -1;  
//	    }  
//
//	 /** 
//		 * 检查后置摄像头
//		 * @return 如果有后置摄像头，返回后置摄像头id，否则返回-1
//		 */
//	    private int FindBackCamera(){  
//	        int cameraCount = 0;  
//	        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();  
//	        cameraCount = Camera.getNumberOfCameras(); // get cameras number  
//	                
//	        for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {  
//	            Camera.getCameraInfo( camIdx, cameraInfo ); // get camerainfo  
//	            if ( cameraInfo.facing ==Camera.CameraInfo.CAMERA_FACING_BACK ) {   
//	                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置  
//	               return camIdx;  
//	            }  
//	        }  
//	        return -1;  
//	    }

	@Override
	public void onInfo(MediaRecorder mr, int what, int extra) {
		if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED)
		{
			completeRecorderVideo();
		}
	}

	private void completeRecorderVideo() {
		stop_recorder.setEnabled(false);
		stopRecorder();
		chronometer.stop();
		stop_recorder.setVisibility(View.INVISIBLE);
		start_recorder.setVisibility(View.VISIBLE);
		isRecordering = false;
		if(localPath == null)
		{
			Toast.makeText(this,
					"视频存储失败，请检查SD卡",
					Toast.LENGTH_SHORT).show();
		}
		else
		{
			Intent intent = new Intent(MyRecorderVideoActivity.this, PreviewViedoShowActivity.class);
			intent.putExtra("path", localPath);
			if(isFromDynamin)
			{
				intent.putExtra("isFromDynamin", true);
			}
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onError(MediaRecorder mr, int what, int extra) {
		stopRecorder();
		Toast.makeText(this,
				"录制错误，停止录制",
				Toast.LENGTH_SHORT).show();
	} 

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			releaseRecorder();
			releaseCamera();
            finish();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
