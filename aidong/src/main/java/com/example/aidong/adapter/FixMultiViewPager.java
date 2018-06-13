package com.example.aidong.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;



public class FixMultiViewPager extends ViewPager {
    private final String TAG = FixMultiViewPager.class.getSimpleName();

    public FixMultiViewPager(Context context) {
        super(context);
    }

    public FixMultiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "ACTION_UP");
                    break;
            }
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "onInterceptTouchEvent() ", ex);
            ex.printStackTrace();
        }
        return false;
    }

}
