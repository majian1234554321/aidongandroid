package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.ViewHolder> {
    private LayoutInflater inflaster;
    private List<CommentBean> mComments = new ArrayList<>();

    public VideoCommentAdapter(Context context) {
        inflaster = LayoutInflater.from(context);
    }

    public void freshData(List<CommentBean> comments) {
        mComments.clear();
        if (comments != null) {
            mComments.addAll(comments);
        }
        notifyDataSetChanged();
    }

    public void addData(List<CommentBean> comments) {

        if (comments != null) {
            mComments.addAll(comments);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflaster.inflate(R.layout.item_video_comments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentBean comment = mComments.get(position);
        final String name = comment.getPublisher().getName();

        GlideLoader.getInstance().displayRoundAvatarImage(comment.getPublisher().getAvatar(), holder.img_avatar);

        holder.txt_user.setText("" + name);
        holder.txt_content.setText("" + comment.getContent());
        holder.txt_time.setText("" + comment.getCreated_at());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(name);
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_avatar;
        TextView txt_user, txt_content, txt_time;

        public ViewHolder(View convertView) {
            super(convertView);
            img_avatar = (ImageView) convertView.findViewById(R.id.img_avatar);
            txt_user = (TextView) convertView.findViewById(R.id.txt_user);
            txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onClick(String name);
    }

}
