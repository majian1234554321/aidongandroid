package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class HomeRecommendCourseAdapter extends RecyclerView.Adapter<HomeRecommendCourseAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<CourseBeanNew> course;
    private OnAttentionClickListener listener;

    public HomeRecommendCourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (course!=null){
        final CourseBeanNew courseBean = course.get(position);
        GlideLoader.getInstance().displayImage(courseBean.getCover(), holder.imgCoach);
        holder.txtCourseName.setText(courseBean.getName());
        holder.txtAttentionNum.setText(courseBean.getFollows_count() + "人关注");
        holder.txtCourseDesc.setText(courseBean.getTagString());

        for (int i = 0; i < 5; i++) {
            if (i < courseBean.getStrength()) {
                holder.starList.get(i).setVisibility(View.VISIBLE);
            } else {
                holder.starList.get(i).setVisibility(View.GONE);
            }
        }

        if (courseBean.isFollowed()) {
            holder.btAttention.setImageResource(R.drawable.icon_attented);
        } else {
            holder.btAttention.setImageResource(R.drawable.icon_attention);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(courseBean.getId(), position);
                }

//                CourseCircleDetailActivity.start(context, courseBean.getId());
            }
        });
        holder.btAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCourseAttentionClick(courseBean.getId(),position,courseBean.isFollowed());
                }
            }
        });
    }}

    @Override
    public int getItemCount() {
        if (course == null)
            return 0;
        return course.size();

    }

    public void setData(ArrayList<CourseBeanNew> course) {
        this.course = course;
        notifyDataSetChanged();
    }

    public void setOnAttentionClickListener(OnAttentionClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rootView;
        private ImageView imgCoach;
        private TextView txtCourseName;
        private TextView txtAttentionNum;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;
        private ImageButton btAttention;

        private ArrayList<ImageView> starList = new ArrayList<>();

        public ViewHolder(View view) {
            super(view);
            rootView = (RelativeLayout) view.findViewById(R.id.rootView);
            imgCoach = (ImageView) view.findViewById(R.id.img_coach);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
            txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);

            starList.add((ImageView) view.findViewById(R.id.img_star_first));
            starList.add((ImageView) view.findViewById(R.id.img_star_second));
            starList.add((ImageView) view.findViewById(R.id.img_star_three));
            starList.add((ImageView) view.findViewById(R.id.img_star_four));
            starList.add((ImageView) view.findViewById(R.id.img_star_five));
        }
    }


    public interface OnAttentionClickListener {
        void onCourseAttentionClick(String id, int position, boolean followed);

        void onItemClick(String id, int position);
    }
}
