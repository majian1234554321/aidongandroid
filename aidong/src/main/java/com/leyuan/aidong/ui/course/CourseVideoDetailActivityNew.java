package com.leyuan.aidong.ui.course;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.DetailsRelativeViedeoAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.course.activity.RelativeVideoListActivity;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.home.activity.CourseListActivityNew;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseVideoDetailActivityView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastGlobal;

import java.util.List;

import static com.leyuan.aidong.R.id.bt_share;
import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/1/9.
 */

public class CourseVideoDetailActivityNew extends BaseActivity implements View.OnClickListener,CourseVideoDetailActivityView {

    private ImageView ivBack;
    private RelativeLayout relTop;
    private ImageView imgBg;
    private ImageView imgLiveBeginOrEnd;
    private TextView txtCourseIntro,txt_course_name,txt_course_sub_name;
    private LinearLayout llRelateCourseVideo;
    private TextView txtRelateCourseVideo;
    private TextView txtCheckAllVideo;
    private RecyclerView rvRelateVideo;

    TextView txt_share_image, txt_appoint_immediately;
    private SharePopupWindow sharePopupWindow;
    private DetailsRelativeViedeoAdapter relativeViedeoAdapter;
    private String courseId;
    CourseVideoBean courseVideoBean;

    CoursePresentImpl coursePresent;

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, CourseVideoDetailActivityNew.class);
        intent.putExtra("courseId", id);
        context.startActivity(intent);
    }

    public static void start(Context context, String id, CourseVideoBean bean) {
        Intent intent = new Intent(context, CourseVideoDetailActivityNew.class);
        intent.putExtra("courseId", id);
        intent.putExtra("courseVideoBean", bean);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseId = getIntent().getStringExtra("courseId");
        courseVideoBean = (CourseVideoBean) getIntent().getSerializableExtra("courseVideoBean");

        setContentView(R.layout.activity_course_video_details_new);

        ivBack = (ImageView) findViewById(R.id.iv_back);

        relTop = (RelativeLayout) findViewById(R.id.rel_top);
        imgBg = (ImageView) findViewById(R.id.img_bg);
        imgLiveBeginOrEnd = (ImageView) findViewById(R.id.img_live_begin_or_end);

        txt_course_name = (TextView) findViewById(R.id.txt_course_name);
        txt_course_sub_name = (TextView) findViewById(R.id.txt_course_sub_name);
        txtCourseIntro = (TextView) findViewById(R.id.txt_course_intro);
        llRelateCourseVideo = (LinearLayout) findViewById(R.id.ll_relate_course_video);
        txtRelateCourseVideo = (TextView) findViewById(R.id.txt_relate_course_video);
        txtCheckAllVideo = (TextView) findViewById(R.id.txt_check_all_video);
        rvRelateVideo = (RecyclerView) findViewById(R.id.rv_relate_video);


        txt_share_image = (TextView) findViewById(R.id.txt_share_image);
        txt_appoint_immediately = (TextView) findViewById(R.id.txt_appoint_immediately);

        if(courseVideoBean != null){
            GlideLoader.getInstance().displayImage(courseVideoBean.getCover(),imgBg);
            txt_course_name.setText(courseVideoBean.getName());
            txt_course_sub_name.setText("#"+courseVideoBean.getName()+"#");
            txtCourseIntro.setText(courseVideoBean.getIntroduce());
        }

        findViewById(bt_share).setOnClickListener(this);
        txt_appoint_immediately.setOnClickListener(this);
        imgLiveBeginOrEnd.setOnClickListener(this);
        txt_share_image.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);


        sharePopupWindow = new SharePopupWindow(this);


        GridLayoutManager manager = new GridLayoutManager(this, 2);
        rvRelateVideo.setLayoutManager(manager);
        relativeViedeoAdapter = new DetailsRelativeViedeoAdapter(this, courseId);
        rvRelateVideo.setAdapter(relativeViedeoAdapter);
        txtCheckAllVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RelativeVideoListActivity.start(CourseVideoDetailActivityNew.this, courseId);

            }
        });

        coursePresent = new CoursePresentImpl(this,this);
        coursePresent.getRelateCourseVideo(courseId,courseVideoBean.getId());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_live_begin_or_end:
                Intent intent = new Intent(this, PlayerActivity.class)
                        .setData(Uri.parse(""))
                        .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
                startActivity(intent);

                break;
            case R.id.txt_share_image:
                if (App.mInstance.isLogin()) {
                    new MaterialDialog.Builder(this)
                            .items(R.array.mediaType)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    if (position == 0) {
                                        takePhotos();
                                    } else {
                                        takeVideo();
                                    }
                                }
                            }).show();
                } else {
                    ToastGlobal.showLong("请先登陆再来发帖");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.bt_share:
                sharePopupWindow.showAtBottom("我分享了" + "的动态，速速围观", "dsklajdsads",
                        "kasdkads", ConstantUrl.URL_SHARE_DYNAMIC + 123213);
                break;
            case R.id.txt_appoint_immediately:

                CourseListActivityNew.start(this, "");
                break;
        }
    }


    private void takePhotos() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
    }

    private void takeVideo() {
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PHOTO || requestCode == REQUEST_SELECT_VIDEO) {
                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);

            }
        }
    }

    @Override
    public void updateRelateVideo(String title, List<CourseVideoBean> videoBeanList) {
        relativeViedeoAdapter.setData(videoBeanList);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showContentView() {

    }
}
