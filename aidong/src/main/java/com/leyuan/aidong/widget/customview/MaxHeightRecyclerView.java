package com.leyuan.aidong.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.leyuan.aidong.R;

/**
 * 控制最大高度的RecyclerView
 * Created by song on 2016/11/18.
 */
public class MaxHeightRecyclerView extends RecyclerView{
    private int maxHeight;
    private final int defaultHeight = 200;

    public MaxHeightRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView);
            //200 is a defualt value
            maxHeight = styledAttrs.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_recyclerView_maxHeight, defaultHeight);
            styledAttrs.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
