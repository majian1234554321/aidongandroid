package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.home.activity.CourseDetailNewActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课列表适配器
 * Created by song on 2016/8/17.
 */
public class CourseListAdapterNew extends RecyclerView.Adapter<CourseListAdapterNew.CourseViewHolder> {
    private Context context;
    private List<CourseBeanNew> data = new ArrayList<>();

    public CourseListAdapterNew(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBeanNew> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
//        return 9;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_list, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

        final CourseBeanNew courseBean = data.get(position);
        GlideLoader.getInstance().displayImage(courseBean.getCoach().getAvatar(), holder.imgCoach);
        holder.txtCoachName.setText(courseBean.getCoach().getName());
        holder.txtCourseName.setText(courseBean.getName());
        holder.txtCourseTime.setText(courseBean.getClass_time());
        holder.txtCourseDesc.setText(courseBean.getTagString());

        holder.txtCourseDifficulty.setText("难度系数: " + courseBean.getStrength());
        holder.txtCourseOriginPrice.setText("￥ " + courseBean.getPrice());
        holder.txtCourseMemberPrice.setText("会员价: ￥" + courseBean.getMember_price());

        switch (courseBean.getStatus()) {

            case CourseBeanNew.NORMAL:
                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.APPOINTED:
                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.APPOINTED_NO_PAY:
                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.QUEUED:
                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.FEW:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_few);
                break;

            case CourseBeanNew.QUEUEABLE:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_queue);
                break;

            case CourseBeanNew.FULL:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_full);
                break;

            case CourseBeanNew.END:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_end);
                break;

            default:
                holder.imgCourseState.setVisibility(View.GONE);
                break;
        }

        if(courseBean.getReservable() != 1 && courseBean.getStatus()!=CourseBeanNew.END){
            //无需预约
            holder.imgCourseState.setVisibility(View.GONE);
        }

        if(courseBean.isMember_only()){
            //只有会员可以
            holder.txtCourseOriginPrice.setText("");
            holder.txtCourseMemberPrice.setText("会员专享");
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailNewActivity.start(context, courseBean.getId());
            }
        });

    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCoach;
        private TextView txtCoachName;
        private TextView txtCourseName;
        private TextView txtCourseTime;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;
        private ImageView imgCourseState;
        private TextView txtCourseOriginPrice;
        private TextView txtCourseMemberPrice;
        private LinearLayout rootView;

        public CourseViewHolder(View view) {
            super(view);
            imgCoach = (ImageView) view.findViewById(R.id.img_coach);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtCourseTime = (TextView) view.findViewById(R.id.txt_course_time);
            txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            imgCourseState = (ImageView) view.findViewById(R.id.img_course_state);
            txtCourseOriginPrice = (TextView) view.findViewById(R.id.txt_course_origin_price);
            txtCourseMemberPrice = (TextView) view.findViewById(R.id.txt_course_member_price);
            rootView = (LinearLayout) view.findViewById(R.id.rootView);
        }
    }
}
