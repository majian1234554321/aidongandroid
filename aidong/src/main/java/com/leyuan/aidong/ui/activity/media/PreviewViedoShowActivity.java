package com.leyuan.aidong.ui.activity.media;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;

import java.io.IOException;

public class PreviewViedoShowActivity extends BaseActivity implements
		OnCompletionListener, OnErrorListener, OnClickListener {
	private TextView text_abandon;
	private TextView text_apply;
	private SurfaceView surface_view;
	private ImageView start_play;
	private String path;
	private MediaPlayer media;
	private boolean isFromDynamin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		path = getIntent().getStringExtra("path");
		isFromDynamin = getIntent().getBooleanExtra("isFromDynamin", false);
		setContentView(R.layout.layout_preview_viedo_show_activity);
		findView();
		initPlay();
		setClick();
		sendBroadCaseRemountSDcard();
	}

	

	private void findView() {
		text_abandon = (TextView) findViewById(R.id.text_abandon);
		text_apply = (TextView) findViewById(R.id.text_apply);
		surface_view = (SurfaceView) findViewById(R.id.surface_view);
		surface_view.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surface_view.getHolder().addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				play();
				media.stop();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
		start_play = (ImageView) findViewById(R.id.start_play);
	}

	/** 初始化播放器 */
	private void initPlay() {
		if (path != null) {
			media = new MediaPlayer();
			media.setOnCompletionListener(this);
			media.setOnErrorListener(this);
		}

	}

	private void play() {
		try {
			if (media != null) {
				media.reset();
				media.setAudioStreamType(AudioManager.STREAM_MUSIC);
				media.setDataSource(path);
				media.setDisplay(surface_view.getHolder());
				media.prepare();
				media.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setClick() {
		text_abandon.setOnClickListener(this);
		text_apply.setOnClickListener(this);
		start_play.setOnClickListener(this);

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		start_play.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{  if(media != null)
        {
     	   media.release();
     	   media = null;
        }
        finish();
        return true;
			
		}
		
		return super.onKeyDown(keyCode, event);
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_abandon:
           if(media != null)
           {
        	   media.release();
        	   media = null;
           }
           finish();
			break;
		case R.id.text_apply:
			 if(media != null)
	           {
	        	   media.release();
	        	   media = null;
	           }
			 //启动添加视频界面
			 
             Intent intent;
             if (isFromDynamin) {
            	 intent = new Intent(this, TabTheIndividualDynaminActivity.class);
			}else{
             intent = new Intent(this, TabVideoShowActivity.class);
			}
             intent.putExtra("path", path);
             intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(intent);
			break;
		case R.id.start_play:
			if(media != null)
			{
				play();
			    start_play.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}

	}
	/** 通知多媒体库扫描该文件*/
	private void sendBroadCaseRemountSDcard() {
		
//		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));  
		MediaScannerConnection.scanFile(this, new String[]{path}, null, null);
		
	}

}
