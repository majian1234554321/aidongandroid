package com.example.aidong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class InSListView extends ListView {

	public InSListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public InSListView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public InSListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
	    // TODO Auto-generated method stub 
	    int expandSpec = MeasureSpec.makeMeasureSpec(  
	               Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	     
	    super.onMeasure(widthMeasureSpec, expandSpec); 
	} 

}
