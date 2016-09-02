package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.support.entity.AppointmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约适配器
 * Created by song on 2016/9/1.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>{
    private Context context;
    private List<AppointmentBean> data = new ArrayList<>();

    public AppointmentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<AppointmentBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, int position) {

    }

    class AppointmentHolder extends RecyclerView.ViewHolder{

        public AppointmentHolder(View itemView) {
            super(itemView);
        }
    }
}
