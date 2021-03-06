package com.example.aidong.adapter.discover;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间选择RecyclerView适配器
 * Created by song on 2016/8/30.
 */
public class CourseDateAdapter extends RecyclerView.Adapter<CourseDateAdapter.DateHolder> {
    private List<String> data = new ArrayList<>();
    private int selectedPosition = 0;
    private ItemClickListener itemClickListener;

    public void setData(List<String> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public CourseDateAdapter() {
    }

    public CourseDateAdapter(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_course_date, null);
        return new DateHolder(view);
    }

    @Override
    public void onBindViewHolder(DateHolder holder, final int position) {
        final String date = data.get(position);
        holder.date.setText(date);
        holder.date.setTextColor(position == selectedPosition ? Color.parseColor("#D51121") :
                Color.parseColor("#000000"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    class DateHolder extends RecyclerView.ViewHolder {
        TextView date;

        public DateHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

}
