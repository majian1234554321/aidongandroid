package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.adapter.discover.DynamicCommentAdapter;
import com.leyuan.aidong.adapter.discover.DynamicLikeAdapter;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.GlideLoader;


/**
 * 爱动圈动态
 * @author song
 */
public abstract class BaseCircleViewHolder extends BaseRecyclerViewHolder<DynamicBean> implements IViewHolder<DynamicBean> {
    protected Context context;
    private LinearLayout root;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvTime;

    private TextView tvContent;
    private LinearLayout likeLayout;
    private LinearLayout commentLayout;
    private RecyclerView likesRecyclerView;
    private RecyclerView commentRecyclerView;
    private ImageView ivLike;
    private ImageView ivComment;
    private ImageView ivShare;
    private TextView tvLikeCount;
    private TextView tvCommentCount;
    private RelativeLayout bottomLikeLayout;
    private RelativeLayout bottomCommentLayout;
    private RelativeLayout bottomShareLayout;
    private View line;

    protected IDynamicCallback callback;
    private boolean showLikeAndCommentLayout = true;

    public BaseCircleViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        ivAvatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        likeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
        likesRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_likes);
        commentLayout = (LinearLayout) itemView.findViewById(R.id.ll_comment_layout);
        commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_comment);
        ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
        ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
        ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
        tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
        tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
        bottomCommentLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_comment_layout);
        bottomLikeLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_like_layout);
        bottomShareLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_share_layout);
        line = itemView.findViewById(R.id.view_line);
        onFindChildView(itemView);
    }


    @Override
    public void onBindData(final DynamicBean dynamic, final int position) {
        if (dynamic.publisher != null) {
            tvName.setText(dynamic.publisher.name);
            GlideLoader.getInstance().displayCircleImage(dynamic.publisher.avatar, ivAvatar);
            tvTime.setText(dynamic.published_at);
        }
        tvContent.setText(dynamic.content);

        if(showLikeAndCommentLayout) {
            if (dynamic.like.counter > 0) {
                likeLayout.setVisibility(View.VISIBLE);
                likesRecyclerView.setLayoutManager(new LinearLayoutManager
                        (context, LinearLayoutManager.HORIZONTAL, false));
                DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context);
                likeAdapter.setData(dynamic.like.item,dynamic.like.counter);
                likesRecyclerView.setAdapter(likeAdapter);
            } else {
                likeLayout.setVisibility(View.GONE);
            }
            if (dynamic.comment.count > 0) {
                commentLayout.setVisibility(View.VISIBLE);
                DynamicCommentAdapter commonAdapter = new DynamicCommentAdapter(context);
                commentRecyclerView.setAdapter(commonAdapter);
                commonAdapter.setData(dynamic.comment.item,dynamic.comment.count);
                commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                commentLayout.setVisibility(View.GONE);
            }
            line.setVisibility(View.VISIBLE);
        }else {
            likeLayout.setVisibility(View.GONE);
            commentLayout.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        ivLike.setBackgroundResource(isLike(dynamic)
                ? R.drawable.icon_liked : R.drawable.btn_praise_normal);
        tvLikeCount.setText(String.valueOf(dynamic.like.counter));
        tvCommentCount.setText(String.valueOf(dynamic.comment.count));
        onBindDataToChildView(dynamic, position, dynamic.getDynamicType());

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onBackgroundClick(dynamic);
                }
            }
        });

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onAvatarClick(dynamic.publisher.id);
                }
            }
        });

        bottomLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onLikeClick(position, dynamic.id, isLike(dynamic));
                }
            }
        });

        bottomCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCommentClick(dynamic);
                }
            }
        });

        bottomShareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onShareClick();
                }
            }
        });
    }

    public void setCallback(IDynamicCallback callback) {
        this.callback = callback;
    }

    public void showLikeAndCommentLayout(boolean show){
        this.showLikeAndCommentLayout = show;
    }


    private boolean isLike(DynamicBean dynamic){
        if(!App.mInstance.isLogin()){
            return false;
        }
        if(dynamic.like.item.isEmpty()){
            return false;
        }
        for (DynamicBean.LikeUser.Item item : dynamic.like.item) {
            if (!TextUtils.isEmpty(item.id) &&
                    item.id.equals(String.valueOf(App.mInstance.getUser().getId()))) {
                return true;
            }
        }
        return false;
    }
}
