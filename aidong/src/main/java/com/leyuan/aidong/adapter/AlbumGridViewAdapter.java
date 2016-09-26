package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.utils.photo.BitmapCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个是显示一个文件夹里面的所有图片时用的适配器
 */
public class AlbumGridViewAdapter extends AbstractCommonAdapter {
	final String TAG = getClass().getSimpleName();
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private ArrayList<ImageItem> selectedDataList;
	private onFirstItemClickListener mOnFirstItemClickListener;
	private boolean isGetIcon;
	private Map<Integer, Boolean> map_load = new HashMap<Integer, Boolean>();
	BitmapCache cache;

	public AlbumGridViewAdapter(Context c, ArrayList<ImageItem> dataList,
								ArrayList<ImageItem> selectedDataList) {
		mContext = c;
		cache = new BitmapCache();
		this.dataList = dataList;
		this.selectedDataList = selectedDataList;
		option = new DisplayImageOptions.Builder()
				//				 .showImageOnLoading(R.drawable.icon_picture)
				//				 .showImageForEmptyUri(R.drawable.icon_picture)
				.showImageOnFail(R.drawable.icon_picture)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();

		for (int i = 0; i < dataList.size(); i++) {
			map.put(i, false);
			// map_load.put(i, false);
		}
	}

	public Map<Integer, Boolean> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Boolean> map) {
		this.map = map;
	}

	public void setCanGetIcon(boolean isGetIcon) {
		if (this.isGetIcon != isGetIcon) {
			this.isGetIcon = isGetIcon;
		}

	}

	private OnCheckBoxClickListener mOncheckListener;

	private static Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

	public void freshData(ArrayList<ImageItem> newDataList,
			ArrayList<ImageItem> selectedDataList) {
		this.dataList.clear();
		this.dataList.addAll(newDataList);
		this.selectedDataList = selectedDataList;
		map.clear();
		for (int i = 0; i < dataList.size(); i++) {
			map.put(i, false);
		}

		notifyDataSetChanged();
	}

	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	private DisplayImageOptions option;

	static class ViewHolder {
		public ImageView imageView;
		public ImageView image_album;
		public View view_stroke_album;
		public CheckBox check_box;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Log.i("album", "position == " + position +", 子孩子个数 ： " +
		// parent.getChildCount());

		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.plugin_camera_select_imageview, parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.image_view);
			viewHolder.image_album = (ImageView) convertView
					.findViewById(R.id.image_album);
			viewHolder.check_box = (CheckBox) convertView
					.findViewById(R.id.check_box);
			viewHolder.view_stroke_album = (View) convertView
					.findViewById(R.id.view_stroke_album);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			// map_load.put(position, false);
		}

		String path = "";
		if (dataList != null && dataList.size() > position)
			path = dataList.get(position).imagePath;

		if ("photoPath".equals(path)) {
			viewHolder.image_album.setVisibility(View.VISIBLE);
			viewHolder.view_stroke_album.setVisibility(View.GONE);
			viewHolder.check_box.setVisibility(View.GONE);

		} else {
			ImageItem item = dataList.get(position);
			viewHolder.image_album.setVisibility(View.GONE);
			viewHolder.check_box.setVisibility(View.VISIBLE);
			String imagepath = "file://" + item.getImagePath();
			
			if (!imagepath.equals(viewHolder.imageView.getTag() )) {
				viewHolder.imageView.setTag(imagepath);
				ImageSize targetImageSize = new ImageSize(250, 250);
				viewHolder.imageView.setImageBitmap(imageLoader.loadImageSync(imagepath, targetImageSize , option));
			}

//			viewHolder.imageView.setTag("file://" + item.getImagePath());
//			imageLoader.loadImage("file://" + item.getImagePath(), option,
//					new SimpleImageLoadingListener() {
//						@Override
//						public void onLoadingComplete(String imageUri,
//								View view, Bitmap loadedImage) {
//							super.onLoadingComplete(imageUri, view, loadedImage);
//							// System.out.println("uri : "
//							// + imageUri
//							// + "\n tag : "
//							// + (String) (viewHolder.imageView
//							// .getTag()));
//
//							if (viewHolder.imageView.getTag() != null
//									&& TextUtils.equals(
//											(String) (viewHolder.imageView
//													.getTag()), imageUri)) {
//
//								viewHolder.imageView
//										.setImageBitmap(loadedImage);
//							}
//						}
//					});
			// imageLoader.displayImage("file://" + item.getImagePath(),
			// viewHolder.imageView,option);

			// cache.displayBmp(viewHolder.imageView, item.thumbnailPath,
			// item.imagePath,
			// callback);

			viewHolder.check_box.setTag(position);

			viewHolder.check_box
					.setOnCheckedChangeListener(new MyOnCheckedChangeListener(
							viewHolder.view_stroke_album));

			if (map.get(position)) {
				viewHolder.check_box.setChecked(true);
				viewHolder.view_stroke_album.setVisibility(View.VISIBLE);
				// viewHolder.check_box.setTag(R.id.tag_first, true);
			} else {
				viewHolder.check_box.setChecked(false);
				viewHolder.view_stroke_album.setVisibility(View.GONE);
				// viewHolder.check_box.setTag(R.id.tag_first, false);
			}

			//
			// if (viewHolder.check_box.isChecked()) {
			// viewHolder.view_stroke_album.setVisibility(View.VISIBLE);
			// //
			// viewHolder.check_box.setBackgroundResource(R.drawable.btn_select_camera_pressed);
			// }
			// else{
			// viewHolder.view_stroke_album.setVisibility(View.GONE);
			// //
			// viewHolder.check_box.setBackgroundResource(R.drawable.btn_select_camera_normal);
			// }

			// if ( (Integer)(viewHolder.view_stroke_album.getTag()) ==
			// position) {
			// viewHolder.check_box.setChecked(true);
			// viewHolder.view_stroke_album.setVisibility(View.VISIBLE);
			// }
			// else{
			// viewHolder.check_box.setChecked(false);
			// viewHolder.view_stroke_album.setVisibility(View.GONE);
			// }

		}

		return convertView;
	}

	class MyOnCheckedChangeListener implements OnCheckedChangeListener {
		private View view;

		public MyOnCheckedChangeListener(View view) {
			this.view = view;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			Log.e("album", "点击状态发生变化了");

			if (buttonView instanceof CheckBox) {
				CheckBox box = (CheckBox) buttonView;
				int position = (Integer) box.getTag();
				if (map.get(position) == isChecked) {
					return;
				}

				if (isChecked) {
					map.put(position, true);
					view.setVisibility(View.VISIBLE);
				} else {
					map.put(position, false);
					view.setVisibility(View.GONE);
				}

				// boolean last_is_selected = (Boolean)
				// box.getTag(R.id.tag_first);
				// if (last_is_selected != isChecked) {
				if (mOncheckListener != null) {
					Log.e("album", "执行了chenckbox代码");
					mOncheckListener.onCheckBoxClick(box, isChecked, position,
							view);
				}

				// }

			}

		}

	}

	public void setOnFirstItemClickListener(
			onFirstItemClickListener firstListener) {
		mOnFirstItemClickListener = firstListener;
	}

	public interface onFirstItemClickListener {
		public void onFirstItemClick();
	}

	public void setOnCheckBoxClickListener(OnCheckBoxClickListener checkListener) {
		mOncheckListener = checkListener;
	}

	public interface OnCheckBoxClickListener {
		public void onCheckBoxClick(CheckBox buttonView, boolean isChecked,
									int position, View view);
	}

}
