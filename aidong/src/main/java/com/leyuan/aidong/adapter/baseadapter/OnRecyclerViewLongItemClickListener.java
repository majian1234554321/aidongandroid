package com.leyuan.aidong.adapter.baseadapter;

import android.view.View;


public interface OnRecyclerViewLongItemClickListener<T> {
    boolean onItemLongClick(View v, int position, T data);
}
