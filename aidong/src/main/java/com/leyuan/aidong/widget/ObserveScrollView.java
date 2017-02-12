package com.leyuan.aidong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by song on 2016/9/13.
 */
public class ObserveScrollView extends ScrollView{

    public  interface ScrollViewListener {
        void onScrollChanged(ObserveScrollView scrollView, int x, int y, int oldX, int oldY);

    }

    private ScrollViewListener scrollViewListener = null;

    public ObserveScrollView(Context context) {
        super(context);
    }

    public ObserveScrollView(Context context, AttributeSet attrs,
                               int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
}
