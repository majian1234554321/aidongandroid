package com.leyuan.custompullrefresh;

/**
 * Classes that wish to be notified when the swipe gesture correctly
 * triggers a refresh should implement this interface.
 */
public interface OnRefreshListener {
    /**
     * Called when a swipe gesture triggers a refresh.
     */
    void onRefresh();
}

