package com.example.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.adapter.home.ApplicantAdapter;
import com.example.aidong.config.ConstantUrl;
import com.example.aidong.entity.course.CourseBeanNew;
import com.example.aidong.entity.course.CourseCouponPack;
import com.example.aidong.entity.course.CourseDetailDataNew;
import com.example.aidong.entity.course.CourseStore;
import com.example.aidong.module.share.SharePopupWindow;
import com.example.aidong.ui.App;
import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.mine.activity.AppointDetailCourseNewActivity;
import com.example.aidong.ui.mine.activity.UserInfoActivity;
import com.example.aidong.ui.mine.activity.account.LoginActivity;
import com.example.aidong.ui.mvp.presenter.impl.CourseDetailPresentImpl;
import com.example.aidong.ui.mvp.view.CourseDetailViewNew;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.DateUtils;
import com.example.aidong.utils.DialogUtils;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.Logger;
import com.example.aidong.utils.ToastGlobal;
import com.example.aidong.widget.richtext.RichWebView;
import com.zzhoujay.richtext.RichText;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2017/10/30.
 */

public class CourseDetailNewActivity extends BaseActivity implements View.OnClickListener, CourseDetailViewNew {

    private static final java.lang.String TAG = "CourseDetailNewActivity";

    private BGABanner banner;
    private TextView txtCourseName;
    private TextView txtCoachName;

    private TextView txtCoachDesc, tv_UnMember;
    private ImageView imgCoachAvatar;
    private TextView txtNormalPrice;
    private TextView txtMemberPrice;
    private TextView txtCourseTime;
    private TextView txtCourseRoom, tv_admission;
    private TextView txtCourseLocation;

    private RelativeLayout layoutCoursePack;
    private TextView txtCoursePackInfo;
    private TextView txtCoursePackPrice, tv_tips, tv_tips2;

    //    private TextView tvDesc;
    private RelativeLayout layout_course_coach;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout llApply, ll_pay;


    private RichWebView webView;

    private String code;

    private CourseDetailPresentImpl coursePresent;

    private SharePopupWindow sharePopupWindow;
    private CourseBeanNew course;
    private Handler handler = new Handler();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {

                case Constant.BROADCAST_ACTION_LOGIN_SUCCESS:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_GOODS_PAY_FAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS:
                    finish();
                    break;

                case Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED:
                    coursePresent.getCourseDetail(code);
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED:
                    coursePresent.getCourseDetail(code);
                    break;
            }
        }
    };
    private CourseCouponPack coupon_pack;

    public static void start(Context context, String code) {
        Intent starter = new Intent(context, CourseDetailNewActivity.class);
        starter.putExtra("code", code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail_new);
        initStatusBar(true);
        coursePresent = new CourseDetailPresentImpl(this, this);
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
        }

        initView();
        setListener();
        DialogUtils.showDialog(this, "加载中...", false);
        coursePresent.getCourseDetail(code);

        sharePopupWindow = new SharePopupWindow(this);


    }

    private void initView() {

        banner = (BGABanner) findViewById(R.id.banner);
        txtCourseName = (TextView) findViewById(R.id.txt_course_name);
        txtCoachName = (TextView) findViewById(R.id.txt_coach_name);

        txtCoachDesc = (TextView) findViewById(R.id.txt_coach_desc);
        imgCoachAvatar = (ImageView) findViewById(R.id.img_coach_avatar);
        txtNormalPrice = (TextView) findViewById(R.id.txt_normal_price);
        txtMemberPrice = (TextView) findViewById(R.id.txt_member_price);
        txtCourseTime = (TextView) findViewById(R.id.txt_course_time);
        txtCourseRoom = (TextView) findViewById(R.id.txt_course_room);
        txtCourseLocation = (TextView) findViewById(R.id.txt_course_location);
        tv_UnMember = (TextView) findViewById(R.id.tv_UnMember);
        tv_admission = (TextView) findViewById(R.id.tv_admission);


        layoutCoursePack = (RelativeLayout) findViewById(R.id.layout_course_pack);
        txtCoursePackInfo = (TextView) findViewById(R.id.txt_course_pack_info);
        txtCoursePackPrice = (TextView) findViewById(R.id.txt_course_pack_price);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        llApply = findViewById(R.id.ll_apply);

        ll_pay = findViewById(R.id.ll_pay);


        layout_course_coach = (RelativeLayout) findViewById(R.id.layout_course_coach);
        webView = (RichWebView) findViewById(R.id.web_view);
        tv_tips = findViewById(R.id.tv_tips);
        tv_tips2 = findViewById(R.id.tv_tips2);
        tv_tips2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_tips2.setVisibility(View.GONE);
            }
        });


    }

    private void setListener() {

        IntentFilter filter = new IntentFilter();

        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED);

        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        tv_tips.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        layout_course_coach.setOnClickListener(this);
        txtCourseLocation.setOnClickListener(this);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageView imageView = (ImageView) view;
                imageView.setScaleType(ImageView.ScaleType.CENTER);

                GlideLoader.getInstance().displayImage2((String) model, imageView);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_course_coach:
                if (course.getCoach() != null)
                    UserInfoActivity.start(this, course.getCoach().getContact());
                else
                    ToastGlobal.showShortConsecutive("教练信息有误");

                break;
            case R.id.iv_back:
                finish();
                break;


            case R.id.tv_tips:
                GoodsVirtualListActivity.start(this, coupon_pack.getItemProduct());
                break;
            case R.id.iv_share:
                String image = "";
                if (course.getImage() != null && course.getImage().size() > 0) {
                    image = course.getImage().get(0);
                }

                sharePopupWindow.showAtBottom(course.getName(), course.getIntroduce(),
                        image, ConstantUrl.URL_SHARE_COURSE + course.getId() + "/course");

                break;
            case R.id.txt_course_location:
                CourseStore store = course.getStore();
                if (store != null) {
                    if (store.getCoordinate() != null && store.getCoordinate().length >= 2) {
                        MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                                store.getCoordinate()[0] + "", store.getCoordinate()[1] + "");
                    } else {
                        MapActivity.start(this, store.getName(), store.getName(), store.getAddress(),
                                "31.00", "108.00");
                    }
                }

                break;
            case R.id.ll_pay:
                if (!App.getInstance().isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }

                if (course != null && course.isMember_only() && !course.isMember()) {
                    ToastGlobal.showShortConsecutive("该课程只有会员才能预约");
                    return;
                }

                switch (course != null ? course.getStatus() : 0) {

                    case CourseBeanNew.NORMAL:
                    case CourseBeanNew.FEW:
                        if ((course != null ? course.getSeat() : null) != null && course.getSeat().isNeed()) {
                            CourseChooseSeat.start(this, course);
                        } else {
                            ConfirmOrderCourseActivity.start(this, course);
                        }

                        break;
                    case CourseBeanNew.APPOINTED:
                        //跳查看预约
                        AppointDetailCourseNewActivity.courseStart(this, course != null ? course.getId() : null);

                        break;
                    case CourseBeanNew.APPOINTED_NO_PAY:
                        ///跳查看预约
                        AppointDetailCourseNewActivity.courseStart(this, course != null ? course.getId() : null);

                        break;
                    case CourseBeanNew.QUEUED:

                        //跳查看排队
                        CourseQueueDetailActivity.startFromCourse(this, course != null ? course.getId() : null);

                        break;

                    case CourseBeanNew.QUEUEABLE:
                        ///跳确认排队
                        CourseQueueConfirmActivity.start(this, course);
                        break;
                    case CourseBeanNew.FULL:
                        break;
                    case CourseBeanNew.END:
                        break;
                    default:
                        break;
                }
                break;
        }

    }

    @Override
    public void onGetCourseDetail(CourseDetailDataNew courseData) {
        DialogUtils.dismissDialog();
        if (courseData == null || courseData.getTimetable() == null) return;
        this.course = courseData.getTimetable();
        this.coupon_pack = courseData.getCoupon_pack();
        tvTitle.setText(courseData.getTimetable().getName());

        if (TextUtils.isEmpty(coupon_pack.getItemProduct())) {
            layoutCoursePack.setVisibility(View.GONE);
        } else {
            layoutCoursePack.setVisibility(View.GONE);
            txtCoursePackInfo.setText(coupon_pack.getTitle());
            txtCoursePackPrice.setText(coupon_pack.getPrice() + "元/节!");

        }


        banner.setData(course.getImage(), null);
        txtCourseName.setText(course.getName());
        if (course.getCoach() != null) {
            txtCoachName.setText(course.getCoach().getName());
            if (!TextUtils.isEmpty(course.getCoach().getIntroduce())) {

                // txtCoachDesc.setText(course.getCoach().getIntroduce());

                RichText.from(course.getCoach().getIntroduce()).placeHolder(R.drawable.place_holder_logo)
                        .error(R.drawable.place_holder_logo).into(txtCoachDesc);
            }
            GlideLoader.getInstance().displayCircleImage(course.getCoach().getAvatar(), imgCoachAvatar);
        }


        txtNormalPrice.setText(String.format(getString(R.string.rmb_price_double), course.getPrice()));
        txtMemberPrice.setText("会员: " + String.format(getString(R.string.rmb_price_double), course.getMember_price()));
        txtCourseTime.setText(course.getClass_time());
        txtCourseRoom.setText(course.getStore().getName() + "-" + course.getStore().getClassroom());
        txtCourseLocation.setText(course.getStore().getAddress());
        webView.setRichText(course.getIntroduce());


        switch (course.getStatus()) {

            case CourseBeanNew.NORMAL:
            case CourseBeanNew.FEW:

                // tv_price.setTextColor(getResources().getColor(R.color.white));

                ll_pay.setBackgroundColor(getResources().getColor(R.color.main_blue));
                ll_pay.setClickable(true);

                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_UnMember.setText(String.format(getString(R.string.rmb_price_double), course.getMember_price()) + "立即预约");
                if (course.member) { //会员
                    tv_admission.setVisibility(View.GONE);
                } else {
                    if (course.admission!=0) {
                        tv_admission.setText("非会员入场+" + course.admission);
                        tv_admission.setVisibility(View.VISIBLE);
                    } else {
                        tv_admission.setVisibility(View.GONE);
                    }
                }




                //   ForegroundColorSpan redSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.main_red));
                // TypefaceSpan span = new  TypefaceSpan(Typeface.create("serif",Typeface.ITALIC));
                //StyleSpan styleSpan = new StyleSpan(Typeface.ITALIC);//斜体
                StrikethroughSpan StrikethroughSpan = new StrikethroughSpan(); //删除线
               // UnderlineSpan underlineSpan = new UnderlineSpan(); //下划线
                //TextAppearanceSpan span =   new TextAppearanceSpan("serif", Typeface.BOLD_ITALIC);

                StringBuilder sb = new StringBuilder();


                if (course.market_price > course.getMember_price()) {
                    sb.append("市场价：").append(String.format(getString(R.string.rmb_price_double2), course.market_price));
                }
                if (course.slogan != null) {
                    if (sb.length() > 0) {
                        sb.append(" ").append(course.slogan).append(" ");
                    } else {
                        sb.append(course.slogan);
                    }

                }

                if (sb.length() > 0) {

                    //如果市场价和提示语都有
                    if (course.market_price > course.getMember_price() && !TextUtils.isEmpty(course.slogan)) {//既有提示语又有市场价格


                        SpannableStringBuilder builder = new SpannableStringBuilder(sb.toString());
                        String[] split = sb.toString().split(" ");
                        builder.setSpan(StrikethroughSpan, 0, split[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        //  builder.setSpan(redSpan, split[0].length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        builder.setSpan(styleSpan, split[0].length() + 1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        builder.setSpan(underlineSpan, split[0].length() + 1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                        tv_tips2.setText(builder);
                        tv_tips2.setVisibility(View.VISIBLE);

                    } else {
                        if (course.market_price > 0f && course.market_price > course.getMember_price() && TextUtils.isEmpty(course.slogan)) { //如果 市场价格不为空但是没有提示语言
                            tv_tips2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //市场价添加删除线
                            tv_tips2.setText("市场价：" + course.market_price);
                            tv_tips2.setTextColor(ContextCompat.getColor(this, R.color.orangeyellow));
                            tv_tips2.setVisibility(View.VISIBLE);
                        } else if (!TextUtils.isEmpty(course.slogan)) {//只有提示语没有市场价
                           // tv_tips2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
                            tv_tips2.getPaint().setColor(ContextCompat.getColor(this, R.color.orangeyellow));
                           // tv_tips2.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                            tv_tips2.setText(course.slogan);
                            tv_tips2.setVisibility(View.VISIBLE);
                        } else {
                            tv_tips2.setVisibility(View.GONE);
                        }
                    }


                } else {
                    tv_tips2.setVisibility(View.GONE);
                }


                break;
            case CourseBeanNew.APPOINTED:

//                rl_Member.setVisibility(View.GONE);
//                rl_UnMember.setVisibility(View.VISIBLE);
                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText(R.string.appointmented_look_detail);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.main_blue));
                ll_pay.setClickable(true);


                break;
            case CourseBeanNew.APPOINTED_NO_PAY:

//                rl_Member.setVisibility(View.GONE);
//                rl_UnMember.setVisibility(View.VISIBLE);
                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText(R.string.wait_pay_appointed);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.main_blue));
                ll_pay.setClickable(true);
                break;
            case CourseBeanNew.QUEUED:

//                rl_Member.setVisibility(View.GONE);
//                rl_UnMember.setVisibility(View.VISIBLE);
                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText(R.string.queueing_look_detail);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.main_blue));
                ll_pay.setClickable(true);
                break;

            case CourseBeanNew.QUEUEABLE:

//                rl_Member.setVisibility(View.GONE);
//                rl_UnMember.setVisibility(View.VISIBLE);
                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText(R.string.appoint_full_queue_immedialtely);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.main_blue));
                ll_pay.setClickable(true);
                break;
            case CourseBeanNew.FULL:

//                rl_Member.setVisibility(View.GONE);
//                rl_UnMember.setVisibility(View.VISIBLE);
                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText("已满员");
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                ll_pay.setClickable(false);
                break;
            case CourseBeanNew.END:

//                ll_pay.setVisibility(View.GONE);
//                ll_pay.setVisibility(View.VISIBLE);

                tv_tips2.setVisibility(View.GONE);
                tv_UnMember.setText(R.string.ended);
                tv_UnMember.setTextColor(getResources().getColor(R.color.white));
                tv_admission.setVisibility(View.GONE);
                ll_pay.setBackgroundColor(getResources().getColor(R.color.list_line_color));
                ll_pay.setClickable(false);
                break;
            default:
                break;
        }


//        if (course != null && course.isMember_only() && !course.isMember()) {
//            tv_tips2.setVisibility(View.GONE);
//        }


        if (DateUtils.compareLongTime(course.getReserve_time()) > 0&&course.getStatus() != CourseBeanNew.END) {


            tv_tips2.setVisibility(View.GONE);
            tv_UnMember.setText("预约开始时间：" + course.getReserve_time());
            tv_admission.setVisibility(View.GONE);
            tv_UnMember.setTextColor(Color.WHITE);
            ll_pay.setBackgroundColor(Color.BLACK);
            ll_pay.setClickable(false);

        }


        if (course.getReservable() != 1 && course.getStatus() != CourseBeanNew.END) {
            //无需预约
            // holder.imgCourseState.setVisibility(View.GONE);
            tv_UnMember.setText("无需预约");
            tv_admission.setVisibility(View.GONE);
            tv_UnMember.setTextColor(ContextCompat.getColor(this, R.color.white));
            ll_pay.setBackgroundColor(getResources().getColor(R.color.list_line_color));
            ll_pay.setClickable(false);
            tv_tips2.setVisibility(View.GONE);
        }


        Log.i("AAAAAAAAA", tv_tips2.getVisibility() + "");


        if (!TextUtils.isEmpty(coupon_pack.getItemProduct()) && tv_tips2.getVisibility() != View.VISIBLE) {
            //        String value = "抢购套券享受特惠价,热门课程低至 59元/每节";
//        SpannableString ss = new SpannableString(value);
//        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.main_red)), value.length() - 6, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_tips.setText(ss);
//        tv_tips.setVisibility(View.GONE);


            tv_tips.setText(coupon_pack.getTitle() + " " + coupon_pack.getPrice() + "元/节!");
            tv_tips.setVisibility(View.VISIBLE);
        } else {
            tv_tips.setVisibility(View.GONE);


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        RichText.clear(this);
        sharePopupWindow.release();
        handler.removeCallbacksAndMessages(null);
    }
}
