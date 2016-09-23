package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自提时间适配器
 * Created by song on 2016/9/22.
 */
public class ChooseTimeAdapter extends RecyclerView.Adapter<ChooseTimeAdapter.TimeHolder> {
    private Context context;
    private List<String> data = new ArrayList<>();

    public ChooseTimeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        //this.data = data;
        if(data == null){
            for(int i =0;i<5;i++){
               this.data.add("2015");
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public TimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_chooes_time,null);
        return new TimeHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeHolder holder, int position) {
        String time = data.get(position);
        holder.tvTime.setText(time);
    }

    class TimeHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        RadioButton rbCheck;

        public TimeHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            rbCheck = (RadioButton) itemView.findViewById(R.id.rb_check);
        }
    }
}
