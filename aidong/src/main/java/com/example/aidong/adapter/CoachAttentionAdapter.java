package com.example.aidong.adapter;

import android.content.Context;
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

import java.util.ArrayList;

/**
 * Created by user on 2018/1/4.
 */
public class CoachAttentionAdapter extends RecyclerView.Adapter<CoachAttentionAdapter.ViewHolder> {


    private final Context context;
    private ArrayList<CoachBean> coachs;

    public CoachAttentionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_attention2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final CoachBean coachBean = coachs.get(position);
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
        if(coachs == null)
            return 0;
        return coachs.size();
    }

    public void setData(ArrayList<CoachBean> coach) {
        this.coachs = coach;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
