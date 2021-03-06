package com.example.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CoachBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.discover.activity.AppointCoachActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.UiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 场馆详情教练列表适配器
 * Created by song on 2016/8/29.
 */
public class VenuesCoachAdapter extends RecyclerView.Adapter<VenuesCoachAdapter.CoachHolder> {
    private Context context;
    private String venuesId;
    private List<CoachBean> data = new ArrayList<>();

    public VenuesCoachAdapter(Context context, String venuesId) {
        this.context = context;
        this.venuesId = venuesId;
    }

    public void setData(List<CoachBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
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
        final CoachBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundAvatarImage(bean.getAvatar(), holder.cover);
        holder.name.setText(bean.getName());
        if ("0".equals(bean.getGender())) {   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        } else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }
        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.mInstance.isLogin()) {
                    AppointCoachActivity.start(context, venuesId, bean);
                } else {
                    UiManager.activityJump(context, LoginActivity.class);
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context, bean.getId());
            }
        });
    }

    class CoachHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        ImageView gender;
        TextView name;
        TextView distance;
        TextView hot;
        ImageView appointment;

        public CoachHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_coach_cover);
            name = (TextView) itemView.findViewById(R.id.tv_coach_name);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
            hot = (TextView) itemView.findViewById(R.id.tv_hot);
            gender = (ImageView) itemView.findViewById(R.id.iv_gender);
            appointment = (ImageView) itemView.findViewById(R.id.iv_appointment);
        }
    }
}
