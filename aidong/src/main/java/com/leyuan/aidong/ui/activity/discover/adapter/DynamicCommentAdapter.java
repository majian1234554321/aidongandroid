package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;

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
    private int totalCount;
    private List<DynamicBean.Comment.Item> useData = new ArrayList<>();
    private List<DynamicBean.Comment.Item> originalData = new ArrayList<>();
    private CommentListener commentListener;


    public DynamicCommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DynamicBean.Comment.Item> data,String totalCount) {
        this.totalCount = FormatUtil.parseInt(totalCount);
        if(data != null) {
            originalData = data;
            this.useData.clear();
            this.useData.addAll(data);
            while (this.useData.size() > SHOW_COMMENT_COUNT) {
                this.useData.remove(SHOW_COMMENT_COUNT);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(originalData.size() > SHOW_COMMENT_COUNT){
            return useData.size() + 1;
        }else {
            return useData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < useData.size()){
            return COMMENT;
        }else {
            return MORE_COMMENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.w("recyclerView","DynamicCommentAdapter onCreateViewHolder" );
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Logger.w("recyclerView","DynamicCommentAdapter onBindView" + position);
        if(position < useData.size()) {
            if(holder instanceof CommonHolder) {
                final  DynamicBean.Comment.Item bean = useData.get(position);
                ((CommonHolder) holder).user.setText(bean.publisher.name + "：");
                ((CommonHolder) holder).comment.setText(bean.content);
            }
        }else {
            ((MoreHolder) holder).more.setText("查看更多" + (totalCount - SHOW_COMMENT_COUNT)  +"条评论");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commentListener != null){
                    commentListener.onCommentClick();
                }
            }
        });
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

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public interface CommentListener{
        void onCommentClick();
    }
}
