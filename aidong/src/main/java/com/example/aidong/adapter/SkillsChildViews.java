package com.example.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.interfaces.AutoLinefeedChildViews;

import java.util.ArrayList;


public class SkillsChildViews implements AutoLinefeedChildViews {
	private ArrayList<String> array;
	Context context;
	private int colortype = 0;
	
	public SkillsChildViews(Context context,ArrayList<String> array) {
		this.array = array;
		this.context = context;
		if(this.array ==null){
			this.array = new ArrayList<String>();
		}
	}
	public SkillsChildViews(Context context,ArrayList<String> array, int colortype) {
		this.array = array;
		this.context = context;
		this.colortype = colortype;
		if(this.array ==null){
			this.array = new ArrayList<String>();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public View getView(int i) {
		TextView tv = new TextView(context);
		tv.setText(array.get(i));
		if(colortype == 0){
			tv.setBackgroundResource(R.drawable.gray_rounded_hollow_bg);
			tv.setTextColor(context.getResources().getColor(R.color.store));
		}else{
			tv.setBackgroundResource(R.drawable.orange_rounded_hollow_bg);
			tv.setTextColor(context.getResources().getColor(R.color.sort));
		}
		
		return tv;
	}

}
