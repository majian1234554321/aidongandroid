//package com.leyuan.aidong.ui.discover.viewholder;
//
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.example.aidong.R;
//import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
//import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
//import com.leyuan.aidong.adapter.discover.DynamicCommentAdapter;
//import com.leyuan.aidong.adapter.discover.DynamicLikeAdapter;
//import com.leyuan.aidong.entity.CircleDynamicBean;
//import com.leyuan.aidong.entity.CommentBean;
//import com.leyuan.aidong.entity.DynamicBean;
//import com.leyuan.aidong.entity.UserBean;
//import com.leyuan.aidong.ui.App;
//import com.leyuan.aidong.ui.MainActivity;
//import com.leyuan.aidong.ui.discover.activity.CMDMessageActivity;
//import com.leyuan.aidong.utils.GlideLoader;
//import com.leyuan.aidong.utils.SystemInfoUtils;
//import com.leyuan.aidong.utils.Utils;
//
//import java.util.ArrayList;
//
//
///**
// * 爱动圈动态
// *
// * @author song
// */
//public abstract class BaseCircleViewHolderOld extends BaseRecyclerViewHolder<DynamicBean> implements IChildViewHolder<DynamicBean> {
//    private final FrameLayout layoutCmdMessage;
//    private final ImageView imgCMDAvatar;
//    private final TextView txtCmdMessageNum;
//    protected Context context;
//    private LinearLayout root;
//    private ImageView ivAvatar;
//    private ImageView ivGender;
//    private TextView tvName;
//    private TextView tvTime;
//    private ImageView ivCoachFlag;
//    private ImageView ivFollow;
//
//    private TextView tvContent;
//    private LinearLayout likeLayout;
//    private LinearLayout commentLayout;
//    private RecyclerView likesRecyclerView;
//    private RecyclerView commentRecyclerView;
//    private ImageView ivLike;
//    private TextView tvLikeCount;
//    private TextView tvCommentCount;
//    private RelativeLayout bottomLikeLayout;
//    private RelativeLayout bottomCommentLayout;
//    private RelativeLayout bottomShareLayout;
//
//    protected IDynamicCallback callback;
//    private boolean showCommentLayout = true;
//    private boolean showFollowButton = false;
//
//    public BaseCircleViewHolderOld(Context context, ViewGroup parent, int layoutResId) {
//        super(context, parent, layoutResId);
//        onFindChildView(itemView);
//        this.context = context;
//        layoutCmdMessage = (FrameLayout) itemView.findViewById(R.id.layout_cmd_message);
//        imgCMDAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
//        txtCmdMessageNum = (TextView) itemView.findViewById(R.id.txt_cmd_message_num);
//
//        root = (LinearLayout) itemView.findViewById(R.id.ll_root);
//        ivAvatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
//        ivGender = (ImageView) itemView.findViewById(R.id.iv_gender);
//        ivCoachFlag = (ImageView) itemView.findViewById(R.id.iv_coach_flag);
//        ivFollow = (ImageView) itemView.findViewById(R.id.iv_follow);
//        tvName = (TextView) itemView.findViewById(R.id.tv_name);
//        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
//        tvContent = (TextView) itemView.findViewById(R.id.tv_content);
//        likeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
//        likesRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_likes);
//        commentLayout = (LinearLayout) itemView.findViewById(R.id.ll_comment_layout);
//        commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_comment);
//        ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
//        tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
//        tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
//        bottomCommentLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_comment_layout);
//        bottomLikeLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_like_layout);
//        bottomShareLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_share_layout);
//    }
//
//
//    @Override
//    public void onBindData(final DynamicBean dynamic, final int position) {
//        if (position == 0  && context instanceof MainActivity && App.getInstance().isLogin() && App.getInstance().getCMDCirleDynamicBean()
//                != null && !App.getInstance().getCMDCirleDynamicBean().isEmpty()) {
//
//            layoutCmdMessage.setVisibility(View.VISIBLE);
//            layoutCmdMessage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CMDMessageActivity.start(context);
//                }
//            });
//
//            ArrayList<CircleDynamicBean> beanList = App.getInstance().getCMDCirleDynamicBean();
//            CircleDynamicBean bean = beanList.get(beanList.size() - 1);
//
//            GlideLoader.getInstance().displayRoundAvatarImage(bean.getFromAvatar(), imgCMDAvatar);
//            txtCmdMessageNum.setText(beanList.size() + "条新消息");
//        } else {
//            layoutCmdMessage.setVisibility(View.GONE);
//        }
//        if (dynamic.publisher != null) {
//            tvName.setText(dynamic.publisher.getName());
//            GlideLoader.getInstance().displayRoundAvatarImage(dynamic.publisher.getAvatar(), ivAvatar);
//            tvTime.setText(Utils.getData(dynamic.published_at));
//            ivCoachFlag.setVisibility("Coach".equals(dynamic.publisher.getUser_type())
//                    ? View.VISIBLE : View.GONE);
//            ivGender.setBackgroundResource("0".equals(dynamic.publisher.getGender())
//                    ? R.drawable.icon_man : R.drawable.icon_woman);
//            if (showFollowButton) {
//                ivFollow.setVisibility(View.VISIBLE);
//                boolean isFollow = SystemInfoUtils.isFollow(context, dynamic.publisher.getId());
//                ivFollow.setBackgroundResource(isFollow ? R.drawable.icon_followed : R.drawable.icon_follow);
//            } else {
//                ivFollow.setVisibility(View.GONE);
//            }
//        }
//
//        if (TextUtils.isEmpty(dynamic.content)) {
//            tvContent.setVisibility(View.GONE);
//        } else {
//            tvContent.setText(dynamic.content);
//            tvContent.setVisibility(View.VISIBLE);
//        }
//
//        if (dynamic.like.counter > 0) {
//            likeLayout.setVisibility(View.VISIBLE);
//            likesRecyclerView.setLayoutManager(new LinearLayoutManager
//                    (context, LinearLayoutManager.HORIZONTAL, false));
//            DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context, dynamic.id);
//            likeAdapter.setData(dynamic.like.item, dynamic.like.counter);
//            likesRecyclerView.setAdapter(likeAdapter);
//        } else {
//            likeLayout.setVisibility(View.GONE);
//        }
//
//        if (showCommentLayout) {
//            if (dynamic.comment.count > 0) {
//                commentLayout.setVisibility(View.VISIBLE);
//                DynamicCommentAdapter commonAdapter = new DynamicCommentAdapter(context);
//                commentRecyclerView.setAdapter(commonAdapter);
//                commonAdapter.setData(dynamic.comment.item, dynamic.comment.count);
//                commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//                commonAdapter.setOnMoreCommentClickListener(new DynamicCommentAdapter.OnMoreCommentClickListener() {
//                    @Override
//                    public void onMoreCommentClick() {
//                        if (callback != null) {
//                            callback.onCommentClick(dynamic, position);
//                        }
//                    }
//
//                    @Override
//                    public void onCommentClick(CommentBean item) {
//                        if (callback != null) {
//                            callback.onCommentListClick(dynamic, position,item);
//                        }
//                    }
//                });
//            } else {
//                commentLayout.setVisibility(View.GONE);
//            }
//        } else {
//            commentLayout.setVisibility(View.GONE);
//        }
//
//        ivLike.setBackgroundResource(isLike(dynamic)
//                ? R.drawable.icon_liked : R.drawable.btn_praise_normal);
//        tvLikeCount.setText(String.valueOf(dynamic.like.counter));
//        tvCommentCount.setText(String.valueOf(dynamic.comment.count));
//        onBindDataToChildView(dynamic, position, dynamic.getDynamicType());
//
//        root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onBackgroundClick(position);
//                }
//            }
//        });
//
//        ivAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onAvatarClick(dynamic.publisher.getId(), dynamic.publisher.getUserTypeByUserType());
//                }
//            }
//        });
//
//        ivFollow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onFollowClick(dynamic.publisher.getId());
//                }
//            }
//        });
//
//        bottomLikeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onLikeClick(position, dynamic.id, isLike(dynamic));
////                    if (App.getInstance().isLogin() && !TextUtils.equals(App.getInstance().getUser().getId() + "", dynamic.publisher.getId())
////                            && isLike(dynamic)) {
////                        CMDMessageManager.sendCMDMessage(dynamic.publisher.getId(), App.getInstance().getUser().getAvatar(),
////                                App.getInstance().getUser().getName(), dynamic.id, null, dynamic.getUnifromCover(), 1, null,
////                                dynamic.getDynamicTypeInteger(), null);
////
////                    }
////                    callback.onLikeClick(dynamic);
//                }
//            }
//        });
//
//        bottomCommentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onCommentClick(dynamic, position);
//                }
//            }
//        });
//
//        bottomShareLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null) {
//                    callback.onShareClick(dynamic);
//                }
//            }
//        });
//    }
//
//    public void setCallback(IDynamicCallback callback) {
//        this.callback = callback;
//    }
//
//    public void showLikeAndCommentLayout(boolean show) {
//        this.showCommentLayout = show;
//    }
//
//
//    public void setShowFollowButton(boolean show) {
//        this.showFollowButton = show;
//    }
//
//    private boolean isLike(DynamicBean dynamic) {
//        if (!App.mInstance.isLogin()) {
//            return false;
//        }
//        if (dynamic.like.item.isEmpty()) {
//            return false;
//        }
//        for (UserBean item : dynamic.like.item) {
//            if (!TextUtils.isEmpty(item.getId()) &&
//                    item.getId().equals(String.valueOf(App.mInstance.getUser().getId()))) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
