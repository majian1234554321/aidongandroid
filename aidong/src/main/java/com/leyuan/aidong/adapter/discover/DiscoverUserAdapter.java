package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面 用户适配器
 * Created by song on 2016/8/31.
 */
public class DiscoverUserAdapter extends RecyclerView.Adapter<DiscoverUserAdapter.UserHolder>{
    private Context context;
    private List<UserBean> data = new ArrayList<>();

    public DiscoverUserAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<UserBean> data) {
        if(data != null) {
            this.data = data;
            while (this.data.size() > 8){
                this.data.remove(8);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_discover_user,null);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        final UserBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundAvatarImage(bean.getAvatar(), holder.avatar);
        if("0".equals(bean.getGender())){   //男
            holder.gender.setBackgroundResource(R.drawable.icon_man);
        }else {
            holder.gender.setBackgroundResource(R.drawable.icon_woman);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context,bean.getId());
            }
        });
    }

    class UserHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        ImageView gender;

        public UserHolder(View itemView) {
            super(itemView);
            avatar = (ImageView)itemView.findViewById(R.id.dv_user_cover);
            gender = (ImageView)itemView.findViewById(R.id.iv_gender);
        }
    }
}
