package com.example.aidong.ui.activity.vedio.media;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.utils.common.Constant;
import com.example.aidong.entity.model.AttributeImages;
import com.example.aidong.utils.ImageUtil;
import com.example.aidong.utils.Utils;
import com.example.aidong.widget.customview.ImageLoadingDialog;
import com.example.aidong.widget.customview.MybigImageViewPager;
import com.example.aidong.widget.customview.PhotoView;
import com.example.aidong.widget.customview.ZoomImageView.FillingListener;
import com.example.aidong.widget.customview.ZoomImageView.SingleTapListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageShowerActivity extends BaseActivity implements
		SingleTapListener, FillingListener{

	private static final String ISLOCKED_ARG = "isLocked";
	String TAG = "ImageShowerActivity";
	private ArrayList<AttributeImages> imageArray;
	private int currentIndex;
	private int viewPageIndex;
	private int lastIndex;
	private LinearLayout pointGroup;
	private ArrayList<ImageView> pointView;
//	private ImageView bigImageView;
//	private PhotoViewAttacher mAttacher;
//	private HackyViewPager mViewPager;
	private MybigImageViewPager mViewPager;
	private GestureDetector gestureDetector;
	private SamplePagerAdapter mSamplePagerAdapter;
	private PhotoView currentView;
	private boolean isFirstInView;
//	private ScaleGestureDetector mScaleGestureDetector;
//	private SurfaceHolder mSurfaceHolder = null;
	private ImageView img_back;
	ImageLoadingDialog dialog;
//	ImageDownloadTask imgtask;
//	Bitmap bigBitmap = null;
	int widthPixels, heightPixels;
	int totalImageArrayCount;
	TextView txt_number;
	
	ScrollView scrollview;
	protected DisplayImageOptions options;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getBundleData();
		setupView();
	}

	protected void setupView() {
		setContentView(R.layout.imageshower);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.icon_picture)
		.showImageForEmptyUri(R.drawable.icon_picture)
		.showImageOnFail(R.drawable.icon_picture)
		.cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565).build();
		initView();
		setViewClick();
	}

	protected void getBundleData() {
		imageArray = getIntent().getParcelableArrayListExtra(Constant.BUNDLE_BIGIMAGEITEM);
		currentIndex = getIntent().getIntExtra(Constant.BUNDLE_BIGIMAGEITEM_INDEX, 0);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		freeBigBitmap();
//		freeLoadTask();
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
//		MxImageUtil.setImageFreshListener(null);
		System.gc();
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		 if (gestureDetector.onTouchEvent(event))
//			 return true;
//		 else
//			 return false;
//	}

//	private void freeBigBitmap() {
//		if (bigBitmap != null) {
//			bigBitmap.recycle();
//			bigBitmap = null;
//			System.gc();
//		}
//	}

//	private ImageFreshListener mImageFreshListener = new ImageUtil.ImageFreshListener() {
//		@Override
//		public void onADAdpaterFresh() {
//			// TODO Auto-generated method stub
//			if (dialog != null) {
//				dialog.dismiss();
//			}
//		}
//	};

	private OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			finish();
		}

	};

//	class ScaleGestureListener implements
//			ScaleGestureDetector.OnScaleGestureListener {
//
//		@Override
//		public boolean onScale(ScaleGestureDetector detector) {
//			// TODO Auto-generated method stub
//
//			Matrix mMatrix = new Matrix();
//			// 缩放比例
//			float scale = detector.getScaleFactor() / 3;
//			mMatrix.setScale(scale, scale);
//
//			// 锁定整个SurfaceView
//			Canvas mCanvas = mSurfaceHolder.lockCanvas();
//			// 清屏
//			mCanvas.drawColor(Color.BLACK);
//			// 画缩放后的图
//			mCanvas.drawBitmap(bigBitmap, mMatrix, null);
//			// 绘制完成，提交修改
//			mSurfaceHolder.unlockCanvasAndPost(mCanvas);
//			// 重新锁一次
//			mSurfaceHolder.lockCanvas(new Rect(0, 0, 0, 0));
//			mSurfaceHolder.unlockCanvasAndPost(mCanvas);
//
//			return false;
//		}
//
//		@Override
//		public boolean onScaleBegin(ScaleGestureDetector detector) {
//			// TODO Auto-generated method stub
//			// 一定要返回true才会进入onScale()这个函数
//			return true;
//		}
//
//		@Override
//		public void onScaleEnd(ScaleGestureDetector detector) {
//			// TODO Auto-generated method stub
//
//		}
//	}

	class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onSingleTapUp(MotionEvent ev) {
			Log.d("onSingleTapUp", ev.toString());
			finish();
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
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			float x = e2.getX() - e1.getX();
			Log.e(TAG, "x  = " + x);
			if (x > 0) {
				doResult(Constant.RIGHT);
			} else if (x < 0) {
				doResult(Constant.LEFT);
			}
			return true;
		}
	}

	public void doResult(int action) {
		ImageView lastView = null;
		ImageView nextView = null;
		boolean hasChange = false;
		lastView = (ImageView) pointGroup.getChildAt(currentIndex);
		lastView.setImageResource(R.drawable.page_indicator_unfocused);
		if (action == Constant.RIGHT) {
			if(mViewPager.getCurrentItem()>0){
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
			}
//			if (imageArray.size() > 1) {
//				currentIndex--;
//				if (currentIndex < 0) {
//					currentIndex = imageArray.size() - 1;
//				}
//				hasChange = true;
//			}
		} else {
			if(mViewPager.getCurrentItem()<mViewPager.getChildCount()){
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
			}
//			if (imageArray.size() > 1) {
//				currentIndex++;
//				if (currentIndex == imageArray.size()) {
//					currentIndex = 0;
//				}
//				hasChange = true;
//			}
		}
//		if (hasChange) {
//			nextView = (ImageView) pointGroup.getChildAt(currentIndex);
//			nextView.setImageResource(R.drawable.page_indicator_focused);
//			loadAndShowBigBitmap();
//		}
	}

	private void initView() {
		isFirstInView = true;
//		currentIndex = 0;
		totalImageArrayCount = imageArray.size();
		img_back = (ImageView) findViewById(R.id.img_back);
//		MxImageUtil.setImageFreshListener(mImageFreshListener);
		pointGroup = (LinearLayout) findViewById(R.id.pointGroup);
		txt_number = (TextView) findViewById(R.id.txt_number);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		mSamplePagerAdapter = new SamplePagerAdapter();
		mViewPager = (MybigImageViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(mSamplePagerAdapter);
		mSamplePagerAdapter.notifyDataSetChanged();
		/** 这里是获取手机屏幕的分辨率用来处理 图片 溢出问题的。begin */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		widthPixels = dm.widthPixels;
		heightPixels = dm.heightPixels;
//		Log.e(TAG, "mViewPager.getChildCount() = "+mViewPager.getChildCount());
//		currentView = (PhotoView) mViewPager.getChildAt(viewPageIndex);
//		loadAndShowBigBitmap();

		if (imageArray != null && imageArray.size() > 0) {
			pointView = new ArrayList<ImageView>();
			for (int i = 0; i < imageArray.size(); i++) {
				ImageView imageV = new ImageView(this);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.weight = 1;
				imageV.setLayoutParams(params);
				imageV.setImageResource(R.drawable.page_indicator_unfocused);
				if (i == currentIndex) {
					imageV.setImageResource(R.drawable.page_indicator_focused);
				}
				pointGroup.addView(imageV);
				pointView.add(imageV);
			}
			
			if(imageArray.get(currentIndex).getCaption()!=null){
				pointGroup.setVisibility(View.GONE);
				scrollview.setVisibility(View.VISIBLE);
				String s = (currentIndex+1)+"/"+imageArray.size()+" "+imageArray.get(currentIndex).getCaption();
				 SpannableString str = new SpannableString(s);
				 AbsoluteSizeSpan span = new AbsoluteSizeSpan(Utils.dip2px(ImageShowerActivity.this, 24));
				 str.setSpan(span, 0, s.indexOf(" "), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				txt_number.setText(str);
			}
		} else {
			pointGroup.setVisibility(View.GONE);
		}
		mViewPager.setCurrentItem(currentIndex);
	}

//	private void freeLoadTask() {
//		if (imgtask != null) {
//			imgtask.cancel(true);
//			imgtask.freeBitmap();
//			imgtask = null;
//		}
//	}

//	private void loadAndShowBigBitmap() {
//		freeBigBitmap();
//		freeLoadTask();
//		String imageUrl = imageArray.get(currentIndex).getOriginal();
//		if (imageUrl != null) {
//			String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
//			bigBitmap = MxFileUtil.getBigImageBitmap(imageName);
//			if (bigBitmap != null) {
//				if(currentView != null){
//					currentView.setImageBitmap(bigBitmap);
//				}
//			} else {
//				if (dialog == null) {
//					dialog = new ImageLoadingDialog(this);
//					dialog.setCanceledOnTouchOutside(false);
//					dialog.setOnCancelListener(cancelListener);
//				}
//				dialog.show();
//				imgtask = new ImageDownloadTask();
//				imgtask.setDisplayWidth(widthPixels);
//				imgtask.setDisplayHeight(heightPixels);
//				imgtask.setDisplayPixels();
//				imgtask.execute(imageArray.get(currentIndex).getOriginal(), null);
//			}
//		}
//	}

	private void setViewClick() {
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mViewPager.setOnPageChangeListener(onPageChangeListener);

	}
	
	ViewPager.OnPageChangeListener onPageChangeListener = 
			new ViewPager.OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int position) {
			for(int i = 0; i < pointGroup.getChildCount(); i++){
				ImageView view = (ImageView) pointGroup.getChildAt(i);
				if(i == position){
					view.setImageResource(R.drawable.page_indicator_focused);
				}else{
					view.setImageResource(R.drawable.page_indicator_unfocused);
				}
				
			
					
			}
			if(imageArray.get(position).getCaption()!=null){
				pointGroup.setVisibility(View.GONE);
				scrollview.setVisibility(View.VISIBLE);
				String s = (position+1)+"/"+imageArray.size()+" "+imageArray.get(position).getCaption();
				 SpannableString str = new SpannableString(s);
				 AbsoluteSizeSpan span = new AbsoluteSizeSpan(Utils.dip2px(ImageShowerActivity.this, 24));
				 str.setSpan(span, 0, s.indexOf(" "), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				txt_number.setText(str);
			}
			
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	

	// private class ImageDownloadThread extends Thread{
	//
	// private String url;
	// private int _displaywidth = 480;
	// private int _displayheight = 800;
	// private int _displaypixels = _displaywidth * _displayheight;
	//
	// public ImageDownloadThread(String url){
	// this.url = url;
	// }
	// public void setDisplayWidth(int width) {
	// _displaywidth = width;
	// }
	// public void setDisplayHeight(int height) {
	// _displayheight = height;
	// }
	//
	// @Override
	// public void run() {
	// Bitmap bmp = null;
	// try {
	// bmp = getBitmap(url, _displaypixels, true);
	// } catch (Exception e) {
	//
	// }
	// }
	//
	// }

//	public class ImageDownloadTask extends AsyncTask<Object, Object, Bitmap> {
////		private ImageView imageView = null;
//		private Bitmap bmp = null;
//		String fileName = null;
//		String url = null;
//
//		/***
//		 * 这里获取到手机的分辨率大小
//		 * */
//		public void setDisplayWidth(int width) {
//			_displaywidth = width;
//		}
//
//		public int getDisplayWidth() {
//			return _displaywidth;
//		}
//
//		public void setDisplayHeight(int height) {
//			_displayheight = height;
//		}
//
//		public void setDisplayPixels() {
//			_displaypixels = _displaywidth * _displayheight;
//		}
//
//		public int getDisplayHeight() {
//			return _displayheight;
//		}
//
//		public int getDisplayPixels() {
//			return _displaypixels;
//		}
//
//		private int _displaywidth = 480;
//		private int _displayheight = 800;
//		private int _displaypixels;
//
//		public void freeBitmap() {
//			if (bmp != null) {
//				bmp.recycle();
//				bmp = null;
//			}
//			System.gc();
//		}
//
//		@Override
//		protected Bitmap doInBackground(Object... params) {
//			// TODO Auto-generated method stub
//			freeBitmap();
////			imageView = (ImageView) params[1];
//			try {
//				url = (String) params[0];
//				bigBitmap = getBitmap(url, _displaypixels, true, _displaywidth,
//						_displayheight);
//			} catch (Exception e) {
//
//			}
//			return null;
//		}
//
//		protected void onPostExecute(Bitmap result) {
//			if (dialog != null) {
//				dialog.dismiss();
//			}
//			if(bigBitmap != null){
//				fileName = url.substring(url.lastIndexOf("/"));
//				MxFileUtil.saveBigBitmapInSD(fileName, bigBitmap);
//				if(currentView != null){
//					currentView.setImageBitmap(bigBitmap);
//				}
//			}
////			if (imageView != null && bmp != null) {
////				fileName = url.substring(url.lastIndexOf("/"));
////				MxFileUtil.saveBigBitmapInSD(fileName, bmp);
////				imageView.setImageBitmap(bmp);
////				if (null != bmp && bmp.isRecycled() == false) {
////					System.gc();
////				}
////			}
//		}
//	}

	/**
	 * 通过URL获得网上图片。如:http://www.xxxxxx.com/xx.jpg
	 * */
	private Bitmap getBitmap(String url, int displaypixels, Boolean isBig,
			int displayW, int displayH) throws MalformedURLException,
			IOException {
		Bitmap bmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		InputStream stream = new URL(url).openStream();
		byte[] bytes = getBytes(stream);
		// 这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;)
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		double scaleWH = opts.outWidth / opts.outHeight;
		int mobileWidthPixels = displayW;
		int mobileHeightPixels = displayH;
		int bitmapW = opts.outWidth;
		int bitmapH = opts.outHeight;
		opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);
		opts.inJustDecodeBounds = false;
		bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		Log.e(TAG, "opts.inSampleSize = " + opts.inSampleSize);
		// opts.outWidth = mobileWidthPixels;
		// opts.outHeight = (int) (mobileWidthPixels / scaleWH);
		// // end
		// try{
		// opts.inJustDecodeBounds = false;
		// bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		// }catch(Exception e){
		// //如果图片出现oom需要进行处理
		// System.gc();
		// opts.outWidth = bitmapW;
		// opts.outHeight = bitmapH;
		// opts.inSampleSize = computeSampleSize(opts, -1, displaypixels);
		// bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		// }
		return bmp;
	}

	/**
	 * 数据流转成btyle[]数组
	 * */
	private byte[] getBytes(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] b = new byte[2048];
		int len = 0;
		try {
			while ((len = is.read(b, 0, 2048)) != -1) {
				baos.write(b, 0, len);
				baos.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();
		return bytes;
	}

	/****
	 * 处理图片bitmap size exceeds VM budget （Out Of Memory 内存溢出）
	 */
	private int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}

	private int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;
		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));
		if (upperBound < lowerBound) {
			return lowerBound;
		}
		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSingleTap() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onFilling(float move_x) {
		// TODO Auto-generated method stub
		if (move_x > 0) {
			doResult(Constant.RIGHT);
		} else if (move_x < 0) {
			doResult(Constant.LEFT);
		}
	}
	
	
	class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return totalImageArrayCount;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			// Now just add PhotoView to ViewPager and return it
			View v=LayoutInflater.from(ImageShowerActivity.this).inflate(
					R.layout.bug_pic_item, null);
//			PhotoView photoView = new PhotoView(container.getContext());
			PhotoView photoView = (PhotoView) v.findViewById(R.id.bigimage_pv);
			currentIndex = position;
			currentView = photoView;
			imageLoader.displayImage(ImageUtil.getUrlWindow(ImageShowerActivity.this, imageArray.get(position).getUrl()), photoView, options);
			
			
//			loadAndShowBigBitmap();
			container.addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			Log.e(TAG, "destroyItem position = "+position);
			container.removeView((View) object);
//			freeBigBitmap();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}



}
