package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.adapter.discover.DynamicCommentAdapter;
import com.leyuan.aidong.adapter.discover.DynamicLikeAdapter;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.FormatUtil;

import static com.leyuan.aidong.ui.App.context;
import static com.leyuan.aidong.utils.DateUtils.yyyyMMddHHmmss;


/**
 * 爱动圈动态
 */
public abstract class BaseCircleViewHolder extends BaseRecyclerViewHolder<DynamicBean> implements IViewHolder<DynamicBean> {
    private LinearLayout root;
    private SimpleDraweeView dvAvatar;
    private TextView tvName;
    private TextView tvTime;

    private TextView tvContent;
    private RelativeLayout likeLayout;
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

    protected IDynamicCallback callback;
    private boolean showLikeAndCommentLayout = true;

    public BaseCircleViewHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        dvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.dv_avatar);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        likeLayout = (RelativeLayout) itemView.findViewById(R.id.like_layout);
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
        onFindChildView(itemView);
    }


    @Override
    public void onBindData(final DynamicBean dynamic, final int position) {
        if (dynamic.publisher != null) {
            tvName.setText(dynamic.publisher.name);
            dvAvatar.setTag(dynamic.publisher);
            dvAvatar.setImageURI(dynamic.publisher.avatar);
            tvTime.setText(DateUtils.translateDate(DateUtils.parseDate
                    (dynamic.published_at, yyyyMMddHHmmss).getTime(), System.currentTimeMillis()));
        }

        tvContent.setText(dynamic.content);
        if(showLikeAndCommentLayout) {
            if (FormatUtil.parseInt(dynamic.like.counter) > 0) {
                likeLayout.setVisibility(View.VISIBLE);
                likesRecyclerView.setLayoutManager(new LinearLayoutManager
                        (context, LinearLayoutManager.HORIZONTAL, false));
                DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context);
                likeAdapter.setData(dynamic.like.item);
                likesRecyclerView.setAdapter(likeAdapter);
            } else {
                likeLayout.setVisibility(View.GONE);
            }
            if (FormatUtil.parseInt(dynamic.comment.count) > 0) {
                commentLayout.setVisibility(View.VISIBLE);
                DynamicCommentAdapter commonAdapter = new DynamicCommentAdapter(context);
                commentRecyclerView.setAdapter(commonAdapter);
                commonAdapter.setData(dynamic.comment.item, dynamic.comment.count);
                commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                commentLayout.setVisibility(View.GONE);
            }
        }else {
            likeLayout.setVisibility(View.GONE);
            commentLayout.setVisibility(View.GONE);
        }

        tvLikeCount.setText(dynamic.like.counter);
        tvCommentCount.setText(dynamic.comment.count);
        onBindDataToChildView(dynamic, position, getViewType());


        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onBackgroundClick(dynamic);
                }
            }
        });

        dvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onAvatarClick(dynamic.publisher.publisher_id);
                }
            }
        });

        boolean isLiked = false;
        for (DynamicBean.LikeUser.Item item : dynamic.like.item) {
            if (item.publisher_id.equals(String.valueOf(App.mInstance.getUser().getId()))) {
                isLiked = true;
                break;
            }
        }
        final boolean like = isLiked;
        bottomLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onLikeClick(position, dynamic.id, like);
                }
            }
        });

        bottomCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCommentClick();
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
}
