package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝和关注适配器
 * Created by song on 2016/9/10.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.UserHolder> {
    private Context context;
    private List<UserBean> data = new ArrayList<>();
    private FollowListener followListener;
    private boolean isFollow;

    public FollowAdapter(Context context,boolean isFollow) {
        this.context = context;
        this.isFollow = isFollow;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_follow,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        final UserBean bean = data.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.avatar);
        holder.nickname.setText(bean.getName());
        holder.distance.setText(bean.getSignature());
        holder.gender.setBackgroundResource("0".equals(bean.getGender()) ? R.drawable.icon_man
                :R.drawable.icon_woman);
        holder.follow.setBackgroundResource(isFollow ? R.drawable.icon_following
                :R.drawable.icon_follow);

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean.isFollow()){
                    if(followListener != null){
                        followListener.onCancelFollow(bean.getId());
                    }
                }else {
                    if(followListener != null){
                        followListener.onAddFollow(bean.getId());
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(followListener != null){
                    followListener.onItemClick(bean.getId());
                }
            }
        });
    }

    class UserHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        ImageView gender;
        ImageView follow;
        TextView nickname;
        TextView distance;

        public UserHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.dv_user_cover);
            gender = (ImageView) itemView.findViewById(R.id.iv_gender);
            follow = (ImageView) itemView.findViewById(R.id.iv_follow);
            nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
        }
    }

    public void setFollowListener(FollowListener followListener) {
        this.followListener = followListener;
    }

    public interface FollowListener {
        void onAddFollow(String id);
        void onCancelFollow(String id);
        void onItemClick(String id);
    }
}
