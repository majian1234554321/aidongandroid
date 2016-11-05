package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.activity.discover.AppointCoachActivity;

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
        if(data != null){
            this.data = data;
        }else{
            for (int i = 0; i < 10; i++) {
                CoachBean bean = new CoachBean();
                if( i % 2 == 0){
                    bean.setName("AiFukuhara ");
                    bean.setAvatar("http://ww3.sinaimg.cn/mw690/718878b5jw1f9fiyllzqpj20go0b5tah.jpg");
                }else{
                    bean.setAvatar("https://www.baidu.com/img/bd_logo1.png");
                    bean.setName("百度保健 ");
                }
                this.data.add(bean);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CoachHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venues_coach, parent, false);
        return new CoachHolder(view);
    }

    @Override
    public void onBindViewHolder(CoachHolder holder, int position) {
        CoachBean bean = data.get(position);
        holder.cover.setImageURI(bean.getAvatar());
        holder.name.setText(bean.getName());
        holder.gender.setTag(bean.getGender());

        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointCoachActivity.start(context);
            }
        });
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
