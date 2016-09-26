package com.leyuan.aidong.ui.activity.discover.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间选择RecyclerView适配器
 * Created by song on 2016/8/30.
 */
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder>{
    private List<String> data = new ArrayList<>();

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_date,null);
        return new DateHolder(view);
    }

    @Override
    public void onBindViewHolder(DateHolder holder, int position) {
        String date = data.get(position);
        holder.date.setText(date);
    }

    class DateHolder extends RecyclerView.ViewHolder{
        TextView date;

        public DateHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.tv_date);
        }
    }
}
