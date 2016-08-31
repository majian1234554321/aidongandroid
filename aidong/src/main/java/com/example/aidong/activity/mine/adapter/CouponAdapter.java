package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.support.entity.CouponBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵适配器
 * Created by song on 2016/8/31.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponHolder>{
    private Context context;
    private List<CouponBean> data = new ArrayList<>();

    public CouponAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CouponBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CouponHolder holder, int position) {

    }

    class CouponHolder extends RecyclerView.ViewHolder{

        public CouponHolder(View itemView) {
            super(itemView);
        }
    }
}
