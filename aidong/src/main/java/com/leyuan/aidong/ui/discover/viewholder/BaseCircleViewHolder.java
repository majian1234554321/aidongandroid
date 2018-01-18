package com.leyuan.aidong.ui.discover.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.discover.activity.CMDMessageActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.SystemInfoUtils;
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
    protected Context context;
    private LinearLayout root;
    private ImageView ivAvatar;
    private ImageView ivGender;
    private TextView tvName;
    private TextView tvTime;
    private ImageView ivCoachFlag;
    private ImageView ivFollow;
    private LinearLayout layoutCourseOrActivity;
    private ImageView imgCover;
    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtTime;
    private TextView txtLocation;
    private TextView txtParse;
    private TextView txtComment;



    protected IDynamicCallback callback;
    private boolean showCommentLayout = true;
    private boolean showFollowButton = false;

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

        layoutCourseOrActivity = (LinearLayout) itemView.findViewById(R.id.layout_course_or_activity);
        imgCover = (ImageView) itemView.findViewById(R.id.img_cover);
        txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        txtDesc = (TextView) itemView.findViewById(R.id.txt_desc);
        txtTime = (TextView) itemView.findViewById(R.id.txt_time);

        txtLocation = (TextView) itemView.findViewById(R.id.txt_location);
        txtParse = (TextView) itemView.findViewById(R.id.txt_parse);
        txtComment = (TextView) itemView.findViewById(R.id.txt_comment);
    }


    @Override
    public void onBindData(final DynamicBean dynamic, final int position) {
        if (position == 0  && context instanceof MainActivity && App.getInstance().isLogin() && App.getInstance().getCMDCirleDynamicBean()
                != null && !App.getInstance().getCMDCirleDynamicBean().isEmpty()) {

            layoutCmdMessage.setVisibility(View.VISIBLE);
            layoutCmdMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CMDMessageActivity.start(context);
                }
            });

            ArrayList<CircleDynamicBean> beanList = App.getInstance().getCMDCirleDynamicBean();
            CircleDynamicBean bean = beanList.get(beanList.size() - 1);

            GlideLoader.getInstance().displayRoundAvatarImage(bean.getFromAvatar(), imgCMDAvatar);
            txtCmdMessageNum.setText(beanList.size() + "条新消息");
        } else {
            layoutCmdMessage.setVisibility(View.GONE);
        }
        if (dynamic.publisher != null) {
            tvName.setText(dynamic.publisher.getName());
            GlideLoader.getInstance().displayRoundAvatarImage(dynamic.publisher.getAvatar(), ivAvatar);
            tvTime.setText(Utils.getData(dynamic.published_at));
            ivCoachFlag.setVisibility("Coach".equals(dynamic.publisher.getUser_type())
                    ? View.VISIBLE : View.GONE);
            ivGender.setBackgroundResource("0".equals(dynamic.publisher.getGender())
                    ? R.drawable.icon_man : R.drawable.icon_woman);
            if (showFollowButton) {
                ivFollow.setVisibility(View.VISIBLE);
                boolean isFollow = SystemInfoUtils.isFollow(context, dynamic.publisher.getId());
                ivFollow.setBackgroundResource(isFollow ? R.drawable.icon_following : R.drawable.icon_follow);
            } else {
                ivFollow.setVisibility(View.GONE);
            }
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
                    callback.onAvatarClick(dynamic.publisher.getId());
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
}
