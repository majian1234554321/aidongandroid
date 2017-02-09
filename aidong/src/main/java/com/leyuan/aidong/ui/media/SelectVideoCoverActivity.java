package com.leyuan.aidong.ui.media;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.GirdAdapterSelectVideoCover;

import java.io.File;
import java.util.ArrayList;

public class SelectVideoCoverActivity extends BaseActivity implements OnClickListener {
	
	private static final int RESULT_SELECT_COVER = 4;
	private ImageView btnBack;
	private TextView txtTopTitle;
	private TextView txtTopRight;
	private GridView gridView;
	private String path;
	private ArrayList<Bitmap> array = new ArrayList<Bitmap>();
	private GirdAdapterSelectVideoCover adapter;
	private int select_position = -1;
	private Bitmap select_cover;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_select_video_cover_activity);
		path = getIntent().getStringExtra("path");
		initView();
		setClick();
		initGirdView();
	}
	
	private void initView() {
		btnBack = (ImageView) findViewById(R.id.btnBack); 
		txtTopTitle = (TextView) findViewById(R.id.txtTopTitle); 
		txtTopRight = (TextView) findViewById(R.id.txtTopRight);
		gridView = (GridView) findViewById(R.id.gridView);
		btnBack.setVisibility(View.VISIBLE);
		txtTopRight.setVisibility(View.VISIBLE);
		txtTopTitle.setText("选取封面");
		txtTopRight.setText("完成");
	}
private void setClick() {
	btnBack.setOnClickListener(this);
	txtTopRight.setOnClickListener(this);
	}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.btnBack:
		if(select_cover != null)
		{
			Intent intent = new Intent();
			intent.putExtra("select_cover", select_cover);
			setResult(RESULT_SELECT_COVER, intent);
			finish();
		}else{
			Toast.makeText(this, "请选择封面",Toast.LENGTH_SHORT).show();
		}
		
		break;
case R.id.txtTopRight:
	if(select_cover != null)
	{
		Intent intent = new Intent();
		intent.putExtra("select_cover", select_cover);
		setResult(RESULT_SELECT_COVER, intent);
		finish();
	}else{
		Toast.makeText(getApplicationContext(), "请选择一个视频封面", Toast.LENGTH_SHORT).show();
	}
		
		break;

	default:
		break;
	}
}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	return super.onKeyDown(keyCode, event);
}

	private void initGirdView() {
		array = initBitmaps(path);
		adapter = new GirdAdapterSelectVideoCover(this, array);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int temp_select_position = select_position;
				select_position = position;
				if(temp_select_position != select_position)
				{
				select_cover = array.get(select_position);
				adapter.freshSelectPosition(select_position);
				}
				
			}
		});
	}

	private ArrayList<Bitmap> initBitmaps(String path) {
		ArrayList<Bitmap> array = new ArrayList<Bitmap>();
		if(path!= null && new File(path).length() > 0)
		{
			MediaMetadataRetriever retriever = new MediaMetadataRetriever();
			try {
				retriever.setDataSource(path);
				String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
				int millons = Integer.parseInt(time) / 8;
				for(int i = 0; i < 8; i++)
				{
					//图片未做压缩处理
				Bitmap	bitmap = retriever.getFrameAtTime(i * millons* 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
				array.add(Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() /2, bitmap.getHeight() /2, false));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				retriever.release();
			}
			
		}
		
		return array;
	}

	
	

}
