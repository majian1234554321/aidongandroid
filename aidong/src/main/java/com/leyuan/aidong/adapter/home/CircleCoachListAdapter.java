package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.CoachInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleCoachListAdapter extends RecyclerView.Adapter<CircleCoachListAdapter.ViewHolder> {

    private final Context context;
    private List<UserBean> users;

    public CircleCoachListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserBean user = users.get(position);
        GlideLoader.getInstance().displayCircleImage(user.getAvatar(), holder.imgAvatar);
        holder.txtCoachName.setText(user.getName());
        holder.txt_attention_num.setVisibility(View.VISIBLE);
        holder.txt_attention_num.setText(user.followers_count + "人关注");
        holder.txtIntro.setText(user.personal_intro);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoachInfoActivity.start(context, user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users == null) return 0;
        return users.size();
    }

    public void setData(List<UserBean> followings) {
        this.users = followings;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtCoachName, txt_attention_num;
        private TextView txtIntro;
        private ImageButton btAttention;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);
            txt_attention_num = (TextView) view.findViewById(R.id.txt_attention_num);
        }
    }
}
