package com.leyuan.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.VideoComment;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.media.CircleImageView;

import java.util.ArrayList;

public class VideoCommentAdapter extends android.widget.BaseAdapter {
    private Context context;
    private ArrayList<VideoComment> mComments = new ArrayList<>();

    public VideoCommentAdapter(Context context) {
        this.context = context;
    }

    public void freshData(ArrayList<VideoComment> comments) {
        mComments.clear();
        if (comments != null) {
            mComments.addAll(comments);
        }
        notifyDataSetChanged();
    }

    public void addData(ArrayList<VideoComment> comments) {

        if (comments != null) {
            mComments.addAll(comments);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mComments.size();
    }


    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video_comments, null);
            holder = new ViewHolder();
            holder.img_avatar = (CircleImageView) convertView.findViewById(R.id.img_avatar);
            holder.txt_user = (TextView) convertView.findViewById(R.id.txt_user);
            holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            holder.txt_time = (TextView) convertView.findViewById(R.id.txt_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoComment comment = mComments.get(position);

        GlideLoader.getInstance().displayImage(comment.getAuthor().getImgUrl(), holder.img_avatar);
        holder.txt_user.setText(""+comment.getAuthor().getName());
        holder.txt_content.setText(""+comment.getContent());
        holder.txt_time.setText(""+comment.getTime());
        return convertView;
    }

    static class ViewHolder {
        CircleImageView img_avatar;
        TextView txt_user, txt_content, txt_time;
    }

}
