package com.leyuan.aidong.adapter.baseadapter;

import android.view.View;


public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View v, int position, T data);
}
