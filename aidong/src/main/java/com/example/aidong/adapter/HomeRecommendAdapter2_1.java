package com.example.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeRecommendAdapter2_1 extends RecyclerView.Adapter<HomeRecommendAdapter2_1.ViewHolder> {

    public Context context;
    private List<CourseBeanNew> course;
    public HomeRecommendAdapter2_1(Context context,  Object course){
        this.context = context;
        this.course = (List<CourseBeanNew>) course;
    }



    @NonNull
    @Override
    public HomeRecommendAdapter2_1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendAdapter2_1.ViewHolder holder, int position) {


        final CourseBeanNew courseBean = course.get(position);
        GlideLoader.getInstance().displayImage(courseBean.getCover(), holder.imgCoach);
        holder.txtCourseName.setText(courseBean.getName());
        holder.txtAttentionNum.setText(courseBean.getFollows_count() + "人关注");
        holder.txtCourseDesc.setText(courseBean.getTagString());



        if (courseBean.isFollowed()) {
            holder.btAttention.setImageResource(R.drawable.icon_attented);
        } else {
            holder.btAttention.setImageResource(R.drawable.icon_attention);
        }


       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(courseBean.getId(), position);
                }

            }
        });
        holder.btAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCourseAttentionClick(courseBean.getId(),position,courseBean.isFollowed());
                }
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return course.size()>5?5:course.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rootView;
        private ImageView imgCoach;
        private TextView txtCourseName;
        private TextView txtAttentionNum;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;
        private ImageButton btAttention;


        public ViewHolder(View view) {
            super(view);
            rootView = (RelativeLayout) view.findViewById(R.id.rootView);
            imgCoach = (ImageView) view.findViewById(R.id.img_coach);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
            txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);


        }
    }
}
