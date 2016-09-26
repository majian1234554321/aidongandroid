package com.leyuan.aidong.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.widget.GridView;

public class PhotoGridView extends GridView {
	public boolean hasScrollBar = true;

	/**
	 * 存储宽度
	 */
	private SparseIntArray mGvWidth = new SparseIntArray();

	/**
	 * @param context
	 */
	public PhotoGridView(Context context) {
		this(context, null);
	}

	public PhotoGridView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public PhotoGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = heightMeasureSpec;
		if (hasScrollBar) {
			expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
					MeasureSpec.AT_MOST);
			// 注意这里,这里的意思是直接测量出GridView的高度
			super.onMeasure(widthMeasureSpec, expandSpec);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public boolean isHasScrollBar() {
		return hasScrollBar;
	}

	public void setHasScrollBar(boolean hasScrollBar) {
		this.hasScrollBar = hasScrollBar;
	}

	// /**
	// * 计算GridView的高度
	// *
	// * @param gridView 要计算的GridView
	// */
	// public void updateGridViewLayoutParams(int maxColumn) {
	// int childs = this.getAdapter().getCount();
	//
	// if (childs > 0) {
	// int columns = childs < maxColumn ? childs % maxColumn : maxColumn;
	// this.setNumColumns(columns);
	// int width = 0;
	// int cacheWidth = mGvWidth.get(columns);
	// if (cacheWidth != 0) {
	// width = cacheWidth;
	// } else {
	// // 计算gridview每行的宽度, 如果item小于3则计算所有item的宽度;
	// // 否则只计算3个child宽度，因此一行最多3个child。 (这里我们以3为例)
	// // int rowCounts = childs < maxColumn ? childs : maxColumn;
	// // for (int i = 0; i < rowCounts; i++) {
	// // View childView = this.getAdapter().getView(i, null, this);
	// // childView.measure(0, 0);
	// // width += childView.getMeasuredWidth();
	// // }
	// }
	//
	// ViewGroup.LayoutParams params = this.getLayoutParams();
	// params.width = width;
	// this.setLayoutParams(params);
	// if (mGvWidth.get(columns) == 0) {
	// mGvWidth.append(columns, width);
	// }
	// }
	// }
}
