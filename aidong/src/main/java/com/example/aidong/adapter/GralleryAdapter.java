package com.example.aidong.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

import java.util.ArrayList;


public class GralleryAdapter extends AbstractCommonAdapter {
	private Context context;
	ArrayList<String> typeUrlArray = new ArrayList<String>();

	public GralleryAdapter(Context context,
			ArrayList<String> fitTypeArray) {
		this.context = context;
		typeUrlArray = new ArrayList<String>();
		typeUrlArray.addAll(fitTypeArray);
	}

	public void freshData(ArrayList<String> fitTypeArray) {
		typeUrlArray.clear();
		typeUrlArray.addAll(fitTypeArray);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return typeUrlArray.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		if(typeUrlArray.size() > 0){
			String url = typeUrlArray.get(position);
			Log.e("grllery", "url = "+url);
			imageLoader.displayImage(url, imageView,options);
		}
		return imageView;
	}

}
