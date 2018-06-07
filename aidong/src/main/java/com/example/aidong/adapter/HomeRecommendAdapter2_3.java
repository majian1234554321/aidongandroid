package com.example.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CoachBean;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .utils.GlideLoader;

import java.util.List;

public class HomeRecommendAdapter2_3 extends RecyclerView.Adapter<HomeRecommendAdapter2_3.ViewHolder> {

    private  Context context;
    private List<CoachBean> coachs;

     public  HomeRecommendAdapter2_3( Context context,Object coachs){
         this.context = context;
         this.coachs = (List<CoachBean>) coachs;
     }

    @NonNull
    @Override
    public HomeRecommendAdapter2_3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_attention, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter2_3.ViewHolder holder, int position) {
        final CoachBean coachBean = (CoachBean)coachs.get(position);
        GlideLoader.getInstance().displayCircleImage(coachBean.getAvatar(),holder.imgAvatar);
        holder.txtName.setText(coachBean.getName());
        holder.txt_attention_num.setText(coachBean.followers_count+"人关注");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context,coachBean.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return coachs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtName,txt_attention_num;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txt_attention_num = (TextView) view.findViewById(R.id.txt_attention_num);
        }
    }
}
