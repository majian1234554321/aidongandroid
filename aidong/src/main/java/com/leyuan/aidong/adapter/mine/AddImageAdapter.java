package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageInfoBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.MAX_UPLOAD_IMAGE_COUNT;


/**
 * 上传照片图片适配器
 * Created by song on 2016/9/26.
 */
public class AddImageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_ADD = 2;

    private Context context;
    private List<ImageInfoBean> data = new ArrayList<>();
    private OnAddImageClickListener onAddImageListener;
    private OnItemClickListener onItemClickListener;


    public AddImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ImageInfoBean> data){
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public void setOnAddImageListener(OnAddImageClickListener l) {
        this.onAddImageListener = l;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            if(data.size() < MAX_UPLOAD_IMAGE_COUNT){
                return data.size() + 1;
            }else{
                return MAX_UPLOAD_IMAGE_COUNT;
            }
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data.size() > 0 && position < data.size()) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_ADD;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            return new ImageHolder(view);
        } else if (viewType == ITEM_TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false);
            return new AddHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder instanceof ImageHolder) {
            ((ImageHolder) holder).image.setImageURI("file:///" + data.get(position).getImageFile().getAbsolutePath());

            ((ImageHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                }
            });

            ((ImageHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

        } else if (holder instanceof AddHolder) {
            ((AddHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddImageListener.onAddViewClick();
                }
            });
        }
    }


    private class ImageHolder extends ViewHolder {
        SimpleDraweeView image;
        ImageView delete;

        public ImageHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.dv_image);
            delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

    private class AddHolder extends ViewHolder {
        ImageView add;

        public AddHolder(View itemView) {
            super(itemView);
            add = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }

    public interface OnAddImageClickListener {
        void onAddViewClick();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
