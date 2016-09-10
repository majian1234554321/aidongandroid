package com.example.aidong.activity.mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 粉丝和关注适配器
 * Created by song on 2016/9/10.
 */
public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.UserHolder>{

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

    }

    class UserHolder extends RecyclerView.ViewHolder{

       public UserHolder(View itemView) {
           super(itemView);
       }
   }
}
