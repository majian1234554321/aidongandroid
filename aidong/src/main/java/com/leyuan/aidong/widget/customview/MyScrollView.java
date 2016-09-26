package com.leyuan.aidong.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * 
 * 
 */
public class MyScrollView extends ScrollView {

	
	
	private ScrollViewListener scrollViewListener = null;
	public MyScrollView(Context context) {
		this(context,null);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
		this.scrollViewListener = scrollViewListener;
	}
	
	
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(scrollViewListener != null)
		{
			scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
		}
	}
	
	
	
	
	
	public interface ScrollViewListener {  
		
		   void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy);  
		  
	}  

	

}
