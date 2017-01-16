package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.activity.discover.view.GridItemDecoration;
import com.leyuan.aidong.widget.customview.SquareRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面动态适配器
 * Created by song on 2016/8/29.
 */
public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DynamicHolder>  {
    private Context context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic,parent,false);
        return new DynamicHolder(view);
    }

    @Override
    public void onBindViewHolder(final DynamicHolder holder, final int position) {
        final DynamicBean dynamic = data.get(position);
        DynamicBean.Publisher publisher = dynamic.publisher;

        //头部信息
        if (publisher != null) {
            holder.tvName.setText(publisher.name);
            holder.dvAvatar.setTag(publisher);
            holder.dvAvatar.setImageURI(publisher.avatar);
            holder.dvAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(handleDynamicListener != null){
                        handleDynamicListener.onAvatarClickListener();
                    }
                }
            });
        }
        holder.tvTime.setText(dynamic.published_at);

        //图片
        List<String> images = dynamic.image;
        DynamicImageAdapter imageAdapter = null;
        if(images != null) {
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
                    holder.dvThreeFirst.setImageURI(images.get(0));
                    holder.dvThreeSecond.setImageURI(images.get(1));
                    holder.dvThreeThird.setImageURI(images.get(2));
                    break;
                case 5:
                    holder.videoLayout.setVisibility(View.GONE);
                    holder.photoLayout.setVisibility(View.GONE);
                    holder.threePhotoLayout.setVisibility(View.GONE);
                    holder.fivePhotoLayout.setVisibility(View.VISIBLE);
                    holder.dvFiveFirst.setImageURI(images.get(0));
                    holder.dvFiveSecond.setImageURI(images.get(1));
                    holder.dvFiveThird.setImageURI(images.get(2));
                    holder.dvFiveFourth.setImageURI(images.get(3));
                    holder.dvFiveLast.setImageURI(images.get(4));
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
        }

        //视频
       /* if(dynamic.video != null){
            holder.videoLayout.setVisibility(View.VISIBLE);
            holder.photoLayout.setVisibility(View.GONE);
            holder.threePhotoLayout.setVisibility(View.GONE);
            holder.fivePhotoLayout.setVisibility(View.GONE);
            holder.dvVideo.setImageURI(dynamic.video.cover);
        }*/

        //内容
        holder.tvContent.setText(dynamic.content);

        /*//点赞
        if (FormatUtil.parseInt(dynamic.like.counter) > 0) {
            holder.likeLayout.setVisibility(View.VISIBLE);
            holder.likesRecyclerView.setLayoutManager(new LinearLayoutManager
                    (context, LinearLayoutManager.HORIZONTAL, false));
            DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(context);
            likeAdapter.setData(dynamic.like.item);
            holder.likesRecyclerView.setAdapter(likeAdapter);

        } else {
            holder.likeLayout.setVisibility(View.GONE);
        }

        //评论
        if(FormatUtil.parseInt(dynamic.comment.count) > 0){
            holder.commentLayout.setVisibility(View.VISIBLE);
            DynamicCommentAdapter commonAdapter = new DynamicCommentAdapter(context);
            holder.commentRecyclerView.setAdapter(commonAdapter);
            commonAdapter.setData(dynamic.comment.item);
            holder.commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else {
            holder.commentLayout.setVisibility(View.GONE);
        }*/

        //底部操作
//        holder.tvLikeCount.setText(dynamic.like.counter);
 //       holder.tvCommentCount.setText(dynamic.comment.count);

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

        holder.commentRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleDynamicListener != null){
                    handleDynamicListener.onDynamicDetailClickListener(position);
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
                    handleDynamicListener.onCommonClickListener();
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
        private SimpleDraweeView dvAvatar;
        private TextView tvName;
        private ImageView ivCoachFlag;
        private TextView tvTime;

        //视频
        private SquareRelativeLayout videoLayout;
        private SimpleDraweeView dvVideo;
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

            dvAvatar = (SimpleDraweeView) itemView.findViewById(R.id.dv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivCoachFlag = (ImageView) itemView.findViewById(R.id.iv_coach_flag);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            videoLayout = (SquareRelativeLayout) itemView.findViewById(R.id.video_layout);
            dvVideo = (SimpleDraweeView) itemView.findViewById(R.id.dv_video);
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
            commentRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_comment);
            bottomLikeLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_like_layout);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tv_like_count);
            bottomCommentLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_comment_layout);
            ivComment = (ImageView) itemView.findViewById(R.id.iv_comment);
            tvCommentCount = (TextView) itemView.findViewById(R.id.tv_comment_count);
            bottomShareLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_share_layout);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
            photoLayout.addItemDecoration(new GridItemDecoration(context));
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
        void onVideoClickListener();
        void onShowMoreLikeClickListener();
        void onShowMoreCommentClickListener();
        void onLikeClickListener(String id,boolean isAddLike);
        void onCommonClickListener();
        void onShareClickListener();
        void onDynamicDetailClickListener(int position);
    }
}
