package com.example.aidong.adapter.home;

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
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.course.CourseBeanNew;
import com.example.aidong.entity.user.Type;
import com.example.aidong.ui.home.activity.CourseDetailNewActivity;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.widget.CustomTypefaceSpan;

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

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_course_list_child, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {

        final CourseBeanNew courseBean = data.get(position);


        GlideLoader.getInstance().displayCircleImage(courseBean.getCoach().getAvatar(), holder.imgCoach);
        holder.txtCoachName.setText(courseBean.getCoach().getName());
        holder.txtCourseName.setText(courseBean.getName());
        holder.txtCourseTime.setText(courseBean.getClass_time());
        holder.txtCourseDesc.setText(courseBean.getTagString());


        holder.tv_level.setText("(" + courseBean.professionalism + ")");


        StringBuilder sb = new StringBuilder();


        if (courseBean.market_price != null) {
            sb.append("市场价：").append(String.format(context.getString(R.string.rmb_price_double2), courseBean.getPrice()));
        }
        if (courseBean.slogan != null) {
            if (sb.length() > 0) {
                sb.append(" ").append(courseBean.slogan);
            } else {
                sb.append(courseBean.slogan);
            }

        }


        ForegroundColorSpan redSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.main_red));
        // TypefaceSpan span = new  TypefaceSpan(Typeface.create("serif",Typeface.ITALIC));
        StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);//斜体
        StrikethroughSpan StrikethroughSpan = new StrikethroughSpan(); //删除线
        UnderlineSpan underlineSpan = new UnderlineSpan(); //下划线
        //TextAppearanceSpan span =   new TextAppearanceSpan("serif", Typeface.BOLD_ITALIC);

        if (sb.length() > 0) {
            holder.txtCourseDifficulty.setVisibility(View.VISIBLE);


            //如果市场价和提示语都有
            if (courseBean.market_price != null && courseBean.slogan != null) {//既有提示语又有市场价格


                SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
                String[] split = sb.toString().split(" ");
                builder.setSpan(StrikethroughSpan, 0, split[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.setSpan(redSpan, split[0].length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(styleSpan, split[0].length()+1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.setSpan(underlineSpan, split[0].length()+1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                holder.txtCourseDifficulty.setText(builder);


            } else {
                if (courseBean.market_price != null && courseBean.slogan == null) { //如果 市场价格不为空但是没有提示语言
                    holder.txtCourseDifficulty.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //市场价添加删除线
                    holder.txtCourseDifficulty.setText(courseBean.market_price);

                } else {//只有提示语没有市场价
                    holder.txtCourseDifficulty.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
                    holder.txtCourseDifficulty.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                    holder.txtCourseDifficulty.setText(courseBean.slogan);
                }
            }


        } else {
            holder.txtCourseDifficulty.setVisibility(View.GONE);
        }


        holder.mb_level3.setText("非会员入场+" + courseBean.admission);
        // holder.txtCourseDifficulty.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtCourseOriginPrice.setText(String.format(context.getString(R.string.rmb_price_double), courseBean.getPrice()));
        holder.txtCourseMemberPrice.setText(String.format(context.getString(R.string.rmb_price_double), courseBean.getMember_price()));


        String fontPath = "fonts/Hiragino_Sans_GB_W3.ttf";

        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);

        holder.txtCourseTime.setTypeface(tf);
        holder.txtCourseOriginPrice.setTypeface(tf);


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
        private TextView txtCourseName;
        private TextView txtCourseTime;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;

        private ImageView imgCourseState;
        // ,img_star_first,img_star_second,img_star_three,img_star_four,img_star_five;
        private TextView txtCourseOriginPrice;
        private TextView txtCourseMemberPrice, tv_level;
        private LinearLayout rootView, ll_extend;

        public TextView mb_level, tv_link, mb_level3;

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
            tv_level = (TextView) view.findViewById(R.id.tv_level);

            tv_link = (TextView) view.findViewById(R.id.tv_link);


            rootView = (LinearLayout) view.findViewById(R.id.rootView);
            ll_extend = view.findViewById(R.id.ll_extend);


            mb_level3 = view.findViewById(R.id.mb_level3);
            mb_level = view.findViewById(R.id.mb_level);
        }
    }
}
