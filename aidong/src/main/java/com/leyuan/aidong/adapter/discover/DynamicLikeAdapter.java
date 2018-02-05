package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.discover.activity.DynamicParseUserActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈点赞用户
 * Created by song on 2016/12/26.
 */
public class DynamicLikeAdapter extends RecyclerView.Adapter<DynamicLikeAdapter.UserHolder> {
    private static final int MAM_USER_COUNT = 10;
    private static final int TYPE_USER = 1;
    private static final int TYPE_MORE = 2;

    private Context context;
    private int totalCount;
    private List<UserBean> data = new ArrayList<>();
    private String dynamicId;

    public DynamicLikeAdapter(Context context, String dynamicId) {
        this.context = context;
        this.dynamicId = dynamicId;

    }

    public void setData(List<UserBean> data, int totalCount) {
        this.data = data;
        this.totalCount = totalCount;
        while (this.data.size() > MAM_USER_COUNT) {
            this.data.remove(MAM_USER_COUNT);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (totalCount <= MAM_USER_COUNT) {
            return TYPE_USER;
        } else {
            if (position == data.size() - 1) {
                return TYPE_MORE;
            } else {
                return TYPE_USER;
            }
        }
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        if (getItemViewType(position) == TYPE_USER) {
            final UserBean bean = data.get(position);
            GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.avatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfoActivity.start(context, bean.getId());
                }
            });
        } else {
            GlideLoader.getInstance().displayCircleImage(R.drawable.icon_more_user, holder.avatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DynamicParseUserActivity.start(context, dynamicId);
                }
            });
        }
    }

    class UserHolder extends RecyclerView.ViewHolder {
        ImageView avatar;

        public UserHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
            itemView.getLayoutParams().width =
                    (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context, 49)) / 10;
            itemView.getLayoutParams().height =
                    (ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context, 49)) / 10;
        }
    }
}
