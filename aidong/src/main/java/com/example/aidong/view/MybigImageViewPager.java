package com.example.aidong.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MybigImageViewPager extends ViewPager {

	public MybigImageViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MybigImageViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
//		return super.onInterceptTouchEvent(arg0);
		boolean b=false;
		try{  
		   b = super.onInterceptTouchEvent(arg0);  
		} catch(IllegalArgumentException ex) {  
		}  
		return b; 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
//		return super.onTouchEvent(arg0);
		boolean b=false;
		try{  
		   b = super.onTouchEvent(arg0);  
		} catch(IllegalArgumentException ex) {  
		}  
		return b; 
	}

}
