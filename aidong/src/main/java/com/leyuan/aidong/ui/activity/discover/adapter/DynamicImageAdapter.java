package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈图片适配器
 * Created by song on 2016/12/26.
 */
public class DynamicImageAdapter extends RecyclerView.Adapter<DynamicImageAdapter.ImageHolder>{
    private Context context;
    private List<String> data = new ArrayList<>();

    public DynamicImageAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_image,parent,false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        String url = data.get(position);
        holder.image.setImageURI(url);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image;
        public ImageHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.dv_image);
            if(data.size() == 1){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context);
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context);
            }else if(data.size() == 2 || data.size() == 4){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context)/2;
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context)/2;
            }else if(data.size() == 6){
                image.getLayoutParams().width = ScreenUtil.getScreenWidth(context)/3;
                image.getLayoutParams().height = ScreenUtil.getScreenWidth(context)/3;
            }
        }
    }
}
