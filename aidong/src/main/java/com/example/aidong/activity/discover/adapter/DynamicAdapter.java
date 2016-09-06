package com.example.aidong.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.AttributeImages;
import com.example.aidong.model.Dynamic;
import com.example.aidong.model.UserCoach;
import com.example.aidong.view.SquareRelativeLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面动态适配器
 * Created by song on 2016/8/29.
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicHolder>{

    private Context context;
    private List<Dynamic> data = new ArrayList<>();
    private View.OnClickListener onAvatarClickListener;

    public DynamicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Dynamic> data) {
        this.data = data;
    }

    public void setOnAvatarClickListener(View.OnClickListener onAvatarClickListener) {
        this.onAvatarClickListener = onAvatarClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DynamicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_dynamic,null);
        return new DynamicHolder(view);
    }

    @Override
    public void onBindViewHolder(DynamicHolder holder, int position) {
        Dynamic dynamic = data.get(position);
        UserCoach user = dynamic.getPublisher();

        if (user != null) {
            holder.tvName.setText(user.getName());
            holder.dvAvatar.setTag(user);
            holder.dvAvatar.setImageURI(user.getAvatar());
            holder.dvAvatar.setOnClickListener(onAvatarClickListener);
        }

       // holder.tvTime.setText(dynamic.getPublisher());

        ArrayList<AttributeImages> images = dynamic.getImage();
        switch (images.size()){
            case 0:

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;

        }

        holder.tvContent.setText(dynamic.getContent());

    }

    class DynamicHolder extends RecyclerView.ViewHolder{

        //头部信息
        private SimpleDraweeView dvAvatar;
        private TextView tvName;
        private ImageView ivCoachFlag;
        private TextView tvTime;

        //视频
        private SquareRelativeLayout videoLayout;
        private ImageView ivVideo;
        private ImageButton ibPlay;

        //1,2,4,5张图
        private RecyclerView photoLayout;

        //3张图
        private RelativeLayout threePhotoLayout;
        private SimpleDraweeView dvThreeFirst;
        private SimpleDraweeView dvThreeSecond;
        private SimpleDraweeView dvThreeThird;

        //5张图
        private RelativeLayout fivePhotoLayout;
        private SimpleDraweeView dvFiveFirst;
        private SimpleDraweeView dvFiveSecond;
        private SimpleDraweeView dvFiveThird;
        private SimpleDraweeView dvFiveFourth;
        private SimpleDraweeView dvFiveLast;

        //内容
        private TextView tvContent;

        //点赞
        private LinearLayout likeLayout;
        private RecyclerView likesRecyclerView;

        //评论
        private LinearLayout commentLayout;
        private TextView tvCommentFirst;
        private TextView tvCommentSecond;
        private TextView tvCommentMore;

        //底部信息
        private RelativeLayout bottomLikeLayout;
        private ImageView ivLike;
        private TextView tvLikeCount;
        private RelativeLayout bottomCommentLayout;
        private ImageView ivComment;
        private TextView tvCommentCount;
        private RelativeLayout bottomShareLayout;
        private ImageView ivShare;

        public DynamicHolder(View itemView) {
            super(itemView);

            dvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.dv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivCoachFlag = (ImageView) itemView.findViewById(R.id.iv_coach_flag);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            videoLayout = (SquareRelativeLayout) itemView.findViewById(R.id.video_layout);
            ivVideo = (ImageView) itemView.findViewById(R.id.iv_video);
            ibPlay = (ImageButton) itemView.findViewById(R.id.ib_play);
            photoLayout = (RecyclerView) itemView.findViewById(R.id.photo_layout);
            threePhotoLayout = (RelativeLayout) itemView.findViewById(R.id.three_photo_layout);
            dvThreeFirst = (SimpleDraweeView) itemView.findViewById(R.id.dv_three_first);
            dvThreeSecond = (SimpleDraweeView) itemView.findViewById(R.id.dv_three_second);
            dvThreeThird = (SimpleDraweeView) itemView.findViewById(R.id.dv_three_third);
            fivePhotoLayout = (RelativeLayout) itemView.findViewById(R.id.five_photo_layout);
            dvFiveFirst = (SimpleDraweeView) itemView.findViewById(R.id.dv_five_first);
            dvFiveSecond = (SimpleDraweeView) itemView.findViewById(R.id.dv_five_second);
            dvFiveThird = (SimpleDraweeView) itemView.findViewById(R.id.dv_five_third);
            dvFiveFourth = (SimpleDraweeView) itemView.findViewById(R.id.dv_five_fourth);
            dvFiveLast = (SimpleDraweeView) itemView.findViewById(R.id.dv_five_last);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            likeLayout = (LinearLayout) itemView.findViewById(R.id.like_layout);
            likesRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_likes);
            commentLayout = (LinearLayout) itemView.findViewById(R.id.comment_layout);
            tvCommentFirst = (TextView) itemView.findViewById(R.id.tv_comment_first);
            tvCommentSecond = (TextView) itemView.findViewById(R.id.tv_comment_second);
            tvCommentMore = (TextView) itemView.findViewById(R.id.tv_comment_more);
            bottomLikeLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_like_layout);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
            bottomCommentLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_comment_layout);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            bottomShareLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_share_layout);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
        }
    }
}
