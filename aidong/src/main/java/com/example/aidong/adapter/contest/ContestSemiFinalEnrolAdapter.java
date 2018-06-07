package com.example.aidong.adapter.contest;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.data.ContestSchedulesDateData;
import com.example.aidong .ui.mvp.view.ContestSemiFinalEnrolClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class ContestSemiFinalEnrolAdapter extends RecyclerView.Adapter<ContestSemiFinalEnrolAdapter.ViewHolder> {
    private Context context;
    private List<ContestSchedulesDateData> data = new ArrayList<>();
    private ContestSemiFinalEnrolClickListener listener;

    public ContestSemiFinalEnrolAdapter(Context context) {
        this.context = context;
    }

    public void setListener(ContestSemiFinalEnrolClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<ContestSchedulesDateData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contest_semi_final_enrol, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContestSchedulesDateData schedule = data.get(position);
        holder.txtDate.setText(schedule.date);
        holder.rvOrder.setLayoutManager(new LinearLayoutManager(context));
        ContestSemiFinalEnrolChildAdapter adapter = new ContestSemiFinalEnrolChildAdapter(context, schedule.item);
        adapter.setListener(listener);
        holder.rvOrder.setAdapter(adapter);


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDate;
        private RecyclerView rvOrder;

        public ViewHolder(View view) {
            super(view);
            txtDate = (TextView) view.findViewById(R.id.txt_date);
            rvOrder = (RecyclerView) view.findViewById(R.id.rv_order);
        }
    }
}
