package com.leyuan.custompullrefresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;


/**
 * Created by user on 2017/6/6.
 */

public class CustomRefreshLayout extends SwipeRefreshLayout {
    com.leyuan.custompullrefresh.OnRefreshListener mListener;
    private OnRefreshListener refreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            mListener.onRefresh();
        }
    };

    public CustomRefreshLayout(Context context) {
        super(context);
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnRefreshListener(com.leyuan.custompullrefresh.OnRefreshListener listener) {
        mListener = listener;
        super.setOnRefreshListener(refreshListener);
    }
}
