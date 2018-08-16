package com.example.aidong.widget.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aidong.R;

import com.example.aidong .entity.course.CourseBeanNew;


import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.GlideRoundTransform;

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

    @NonNull
    @Override
    public UniversalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recylerview_item, null);
        UniversalViewHolder holder = new UniversalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UniversalViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        final CourseBeanNew courseBean = courseBeanNews.get(position);


        Glide.with(context)
                .load(courseBean.getCover())

                .centerCrop()
                .placeholder(R.drawable.img_default)
//                .bitmapTransform(new RoundedCornersTransformation(context, 8, 0,
//                        RoundedCornersTransformation.CornerType.TOP))
               // .transform(new GlideRoundTransform(context, 10))
                .into(holder.recy_item_im);





        holder.tv_1.setText(courseBean.getName());

        holder.tv_2.setText(courseBean.getTagString());



        holder.tv_3.setText(courseBean.professionalism);







        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                   // Toast.makeText(context, "position"+position, Toast.LENGTH_SHORT).show();
                    if (position==courseBeanNews.size()-1){
                        listener.onItemClick(courseBean.getId(), position,holder.itemView);
                    }

                }
            }
        });

    }




    @Override
    public int getItemCount() {
        return courseBeanNews == null ? 0 : courseBeanNews.size();
    }

    public class UniversalViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_2,tv_1;
        public ImageView recy_item_im;
        public TextView tv_3;
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