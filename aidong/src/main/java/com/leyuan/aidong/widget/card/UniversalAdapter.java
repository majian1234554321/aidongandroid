package com.leyuan.aidong.widget.card;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyuan.aidong.R;

import com.leyuan.aidong.entity.course.CourseBeanNew;


import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.GlideRoundTransform;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by ls on 2017/11/25.
 */

public class UniversalAdapter extends RecyclerView.Adapter<UniversalAdapter.UniversalViewHolder> {
    public ArrayList<CourseBeanNew> courseBeanNews;
    public Context context;

    public UniversalAdapter(ArrayList<CourseBeanNew> courseBeanNews, Context context) {
        this.courseBeanNews = courseBeanNews;
        this.context = context;
    }

    @Override
    public UniversalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recylerview_item, null);
        UniversalViewHolder holder = new UniversalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UniversalViewHolder holder, final int position) {


        final CourseBeanNew courseBean = courseBeanNews.get(position);


        Glide.with(context)
                .load(courseBean.video_cover)
                .thumbnail(0.2f)

                .placeholder(new ColorDrawable(0xffc6c6c6))
                .bitmapTransform(new RoundedCornersTransformation(context, 8, 0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(holder.recy_item_im);




        holder.tv_1.setText(courseBean.getName());

        holder.tv_2.setText(courseBean.getTagString());

        String values = "" ;
        if (courseBean.getStrength()>=5){
            values = "高难度";
        }else if(courseBean.getStrength()>=3&&courseBean.getStrength()<5){
            values = "中级进阶";
        }else {
            values = "初级难度";
        }

        holder.tv_3.setText(values);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(courseBean.getId(), position,holder.itemView);
                }
            }
        });

    }


    public void resetData( ){




        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return courseBeanNews == null ? 0 : courseBeanNews.size();
    }

    public class UniversalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_2,tv_1;
        public ImageView recy_item_im;
        public MaterialButton tv_3;
        public UniversalViewHolder(View itemView) {
            super(itemView);
            recy_item_im=itemView.findViewById(R.id.recy_item_im);
            tv_1=itemView.findViewById(R.id.tv_1);
            tv_2=itemView.findViewById(R.id.tv_2);
            tv_3=itemView.findViewById(R.id.tv_3);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String id, int position,View view );
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}