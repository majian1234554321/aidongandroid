package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.adapter.discover.DynamicLikeAdapter;
import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.competition.activity.ContestHomeActivity;
import com.leyuan.aidong.ui.course.CourseCircleDetailActivity;
import com.leyuan.aidong.ui.discover.activity.CMDMessageActivity;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailByIdActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.ui.home.activity.MapActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.Utils;

import java.util.ArrayList;


/**
 * 爱动圈动态
 *
 * @author song
 */
public abstract class BaseCircleViewHolder extends BaseRecyclerViewHolder<DynamicBean> implements IChildViewHolder<DynamicBean> {
    private final FrameLayout layoutCmdMessage;
    private final ImageView imgCMDAvatar;
    private final TextView txtCmdMessageNum;
    private final LinearLayout layout_difficulty_star;
    protected Context context;
    private LinearLayout root;
    private ImageView ivAvatar;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvTime;
    private ImageView ivCoachFlag;
    private ImageView ivFollow;
    private LinearLayout layoutCourseOrActivity;
    private ImageView imgCover, img_parse;
    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtTime, tv_content;
    private TextView txtLocation;
    private TextView txtParse;
    private TextView txtComment;
    private RecyclerView likesRecyclerView;
    private LinearLayout likeLayout, layout_parse;


    protected IDynamicCallback callback;
    private boolean showCommentLayout = true;
    private boolean showFollowButton = false;
    private boolean showCMDMessageLayout = true;
    private ArrayList<ImageView> starList = new ArrayList<>();


    public BaseCircleViewHolder(Context context, ViewGroup parent, int layoutResId) {
        super(context, parent, layoutResId);
        onFindChildView(itemView);
        this.context = context;

        layoutCmdMessage = (FrameLayout) itemView.findViewById(R.id.layout_cmd_message);
        imgCMDAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
        txtCmdMessageNum = (TextView) itemView.findViewById(R.id.txt_cmd_message_num);

        root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        ivAvatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
        ivGender = (ImageView) itemView.findViewById(R.id.iv_gender);
        ivCoachFlag = (ImageView) itemView.findViewById(R.id.iv_coach_flag);
        ivFollow = (ImageView) itemView.findViewById(R.id.iv_follow);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tv_content = (TextView) itemView.findViewById(R.id.tv_content);

        layoutCourseOrActivity = (LinearLayout) itemView.findViewById(R.id.layout_course_or_activity);
        imgCover = (ImageView) itemView.findViewById(R.id.img_cover);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
        txtTime = (TextView) itemView.findViewById(R.id.txt_time);

        layout_difficulty_star = (LinearLayout) itemView.findViewById(R.id.layout_difficulty_star);
        starList.add((ImageView) itemView.findViewById(R.id.img_star_first));
        starList.add((ImageView) itemView.findViewById(R.id.img_star_second));
        starList.add((ImageView) itemView.findViewById(R.id.img_star_three));
        starList.add((ImageView) itemView.findViewById(R.id.img_star_four));
        starList.add((ImageView) itemView.findViewById(R.id.img_star_five));

        txtLocation = (TextView) itemView.findViewById(R.id.txt_location);
        txtParse = (TextView) itemView.findViewById(R.id.txt_parse);
        img_parse = (ImageView) itemView.findViewById(R.id.img_parse);
        txtComment = (TextView) itemView.findViewById(R.id.txt_comment);

        likeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
        layout_parse = (LinearLayout) itemView.findViewById(R.id.layout_parse);

        likesRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_likes);
    }


    @Override
    public void onBindData(final DynamicBean dynamic, final int position) {
        if (position == 0 && context instanceof MainActivity && App.getInstance().isLogin() && App.getInstance().getCMDCirleDynamicBean()
                != null && !App.getInstance().getCMDCirleDynamicBean().isEmpty() && showCMDMessageLayout ) {

            layoutCmdMessage.setVisibility(View.VISIBLE);
            layoutCmdMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CMDMessageActivity.start(context);

                }
            });

            ArrayList<CircleDynamicBean> beanList = App.getInstance().getCMDCirleDynamicBean();
            CircleDynamicBean bean = beanList.get(beanList.size() - 1);

            GlideLoader.getInstance().displayCircleImage(bean.getFromAvatar(), imgCMDAvatar);
            txtCmdMessageNum.setText(beanList.size() + "条新消息");
        } else {
            layoutCmdMessage.setVisibility(View.GONE);
        }

        if (dynamic.publisher != null) {
            tvName.setText(dynamic.publisher.getName());
            GlideLoader.getInstance().displayCircleImage(dynamic.publisher.getAvatar(), ivAvatar);
            tvTime.setText(Utils.getData(dynamic.published_at));
            ivCoachFlag.setVisibility("Coach".equals(dynamic.publisher.getUser_type())
                    ? View.VISIBLE : View.GONE);
            ivGender.setBackgroundResource("0".equals(dynamic.publisher.getGender())
                    ? R.drawable.icon_man : R.drawable.icon_woman);
            if (showFollowButton) {
                ivFollow.setVisibility(View.VISIBLE);
//                boolean isFollow = SystemInfoUtils.isFollow(context, dynamic.publisher.getId());
                ivFollow.setBackgroundResource(dynamic.publisher.followed ? R.drawable.icon_followed : R.drawable.icon_follow);
            } else {
                ivFollow.setVisibility(View.GONE);
            }
        }

        if (dynamic.extras != null && dynamic.extras.length > 0) {
            SpannableStringBuilder highlightText = StringUtils.highlight(context,dynamic.content, dynamic.extras, "#EA2D2D",1);

            tv_content.setText(highlightText);
            tv_content.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            tv_content.setText(dynamic.content);
        }


        if (dynamic.like != null && dynamic.like.counter > 0 && ( context instanceof DynamicDetailByIdActivity)) {
            likeLayout.setVisibility(View.VISIBLE);
            likesRecyclerView.setLayoutManager(new LinearLayoutManager
                    (context, LinearLayoutManager.HORIZONTAL, false));
            DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context, dynamic.id);
            likeAdapter.setData(dynamic.like.item, dynamic.like.counter);
            likesRecyclerView.setAdapter(likeAdapter);
        } else {
            likeLayout.setVisibility(View.GONE);
        }

        onBindDataToChildView(dynamic, position, dynamic.getDynamicType());

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onBackgroundClick(position);
                }
            }
        });

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onAvatarClick(dynamic.publisher.getId(),dynamic.publisher.getUserTypeByUserType());
                }
            }
        });

        ivFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onFollowClick(dynamic.publisher.getId());
                }
            }
        });

        //关联的课程活动圈子
        if (dynamic.type != null && dynamic.related != null) {
            layoutCourseOrActivity.setVisibility(View.VISIBLE);

            GlideLoader.getInstance().displayImage(dynamic.related.cover, imgCover);
            txtTitle.setText(dynamic.related.name);
            if (Constant.COURSE.equals(dynamic.type)) {
                //类型是课程
                txtTime.setVisibility(View.GONE);
                layout_difficulty_star.setVisibility(View.VISIBLE);
                txtDesc.setText(dynamic.related.getTagString());
                for (int i = 0; i < 5; i++) {
                    if (i < dynamic.related.strength) {
                        starList.get(i).setVisibility(View.VISIBLE);
                    } else {
                        starList.get(i).setVisibility(View.GONE);
                    }
                }

            } else {
                txtTime.setVisibility(View.VISIBLE);
                layout_difficulty_star.setVisibility(View.GONE);
                txtDesc.setText(dynamic.related.slogan);
                txtTime.setText(dynamic.related.start);
            }

        } else {
            layoutCourseOrActivity.setVisibility(View.GONE);
        }
        //位置，赞，评论
        if (dynamic.postion != null && ! TextUtils.isEmpty(dynamic.postion.position_name)) {
            txtLocation.setVisibility(View.VISIBLE);
            txtLocation.setText(dynamic.postion.position_name);
        }else {
            txtLocation.setVisibility(View.GONE);
        }

        img_parse.setBackgroundResource(isLike(dynamic)
                ? R.drawable.icon_parsed : R.drawable.icon_parse);
        txtParse.setText(String.valueOf(dynamic.like.counter));
        txtComment.setText(String.valueOf(dynamic.comment.count));
        layout_parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onLikeClick(position, dynamic.id, isLike(dynamic));
                }
            }
        });

        txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCommentClick(dynamic, position);
                }
            }
        });

        layoutCourseOrActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamic.related == null) return;
                if (Constant.CAMPAIGN.equals(dynamic.type)) {
                    ActivityCircleDetailActivity.start(context, dynamic.related.getId());
                } else if (Constant.COURSE.equals(dynamic.type)) {
                    CourseCircleDetailActivity.start(context, dynamic.related.getId());
                } else if (Constant.CONTEST.equals(dynamic.type)) {
                    ContestHomeActivity.start(context, dynamic.related);
                }
            }
        });

        txtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamic.postion != null) {
                    MapActivity.start(context, "", "", dynamic.postion.position_name, dynamic.postion.getLat(), dynamic.postion.getLng());
                } else {
                    ToastGlobal.showShortConsecutive("地址错误");
                }

            }
        });


    }

    public void setCallback(IDynamicCallback callback) {
        this.callback = callback;
    }

    public void showLikeAndCommentLayout(boolean show) {
        this.showCommentLayout = show;
    }


    public void setShowFollowButton(boolean show) {
        this.showFollowButton = show;
    }

    public void setShowCMDMessageLayout(boolean show) {
        this.showCMDMessageLayout = show;
    }

    private boolean isLike(DynamicBean dynamic) {
        if (!App.mInstance.isLogin()) {
            return false;
        }
        if (dynamic.like.item.isEmpty()) {
            return false;
        }
        for (UserBean item : dynamic.like.item) {
            if (!TextUtils.isEmpty(item.getId()) &&
                    item.getId().equals(String.valueOf(App.mInstance.getUser().getId()))) {
                return true;
            }
        }
        return false;
    }


//    private SpannableStringBuilder highlight(String text, UserBean[] users,
//                                             String color) {
//        SpannableStringBuilder spannableString = new SpannableStringBuilder(text);
//
//
//        for (final UserBean user : users) {
//            Pattern pattern = Pattern.compile(user.getName().replaceAll("\\*",""));
//            Matcher matcher = pattern.matcher(text);
//
////            String quote = Pattern.quote(user.getName());
////            return Pattern.matches(quote, text);
//
//            while (matcher.find()) {
//                ClickableSpan clickableSpan = new ClickableSpan() {
//                    @Override
//                    public void onClick(View view) {
//
//                        UserInfoActivity.start(context, user.user_id);
//                    }
//                };
//
//                spannableString.setSpan(clickableSpan, matcher.start() - 1, matcher.end(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
//                spannableString.setSpan(span, matcher.start() - 1, matcher.end(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//        }
//        return spannableString;
//    }

}
