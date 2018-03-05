package com.leyuan.aidong.adapter.contest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.campaign.ContestScheduleBean;
import com.leyuan.aidong.ui.mvp.view.ContestSemiFinalEnrolClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 运动之窗适配器
 * Created by song on 2016/10/17.
 */
public class ContestSemiFinalEnrolChildAdapter extends RecyclerView.Adapter<ContestSemiFinalEnrolChildAdapter.ViewHolder> {
    private Context context;
    private List<ContestScheduleBean> data = new ArrayList<>();
    private ContestSemiFinalEnrolClickListener listener;


    public ContestSemiFinalEnrolChildAdapter(Context context) {
        this.context = context;
    }

    public ContestSemiFinalEnrolChildAdapter(Context context, ArrayList<ContestScheduleBean> item) {
        this.context = context;
        data = item;
    }

    public void setData(List<ContestScheduleBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(ContestSemiFinalEnrolClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contest_semi_final_enrol_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ContestScheduleBean schedule = data.get(position);
        holder.txtName.setText(schedule.store_name);
        holder.txtTime.setText(schedule.class_date + " " + schedule.class_time);
        holder.txtAddress.setText(schedule.address);
        holder.btEnrol.setImageResource(schedule.appointed ? R.drawable.icon_contest_enroled : R.drawable.icon_contest_enrol);


        holder.btEnrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSemiFinalEnrolClick(schedule.id, schedule.appointed);
                }
            }
        });


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtTime;
        private TextView txtAddress;
        private ImageButton btEnrol;

        public ViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtAddress = (TextView) view.findViewById(R.id.txt_address);
            btEnrol = (ImageButton) view.findViewById(R.id.bt_enrol);
        }
    }
}
