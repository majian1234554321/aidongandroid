package com.example.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.competition.activity.ContestHomeActivity;
import com.example.aidong .ui.course.CourseCircleDetailActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;

import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class PersonHorizontalAdapter extends RecyclerView.Adapter<PersonHorizontalAdapter.ViewHolder> {


    private final Context context;
    private List<UserBean> userList;

    public PersonHorizontalAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_horizontal, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserBean bean = userList.get(position);
        if (Constant.COURSE.equals(bean.type) || Constant.CAMPAIGN.equals(bean.type)) {
            GlideLoader.getInstance().displayCircleImage(bean.image, holder.imgAvatar);
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
                }  else if (Constant.CONTEST.equals(bean.type)) {
                    ContestHomeActivity.start(context, bean.getId());
                }else {
                    UserInfoActivity.start(context, bean.getId());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
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
