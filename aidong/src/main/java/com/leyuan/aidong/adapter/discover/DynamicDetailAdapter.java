package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Utils;

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
        }
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_comment,parent,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, final int position) {
        final CommentBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundAvatarImage(bean.getPublisher().getAvatar(), holder.avatar);
        holder.name.setText(bean.getPublisher().getName());
        holder.content.setText(bean.getContent());
        holder.time.setText(Utils.getData(bean.getPublishedAt()));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context,bean.getPublisher().getId());
            }
        });

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
        ImageView avatar;
        TextView name;
        TextView content;
        TextView time;
        public CommentHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
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
