package com.leyuan.aidong.ui.course;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.PersonHorizontalAdapter;
import com.leyuan.aidong.adapter.home.PersonAttentionAdapter;
import com.leyuan.aidong.adapter.video.RelativeViedeoAdapter;
import com.leyuan.aidong.ui.course.activity.RelativeVideoListActivity;
import com.leyuan.aidong.utils.UiManager;

/**
 * Created by user on 2018/1/11.
 */
public class CourseCircleHeaderView extends RelativeLayout {


    private RelativeLayout relTop;
    private ImageView imgBg;
    private ImageView imgLiveBeginOrEnd;
    private TextView txtCourseIntro;
    private TextView txtSuggestFrequency;
    private RelativeLayout layoutAttention;
    private RecyclerView rvAttention;
    private TextView txtAttentionNum;
    private View incRelativeCoach;
    private LinearLayout llRelateCourseVideo;
    private TextView txtRelateCourseVideo;
    private TextView txtCheckAllVideo;
    private RecyclerView rvRelateVideo;
    private LinearLayout llRelateDynamic;
    private TextView txtRelateDynamic;

    private TextView txtRelativeCoach;
    private TextView txtChechAllCoach;
    private RecyclerView rvRelativeCoach;
    private PersonHorizontalAdapter adapterRelativeCoach;
    private PersonAttentionAdapter adapterAttentionPerson;
    private Context context;
    private RelativeViedeoAdapter relativeViedeoAdapter;

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
        relTop = (RelativeLayout) view.findViewById(R.id.rel_top);
        imgBg = (ImageView) view.findViewById(R.id.img_bg);
        imgLiveBeginOrEnd = (ImageView) view.findViewById(R.id.img_live_begin_or_end);
        txtCourseIntro = (TextView) view.findViewById(R.id.txt_course_intro);
        txtSuggestFrequency = (TextView) view.findViewById(R.id.txt_suggest_frequency);

        llRelateDynamic = (LinearLayout) view.findViewById(R.id.ll_relate_dynamic);
        txtRelateDynamic = (TextView) view.findViewById(R.id.txt_relate_dynamic);

        initAttentionPerson(view);
        initRelativeCoach(view);
        initRelativeCourseVideo(view);


    }

    private void initRelativeCourseVideo(View view) {

        llRelateCourseVideo = (LinearLayout) view.findViewById(R.id.ll_relate_course_video);
        txtRelateCourseVideo = (TextView) view.findViewById(R.id.txt_relate_course_video);
        txtCheckAllVideo = (TextView) view.findViewById(R.id.txt_check_all_video);
        rvRelateVideo = (RecyclerView) view.findViewById(R.id.rv_relate_video);

        GridLayoutManager manager = new GridLayoutManager(context, 2);
        rvRelateVideo.setLayoutManager(manager);
        relativeViedeoAdapter = new RelativeViedeoAdapter(context);
        rvRelateVideo.setAdapter(relativeViedeoAdapter);
        txtCheckAllVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UiManager.activityJump(context,RelativeVideoListActivity.class);
            }
        });

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

    private void initRelativeCoach(View view) {
        incRelativeCoach = view.findViewById(R.id.inc_relative_coach);
        txtRelativeCoach = (TextView) view.findViewById(R.id.txt_left_title);
        txtChechAllCoach = (TextView) view.findViewById(R.id.txt_check_all);
        rvRelativeCoach = (RecyclerView) view.findViewById(R.id.recyclerView);

        txtRelativeCoach.setText(getResources().getString(R.string.relate_coach));
        txtChechAllCoach.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvRelativeCoach.setLayoutManager(manager);
        adapterRelativeCoach = new PersonHorizontalAdapter(context);
        rvRelativeCoach.setAdapter(adapterRelativeCoach);
    }

    public void setData() {


    }
}
