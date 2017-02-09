package com.leyuan.aidong.ui.media;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.AlbumGridViewAdapter;
import com.leyuan.aidong.adapter.FolderAdapter;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.entity.model.location.ImageBucket;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.utils.FileUtil;
import com.leyuan.aidong.utils.photo.AlbumHelper;
import com.leyuan.aidong.utils.photo.Bimp;
import com.leyuan.aidong.widget.customview.ClipView;
import com.leyuan.aidong.widget.customview.LinearLayoutVerticalScrolling;
import com.leyuan.aidong.widget.customview.LinearLayoutVerticalScrolling.Status;
import com.leyuan.aidong.widget.customview.SquareRelativeLayout;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 这个是进入相册显示所有图片的界面
 * 
 * @author king
 * @version 2014年10月18日 下午11:47:15
 */
public class AlbumActivity extends BaseActivity implements OnTouchListener {

	public  FileUtil MxFileUtil = FileUtil.getInstance();
	/** 显示手机里的所有图片的列表控件 */
	private GridView gridView;
	// 当手机里没有图片时，提示用户没有图片的控件
	// private TextView tv;s
	/** 显示图片的gridView的adapter */
	private AlbumGridViewAdapter gridImageAdapter;
	/** 下一步按钮 */
	private TextView next_Button;
	/** 选择相册按钮，就是之前的返回按钮 */
	private RelativeLayout choose_album;
	/** 取消按钮 */
	private ImageView cancel;
	private Intent intent;
	// 预览按钮,暂不用
	// private Button preview;
	private Context mContext;
	public ArrayList<ImageItem> dataList;
	/** 相册帮助类 */
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	private int currentPosition;
	/** 裁剪框relative */
	private SquareRelativeLayout srcPic;
	private ImageView img_clip_bg;

	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	/** 动作标志：无 */
	private static final int NONE = 0;
	/** 动作标志：拖动 */
	private static final int DRAG = 1;
	/** 动作标志：缩放 */
	private static final int ZOOM = 2;
	/** 初始化动作标志 */
	private int mode = NONE;

	/** 记录起始坐标 */
	private PointF start = new PointF();
	/** 记录缩放时两指中间点坐标 */
	private PointF mid = new PointF();
	private float oldDist = 1f;

	Class<? extends Activity> lastClass;

	Object obj = new Object();
	/** 存放临时图片的集合，当点击取消是若有选择的图片，用该集合还原 */
	ArrayList<ImageItem> huanclist = new ArrayList<ImageItem>();
	/** 选择相册的文字描述 */
	private TextView txt_all_picture;
	/** 选择相册的图标 */
	private ImageView img_down;
	/** 选择图片的图标 */
	private ImageView img_rotate;
	/** 打开或关闭裁剪框的图标 */
	private ImageView img_pull;
	/** 选择的图片个数 */
	private TextView txt_select_number;
	/** 是否打开裁剪框，默认打开 */
	private boolean isOpen = true;
	/** 根布局 */
	private LinearLayoutVerticalScrolling linear_root;
	private RelativeLayout title;
	private FolderAdapter folderAdapter;
	public static final int FROM_VIDEO_SHOW_TO_GALLERY = 2;
	public static final int FROM_VIDEO_SHOW_TO_SHOW_ALL_PHOTO = 3;
	/** 是否是从视频秀跳到该页面的 */
	private boolean isFromVideoShow;

	private ClipView clipview;
	private PopupWindow popupWindow;
	private ImageItem imageItemFirst;
	/** 拍照的照片名称，用当前时间取名 */
	protected String currentAddFileName;
	/** 上一个选中的图片的位置 */
	private int last_checkded_Position = -1;
	/** 当前选中的图片的位置 */
	private int current_checked_Position = -1;
	/** 当前点击的图片的位置 */
	private int current_click_Position = -1;
	/** 上一个点击的图片的位置 */
	private int last_click_Position = -1;
	
	/** 从bimp中去除的bitmap，要放入裁剪框中 */
	private Bitmap clip_bitmap;
	private float degree = 0;
	/** 放进裁剪框的图片在集合中的位置*/
	private int clip_position;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getBundleData();
		setupView();
		initData();
	}

	protected void getBundleData() {
		// TODO Auto-generated method stub
		intent = getIntent();
		currentPosition = intent.getByteExtra(Constant.BUNDLE_PHOTOWALL_POSITION,
				(byte) -1);
		lastClass = (Class<? extends Activity>) intent
				.getSerializableExtra(Constant.BUNDLE_CLASS);
		isFromVideoShow = intent.getBooleanExtra("videoShow", false);
		if (isFromVideoShow) {
			Constant.Num = 1;
			Bimp.tempSelectBitmap.clear();
		}
	}

	protected void setupView() {
		setContentView(R.layout.plugin_camera_album);
		// PublicWay.activityList.add(this);
		mContext = this;
		// 注册一个广播，这个广播主要是用于在GalleryActivity进行预览时，防止当所有图片都删除完后，再回到该页面时被取消选中的图片仍处于选中状态
		IntentFilter filter = new IntentFilter("data.broadcast.action");
		registerReceiver(broadcastReceiver, filter);
		// bitmap =
		// BitmapFactory.decodeResource(getResources(),Res.getDrawableID("plugin_camera_no_pictures"));
		init();
		initListener();
		// 这个函数主要用来控制预览和完成按钮的状态

	}

	// 初始化，给一些对象赋值
	private void init() {
		initList();
		findView();
		listenerLayout();

	}

	private void initList() {
		huanclist = new ArrayList<ImageItem>();
		huanclist.addAll(Bimp.tempSelectBitmap);
		helper = new AlbumHelper();
		helper.init(getApplicationContext());

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		imageItemFirst = new ImageItem();
		imageItemFirst.setImagePath("photoPath");
		dataList.add(imageItemFirst);
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}
	}

	/** 寻找子view */
	private void findView() {
		choose_album = (RelativeLayout) findViewById(R.id.back);
		cancel = (ImageView) findViewById(R.id.cancel);
		gridView = (GridView) findViewById(R.id.myGrid);
		next_Button = (TextView) findViewById(R.id.ok_button);
		srcPic = (SquareRelativeLayout) findViewById(R.id.srcPic);
		img_clip_bg = (ImageView) findViewById(R.id.img_clip_bg);
		txt_all_picture = (TextView) findViewById(R.id.txt_all_picture);
		img_down = (ImageView) findViewById(R.id.img_down);
		img_rotate = (ImageView) findViewById(R.id.img_rotate);
		img_pull = (ImageView) findViewById(R.id.img_pull);
		txt_select_number = (TextView) findViewById(R.id.txt_select_number);
		linear_root = (LinearLayoutVerticalScrolling) findViewById(R.id.linear_root);
		title = (RelativeLayout) findViewById(R.id.title);

		gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
				Bimp.tempSelectBitmap);
		gridImageAdapter.setCanGetIcon(true);
		map = gridImageAdapter.getMap();
		gridView.setAdapter(gridImageAdapter);
		// gridView.setOnScrollListener(new
		// PauseOnScrollListener(gridImageAdapter.getImageLoader(), true,
		// true));

		// preview = (Button) findViewById(Res.getWidgetID("preview"));
		// preview.setOnClickListener(new PreviewListener());
		// tv = (TextView) findViewById(Res.getWidgetID("myText"));
		// gridView.setEmptyView(tv);
		// okButton.setText(Res.getString("finish") + "("
		// + Bimp.tempSelectBitmap.size() + "/" + Num + ")");
	}

	/** 监听layout完成 */
	private void listenerLayout() {
		img_clip_bg.setOnTouchListener(this);
		srcPic.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						srcPic.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						clipview = new ClipView(AlbumActivity.this);
						srcPic.addView(clipview, new LayoutParams(
								LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
						// clipview.addOnDrawCompleteListener(new
						// OnDrawListenerComplete() {
						//
						// @Override
						// public void onDrawCompelete() {
						// initClipViewData();
						// }
						// });
					}
				});
	}

	// protected void initClipView() {
	// clipview = new ClipView(this);
	// // LayoutParams params = new LayoutParams(srcPic.getWidth(),
	// // srcPic.getHeight());
	// srcPic.addView(clipview, new LayoutParams(LayoutParams.MATCH_PARENT,
	// LayoutParams.MATCH_PARENT));
	//
	// }
	/** 把第一张图片放到剪切框里，还没使用 */
//	private void initClipViewData() {
//		if (dataList.get(1).getBitmap() != null)
//			setBitmapToClipView(dataList.get(1).getBitmap(), 0);
//		else if (dataList.get(2).getBitmap() != null)
//			setBitmapToClipView(dataList.get(2).getBitmap(), 0);
//		else if (dataList.get(3).getBitmap() != null)
//			setBitmapToClipView(dataList.get(3).getBitmap(), 0);
//
//	}

	protected void initData() {
		txt_select_number.setText(Bimp.tempSelectBitmap.size() + "/" + Constant.Num);
	}

	@Override
	protected void onRestart() {
		txt_select_number.setText(Bimp.tempSelectBitmap.size() + "/" + Constant.Num);
		super.onRestart();
	}

	/** 初始化监听 */
	private void initListener() {
		cancel.setOnClickListener(new CancelListener());
		choose_album.setOnClickListener(new BackListener());
		img_rotate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 旋转图片
				if (clip_bitmap != null) {
					degree += 90;
					setBitmapToClipView(clip_bitmap, degree);
				}

			}
		});
		img_pull.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (linear_root.getStatus() != Status.Close) {
//					gridImageAdapter.setCanGetIcon(false);
//					linear_root.setOnStatusCloseListener(new OnStatusCloseListener() {
//						
//						@Override
//						public void onStatusClose() {
//							linear_root.removeOnStatusCloseListener();
//							gridImageAdapter.setCanGetIcon(true);
//							gridImageAdapter.notifyDataSetChanged();
//						}
//					});
					linear_root.close();
				} else if (linear_root.getStatus() != Status.Open) {
//					gridImageAdapter.setCanGetIcon(true);
					linear_root.open();
				}
			}
		});
//		gridImageAdapter
//				.setOnFirstItemClickListener(new onFirstItemClickListener() {
//
//					@Override
//					public void onFirstItemClick() {
//						File photoFileFolder = MxFileUtil
//								.getTakePhotoFileFolder();
//						if (photoFileFolder == null) {
//							showShortToast(getResources().getString(
//									R.string.invalidSD));
//						} else {
//							currentAddFileName = String.valueOf(System
//									.currentTimeMillis());
//							File photoFile = MxFileUtil.getTakePhotoFile(
//									photoFileFolder, currentAddFileName);
//							if (photoFile != null) {
//								Uri u = Uri.fromFile(photoFile);
//								Intent intent2 = new Intent(
//										MediaStore.ACTION_IMAGE_CAPTURE);
//								intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
//								startActivityForResult(intent2, RC_TAKEPHOTO);
//							} else {
//								showShortToast(getResources().getString(
//										R.string.error_create_photo_file));
//							}
//						}
//					}
//				});

		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				if (position > 0) {
					
					current_click_Position = position;
					
					
					//若果状态不是打开，就打开
					if (linear_root.getStatus() != Status.Open) {
//						gridImageAdapter.setCanGetIcon(true);
						linear_root.open();
						linear_root.setOnStatusOpenListener(new LinearLayoutVerticalScrolling.OnStatusOpenListener() {
							
							@Override
							public void onStatusOpen() {
								linear_root.removeOnStatusOpenListener();
								//打开后首先判断上一个放在截图内的图片是否被选中，若选中就截图并放入bimp集合，
								if (current_checked_Position != -1 && current_checked_Position != current_click_Position) {
									//重置状态
									
									current_click_Position = current_checked_Position;
									current_checked_Position = -1;
									//截图并放入bimp集合
									saveBitmapToBimp();
									
								}
								
								clip_bitmap = dataList.get(position)
										.getBitmap();
								setBitmapToClipView(clip_bitmap, 0);
								clip_position = position;
								
							}
						});
					}else{
						
						if (current_checked_Position != -1 && current_checked_Position != current_click_Position) {
							//重置状态
							current_click_Position = current_checked_Position;
							current_checked_Position = -1;
							//截图并放入bimp集合
							saveBitmapToBimp();
							
						}
						
						clip_bitmap = dataList.get(position)
								.getBitmap();
						setBitmapToClipView(clip_bitmap, 0);
						clip_position = position;
					}
					if (current_click_Position == current_checked_Position) {
						current_click_Position = -1;
					}
				 
					
				}
				else{

					File photoFileFolder = MxFileUtil
							.getTakePhotoFileFolder();
					if (photoFileFolder == null) {
						ToastUtil.show(getResources().getString(
								R.string.invalidSD),AlbumActivity.this);
					} else {
						currentAddFileName = String.valueOf(System
								.currentTimeMillis());
						File photoFile = MxFileUtil.getTakePhotoFile(
								photoFileFolder, currentAddFileName);
						if (photoFile != null) {
							Uri u = Uri.fromFile(photoFile);
							Intent intent2 = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
							startActivityForResult(intent2, Constant.RC_TAKEPHOTO);
						} else {
							ToastUtil.show(getResources().getString(
									R.string.error_create_photo_file),AlbumActivity.this);
						}
					}
				
				}
				
			}
		});
		
		gridImageAdapter.setOnCheckBoxClickListener(new AlbumGridViewAdapter.OnCheckBoxClickListener() {

			@Override
			public void onCheckBoxClick(CheckBox buttonView, boolean isChecked,
					final int position, View view) {
				
				synchronized (obj)
				{
					if (Bimp.tempSelectBitmap.size() >= Constant.Num) {
						map.put(position, false);
						buttonView.setChecked(false);
//						map.put(position, false);
//						buttonView.setBackgroundResource(R.drawable.btn_select_camera_normal);
						view.setVisibility(View.GONE);
						if (!removeOneData(dataList.get(position))) {
							ToastUtil.show(getResources()
									.getString(
											R.string.only_choose_num),AlbumActivity.this);
						}
						return;
					}else{
						
					}
					
					if (isChecked) {
						current_checked_Position = position;
						
						if (linear_root.getStatus() != Status.Open) {
//							gridImageAdapter.setCanGetIcon(true);
							linear_root.open();
							linear_root.setOnStatusOpenListener(new LinearLayoutVerticalScrolling.OnStatusOpenListener() {
								
								@Override
								public void onStatusOpen() {
									linear_root.removeOnStatusOpenListener();
									if (last_checkded_Position != -1
											&& current_click_Position != last_checkded_Position&& last_checkded_Position != current_checked_Position) {
									saveBitmapToBimp();
									}
									
									clip_bitmap = dataList.get(position)
											.getBitmap();
									setBitmapToClipView(clip_bitmap, 0);
									
									Bimp.tempSelectBitmap.add(dataList
											.get(position));

									txt_select_number
											.setText(Bimp.tempSelectBitmap
													.size() + "/" + Constant.Num);
									last_checkded_Position = position;
									
								}
							});
						}else{
							if (last_checkded_Position != -1
									&& current_click_Position != last_checkded_Position&& last_checkded_Position != current_checked_Position) {
							saveBitmapToBimp();
							}
							clip_bitmap = dataList.get(position)
									.getBitmap();
							setBitmapToClipView(clip_bitmap, 0);
							
							Bimp.tempSelectBitmap.add(dataList
									.get(position));

							txt_select_number
									.setText(Bimp.tempSelectBitmap
											.size() + "/" + Constant.Num);
							last_checkded_Position = position;
							
						}
						
					} else {
//						view.setTag(-1);
//						view.setVisibility(View.GONE);
//						map.put(position, false);
						Bimp.tempSelectBitmap.remove(dataList
								.get(position));
						
						txt_select_number
								.setText(Bimp.tempSelectBitmap
										.size() + "/" + Constant.Num);
						last_checkded_Position = -1;
						current_checked_Position = -1;
					}
					
					
				}
				
			}

		
		});
		
		


		next_Button.setOnClickListener(new AlbumSendListener());
		gridView.setOnScrollListener(new OnScrollListener() {

			private int lastPosition;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
					
				

				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING:
//					gridImageAdapter.setCanGetIcon(false);
					int currentPosition = view.getFirstVisiblePosition();
					Log.i("linearlayout","上一个可见的位置 ： " + lastPosition +", 当前可见位置 ： " +currentPosition);
					if ((currentPosition - lastPosition) >= 3) {
						//像上滚，如果处于打开状态，关闭
						if (linear_root.getStatus() != Status.Close) {
//							gridImageAdapter.setCanGetIcon(false);
//							linear_root.setOnStatusCloseListener(new OnStatusCloseListener() {
//								
//								@Override
//								public void onStatusClose() {
//									linear_root.removeOnStatusCloseListener();
//									gridImageAdapter.setCanGetIcon(true);
//									gridImageAdapter.notifyDataSetChanged();
//								}
//							});
							linear_root.close();
						}
					}else if ((lastPosition - currentPosition) > 3) {
						//像下滚
						if (linear_root.getStatus() != Status.Open) {
							gridImageAdapter.setCanGetIcon(true);
							linear_root.open();
						}
					}

					break;
			
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//					gridImageAdapter.setCanGetIcon(false);
					lastPosition = view.getFirstVisiblePosition();
					break;
				case OnScrollListener.SCROLL_STATE_IDLE:
//					gridImageAdapter.setCanGetIcon(true);
//					gridImageAdapter.notifyDataSetChanged();
					break;
				default:
					break;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				

			}
		});

	}

	// // 预览按钮的监听
	// private class PreviewListener implements OnClickListener {
	//
	//
	// public void onClick(View v) {
	//
	// intent.setClass(AlbumActivity.this, GalleryActivity.class);
	// intent.putExtra(BUNDLE_CLASS, lastClass);
	//
	// if(isFromVideoShow)
	// {
	// startActivityForResult(intent, FROM_VIDEO_SHOW_TO_GALLERY);
	// }
	// else
	// {
	// startActivity(intent);
	// }
	// }
	// }

	// 下一步按钮的监听
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			
			try {
				// 点击先一步，首先保持最后一个被裁剪的图片
				if (current_checked_Position != -1) {
					saveBitmapToBimp();
				}
				setResult(RESULT_OK, intent);
				finish();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// 选择照片按钮监听
	private class BackListener implements OnClickListener {
		public void onClick(View v) {
			// intent.setClass(AlbumActivity.this, ImageFile.class);
			// intent.putExtra(BUNDLE_CLASS, lastClass);
			// startActivity(intent);
			// 弹出popwindow选择相册
			showOutMenu();
			img_down.setImageResource(R.drawable.icon_arrow_up);

		}
	}

	public void showOutMenu() {

		ListView pop_listview = new ListView(this);
		pop_listview.setBackgroundColor(Color.parseColor("#eaebf0"));
		pop_listview.setDivider(null);
		pop_listview.setDividerHeight(0);
		folderAdapter = new FolderAdapter(this, lastClass);

		folderAdapter
				.setOnFolderItemClickListener(new FolderAdapter.OnFolderItemClickListener() {

					@Override
					public void onFolderItemClick(int position,
							String bucketName) {
						if (popupWindow != null) {
							popupWindow.dismiss();
						}
						txt_all_picture.setText(bucketName);

						ArrayList<ImageItem> items = new ArrayList<ImageItem>();
						items.add(imageItemFirst);
						items.addAll((ArrayList<ImageItem>) (contentList
								.get(position).imageList));
						gridImageAdapter
								.freshData(items, Bimp.tempSelectBitmap);

					}
				});
		pop_listview.setAdapter(folderAdapter);
		if (popupWindow == null) {
			popupWindow = new PopupWindow(pop_listview,
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			popupWindow.setAnimationStyle(R.style.popuStyle);
		}
		popupWindow.showAsDropDown(title, 0, 0);
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				img_down.setImageResource(R.drawable.icon_arrow_down);

			}
		});
	}

	// 取消按钮的监听
	private class CancelListener implements OnClickListener {
		
		
		
		public void onClick(View v) {
			
			try {
//				Log.i("album", "是否应该结束上一个界面： "+getIntent().getBooleanExtra("shouldFinishLast", false));
				if (isFromVideoShow) {
					finish();
				}
				
				else if(getIntent().getBooleanExtra("shouldFinishLast", false)){
					setResult(202);
					finish();
				}
				
				else {
					synchronized (obj) {
						Bimp.tempSelectBitmap = huanclist;
						intent.setClass(mContext, lastClass);
						// 这个行代码设置lastclass启动，会移除lastclass上面所有类，并不走lastclass任何生命周期，但是会走onnewintent
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_SINGLE_TOP);
						intent.putExtra("isalbum", true);
						startActivity(intent);
						finish();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 当点击girdview里的图片时,把图片放进剪切框 */
	protected void setBitmapToClipView(Bitmap bitmap, float rotate) {

		int clipHeight = clipview.getClipHeight();
		int clipWidth = clipview.getClipWidth();
		int midX = clipview.getClipLeftMargin() + (clipWidth / 2);
		int midY = clipview.getClipTopMargin() + (clipHeight / 2);
		if (bitmap == null) {
			return;
		}
		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();
		scale_start = (clipWidth * 1.0f) / imageWidth;
		if (imageWidth > imageHeight) {
			scale_start = (clipHeight * 1.0f) / imageHeight;
		}

		// 起始中心点
		float imageMidX = imageWidth * scale_start / 2;
		float imageMidY = clipview.getCustomTopBarHeight() + imageHeight
				* scale_start / 2;
		img_clip_bg.setScaleType(ScaleType.MATRIX);
		 Log.e("album", "imageView初始缩放比例：宽 :"   + scale_start);
		// 缩放
		matrix.setScale(scale_start, scale_start);
		matrix.postRotate(rotate, imageMidX, imageMidY);
		// 平移
		matrix.postTranslate(midX - imageMidX, midY - imageMidY);

		img_clip_bg.setImageMatrix(matrix);
		img_clip_bg.setImageBitmap(bitmap);

	}

	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			txt_select_number.setText(Bimp.tempSelectBitmap.size() + "/" + Constant.Num);
			return true;
		}
		return false;
	}

	// /** 初始化选择个数*/
	// public void isShowOkBt() {
	// // if (Bimp.tempSelectBitmap.size() > 0) {
	// // txt_select_number.setText(Bimp.tempSelectBitmap.size() + "/" + Num);
	// // preview.setPressed(true);
	// // next_Button.setPressed(true);
	// // // preview.setClickable(true);
	// // next_Button.setClickable(true);
	// // next_Button.setTextColor(Color.WHITE);
	// // preview.setTextColor(Color.WHITE);
	// // } else {
	// txt_select_number.setText(Bimp.tempSelectBitmap.size() + "/" + Num);
	// // preview.setPressed(false);
	// // preview.setClickable(false);
	// // next_Button.setPressed(false);
	// // next_Button.setClickable(false);
	// // next_Button.setTextColor(Color.parseColor("#E1E0DE"));
	// // preview.setTextColor(Color.parseColor("#E1E0DE"));
	// // }
	// }

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isFromVideoShow) {
				finish();
			} 
			else if(getIntent().getBooleanExtra("shouldFinishLast", false)){
				setResult(202);
				finish();
			}
			
			else {
				synchronized (obj) {
					
					
//					Log.i("album", "上一个类是 : " + lastClass.getName());
					Bimp.tempSelectBitmap = huanclist;
					intent.setClass(mContext, lastClass);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("isalbum", true);
					startActivity(intent);
					finish();
				}
			}
			return true;
		}
		return false;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
		gridImageAdapter = null;
		helper = null;
		System.gc();
	}

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			gridImageAdapter.notifyDataSetChanged();
		}
	};
	private float scale_start;
	private Map<Integer, Boolean> map;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			// 设置开始点位置
			start.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_UP:
//			Log.e("album", "主手指松开了");
			
			float scale_current = getScale(); 
			if (scale_current < scale_start  ) {
				matrix.postScale(scale_start/scale_current, scale_start/scale_current);
//				getLeftPointF();
//				view.setImageMatrix(matrix);
//				getLeftPointF();
			}
			else if(scale_current > (3 * scale_start)){
				matrix.postScale((3*scale_start)/scale_current, (3*scale_start)/scale_current);
			}
			 PointF p1=getLeftPointF();
             if (p1.x > 0) {
				matrix.postTranslate(-p1.x, 0);
			}
             if (p1.y > 0) {
				matrix.postTranslate(0, -p1.y);
			}
             PointF p2=getRightPointF();
             
             if (p2.x < view.getWidth()) {
            	 matrix.postTranslate(view.getWidth() - p2.x, 0);
			}
             if (p2.y < view.getHeight()) {
            	 matrix.postTranslate(0,view.getHeight() -p2.y);
			}
			
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
//			Log.e("album", "另一根手指松开了");
			
			
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}
		view.setImageMatrix(matrix);

		return true;
	}

	private float getScale() {
		
		float[] values = new float[9];
		matrix.getValues(values);
//		 Log.e("album", "imageView缩放比例："   + values[0]);
		return values[0];
	}

	private PointF getRightPointF() {
		
		 Rect rectTemp = new Rect(0, 0, 720, 720);
		
		if (img_clip_bg.getDrawable() != null) {
			rectTemp = img_clip_bg.getDrawable().getBounds();
		}
		
         float[] values = new float[9];
         matrix.getValues(values);
         float leftX= values[2]+rectTemp.width()*values[0];
         float leftY=values[5]+rectTemp.height()*values[4];
//         Log.e("album", "右下角坐标：x :"   + leftX+   ",y:" +leftY);
//         Log.e("album", "imageView边界：宽 :"   + rectTemp.width()+   ", 高 :" +rectTemp.width());
//         Log.e("album", "imageView缩放比例：宽 :"   + values[0]+   ", 高 :" +values[4]);
         return new PointF(leftX,leftY);
	}

	private PointF getLeftPointF() {
	
		float[] values = new float[9];
		matrix.getValues(values);
		 float leftX=values[2];
         float leftY=values[5];
//         Log.e("album", "左上角坐标：x :"   + leftX+   ",y:" +leftY);
         return new PointF(leftX,leftY);
		
	}

	/** 多点触控时，计算最先放下的两指距离 */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/** 多点触控时，计算最先放下的两指中心坐标 */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	/**
	 * 获取裁剪框内截图
	 */
	private Bitmap getBitmap() {
		// 获取截屏
		View view = this.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		int titleHeight = title.getHeight();
		// 获取状态栏高度
		Rect frame = new Rect();
		this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		Bitmap finalBitmap = Bitmap.createBitmap(view.getDrawingCache(),
				clipview.getClipLeftMargin(), clipview.getClipTopMargin()
						+ statusBarHeight + titleHeight,
				clipview.getClipWidth(), clipview.getClipHeight());

		// 释放资源
		view.destroyDrawingCache();
		// view.setDrawingCacheEnabled(false);
		return finalBitmap;
	}

	/**
	 * 把截图放入bimp临时集合
	 */
	private void saveBitmapToBimp() {

		File photoFileFolder = MxFileUtil.getTakePhotoFileFolder();
		if (photoFileFolder == null) {
			ToastUtil.show(getResources().getString(R.string.invalidSD),this);
		} else {
			File photoFile = MxFileUtil.getTakePhotoFile(photoFileFolder,
					String.valueOf(System.currentTimeMillis()));
			FileOutputStream os = null;
			if (photoFile != null) {
				try {
					os = new FileOutputStream(photoFile);
					getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, os);
					if (Bimp.tempSelectBitmap.size() > 0) {
						Bimp.tempSelectBitmap.get(
								Bimp.tempSelectBitmap.size() - 1).setImagePath(
								photoFile.getAbsolutePath());
						Bimp.tempSelectBitmap.get(
								Bimp.tempSelectBitmap.size() - 1)
								.setThumbnailPath(photoFile.getAbsolutePath());
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						os.flush();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case  Constant.RC_TAKEPHOTO:
			if (resultCode == RESULT_OK) {
				ImageItem takePhoto = new ImageItem();
				try {
					File photoFile = MxFileUtil.getTakePhotoFile(
							MxFileUtil.getTakePhotoFileFolder(),
							currentAddFileName);

					Uri u = Uri.parse(photoFile.getAbsolutePath());
					if (u != null) {
						takePhoto.setImagePath(photoFile.getAbsolutePath());
						takePhoto.setThumbnailPath(photoFile.getAbsolutePath());
						dataList.add(1, takePhoto);
						gridImageAdapter.notifyDataSetChanged();
						MediaScannerConnection.scanFile(mContext, new String[]{photoFile.getAbsolutePath()}, null, null);

					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.show(getResources().getString(
							R.string.error_no_get_origin_pic),this);
				}
			}

			break;

		default:
			break;
		}
	}

}