package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private List<UserBean> data = new ArrayList<>();
    private FollowListener followListener;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> data) {
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        final UserBean bean = data.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.cover);
        holder.nickname.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
        if (!App.mInstance.isLogin()) {
            holder.follow.setBackgroundResource(R.drawable.icon_follow);
        } else {
            holder.follow.setBackgroundResource(SystemInfoUtils.isFollow(context, bean)
                    ? R.drawable.icon_following : R.drawable.icon_follow);
        }

        if ("0".equals(bean.getGender())) {   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        } else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followListener != null) {
                    followListener.onFollowClicked(position);
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

    public void addMoreData(List<UserBean> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }


    class UserHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        ImageView gender;
        TextView nickname;
        TextView distance;
        ImageView follow;

        public UserHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_user_cover);
            gender = (ImageView) itemView.findViewById(R.id.iv_gender);
            nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
            follow = (ImageView) itemView.findViewById(R.id.iv_follow);
        }
    }

    public void setFollowListener(FollowListener followListener) {
        this.followListener = followListener;
    }

    public interface FollowListener {
        void onFollowClicked(int position);
    }
}
