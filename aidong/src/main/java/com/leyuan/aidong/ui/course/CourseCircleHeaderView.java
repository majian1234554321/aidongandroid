package com.leyuan.aidong.ui.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer.util.Util;
import com.example.aidong.R;
import com.leyuan.aidong.adapter.PersonHorizontalAdapter;
import com.leyuan.aidong.adapter.home.PersonAttentionAdapter;
import com.leyuan.aidong.adapter.video.DetailsRelativeViedeoAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.entity.course.CourseDetailBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.course.activity.RelativeVideoListActivity;
import com.leyuan.aidong.ui.home.activity.AppointmentUserActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.FlowLayoutgroup;
import com.leyuan.aidong.widget.richtext.RichWebView;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong.R.id.img_coach;
import static com.example.aidong.R.id.img_live_begin_or_end;
import static com.example.aidong.R.id.txt_course_intro;

/**
 * d by user on 2018/1/11.
 */
public class CourseCircleHeaderView extends RelativeLayout implements View.OnClickListener, FollowView {


    private RichWebView txtCourseIntro;
    private TextView txtSuggestFrequency;
    private RelativeLayout layoutAttention;
    private RecyclerView rvAttention;


    private TextView txtRelateCourseVideo;
    private TextView txtCheckAllVideo;
    private RecyclerView rvRelateVideo;

    private TextView txtRelateDynamic;

    private RelativeLayout rootView;
    private TextView txtCourseName;
    private TextView txtAttentionNum;


    // private ImageButton bt_attention;


    private PersonAttentionAdapter adapterAttentionPerson;
    private Context context;
    private DetailsRelativeViedeoAdapter relativeViedeoAdapter;
    private TextView txt_use_equipment, tv_subjecttitle;
    private TextView txt_target_population;
    private TextView txt_suggest_match_course;
    private TextView tv_fans;
    private CourseDetailBean course;

    FollowPresentImpl followPresent;
    private String relativeVideoTitle;
    private ImageView iv_share,iv_fansssss;
    private FlowLayoutgroup flowLayout;

    public CourseCircleHeaderView(Context context) {
        this(context, null, 0);
    }

    public CourseCircleHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseCircleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.header_course_circle_detail, this, true);
        this.context = context;


        txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
        txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
        tv_fans = (TextView) view.findViewById(R.id.tv_fans);


        iv_fansssss = view.findViewById(R.id.iv_fansssss);

        iv_share = view.findViewById(R.id.iv_share);
        tv_subjecttitle = (TextView) view.findViewById(R.id.tv_subjecttitle);


        //  bt_attention = (ImageButton) view.findViewById(R.id.bt_attention);


        iv_share.setOnClickListener(this);


        flowLayout = (FlowLayoutgroup) findViewById(R.id.flowlayout);

        txtCourseIntro = (RichWebView) view.findViewById(txt_course_intro);
        txtSuggestFrequency = (TextView) view.findViewById(R.id.txt_suggest_frequency);
        txt_use_equipment = (TextView) view.findViewById(R.id.txt_use_equipment);
        txt_target_population = (TextView) view.findViewById(R.id.txt_target_population);
        txt_suggest_match_course = (TextView) view.findViewById(R.id.txt_suggest_match_course);


        txtRelateDynamic = (TextView) view.findViewById(R.id.txt_relate_dynamic);

        initAttentionPerson(view);

        initRelativeCourseVideo(view);


        iv_fansssss.setOnClickListener(this);

        followPresent = new FollowPresentImpl(context);
        followPresent.setFollowListener(this);










    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {



            case R.id.iv_fansssss:
                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(context, LoginActivity.class);
                    return;
                }

                if (course.isFollowed()) {
                    followPresent.cancelFollow(course.getId(), Constant.COURSE);
                } else {
                    followPresent.addFollow(course.getId(), Constant.COURSE);
                }

                break;

            case R.id.iv_share:
                if (onLoadListener != null)
                    onLoadListener.share();
                break;
        }
    }

    private void initAttentionPerson(View view) {
        layoutAttention = (RelativeLayout) view.findViewById(R.id.layout_attention);
        rvAttention = (RecyclerView) view.findViewById(R.id.rv_attention);
        txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvAttention.setLayoutManager(manager);
        adapterAttentionPerson = new PersonAttentionAdapter(context);
        rvAttention.setAdapter(adapterAttentionPerson);


    }

    private void initRelativeCourseVideo(View view) {


        txtRelateCourseVideo = (TextView) view.findViewById(R.id.txt_relate_course_video);
        txtCheckAllVideo = (TextView) view.findViewById(R.id.txt_check_all_video);
        rvRelateVideo = (RecyclerView) view.findViewById(R.id.rv_relate_video);

        GridLayoutManager manager = new GridLayoutManager(context, 2);
        rvRelateVideo.setLayoutManager(manager);
        relativeViedeoAdapter = new DetailsRelativeViedeoAdapter(context);
        rvRelateVideo.setAdapter(relativeViedeoAdapter);
        txtCheckAllVideo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (!TextUtils.isEmpty(course.getId()))
                    RelativeVideoListActivity.start(context, course.getId(), relativeVideoTitle);


            }
        });

    }


    public void setData(CourseDetailBean course, OnLoadListener listener) {

        this.onLoadListener = listener;
        this.course = course;

        relativeViedeoAdapter.setCourseID(course.getId());

        String values = "";

//        if (course.getStrength() >= 5) {
//            values = "高难度";
//        } else if (course.getStrength() < 5 && course.getStrength() >= 3) {
//            values = "中级进阶";
//        } else {
//            values = "初级难度";
//        }
        int ranHeight = dip2px(context, 26);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
        lp.setMargins(dip2px(context, 10), 0, dip2px(context, 10), 0);

        for (int i = 0; i < course.getTags().size(); i++) {


            TextView tv = new TextView(context);
            tv.setPadding(dip2px(context, 8), 0, dip2px(context, 8), 0);
            tv.setTextColor(ContextCompat.getColor(context,R.color.main_red));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

            tv.setText(course.getTags().get(i));
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setLines(1);
           tv.setBackgroundResource(R.drawable.shape_stroke_red_button);
            flowLayout.addView(tv, lp);
        }








        tv_subjecttitle.setText("#"+course.getCategory()+"#-"+(!TextUtils.isEmpty(course.professionalism)?course.professionalism:""));


//        txtCourseName.setText(course.getName());
        tv_fans.setText(course.getFollows_count()+"");
        if (course.getFollowers() != null) {
            value = course.getFollows_count();
            tv_fans.setText(course.getFollows_count()+"");
        } else {
            tv_fans.setText("0");
            value = 0;
        }


        if (course.isFollowed()) {
            setImageResourceVale(R.drawable.follw);
            tv_fans.setTextColor(ContextCompat.getColor(context,R.color.main_red));
        } else {
            setImageResourceVale(R.drawable.unfollw);
            tv_fans.setTextColor(ContextCompat.getColor(context,R.color.gray));
        }
        txtCourseIntro.setRichText(course.getIntroduce());
       // txtSuggestFrequency.setText("建议周频次: " + course.getFrequency() + "次/周");
        txtSuggestFrequency.setVisibility(GONE);
        txt_use_equipment.setText("使用机械: " + course.getInstrument());
        txt_target_population.setText("针对人群: " + course.getCrowd());
        txt_suggest_match_course.setText("建议课程搭配: " + course.getCollocation());

        if (course.getFollowers() != null && !course.getFollowers().isEmpty()) {
            adapterAttentionPerson.setData(course.getFollowers());
        } else {
            layoutAttention.setVisibility(GONE);
        }


    }

    public void setRelativeVideoData(String title, List<CourseVideoBean> videos) {
        this.relativeVideoTitle = title;
        txtRelateCourseVideo.setText(title);
        relativeViedeoAdapter.setData(videos);
    }

    public int value;


    public void setImageResourceVale(int id) {
        iv_fansssss.setBackgroundResource(id);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            setImageResourceVale(R.drawable.follw);
            course.setFollowed(true);
            tv_fans.setTextColor(ContextCompat.getColor(context,R.color.main_red));
            course.setFollows_count(course.getFollows_count() + 1);
            value = value + 1;
            tv_fans.setText(value+"");


            if (onLoadListener != null) {
                onLoadListener.load();
            }

        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            course.setFollowed(false);
            course.setFollows_count(course.getFollows_count() - 1);
            value = value - 1;
            tv_fans.setText(value + "");
            setImageResourceVale(R.drawable.unfollw);
            tv_fans.setTextColor(ContextCompat.getColor(context,R.color.gray));


            if (onLoadListener != null) {
                onLoadListener.load();
            }


        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }


    public void setDynamicEmpty(boolean empty) {

        Logger.i("CourseCircleHeaderView", "empty = " + empty);
        if (empty) {
            txtRelateDynamic.setVisibility(INVISIBLE);
        } else {
            txtRelateDynamic.setVisibility(VISIBLE);
        }
    }


    public interface OnLoadListener {
        void load();

        void share();
    }


    public OnLoadListener onLoadListener;


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
