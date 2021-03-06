package com.example.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.SystemInfoUtils;

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
    private int clickedPostion = -1;
    String mId;

    public UserAdapter(Context context) {
        this.context = context;
        if (App.getInstance().isLogin()) {
            mId = App.getInstance().getUser().getId() + "";
        }
    }

    public void setData(List<UserBean> data) {
        if (App.getInstance().isLogin()) {
            mId = App.getInstance().getUser().getId() + "";
        }
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
        holder.distance.setText(bean.getDistanceFormat());

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
                clickedPostion = position;
                UserInfoActivity.start(context, bean.getId());
            }
        });

        if (mId != null && TextUtils.equals(mId, bean.getId())) {
            holder.follow.setVisibility(View.GONE);
        }
    }

    public void addMoreData(List<UserBean> data) {
        if (data != null) {
            this.data.addAll(data);
        }
    }

    public void refreshClickedPosition() {
        Logger.i("refreshClickedPosition = " + clickedPostion);
        if (clickedPostion > -1 && clickedPostion < data.size()) {
            UserBean bean = data.get(clickedPostion);
            bean.setFollow(SystemInfoUtils.isFollow(context, bean.getId()));
            notifyItemChanged(clickedPostion);
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
            gender.setVisibility(View.VISIBLE);
        }
    }

    public void setFollowListener(FollowListener followListener) {
        this.followListener = followListener;
    }

    public interface FollowListener {
        void onFollowClicked(int position);
    }
}
