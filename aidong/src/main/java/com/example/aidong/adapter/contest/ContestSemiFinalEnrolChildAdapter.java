package com.example.aidong.adapter.contest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.campaign.ContestScheduleBean;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong .ui.mvp.view.ContestSemiFinalEnrolClickListener;
import com.example.aidong .utils.ToastGlobal;

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
        holder.txtTime.setText((schedule.class_date == null ? "" : (schedule.class_date + " ")) + schedule.class_time);
        holder.txtAddress.setText(schedule.address);
        holder.txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (schedule.coordinate == null) {
                    ToastGlobal.showShortConsecutive("无效的地址");
                    return;
                }
                MapActivity.start(context, "比赛地址", schedule.store_name, schedule.address, schedule.coordinate.getLat(), schedule.coordinate.getLng());
            }
        });

        if (schedule.appointed) {
            //已预约
            holder.btEnrol.setBackgroundResource(R.drawable.shape_semi_circle_nine_solid);
            holder.btEnrol.setText("已报名");

        } else if (schedule.place <= 0) {
            //已满员
            holder.btEnrol.setBackgroundResource(R.drawable.shape_semi_circle_nine_solid);
            holder.btEnrol.setText("已满员");

        } else {
            holder.btEnrol.setBackgroundResource(R.drawable.shape_simi_circle_orange_solid);
            holder.btEnrol.setText("报名");
        }

        holder.btEnrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (schedule.place <= 0 && !schedule.appointed) {
                        ToastGlobal.showShortConsecutive("已满员，请选择其他时段");
                    } else {
                        listener.onSemiFinalEnrolClick(schedule.id, schedule.appointed);
                    }

                }
            }
        });


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtTime;
        private TextView txtAddress;
        private Button btEnrol;

        public ViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtAddress = (TextView) view.findViewById(R.id.txt_address);
            btEnrol = (Button) view.findViewById(R.id.bt_enrol);
        }
    }
}
