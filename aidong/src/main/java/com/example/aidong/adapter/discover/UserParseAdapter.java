package com.example.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserParseAdapter extends RecyclerView.Adapter<UserParseAdapter.UserHolder> {
    private Context context;
    private List<CommentBean> data;
    private FollowListener followListener;

    public UserParseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        final  UserBean bean = data.get(position).getPublisher();

        GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.cover);
        holder.nickname.setText(bean.getName());
        holder.distance.setText("");
        if (!App.mInstance.isLogin()) {
            holder.follow.setBackgroundResource(R.drawable.icon_follow);
        } else {
            UserBean userBean = new UserBean();
            userBean.setId(bean.getId());
            holder.follow.setBackgroundResource(SystemInfoUtils.isFollow(context, userBean)
                    ? R.drawable.icon_followed : R.drawable.icon_follow);
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

    public void refreshData(ArrayList<CommentBean> data) {

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
