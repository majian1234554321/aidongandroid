package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Utils;

import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class PersonAttentionAdapter extends RecyclerView.Adapter<PersonAttentionAdapter.ViewHolder> {


    private final Context context;
    private List<UserBean> users;

    public PersonAttentionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_attention, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0) {
            RecyclerView.LayoutParams fp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            fp.leftMargin = Utils.dip2px(context, 15);
            holder.itemView.setLayoutParams(fp);
        }

        final UserBean userBean = users.get(position);
        GlideLoader.getInstance().displayCircleImage(userBean.getAvatar(), holder.imgAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // UserInfoActivity.start(context, userBean.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (users == null) return 0;
        return users.size() > 5 ? 5 : users.size();
    }

    public void setData(List<UserBean> applicant) {
        this.users = applicant;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        }
    }
}
