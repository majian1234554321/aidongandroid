package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageInfoBean;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.MAX_UPLOAD_IMAGE_COUNT;

/**
 * 相册图片适配器
 * Created by song on 2016/9/27.
 */
public class AlbumRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_IMAGE = 2;

    private Context context;
    private int usefulImageSize;
    private List<ImageInfoBean> albumImages = new ArrayList<>();
    private ArrayList<ImageInfoBean> selectImages = new ArrayList<>();
    private OnImgSelectListener onImgSelectListener;

    public AlbumRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ImageInfoBean> albumImages, ArrayList<ImageInfoBean> selectImages) {
        this.albumImages = albumImages;
        this.selectImages = selectImages;
        this.usefulImageSize = MAX_UPLOAD_IMAGE_COUNT - selectImages.size();
        notifyDataSetChanged();
    }

    public void setOnImgSelectListener(OnImgSelectListener l) {
        this.onImgSelectListener = l;
    }

    @Override
    public int getItemCount() {
        return albumImages.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_CAMERA;
        }else {
            return TYPE_IMAGE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_CAMERA){
            view = View.inflate(context,R.layout.item_camera,null);
            return new CameraHolder(view);
        }else{
            view = View.inflate(context,R.layout.item_album,null);
            return new AlbumHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position == 0){
            ((CameraHolder)holder).camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectImages.size() >= usefulImageSize) {
                        Toast.makeText(context,"最多只能选择"+ MAX_UPLOAD_IMAGE_COUNT +"张图片",Toast.LENGTH_LONG).show();
                        return;
                    }
                    onImgSelectListener.onStartCamera();
                }
            });
            return;
        }

        final ImageInfoBean bean = albumImages.get(position - 1);
        if(holder instanceof AlbumHolder){
            final AlbumHolder albumHolder = ((AlbumHolder)holder);
            if (bean.isSelected()) {
                albumHolder.select.setImageResource(R.drawable.radio_checked);
                albumHolder.image.setColorFilter(Color.parseColor("#77000000"));
            } else {
                albumHolder.select.setImageResource(R.drawable.radio_unchecked);
                albumHolder.image.setColorFilter(null);
            }

            albumHolder.image.setImageURI("file://" + bean.getImageFile().getAbsolutePath());
            albumHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isSelected()) { //图片被选中
                        albumHolder.select.setImageResource(R.drawable.radio_unchecked);
                        albumHolder.image.setColorFilter(null);
                        bean.setIsSelected(false);
                        deleteSelectImg(selectImages, bean);
                        onImgSelectListener.OnDisSelect(selectImages);
                    }else {   //图片没有被选中
                        if (selectImages.size() >= usefulImageSize) {
                            Toast.makeText(context,"最多只能选择"+ MAX_UPLOAD_IMAGE_COUNT +"张图片",Toast.LENGTH_LONG).show();
                            return;
                        }
                        albumHolder.select.setImageResource(R.drawable.radio_checked);
                        albumHolder.image.setColorFilter(Color.parseColor("#77000000"));
                        bean.setIsSelected(true);
                        addToSelectImgList(selectImages, bean);
                        onImgSelectListener.OnSelect(selectImages);
                    }
                }
            });
        }
    }

    private class AlbumHolder extends ViewHolder {
        SimpleDraweeView image;
        ImageView select;
        public AlbumHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.dv_photo);
            select = (ImageView) itemView.findViewById(R.id.iv_select);
        }
    }

    private class CameraHolder extends ViewHolder {
        ImageView camera;
        public CameraHolder(View itemView) {
            super(itemView);
            camera = (ImageView)itemView.findViewById(R.id.iv_camera);
        }
    }


    public interface OnImgSelectListener {
        void OnSelect(ArrayList<ImageInfoBean> imageInfo);
        void OnDisSelect(ArrayList<ImageInfoBean> imageInfo);
        void onStartCamera();
    }

    // 第二次打开相册，搜索过后，即使同一张图片，对应的引用的地址和上一次已经发生变化，不可以直接equal来比较，要根据绝对路径名来比较才行
    private void deleteSelectImg(ArrayList<ImageInfoBean> selectImgList, ImageInfoBean imageInfo) {
        for (int i = 0; i < selectImgList.size(); i++) {
            if (selectImgList.get(i).getImageFile().getAbsolutePath().equals(imageInfo.getImageFile().getAbsolutePath())) {
                selectImgList.remove(i);
            }
        }
    }

    //第二次打开相册，搜索过后，即使同一张图片，对应的引用的地址和上一次已经发生变化，不可以直接equal来比较，要根据绝对路径来比较才行
    private void addToSelectImgList(ArrayList<ImageInfoBean> selectImgList, ImageInfoBean imageInfo) {
        for (int i = 0; i < selectImgList.size(); i++) {
            if (selectImgList.get(i).getImageFile().getAbsolutePath().equals(imageInfo.getImageFile().getAbsolutePath())) {
                //如果selectList中已经存在此图片，就不重复进行添加了
                return;
            }
        }
        selectImgList.add(imageInfo);
    }
}
