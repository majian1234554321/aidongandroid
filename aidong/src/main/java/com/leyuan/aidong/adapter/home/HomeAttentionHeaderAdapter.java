package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.competition.activity.ContestHomeActivity;
import com.leyuan.aidong.ui.course.CourseCircleDetailActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.ui.home.activity.CircleListActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class HomeAttentionHeaderAdapter extends RecyclerView.Adapter<HomeAttentionHeaderAdapter.ViewHolder> {


    private final Context context;
    private List<UserBean> userList;

    public HomeAttentionHeaderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_horizontal, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position >= userList.size()) {
            GlideLoader.getInstance().displayCircleImage(R.drawable.icon_add_photo, holder.imgAvatar);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CircleListActivity.start(context, 0);

                }
            });
            holder.txtName.setText("添加关注");

            return;
        }


        final UserBean bean = userList.get(position);
        if (Constant.COURSE.equals(bean.type) || Constant.CAMPAIGN.equals(bean.type)) {
            GlideLoader.getInstance().displayCircleImage(bean.cover, holder.imgAvatar);
        } else {
            GlideLoader.getInstance().displayCircleImage(bean.getAvatar(), holder.imgAvatar);
        }

        holder.txtName.setText(bean.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.COURSE.equals(bean.type)) {
                    CourseCircleDetailActivity.start(context, bean.getId());
                } else if (Constant.CAMPAIGN.equals(bean.type)) {
                    ActivityCircleDetailActivity.start(context, bean.getId());
                } else if (Constant.COACH.equals(bean.type)) {
                    UserInfoActivity.start(context, bean.getId());
                } else if (Constant.CONTEST.equals(bean.type)) {
                    ContestHomeActivity.start(context, bean.getId());
                } else {
                    UserInfoActivity.start(context, bean.getId());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size() + 1;
    }

    public void setData(List<UserBean> userBeanList) {
        this.userList = userBeanList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView txtName;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtName = (TextView) view.findViewById(R.id.txt_name);
        }
    }
}
