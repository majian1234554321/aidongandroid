package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;

import java.util.ArrayList;
import java.util.List;


public class DynamicDetailAdapter extends RecyclerView.Adapter<DynamicDetailAdapter.CommentHolder>{
    private Context context;
    private List<DynamicBean.Comment.Item> data = new ArrayList<>();

    public DynamicDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DynamicBean.Comment.Item> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_comment,parent,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        DynamicBean.Comment.Item item = data.get(position);
        holder.avatar.setImageURI(item.publisher.avatar);
        holder.name.setText(item.publisher.name);
        holder.content.setText(item.content);
        holder.time.setText(item.publisher.published_at);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView avatar;
        TextView name;
        TextView content;
        TextView time;
        public CommentHolder(View itemView) {
            super(itemView);
            avatar = (SimpleDraweeView) itemView.findViewById(R.id.dv_avatar);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
