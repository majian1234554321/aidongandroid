package com.leyuan.aidong.ui.activity.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.ActivityTool;

public class VersionOfTheIntroductionActivity extends BaseActivity {
	private GestureDetector gestureDetector;
	private byte currentIndex;
	Bitmap bigBitmap = null;
	private Button btn_tiao;
	ImageView bigImageView;
	ImageView buttonView;
	RelativeLayout introLayout[];

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_version_of_the_introduction);
		initView();
	}

	protected void initData() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		freeBitmap();
	}

	float x;
	float y;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			y = event.getY();
		
			break;
		case MotionEvent.ACTION_UP:
			int ux = (int) Math.abs(event.getX()-x);
			int uy = (int) Math.abs(event.getY()-y);
			if(ux<5 && uy<5){
				if(currentIndex ==2){
					finish();
					return true;
				}
			}
			break;

		default:
			break;
		}
		if (gestureDetector.onTouchEvent(event))
			return true;
		else
			return false;
	}

	private void freeBitmap() {
		if (bigBitmap != null) {
			bigBitmap.recycle();
			bigBitmap = null;
		}
		System.gc();
	}

	private void showCurrentView() {
		freeBitmap();
//		for (int i = 0; i < introLayout.length; i++) {
//			introLayout[i].setVisibility(View.GONE);
//		}
//		introLayout[currentIndex].setVisibility(View.VISIBLE);
		bigBitmap = BitmapFactory.decodeResource(getResources(),
				ActivityTool.INDIC_IMAGE_ARRAY[currentIndex]);
//		if (currentIndex == introLayout.length - 1) {
			bigImageView.setScaleType(ScaleType.FIT_XY);
//		} else {
//			bigImageView.setScaleType(ScaleType.FIT_CENTER);
//		}
		bigImageView.setImageBitmap(bigBitmap);
	}

	private void initView() {
		gestureDetector = new GestureDetector(this, new LearnGestureListener());
		bigImageView = (ImageView) findViewById(R.id.bigImage);
		bigBitmap = BitmapFactory.decodeResource(getResources(),
				ActivityTool.INDIC_IMAGE_ARRAY[currentIndex]);
		bigImageView.setImageBitmap(bigBitmap);
		btn_tiao = (Button) findViewById(R.id.btn_tiao);
		btn_tiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		introLayout = new RelativeLayout[MAX_INDIC];
//		introLayout[0] = (RelativeLayout) findViewById(R.id.introduction1);
//		introLayout[1] = (RelativeLayout) findViewById(R.id.introduction2);
//		introLayout[2] = (RelativeLayout) findViewById(R.id.introduction3);
//		introLayout[3] = (RelativeLayout) findViewById(R.id.introduction4);
//		introLayout[4] = (RelativeLayout) findViewById(R.id.introduction5);
//		buttonView = (ImageView) findViewById(R.id.img_start_mx);
//		buttonView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
		

		showCurrentView();
	}

	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapUp(MotionEvent ev) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent ev) {
			Log.d("onShowPress", ev.toString());
		}

		@Override
		public void onLongPress(MotionEvent ev) {
			Log.d("onLongPress", ev.toString());
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d("onScroll", e1.toString());
			return true;
		}

		@Override
		public boolean onDown(MotionEvent ev) {
			Log.d("onDownd", ev.toString());
//			if(currentIndex == 2){
//				finish();
//			}
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
			float x = e2.getX() - e1.getX();
			Log.d("onFling", ""+x);
			if (x > 5) {
				doResult(Constant.RIGHT);
			} else if (x < 5) {
				doResult(Constant.LEFT);
			}
			return true;
		}
	}

	public void doResult(int action) {
		if (action == Constant.RIGHT) {
			if (currentIndex > 0) {
				currentIndex--;
			}
		} else {
			if (currentIndex < ActivityTool.INDIC_IMAGE_ARRAY.length - 1) {
				currentIndex++;
			}
		}
		showCurrentView();
	}
}
