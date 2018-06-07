package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.content.res.ColorStateList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.home.activity.CourseDetailNewActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.CustomTypefaceSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课列表适配器
 * Created by song on 2016/8/17.
 */
public class HomeCourseListChildAdapter extends RecyclerView.Adapter<HomeCourseListChildAdapter.CourseViewHolder> {
    private Context context;
    private List<CourseBeanNew> data = new ArrayList<>();

    public HomeCourseListChildAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_course_list_child, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        final CourseBeanNew courseBean = data.get(position);
        if (position == 0) {
            holder.layout_type.setVisibility(View.VISIBLE);
            holder.txt_type.setText(courseBean.company_id == 1 ? "爱动自营门店" : "合作品牌门店");
        } else if (courseBean.company_id != data.get(position - 1).company_id && data.get(position - 1).company_id == 1) {
            holder.layout_type.setVisibility(View.VISIBLE);
            holder.txt_type.setText(courseBean.company_id == 1 ? "爱动自营门店" : "合作品牌门店");
        } else {
            holder.layout_type.setVisibility(View.GONE);
        }

        GlideLoader.getInstance().displayCircleImage(courseBean.getCoach().getAvatar(), holder.imgCoach);
        holder.txtCoachName.setText(courseBean.getCoach().getName());
        holder.txtCourseName.setText(courseBean.getName());
        holder.txtCourseTime.setText(courseBean.getClass_time());
        holder.txtCourseDesc.setText(courseBean.getTagString());

//        if (courseBean.getStrength() >= 5) {
//            holder.tv_level.setText("(高难度)");
//        } else if (courseBean.getStrength() <= 5 && courseBean.getStrength() >= 3) {
//            holder.tv_level.setText("(中级进阶)");
//        } else {
//            holder.tv_level.setText("(初级难度)");
//        }

        holder.tv_level.setText("("+courseBean.professionalism+")");

//        holder.txtCourseDifficulty.setText("难度系数: " + courseBean.getStrength());

        holder.txtCourseOriginPrice.setText(String.format(context.getString(R.string.rmb_price_double), courseBean.getPrice()));
        holder.txtCourseMemberPrice.setText("会员价: " + String.format(context.getString(R.string.rmb_price_double), courseBean.getMember_price()));



        String fontPath = "fonts/Hiragino_Sans_GB_W3.ttf";

        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);

        holder.txtCourseTime.setTypeface(tf);
        holder.txtCourseOriginPrice.setTypeface(tf);



//        String values = "01234中国";
//
//        SpannableStringBuilder style=new SpannableStringBuilder(values);
//
//        for (int i = 0; i < values.length(); i++) {
//
//            if ()else{
//
//            }
//
//
//        }
//
//        style.setSpan(new CustomTypefaceSpan(values,tf), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//
//        holder.txtCourseName.setText(style);


        holder.txtCourseName.setTypeface(tf);



       // holder.txtCourseName.getPaint().setFakeBoldText(true);















        switch (courseBean.getStatus()) {

            case CourseBeanNew.NORMAL:
                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("预约");
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_red_button);
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.course_oringe));

                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.APPOINTED:

                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("已预约");
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_gray_button);
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.c9));
                holder.imgCourseState.setVisibility(View.GONE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_appointed);
                break;

            case CourseBeanNew.APPOINTED_NO_PAY:
                holder.mb_level.setVisibility(View.GONE);
                holder.imgCourseState.setVisibility(View.GONE);
                break;

            case CourseBeanNew.QUEUED:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_full);

                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("排队中");



                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_red_button);
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.course_oringe));
                break;

            case CourseBeanNew.FEW:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_few);

                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("预约");
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_red_button);
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.course_oringe));
                break;

            case CourseBeanNew.QUEUEABLE:
                holder.imgCourseState.setVisibility(View.VISIBLE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_full);


                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("排队");
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.course_oringe));
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_red_button);
                break;

            case CourseBeanNew.FULL:
                holder.imgCourseState.setVisibility(View.GONE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_full);


                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("已满员");
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.c9));
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_gray_button);
                break;

            case CourseBeanNew.END:
                holder.imgCourseState.setVisibility(View.GONE);
                holder.imgCourseState.setImageResource(R.drawable.icon_course_end);


                holder.mb_level.setVisibility(View.VISIBLE);
                holder.mb_level.setText("已结束");
                holder.mb_level.setBackgroundResource(R.drawable.shape_stroke_gray_button);
                holder.mb_level.setTextColor(ContextCompat.getColor(context, R.color.c9));
                break;

            default:
                holder.imgCourseState.setVisibility(View.GONE);
                holder.mb_level.setVisibility(View.GONE);
                break;
        }

        if (courseBean.getReservable() != 1 && courseBean.getStatus() != CourseBeanNew.END) {
            //无需预约
            holder.imgCourseState.setVisibility(View.GONE);
        }

        if (courseBean.isMember_only()) {
            //只有会员可以
            //holder.txtCourseOriginPrice.setText("");
           // holder.txtCourseMemberPrice.setText("会员专享");
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDetailNewActivity.start(context, courseBean.getId());
            }
        });

    }

//    private void resetStart(CourseViewHolder holder) {
//        holder.img_star_first.setVisibility(View.VISIBLE);
//        holder.img_star_second.setVisibility(View.VISIBLE);
//        holder.img_star_three.setVisibility(View.VISIBLE);
//        holder.img_star_four.setVisibility(View.VISIBLE);
//        holder.img_star_five.setVisibility(View.VISIBLE);
//    }

    class CourseViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCoach;
        private TextView txtCoachName;
        private TextView txtCourseName, txt_type;
        private TextView txtCourseTime;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;

        private ImageView imgCourseState;
        // ,img_star_first,img_star_second,img_star_three,img_star_four,img_star_five;
        private TextView txtCourseOriginPrice;
        private TextView txtCourseMemberPrice, tv_level;
        private LinearLayout rootView, layout_type;

        public TextView mb_level;

        public CourseViewHolder(View view) {
            super(view);
            imgCoach = (ImageView) view.findViewById(R.id.img_coach);

            txt_type = (TextView) view.findViewById(R.id.txt_type);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtCourseTime = (TextView) view.findViewById(R.id.txt_course_time);
            txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            imgCourseState = (ImageView) view.findViewById(R.id.img_course_state);
            txtCourseOriginPrice = (TextView) view.findViewById(R.id.txt_course_origin_price);
            txtCourseMemberPrice = (TextView) view.findViewById(R.id.txt_course_member_price);
            tv_level = (TextView) view.findViewById(R.id.tv_level);





            rootView = (LinearLayout) view.findViewById(R.id.rootView);

            layout_type = (LinearLayout) view.findViewById(R.id.layout_type);

            mb_level = view.findViewById(R.id.mb_level);
        }
    }
}
