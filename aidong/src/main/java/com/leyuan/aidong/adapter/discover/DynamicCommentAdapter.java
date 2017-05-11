package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈评论适配器
 * Created by song on 2016/12/26.
 * //todo 不用RecyclerView
 */
public class DynamicCommentAdapter extends RecyclerView.Adapter<DynamicCommentAdapter.CommonHolder>{
    private static final int COMMENT = 0;
    private static final int MORE_COMMENT = 1;
    private static final int MAX_COMMENT_COUNT = 3;

    private Context context;
    private int totalCount;
    private List<CommentBean> data = new ArrayList<>();
    private OnMoreCommentClickListener onMoreCommentClickListener;


    public DynamicCommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommentBean> data,int totalCount) {
        if(data != null) {
            this.data = data;
            this.totalCount = totalCount;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(totalCount > MAX_COMMENT_COUNT){
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
    public CommonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_comment,parent,false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(CommonHolder holder, int position) {
        if(getItemViewType(position) == COMMENT) {
            CommentBean item = data.get(position);
            holder.comment.setText(getClickableSpan(item.getPublisher().getName() + "：" + item.getContent(),item));
            holder.comment.setMovementMethod(LinkMovementMethod.getInstance());
        }else {
            holder.comment.setText("查看更多" + (totalCount - MAX_COMMENT_COUNT)  +"条评论");
            holder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onMoreCommentClickListener != null){
                        onMoreCommentClickListener.onMoreCommentClick();
                    }
                }
            });
        }
    }

    class CommonHolder extends RecyclerView.ViewHolder{
        TextView comment;
        private CommonHolder(View itemView) {
            super(itemView);
            comment = (TextView) itemView.findViewById(R.id.tv_comment);
        }
    }

    private SpannableString getClickableSpan(String content, CommentBean item) {
        SpannableString spanInfo = new SpannableString(content);
        if (item != null && item.getPublisher() != null && !TextUtils.isEmpty(item.getPublisher().getName())) {
            spanInfo.setSpan(new UserClickable(item),0, item.getPublisher().getName().length() + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanInfo.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")),
                    0, item.getPublisher().getName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanInfo;
    }

    private class UserClickable extends ClickableSpan implements View.OnClickListener {
        private CommentBean bean;

        private UserClickable(CommentBean bean) {
            this.bean = bean;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);// 去除下划线
            ds.bgColor = Color.parseColor("#00000000");
        }

        @Override
        public void onClick(View v) {
            UserInfoActivity.start(context,bean.getPublisher().getId());
        }
    }

    public void setOnMoreCommentClickListener(OnMoreCommentClickListener onMoreCommentClickListener) {
        this.onMoreCommentClickListener = onMoreCommentClickListener;
    }

    public interface OnMoreCommentClickListener{
        void onMoreCommentClick();
    }
}
