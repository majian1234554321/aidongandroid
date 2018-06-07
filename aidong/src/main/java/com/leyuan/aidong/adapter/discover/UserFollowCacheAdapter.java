package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.SystemInfoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动同道适配器
 * Created by song on 2016/8/29.
 */
public class UserFollowCacheAdapter extends RecyclerView.Adapter<UserFollowCacheAdapter.UserHolder> {
    private Context context;
    private List<UserBean> data;
    private FollowListener followListener;
    private int clickedPostion = -1;
    String mId;
    private ArrayList<String> following_ids;

    public UserFollowCacheAdapter(Context context) {
        this.context = context;
        if (App.getInstance().isLogin()) {
            mId = App.getInstance().getUser().getId() + "";
        }
    }

    public void setData(List<UserBean> data) {
        if (App.getInstance().isLogin()) {
            mId = App.getInstance().getUser().getId() + "";
        }
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
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
        if (!TextUtils.isEmpty(bean.getSignature())){
            holder.distance.setText(bean.getSignature());
        }else {
            holder.distance.setText("这个人很懒，什么都没有留下");
        }

        if ("coach".equals(bean.type)){
            Drawable drawable = context.getResources().getDrawable(
                    R.drawable.icon_coach);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            holder.nickname.setCompoundDrawables(null, null, drawable, null);
        }else {
            holder.nickname.setCompoundDrawables(null, null, null, null);
        }


        if (!App.mInstance.isLogin()) {
            holder.follow.setBackgroundResource(R.drawable.icon_follow);
        } else {
            if (String.valueOf(App.mInstance.getUser().getId()).equals(bean.getId())) {
                holder.follow.setVisibility(View.GONE);
            } else {
                holder.follow.setVisibility(View.VISIBLE);
                holder.follow.setBackgroundResource(bean.followed
                        ? R.drawable.icon_followed : R.drawable.icon_follow);
            }
        }
        holder.gender.setVisibility(View.VISIBLE);
        if ("0".equals(bean.getGender())) {   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        } else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.followed) {
                    if (followListener != null) {
                        followListener.onCancelFollow(bean.getId(), position);
                    }
                } else {
                    if (followListener != null) {
                        followListener.onAddFollow(bean.getId(), position);
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followListener != null) {
                    followListener.onItemClick(bean, position);
                }

            }
        });

        if (mId != null && TextUtils.equals(mId, bean.getId())) {
            holder.follow.setVisibility(View.GONE);
        }
    }

//    public void addMoreData(List<UserBean> data) {
//        if (data != null) {
//            this.data.addAll(data);
//        }
//    }

    public void refreshClickedPosition() {
        Logger.i("refreshClickedPosition = " + clickedPostion);
        if (clickedPostion > -1 && clickedPostion < data.size()) {
            UserBean bean = data.get(clickedPostion);
            bean.setFollow(SystemInfoUtils.isFollow(context, bean.getId()));
            notifyItemChanged(clickedPostion);
        }
    }

    public void refreshFollowCacheData(ArrayList<String> following_ids) {
        this.following_ids = following_ids;
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
//        void onFollowClicked(int position);

        void onAddFollow(String id, int position);

        void onCancelFollow(String id, int position);

        void onItemClick(UserBean userBean, int position);
    }
}
