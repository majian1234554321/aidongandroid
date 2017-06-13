package com.leyuan.aidong.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
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
        Logger.i("SmartScrollView", "onOverScrolled:  scrollY = " + scrollY + ", clampedY = " + clampedY + ",getscrollY = " + getScrollY());
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

//        Logger.i("SmartScrollView", "getScrollY = " + getScrollY());
        //
        //        if (getScrollY() == 0 && mListener != null) {
        //            mListener.onScrollTop();
        //        }

    }

    private float oldY;
    private boolean scrollTop = true; //true 向下 flase 向上

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        boolean intecept = false;
        int newX = (int) ev.getX();
        int newY = (int) ev.getY();


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = ev.getY();
                Logger.i("SmartScrollView", "onInterceptTouchEvent  MotionEvent.ACTION_DOWN:  oldY = " + oldY);
                intecept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (newY - oldY > 0) {
                    intecept = true;
                } else {
                    intecept = false;
                }
                scrollTop =intecept;
                break;
            case MotionEvent.ACTION_UP:
                intecept = false;
                break;
        }
        Logger.i("SmartScrollView", "intecept = " + intecept+ ", newY = " + newY + " scrollTop = " + scrollTop);

        return intecept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.i("SmartScrollView","onTouchEvent " + MotionEvent.actionToString(ev.getAction()));
        }
        return super.onTouchEvent(ev);
    }

    public interface OnScrollToTopListener {
        void onScrollTop();
    }
}
