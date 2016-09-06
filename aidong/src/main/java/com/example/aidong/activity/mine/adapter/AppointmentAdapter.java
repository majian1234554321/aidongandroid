package com.example.aidong.activity.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
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
        View view = View.inflate(context, R.layout.item_appointment,null);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, int position) {
        AppointmentBean bean = data.get(position);
        holder.state.setText(bean.getStatus());
        holder.cover.setImageURI(bean.getItem().getCover());
        holder.name.setText(bean.getItem().getName());
    }

    class AppointmentHolder extends RecyclerView.ViewHolder{
        TextView state;
        TextView timeOrId;
        SimpleDraweeView cover;
        TextView name;
        TextView address;
        TextView price;
        TextView leftButton;
        TextView rightButton;

        public AppointmentHolder(View itemView) {
            super(itemView);

            state = (TextView) itemView.findViewById(R.id.tv_state);
            timeOrId = (TextView) itemView.findViewById(R.id.tv_id_or_time);
            cover = (SimpleDraweeView) itemView.findViewById(R.id.dv_goods_cover);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            leftButton = (TextView) itemView.findViewById(R.id.tv_left_button);
            rightButton = (TextView) itemView.findViewById(R.id.tv_right_button);
        }
    }
}
