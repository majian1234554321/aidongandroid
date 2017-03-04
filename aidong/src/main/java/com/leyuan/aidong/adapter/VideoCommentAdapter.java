package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.media.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class VideoCommentAdapter extends RecyclerView.Adapter<VideoCommentAdapter.ViewHolder> {
    private Context context;
    private List<CommentBean> mComments = new ArrayList<>();

    public VideoCommentAdapter(Context context) {
        this.context = context;
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
        View view = View.inflate(context, R.layout.item_video_comments, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentBean comment = mComments.get(position);

        GlideLoader.getInstance().displayImage(comment.getPublisher().getAvatar(), holder.img_avatar);
        holder.txt_user.setText("" + comment.getPublisher().getName());
        holder.txt_content.setText("" + comment.getContent());
        holder.txt_time.setText("" + comment.getContent());
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
        CircleImageView img_avatar;
        TextView txt_user, txt_content, txt_time;

        public ViewHolder(View convertView) {
            super(convertView);
            img_avatar = (CircleImageView) convertView.findViewById(R.id.img_avatar);
            txt_user = (TextView) convertView.findViewById(R.id.txt_user);
            txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            txt_time = (TextView) convertView.findViewById(R.id.txt_time);
        }
    }

}
