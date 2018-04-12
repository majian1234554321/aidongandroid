package com.leyuan.aidong.ui.course;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
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
import com.leyuan.aidong.widget.richtext.RichWebView;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.img_coach;
import static com.leyuan.aidong.R.id.img_live_begin_or_end;
import static com.leyuan.aidong.R.id.txt_course_intro;

/**
 * d by user on 2018/1/11.
 */
public class CourseCircleHeaderView extends RelativeLayout implements View.OnClickListener, FollowView {

    private RelativeLayout relTop;
    private ImageView imgBg;
    private ImageView imgLiveBeginOrEnd;
    private RichWebView txtCourseIntro;
    private TextView txtSuggestFrequency;
    private RelativeLayout layoutAttention;
    private RecyclerView rvAttention;
    private View incRelativeCoach;
    private LinearLayout llRelateCourseVideo;
    private TextView txtRelateCourseVideo;
    private TextView txtCheckAllVideo;
    private RecyclerView rvRelateVideo;
    private LinearLayout llRelateDynamic;
    private TextView txtRelateDynamic;

    private RelativeLayout rootView;
    private TextView txtCourseName;
    private TextView txtAttentionNum;
    private TextView txtCourseDesc;
    private TextView txtCourseDifficulty;
    private ImageView imgStarFirst;
    private ImageView imgStarSecond;
    private ImageView imgStarThree;
    private ImageView imgStarFour;
    private ImageView imgStarFive;
    private ImageView imageView6;
    private ImageView imgCoach;
    private ImageButton bt_attention;
    private ArrayList<ImageView> starList = new ArrayList<>();

    private TextView txtRelativeCoach;
    private TextView txtChechAllCoach;
    private RecyclerView rvRelativeCoach;
    private PersonHorizontalAdapter adapterRelativeCoach;
    private PersonAttentionAdapter adapterAttentionPerson;
    private Context context;
    private DetailsRelativeViedeoAdapter relativeViedeoAdapter;
    private TextView txt_use_equipment;
    private TextView txt_target_population;
    private TextView txt_suggest_match_course;
    private TextView txt_bt_attention_num;
    private CourseDetailBean course;

    FollowPresentImpl followPresent;
    private String relativeVideoTitle;

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
        imgLiveBeginOrEnd = (ImageView) view.findViewById(img_live_begin_or_end);

        rootView = (RelativeLayout) view.findViewById(R.id.rootView);
        txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
        txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
        txt_bt_attention_num = (TextView) view.findViewById(R.id.txt_bt_attention_num);
        txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
        txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);

        starList.add((ImageView) view.findViewById(R.id.img_star_first));
        starList.add((ImageView) view.findViewById(R.id.img_star_second));
        starList.add((ImageView) view.findViewById(R.id.img_star_three));
        starList.add((ImageView) view.findViewById(R.id.img_star_four));
        starList.add((ImageView) view.findViewById(R.id.img_star_five));
        bt_attention = (ImageButton) view.findViewById(R.id.bt_attention);

        imageView6 = (ImageView) view.findViewById(R.id.imageView6);
        imgCoach = (ImageView) view.findViewById(img_coach);

        txtCourseIntro = (RichWebView) view.findViewById(txt_course_intro);
        txtSuggestFrequency = (TextView) view.findViewById(R.id.txt_suggest_frequency);
        txt_use_equipment = (TextView) view.findViewById(R.id.txt_use_equipment);
        txt_target_population = (TextView) view.findViewById(R.id.txt_target_population);
        txt_suggest_match_course = (TextView) view.findViewById(R.id.txt_suggest_match_course);

        llRelateDynamic = (LinearLayout) view.findViewById(R.id.ll_relate_dynamic);
        txtRelateDynamic = (TextView) view.findViewById(R.id.txt_relate_dynamic);

        initAttentionPerson(view);
//        initRelativeCoach(view);
        initRelativeCourseVideo(view);

        imgCoach.setVisibility(GONE);
        imgLiveBeginOrEnd.setOnClickListener(this);
        rootView.setOnClickListener(this);
        txt_bt_attention_num.setOnClickListener(this);
        view.findViewById(R.id.bt_attention).setOnClickListener(this);

        followPresent = new FollowPresentImpl(context);
        followPresent.setFollowListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_live_begin_or_end:
                Intent intent = new Intent(getContext(), PlayerActivity.class)
                        .setData(Uri.parse(course.getVideo()))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
                context.startActivity(intent);

                break;
            case R.id.rootView:


                break;
            case R.id.txt_bt_attention_num:

                if (App.getInstance().isLogin()) {
                    AppointmentUserActivity.start(context, course.getFollowers(), "已关注人列表");
                } else {
                    UiManager.activityJump(context, LoginActivity.class);
                }


                break;
            case R.id.bt_attention:
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

        llRelateCourseVideo = (LinearLayout) view.findViewById(R.id.ll_relate_course_video);
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

//                UiManager.activityJump(context, RelativeVideoListActivity.class);
            }
        });

    }


//    private void initRelativeCoach(View view) {
//        incRelativeCoach = view.findViewById(R.id.inc_relative_coach);
//        txtRelativeCoach = (TextView) view.findViewById(R.id.txt_left_title);
//        txtChechAllCoach = (TextView) view.findViewById(R.id.txt_check_all);
//        rvRelativeCoach = (RecyclerView) view.findViewById(R.id.recyclerView);
//
//        txtRelativeCoach.setText(getResources().getString(R.string.relate_coach));
//        txtChechAllCoach.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
//        rvRelativeCoach.setLayoutManager(manager);
//        adapterRelativeCoach = new PersonHorizontalAdapter(context);
//        rvRelativeCoach.setAdapter(adapterRelativeCoach);
//
//    }


    public void setData(CourseDetailBean course) {

        this.course = course;

        relativeViedeoAdapter.setCourseID(course.getId());

        if (course.getVideo_cover() != null) {
            GlideLoader.getInstance().displayImage(course.getVideo_cover(), imgBg);
        }

        txtCourseName.setText(course.getName());
        txtAttentionNum.setText(course.getFollows_count() + "人关注");
        txt_bt_attention_num.setText(course.getFollows_count() + "人已关注");
        txtCourseDesc.setText(course.getTagString());


        for (int i = 0; i < 5; i++) {
            if (i < course.getStrength()) {
                starList.get(i).setVisibility(View.VISIBLE);
            } else {
                starList.get(i).setVisibility(View.GONE);
            }
        }

        if (course.isFollowed()) {
            bt_attention.setImageResource(R.drawable.icon_attented);
        } else {
            bt_attention.setImageResource(R.drawable.icon_attention);
        }
        txtCourseIntro.setRichText(course.getIntroduce());
        txtSuggestFrequency.setText("建议周频次: " + course.getFrequency() + "次/周");
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
//        if (videos != null && !videos.isEmpty()) {
//            llRelateCourseVideo.setVisibility(VISIBLE);
//            relativeViedeoAdapter.setData(videos);
//        } else {
//            llRelateCourseVideo.setVisibility(GONE);
//        }D
        this.relativeVideoTitle = title;
        txtRelateCourseVideo.setText(title);
        relativeViedeoAdapter.setData(videos);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            bt_attention.setImageResource(R.drawable.icon_followed);
            course.setFollowed(true);

            course.setFollows_count(course.getFollows_count() + 1);
            txt_bt_attention_num.setText(course.getFollows_count() + "人关注");
            ToastGlobal.showShortConsecutive(R.string.follow_success);
        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            course.setFollowed(false);
            course.setFollows_count(course.getFollows_count() - 1);
            txt_bt_attention_num.setText(course.getFollows_count() + "人关注");
            bt_attention.setImageResource(R.drawable.icon_follow);
            ToastGlobal.showShortConsecutive(R.string.cancel_follow_success);
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
}
