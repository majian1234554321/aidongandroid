package com.leyuan.aidong.ui.activity.discover.adapter;

import android.graphics.Color;
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
    private int selectedPosition = 0;
    private ItemClickListener itemClickListener;

    public void setData(List<String> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
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
    public void onBindViewHolder(DateHolder holder, final int position) {
        final String date = data.get(position);
        holder.date.setText(date);
        holder.date.setTextColor(position == selectedPosition ? Color.parseColor("#FF5000") :
                Color.parseColor("#000000"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                if(itemClickListener != null){
                    itemClickListener.onItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    class DateHolder extends RecyclerView.ViewHolder{
        TextView date;

        public DateHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.tv_date);
        }
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }
}
