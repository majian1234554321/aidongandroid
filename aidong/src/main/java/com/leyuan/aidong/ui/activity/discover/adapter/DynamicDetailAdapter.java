package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CommentBean;

import java.util.ArrayList;
import java.util.List;


public class DynamicDetailAdapter extends RecyclerView.Adapter<DynamicDetailAdapter.CommentHolder>{
    private Context context;
    private List<CommentBean> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public DynamicDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommentBean> data) {
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
    public void onBindViewHolder(CommentHolder holder, final int position) {
        CommentBean bean = data.get(position);
        holder.avatar.setImageURI(bean.getPublisher().getAvatar());
        holder.name.setText(bean.getPublisher().getName());
        holder.content.setText(bean.getContent());
        holder.time.setText(bean.getPublishedAt());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
