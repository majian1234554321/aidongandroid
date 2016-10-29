package com.leyuan.aidong.ui.activity.media;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.GirdAdapterSelectVideo;
import com.leyuan.aidong.entity.model.location.RecorderVideoInformation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectVideoActivity extends BaseActivity implements OnClickListener {

	private static final int RESULT_SELECT_VIDEO = 1;
	private ImageView img_back;
	private ImageView img_down;
	private TextView text_next;
	private GridView gridView;
	private GirdAdapterSelectVideo adapter;
	private RecorderVideoInformation infromation;
	
	private ArrayList<RecorderVideoInformation> array;
	private ArrayList<Bitmap> bitmaps;
	/** 结果 List */ 
	private List<String> list_path; 
	private static final int VIDEO_REQUEST_CODE = 1;
	private int select_position = 0;
	private PopupWindow popupWindow;
	private RelativeLayout title;
	private boolean isFromDynamin;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isFromDynamin = getIntent().getBooleanExtra("isFromDynamin", false);
		setContentView(R.layout.layout_select_video_activity);
		initView();
		initGridView();
		setClick();
		
	}
	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_down = (ImageView) findViewById(R.id.img_down);
		text_next = (TextView) findViewById(R.id.text_next);
		gridView = (GridView) findViewById(R.id.gridView);
		title = (RelativeLayout) findViewById(R.id.title);

	}

	private void setClick() {
		img_back.setOnClickListener(this);
		title.setOnClickListener(this);
		text_next.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			if(getIntent().getBooleanExtra("shouldFinishLast", false)){
				setResult(202);
			}
               finish();
			break;
		case R.id.text_next:
              if(infromation!= null)
              {
            	  Intent data = new Intent();
            	  data.putExtra("path", infromation.getPath());
				  setResult(RESULT_SELECT_VIDEO, data);
				  finish();
              }else{
            	  Toast.makeText(getApplicationContext(), "请至少选择1个视频", Toast.LENGTH_SHORT).show();
              }
			break;
		case R.id.title:
			showOutMenu();
			break;

		default:
			break;
		}

	}
	
	private void initGridView() {
		array = new ArrayList<RecorderVideoInformation>();
		Bitmap recordVideo = BitmapFactory.decodeResource(getResources(), R.drawable.recorder_video);
		RecorderVideoInformation infro = new RecorderVideoInformation(recordVideo,"","0:00");
		array.add(infro);
		adapter = new GirdAdapterSelectVideo(this ,array);
		gridView.setAdapter(adapter);
		getBitmapsFromLocal();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == 0)
				{Intent intent = new Intent(SelectVideoActivity.this, MyRecorderVideoActivity.class);
				if(isFromDynamin)
				{
					intent.putExtra("isFromDynamin", true);
				}
					startActivityForResult(intent, VIDEO_REQUEST_CODE);
				}
				else
				{
					infromation = array.get(position);
					int temp_select_position = select_position;
					select_position = position;
					if(temp_select_position != select_position)
					{
					adapter.freshSelectPosition(select_position);
					}
					Log.i("select", "====  infromation  : " + infromation.getPath());
				}
			}
		});
		
	}
	
	
//	/** 首先从缓存中获取视频信息*/
//	private void getBitmapsFromCache() {
//		int video_count = BaseApp.getParamsHelper().getPreInt("videoCount", 0);
//		
//		
//	}
	/** 然后扫描本地视频库，并获取第一桢图片*/
	private void getBitmapsFromLocal() {
 	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
 		
// 		new MyAsyncTask().execute();
 		
 		
 		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 SimpleDateFormat format = new SimpleDateFormat("mm:ss");
				 RecorderVideoInformation infro = null;
				 list_path = getVideoPath();
				 long time = 0;
				
				 String durarion = "0:00";
				 MediaMetadataRetriever retriever = new MediaMetadataRetriever();
			 		for(String videoPath : list_path)
			 		{
//			 			Log.i("select", "=======路径：" + videoPath);
//			 			File videoFile = new File(videoPath);
//			 			if (!videoFile.exists()) {
//							continue;
//						}
			 	     Bitmap	thumb_bitmap = null;
			 		 long bytes = new File(videoPath).length();
//			 		Log.i("select", "=======大小：" + bytes);
			 		if( bytes > 0 && bytes < 60 * 1024 * 1024 )
			 		{
			 			try {
							retriever.setDataSource(videoPath);
							thumb_bitmap = retriever.getFrameAtTime(-1);
						     time =Long.parseLong( retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
						    durarion = format.format(time);
						    
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						}
			 			finally{
								try {
									if(thumb_bitmap != null )
									{
										//压缩图片
										infro = new RecorderVideoInformation(Bitmap.createScaledBitmap(thumb_bitmap, 240, 240, false),
												videoPath, durarion);
										array.add(infro);
									}
									continue;
								} catch (Exception e) {
									e.printStackTrace();
								}
							    
			 				
			 			}
			 		
			 		}	
			 		}
			 		retriever.release();
			 	handler.sendEmptyMessage(0);
			}
		}).start();
 		
 	}
	}
	

	Handler handler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			adapter.freshData(array);
			
		};
	};
//	  
//	/**搜索目录，扩展名，是否进入子文件夹  */ 
//	public List<String> GetFiles(String Path, String Extension,boolean IsIterative)
//
//	{ 
//		List<String> listFile =new ArrayList<String>(); 
//	    File[] files =new File(Path).listFiles(); 
//	    if(files.length > 0)
//	    {
//	    for (int i =0; i < files.length; i++) 
//	    { 
//
//	        File f = files[i]; 
//
//	        if (f.isFile()) 
//
//	        { 
//	            if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) //判断扩展名 
//	                listFile.add(f.getPath()); 
////	            if (!IsIterative) 
////	                continue; 
//
//	        } 
//
////	        else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) //忽略点文件（隐藏文件/文件夹） 
////
////	            GetFiles(f.getPath(), Extension, IsIterative); 
//
//	    } 
//	    }
//          return listFile;
//	} 
	private TextView filenum;


//     class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<RecorderVideoInformation>>
//     {
//
//		@Override
//		protected ArrayList<RecorderVideoInformation> doInBackground(Void... params) {
//			
//			
//			ArrayList<RecorderVideoInformation> array = new ArrayList<RecorderVideoInformation>();
////	 		File path = SelectVideoActivity.this.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
////			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//////	 		
////	 		if(!path.exists())
////	 		{
//////	 			if(!path.mkdirs())
//////	 				return null;
////	 			path.mkdirs();
////	 		}
////	 		Log.i("select", "=======" + path.getPath());
//			 SimpleDateFormat format = new SimpleDateFormat("mm:ss");
//			 RecorderVideoInformation infro = null;
//			
////	 		list_path = new ArrayList<String>();
////	 		getVideoFile(list_path, Environment.getExternalStorageDirectory());
//	 		list_path = getVideoPath();
//	 		for(String videoPath : list_path)
//	 		{
////	 			Log.i("select", "=======路径：" + videoPath);
////	 			File videoFile = new File(videoPath);
////	 			if (!videoFile.exists()) {
////					continue;
////				}
//	 		
//	 		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//	 		Bitmap	thumb_bitmap = null;
//	 		 String durarion = "0:00";
//	 		long time = 0;
//	 		 long bytes = new File(videoPath).length();
////	 		Log.i("select", "=======大小：" + bytes);
//	 		if( bytes > 0 && bytes < 60 * 1024 * 1024 )
//	 		{
//	 			try {
//					retriever.setDataSource(videoPath);
//					thumb_bitmap = retriever.getFrameAtTime(-1);
//				     time =Long.parseLong( retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
//				     
//				   
//				    durarion = format.format(time);
////				   Log.i("select", "======duration : " +durarion);
//				    
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				}
//	 			finally{
//	 				try {
//						retriever.release();
////						Log.i("select", "======执行了1");
//						if(thumb_bitmap != null )
//			 			{
//			 				//压缩图片
//			 				infro = new RecorderVideoInformation(Bitmap.createScaledBitmap(thumb_bitmap, 240, 240, false),
//			 						videoPath, durarion);
//			 				array.add(infro);
////			 				Log.i("select", "======执行了2");
//			 			}
//						continue;
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//	 				
//	 			}
//	 		
//	 		}	
//	 		}
////	 		if(list_path.size() > 0){
////	 		for (int i = 0; i < list_path.size(); i++) {
////				BaseApp.getParamsHelper().setPreStr("videopath"+i, list_path.get(i));
////			}
////	 		BaseApp.getParamsHelper().setPreInt("videoCount", list_path.size());
////	 		}
//			return array;
//		}
//		
//		@Override
//		protected void onPostExecute(ArrayList<RecorderVideoInformation> result) {
//			if (result != null) {
//				if(result.size() > 0)
//				{
//					array.addAll(result);
//					adapter.freshData(array);
//				}
//			}
//			
//			
//			
//		}
//    	 
//     }
     private ArrayList<String> getVideoPath(){
    	 ArrayList<String> arrayList = new ArrayList<String>();
    	 ContentResolver resolver = getContentResolver();
    	 
    	 Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, 
    			 new String[]{MediaStore.Video.Media.DATA}, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
    	 while (cursor.moveToNext()) {
    		String path =  cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
    		if (path != null) {
    			arrayList.add(path);
			}
		}
    	 cursor.close();
    	 return arrayList;
     }
     public void showOutMenu() {
 		if (array.size()  <=1) {
 			return;
 		}
 		if(popupWindow == null)
 			{
 				View view = View.inflate(this, R.layout.popup_select_folder, null);
 				ImageView file_image = (ImageView) view.findViewById(R.id.file_image);
 				TextView file_name = (TextView) view.findViewById(R.id.name);
 				filenum = (TextView) view.findViewById(R.id.filenum);
 				file_image.setImageBitmap(array.get(1).getBitmap());
 				
 				view.setOnClickListener(new OnClickListener() {
 					
 					@Override
 					public void onClick(View v) {
 						if(popupWindow != null){
 							popupWindow.dismiss();
 						}
 						
 					}
 				});
 				popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
 				popupWindow.setBackgroundDrawable(new BitmapDrawable());
 				popupWindow.setAnimationStyle(R.style.popupDownStyle);
 				popupWindow.setOnDismissListener(new OnDismissListener() {
 					
 					@Override
 					public void onDismiss() {
 						img_down.setImageResource(R.drawable.icon_arrow_down);
 						
 					}
 				});
 			}	
 		filenum.setText("(" + (array.size()-1) +")");		
 		img_down.setImageResource(R.drawable.icon_arrow_up);
 		popupWindow.showAsDropDown(title, 0, 0);
 		
 	}
     
//     private void getVideoFile(final List<String> list,File file) {// 获得视频文件  
//       
//    	
//             file.listFiles(new FileFilter() {  
//       
//                 @Override  
//                 public boolean accept(File file) {  
//                     // sdCard找到视频名称  
//                     String name = file.getName();  
//       
//                     int i = name.indexOf('.');  
//                     if (i != -1) {  
//                         name = name.substring(i);  
//                         if (name.equalsIgnoreCase(".mp4")  
//                                 || name.equalsIgnoreCase(".3gp")  
//                                 || name.equalsIgnoreCase(".wmv")  
//                                 || name.equalsIgnoreCase(".ts")  
//                                 || name.equalsIgnoreCase(".rmvb")  
//                                 || name.equalsIgnoreCase(".mov")  
//                                 || name.equalsIgnoreCase(".m4v")  
//                                 || name.equalsIgnoreCase(".avi")  
//                                 || name.equalsIgnoreCase(".m3u8")  
//                                 || name.equalsIgnoreCase(".3gpp")  
//                                 || name.equalsIgnoreCase(".3gpp2")  
//                                 || name.equalsIgnoreCase(".mkv")  
//                                 || name.equalsIgnoreCase(".flv")  
//                                 || name.equalsIgnoreCase(".divx")  
//                                 || name.equalsIgnoreCase(".f4v")  
//                                 || name.equalsIgnoreCase(".rm")  
//                                 || name.equalsIgnoreCase(".asf")  
//                                 || name.equalsIgnoreCase(".ram")  
//                                 || name.equalsIgnoreCase(".mpg")  
//                                 || name.equalsIgnoreCase(".v8")  
//                                 || name.equalsIgnoreCase(".swf")  
//                                 || name.equalsIgnoreCase(".m2v")  
//                                 || name.equalsIgnoreCase(".asx")  
//                                 || name.equalsIgnoreCase(".ra")  
//                                 || name.equalsIgnoreCase(".ndivx")  
//                                 || name.equalsIgnoreCase(".xvid")) {  
//                             
//                             
//                        	 list.add(file.getAbsolutePath());
//                            
//                             return true;  
//                         }  
//                     } else if (file.isDirectory()) {  
//                         getVideoFile(list, file);  
//                     }  
//                     return false;  
//                 }  
//             });  
//         }  
	
     
     @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	 
    	 if (keyCode == KeyEvent.KEYCODE_BACK) {
    		 if(getIntent().getBooleanExtra("shouldFinishLast", false)){
 				setResult(202);
 				
 			}
    		 finish();
			return true;
		}
    	 
    	return super.onKeyDown(keyCode, event);
    }
}
