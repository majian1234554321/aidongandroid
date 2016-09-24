package com.example.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.aidong.entity.CoachBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆详情教练列表适配器
 * Created by song on 2016/8/29.
 */
public class VenuesCoachAdapter extends RecyclerView.Adapter<VenuesCoachAdapter.CoachHolder>{
    private Context context;
    private List<CoachBean> data = new ArrayList<>();

    public VenuesCoachAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CoachBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CoachHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_venues_coach,null);
        return new CoachHolder(view);
    }

    @Override
    public void onBindViewHolder(CoachHolder holder, int position) {
        CoachBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
        holder.name.setText(bean.getName());
        holder.gender.setTag(bean.getGender());
    }

    class CoachHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        ImageView gender;
        TextView name;
        TextView distance;
        TextView hot;
        ImageView appointment;

        public CoachHolder(View itemView) {
            super(itemView);

            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_coach_cover);
            name = (TextView)itemView.findViewById(R.id.tv_coach_name);
            distance = (TextView)itemView.findViewById(R.id.tv_distance);
            hot = (TextView)itemView.findViewById(R.id.tv_hot);
            gender = (ImageView) itemView.findViewById(R.id.iv_gender);
            appointment = (ImageView)itemView.findViewById(R.id.iv_appointment);
        }
    }
}
