package com.leyuan.custompullrefresh;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;

import com.leyuan.custompullrefresh.ptr.PtrClassicDefaultHeader;
import com.leyuan.custompullrefresh.ptr.PtrDefaultHandler;
import com.leyuan.custompullrefresh.ptr.PtrFrameLayout;


/**
 * Created by user on 2017/6/6.
 */

public class CustomRefreshLayout extends PtrFrameLayout {

    OnRefreshListener mListener;
    private PtrClassicDefaultHeader mPtrClassicHeader;

    public CustomRefreshLayout(Context context) {
        super(context);
        initViews();
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CustomRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        setResistance(3.5f);
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);
    }

    @Deprecated
    public void setColorSchemeResources(@ColorRes int... colorResIds) {

    }

    @Deprecated
    public void setProgressViewOffset(boolean scale, int start, int end) {

    }

    /**
     * Set the listener to be notified when a refresh is triggered via the swipe
     * gesture.
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
        setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mListener.onRefresh();
            }
        });
    }

    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    public void setRefreshing(boolean refreshing) {
        if (!refreshing) {
            refreshComplete();
        }
    }
}
