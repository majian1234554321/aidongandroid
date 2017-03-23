package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.SquareRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.DateUtils.yyyyMMddHHmmss;

/**
 * 发现界面动态适配器
 * Created by song on 2016/8/29.
 */
@Deprecated
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicHolder>  {
    private Context context;
    private int counter;
    private List<DynamicBean> data = new ArrayList<>();
    private OnHandleDynamicListener handleDynamicListener;

    public DynamicAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DynamicBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DynamicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.w("recyclerView","onCreateViewHolder" +  counter++);
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic,parent,false);
        return new DynamicHolder(view);
    }

    @Override
    public void onBindViewHolder(final DynamicHolder holder, final int position) {
        Logger.w("recyclerView","onBindView" + position);
        final DynamicBean dynamic = data.get(position);
        DynamicBean.Publisher publisher = dynamic.publisher;

        //头部信息
        if (publisher != null) {
            holder.tvName.setText(publisher.name);
            holder.dvAvatar.setTag(publisher);
            GlideLoader.getInstance().displayImage(publisher.avatar, holder.dvAvatar);
            holder.dvAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(handleDynamicListener != null){
                        handleDynamicListener.onAvatarClickListener();
                    }
                }
            });
        }

        holder.tvTime.setText(DateUtils.translateDate(DateUtils.parseDate
                (dynamic.published_at,yyyyMMddHHmmss).getTime(),System.currentTimeMillis()));


        //图片和视频
        List<String> images = dynamic.image;
        DynamicImageAdapter imageAdapter = null;
        if(images != null && !images.isEmpty()) {
            int spanCount = 1;
            if(images.size() == 1){
                spanCount = 1;
            }else if(images.size() == 2 || images.size() == 4){
                spanCount = 2;
            }else if(images.size() == 6){
                spanCount = 3;
            }
            switch (images.size()) {
                case 0:
                    holder.videoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setVisibility(View.GONE);
                    holder.threePhotoLayout.setVisibility(View.GONE);
                    holder.fivePhotoLayout.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.videoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setVisibility(View.GONE);
                    holder.threePhotoLayout.setVisibility(View.VISIBLE);
                    holder.fivePhotoLayout.setVisibility(View.GONE);
                    GlideLoader.getInstance().displayImage(images.get(0), holder.dvThreeFirst);
                    GlideLoader.getInstance().displayImage(images.get(1), holder.dvThreeSecond);
                    GlideLoader.getInstance().displayImage(images.get(2), holder.dvThreeThird);
                    break;
                case 5:
                    holder.videoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setVisibility(View.GONE);
                    holder.threePhotoLayout.setVisibility(View.GONE);
                    holder.fivePhotoLayout.setVisibility(View.VISIBLE);
                    GlideLoader.getInstance().displayImage(images.get(0), holder.dvThreeFirst);
                    GlideLoader.getInstance().displayImage(images.get(1), holder.dvThreeSecond);
                    GlideLoader.getInstance().displayImage(images.get(2), holder.dvThreeThird);
                    GlideLoader.getInstance().displayImage(images.get(3), holder.dvFiveFourth);
                    GlideLoader.getInstance().displayImage(images.get(4), holder.dvFiveFirst);
                    break;
                case 1:
                case 2:
                case 4:
                case 6:
                    holder.videoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setVisibility(View.VISIBLE);
                    holder.threePhotoLayout.setVisibility(View.GONE);
                    holder.fivePhotoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setLayoutManager(new GridLayoutManager(context,spanCount));
                    imageAdapter = new DynamicImageAdapter(context);
                    imageAdapter.setData(dynamic.image);
                    holder.photoLayout.setAdapter(imageAdapter);
                    break;
                default:
                    break;
            }
        }else {
            if(dynamic.videos != null){
                holder.videoLayout.setVisibility(View.VISIBLE);
                holder.photoLayout.setVisibility(View.GONE);
                holder.threePhotoLayout.setVisibility(View.GONE);
                holder.fivePhotoLayout.setVisibility(View.GONE);
                GlideLoader.getInstance().displayImage(dynamic.videos.cover, holder.dvVideo);
            }
        }

        //内容
        holder.tvContent.setText(dynamic.content);

        //点赞
        if (dynamic.like.counter > 0) {
            holder.likeLayout.setVisibility(View.VISIBLE);
            holder.likesRecyclerView.setLayoutManager(new LinearLayoutManager
                    (context, LinearLayoutManager.HORIZONTAL, false));
            DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context,dynamic.id);
            likeAdapter.setData(dynamic.like.item,dynamic.like.counter);
            holder.likesRecyclerView.setAdapter(likeAdapter);
        } else {
            holder.likeLayout.setVisibility(View.GONE);
        }

        //评论
        DynamicCommentAdapter commonAdapter = null;
        if(dynamic.comment.count> 0){
            holder.commentLayout.setVisibility(View.VISIBLE);
            commonAdapter = new DynamicCommentAdapter(context);
            holder.commentRecyclerView.setAdapter(commonAdapter);
            commonAdapter.setData(dynamic.comment.item,dynamic.comment.count);
            holder.commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else {
            holder.commentLayout.setVisibility(View.GONE);
        }

        //底部操作
        holder.tvLikeCount.setText(dynamic.like.counter);
        holder.tvCommentCount.setText(dynamic.comment.count);
        /*if(FormatUtil.parseInt(dynamic.like.counter) > 0){
            boolean hasSelf = false;
            for (DynamicBean.LikeUser.Item item : dynamic.like.item) {
                if(item.publisher_id.equals(String.valueOf(App.mInstance.getUser().getId()))){
                    dynamic.isLiked = true;
                    hasSelf = true;
                    break;
                }
            }
            holder.ivLike.setBackgroundResource(hasSelf ? R.drawable.icon_like_pressed
                    : R.drawable.btn_praise_normal);
        }*/


        if(imageAdapter != null) {
            imageAdapter.setImageClickListener(new DynamicImageAdapter.ImageClickListener() {
                @Override
                public void onImageClick(View view, int imagePosition) {
                    if(handleDynamicListener != null){
                        handleDynamicListener.onImageClickListener(view,position,imagePosition);
                    }
                }
            });
        }

        holder.ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onVideoClickListener(dynamic.videos.url);
                }
            }
        });

        holder.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onVideoClickListener(dynamic.videos.url);
                }
            }
        });

        holder.bottomLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onLikeClickListener(dynamic.id,dynamic.isLiked);
                    dynamic.isLiked = !dynamic.isLiked;
                }
            }
        });

        holder.bottomCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onCommonClickListener(position);
                }
            }
        });

        holder.bottomShareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onShareClickListener();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onDynamicDetailClickListener(position);
                }
            }
        });
    }

    class DynamicHolder extends RecyclerView.ViewHolder{

        //头部信息
        private ImageView dvAvatar;
        private TextView tvName;
        private ImageView ivCoachFlag;
        private TextView tvTime;

        //视频
        private SquareRelativeLayout videoLayout;
        private ImageView dvVideo;
        private ImageButton ibPlay;

        //1,2,4,5张图
        private RecyclerView photoLayout;

        //3张图
        private LinearLayout threePhotoLayout;
        private ImageView dvThreeFirst;
        private ImageView dvThreeSecond;
        private ImageView dvThreeThird;

        //5张图
        private RelativeLayout fivePhotoLayout;
        private ImageView dvFiveFirst;
        private ImageView dvFiveSecond;
        private ImageView dvFiveThird;
        private ImageView dvFiveFourth;
        private ImageView dvFiveLast;

        //内容
        private TextView tvContent;

        //点赞
        private RelativeLayout likeLayout;
        private RecyclerView likesRecyclerView;

        //评论
        private LinearLayout commentLayout;
        private RecyclerView commentRecyclerView;

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

            dvAvatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivCoachFlag = (ImageView) itemView.findViewById(R.id.iv_coach_flag);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            videoLayout = (SquareRelativeLayout) itemView.findViewById(R.id.video_layout);
            dvVideo = (ImageView) itemView.findViewById(R.id.dv_video);
            ibPlay = (ImageButton) itemView.findViewById(R.id.ib_play);
            photoLayout = (RecyclerView) itemView.findViewById(R.id.photo_layout);
            threePhotoLayout = (LinearLayout) itemView.findViewById(R.id.three_photo_layout);
            dvThreeFirst = (ImageView) itemView.findViewById(R.id.dv_three_first);
            dvThreeSecond = (ImageView) itemView.findViewById(R.id.dv_three_second);
            dvThreeThird = (ImageView) itemView.findViewById(R.id.dv_three_third);
            fivePhotoLayout = (RelativeLayout) itemView.findViewById(R.id.five_photo_layout);
            dvFiveFirst = (ImageView) itemView.findViewById(R.id.dv_five_first);
            dvFiveSecond = (ImageView) itemView.findViewById(R.id.dv_five_second);
            dvFiveThird = (ImageView) itemView.findViewById(R.id.dv_five_third);
            dvFiveFourth = (ImageView) itemView.findViewById(R.id.dv_five_fourth);
            dvFiveLast = (ImageView) itemView.findViewById(R.id.dv_five_last);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            likeLayout = (RelativeLayout) itemView.findViewById(R.id.like_layout);
            likesRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_likes);
            commentLayout = (LinearLayout) itemView.findViewById(R.id._comment_layout);
            commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_comment);
            bottomLikeLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_like_layout);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
            bottomCommentLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_comment_layout);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            bottomShareLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_share_layout);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
            photoLayout.setNestedScrollingEnabled(false);
            likesRecyclerView.setNestedScrollingEnabled(false);
            commentRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    public void setHandleDynamicListener(OnHandleDynamicListener listener) {
        this.handleDynamicListener = listener;
    }

    public interface OnHandleDynamicListener {
        void onAvatarClickListener();
        void onImageClickListener(View view,int itemPosition,int imagePosition);
        void onVideoClickListener(String url);
        void onShowMoreLikeClickListener();
        void onShowMoreCommentClickListener();
        void onLikeClickListener(String id,boolean isAddLike);
        void onCommonClickListener(int position);
        void onShareClickListener();
        void onDynamicDetailClickListener(int position);
    }
}
