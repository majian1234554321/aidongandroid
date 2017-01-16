package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈评论适配器
 * Created by song on 2016/12/26.
 */
public class DynamicCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int COMMENT = 0;
    private static final int MORE_COMMENT = 1;
    private static final int SHOW_COMMENT_COUNT = 3;

    private Context context;
    private List<DynamicBean.Comment.Item> data = new ArrayList<>();
    private List<DynamicBean.Comment.Item> originalData = new ArrayList<>();

    public DynamicCommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DynamicBean.Comment.Item> data) {
        if(data != null) {
            originalData = data;
            this.data.clear();
            this.data.addAll(data);
            while (this.data.size() > SHOW_COMMENT_COUNT) {
                this.data.remove(SHOW_COMMENT_COUNT);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(originalData.size() > SHOW_COMMENT_COUNT){
            return data.size() + 1;
        }else {
            return data.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < data.size()){
            return COMMENT;
        }else {
            return MORE_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == COMMENT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comment,parent,false);
            return new CommonHolder(view);
        }else if(viewType == MORE_COMMENT){
            View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comment_more, parent, false);
            return new MoreHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position < data.size()) {
            if(holder instanceof CommonHolder) {
                final  DynamicBean.Comment.Item bean = data.get(position);
                ((CommonHolder) holder).user.setText(bean.publisher.name + "：");
                ((CommonHolder) holder).comment.setText(bean.content);
            }
        }else {
            ((MoreHolder) holder).more.setText("查看更多" + (originalData.size() - SHOW_COMMENT_COUNT)  +"条评论");
        }
    }

    private class CommonHolder extends RecyclerView.ViewHolder{
        TextView user;
        TextView comment;
        public CommonHolder(View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.tv_name);
            comment = (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }

    private class MoreHolder extends RecyclerView.ViewHolder{
        TextView more;
        public MoreHolder(View itemView) {
            super(itemView);
            more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }
}
