package com.leyuan.custompullrefresh;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.View;

import com.leyuan.custompullrefresh.ptr.PtrClassicDefaultHeader;
import com.leyuan.custompullrefresh.ptr.PtrDefaultHandler;
import com.leyuan.custompullrefresh.ptr.PtrFrameLayout;
import com.leyuan.custompullrefresh.ptr.PtrHandler;
import com.leyuan.custompullrefresh.ptr.header.StoreHouseHeader;
import com.leyuan.custompullrefresh.util.DensityUtil;


/**
 * Created by user on 2017/6/6.
 */

public class CustomRefreshLayoutPtr extends PtrFrameLayout {
    protected static final String REFRESH_STRING = "FITNESS";
    OnRefreshListener mListener;
    private StoreHouseHeader header;
    private PtrClassicDefaultHeader mPtrClassicHeader;

    public CustomRefreshLayoutPtr(Context context) {
        this(context, null);
    }

    public CustomRefreshLayoutPtr(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshLayoutPtr(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        initPtrFrameLayout();
//        setResistance(3.5f);
//        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
//        setHeaderView(mPtrClassicHeader);
//        addPtrUIHandler(mPtrClassicHeader);
    }

    protected void initPtrFrameLayout() {
        header = new StoreHouseHeader(getContext());
        header.initWithString(REFRESH_STRING);
        setHeaderView(header);
        addPtrUIHandler(header);
        setDurationToCloseHeader(500);
    /*    postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefresh(false);
            }
        }, 100);*/
        disableWhenHorizontalMove(true);

    }


    @Deprecated
    public void setColorSchemeResources(@ColorRes int... colorResIds) {

    }

    @Deprecated
    public void setProgressViewOffset(boolean scale, int start, int end) {
        header.setPadding(0, DensityUtil.dp2px(getContext(), start), 0, 0);
    }

    /**
     * Set the listener to be notified when a refresh is triggered via the swipe
     * gesture.
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
        setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onRefresh();
                    }
                }, 100);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

    }

    public boolean isRefreshing() {
        return super.isRefreshing();
    }

    public void setRefreshing(boolean refreshing) {
        if (!refreshing) {
            refreshComplete();
        } else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    autoRefresh(false);
                }
            }, 100);
        }
    }
}
