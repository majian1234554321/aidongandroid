package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ImageRectUtils;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的资料照片墙适配器
 * Created by song on 2017/2/7.
 */
public class UserInfoPhotoAdapter extends RecyclerView.Adapter<UserInfoPhotoAdapter.ImageHolder>{
    private static final int MAX_UPLOAD_IMAGE_COUNT = 8;        //上传照片数量限制
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_ADD_IMAGE = 2;

    private Context context;
    private List<ImageBean> data = new ArrayList<>();
    private OnItemClickListener listener;
    private boolean isSelf;

    private List<ImageView> imageViewList = new ArrayList<>();

    public UserInfoPhotoAdapter(Context context,boolean isSelf) {
        this.context = context;
        this.isSelf = isSelf;
    }

    public void setData(List<ImageBean> data){
        if(data != null){
            this.data = data;
            imageViewList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if(isSelf){
            if(!data.isEmpty() && data.size() <= MAX_UPLOAD_IMAGE_COUNT){
                return data.size();
            }else {
                return 0;
            }
        }else {
            return data.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 0 && position < data.size()) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_ADD_IMAGE;
        }
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_no_delete, parent, false);
        return new UserInfoPhotoAdapter.ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        if (getItemViewType(position) == ITEM_TYPE_IMAGE) {
            GlideLoader.getInstance().displayImage(data.get(position).getUrl(),holder.image);
            imageViewList.add(holder.image);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        List<String> urls = new ArrayList<>();
                        for (ImageBean imageBean : data) {
                            urls.add(imageBean.getUrl());
                        }
                        List<Rect> drawableRectList = ImageRectUtils.getDrawableRects(imageViewList);
                        listener.onPreviewPhotoWallImage(urls,drawableRectList,position,holder.image);
                    }
                }
            });
        } else {
            holder.image.setBackgroundResource(R.drawable.icon_add_photo);
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        ImageView image;

        private ImageHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.dv_image);
            int width = (ScreenUtil.getScreenWidth(context) -
                    5 * context.getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin))/4;
            int height = width;
            itemView.getLayoutParams().width = width;
            itemView.getLayoutParams().height = height;
        }
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        void onPreviewPhotoWallImage(List<String> urls, List<Rect> rectList, int currPosition,View view);
    }
}
