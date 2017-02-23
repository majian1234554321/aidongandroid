package com.leyuan.aidong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
/**
 * 正方形relaitive,宽度.高度都等于宽度
 */
public class SquareRelativeLayout extends RelativeLayout {

	public SquareRelativeLayout(Context context) {
		super(context);
	}

	public SquareRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareRelativeLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}
}
