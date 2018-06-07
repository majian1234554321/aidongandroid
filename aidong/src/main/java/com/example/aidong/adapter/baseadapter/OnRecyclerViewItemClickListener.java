package com.example.aidong.adapter.baseadapter;

import android.view.View;


public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View v, int position, T data);
}
