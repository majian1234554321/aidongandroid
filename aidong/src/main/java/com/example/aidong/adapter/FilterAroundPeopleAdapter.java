package com.example.aidong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;


/**
 * Created by song on 2016/7/14.
 */
public class FilterAroundPeopleAdapter extends BaseAdapter<String> {
    @Override
    public int getContentView() {
        return R.layout.item_chooes_result;
    }

    @Override
    public void initView(View view, final int position, ViewGroup parent) {
        TextView tvResult = getView(view, R.id.tv_result);
        String result = getItem(position);
        tvResult.setText(result);
    }
}
