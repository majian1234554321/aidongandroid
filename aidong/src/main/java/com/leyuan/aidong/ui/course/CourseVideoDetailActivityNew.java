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
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.DetailsRelativeViedeoAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.VideoMedia;
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
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.bt_share;
import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2018/1/9.
 */

public class CourseVideoDetailActivityNew extends BaseActivity implements View.OnClickListener, CourseVideoDetailActivityView {

    private ImageView ivBack;
    private RelativeLayout relTop;
    private ImageView imgBg;
    private ImageView imgLiveBeginOrEnd;
    private TextView txtCourseIntro, txt_course_name, txt_course_sub_name;
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
    private ArrayList<BaseMedia> selectedMedia;
    private String relateVideoTitle;

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

        if (courseVideoBean != null) {

            GlideLoader.getInstance().displayImage(courseVideoBean.getCover(), imgBg);
            txt_course_name.setText(courseVideoBean.getName());
            txt_course_sub_name.setText( courseVideoBean.getTypeName() );
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

                RelativeVideoListActivity.start(CourseVideoDetailActivityNew.this, courseId, relateVideoTitle,courseVideoBean.getId());

            }
        });

        coursePresent = new CoursePresentImpl(this, this);
        coursePresent.getRelateCourseVideo(courseId, courseVideoBean.getId());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.img_live_begin_or_end:
                if (courseVideoBean == null) return;
                Intent intent = new Intent(this, PlayerActivity.class)
                        .setData(Uri.parse(courseVideoBean.getFile()))
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
                sharePopupWindow.showAtBottom(courseVideoBean.getName()+" | 爱动健身", courseVideoBean.getIntroduce(),
                        courseVideoBean.getCover(), ConstantUrl.URL_SHARE_VIDEO + courseVideoBean.getId());
                break;
            case R.id.txt_appoint_immediately:

                CourseListActivityNew.start(this, "全部分类",this.courseVideoBean.course_id);
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
            if (requestCode == REQUEST_SELECT_PHOTO) {
                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);

            } else if (requestCode == REQUEST_SELECT_VIDEO) {
                selectedMedia = Boxing.getResult(data);
                if (selectedMedia != null && selectedMedia.size() > 0) {

                    int duration = TrimVideoUtil.VIDEO_MAX_DURATION;

                    if (selectedMedia.get(0) instanceof VideoMedia) {
                        VideoMedia media = (VideoMedia) selectedMedia.get(0);
                        duration = (int) (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1);
                        Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.getDuration());
                    }
                    Logger.i("TrimmerActivity", "onActivityResult  durantion = " + duration);

                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), duration, Constant.REQUEST_VIDEO_TRIMMER);

//                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC);
                }
            }
        }
    }

    @Override
    public void updateRelateVideo(String title, List<CourseVideoBean> videoBeanList) {
        txtRelateCourseVideo.setText(title);
        this.relateVideoTitle = title;
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
