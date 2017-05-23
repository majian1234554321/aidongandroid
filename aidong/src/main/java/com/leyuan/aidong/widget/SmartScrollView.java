package com.leyuan.aidong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.leyuan.aidong.utils.Logger;


public class SmartScrollView extends ScrollView {

    private OnScrollToTopListener mListener;

    public void setScrollTopListener(OnScrollToTopListener listener) {
        mListener = listener;
    }

    public SmartScrollView(Context context) {
        super(context);
    }

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0 && clampedY && mListener != null && scrollTop) {
            mListener.onScrollTop();
        }
        Logger.i("SmartScrollView", "scrollY = " + scrollY + ", clampedY = " + clampedY + ",getscrollY = " + getScrollY());
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Logger.i("SmartScrollView", "getScrollY = " + getScrollY());
        //
        //        if (getScrollY() == 0 && mListener != null) {
        //            mListener.onScrollTop();
        //        }

    }

    //    private boolean intecept;
    private float oldY;
    //    private float oldX;
    private boolean scrollTop = true; //true 向下 flase 向上

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getRawY();
                //                intecept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = ev.getRawY();
                //                Logger.i("action newY = " + newY + ", newX = " + ev.getX());
                //                if (Math.abs(newY - oldY) >= 0) {
                //                    intecept = true;
                //                } else {
                //                    intecept = false;
                //                }

                scrollTop = newY - oldY > 0;

                break;
            //            case MotionEvent.ACTION_UP:
            //                intecept = false;
            //                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    public interface OnScrollToTopListener {
        void onScrollTop();
    }
}
