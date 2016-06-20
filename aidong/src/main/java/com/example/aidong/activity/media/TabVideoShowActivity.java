package com.example.aidong.activity.media;

import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.BaseUrlLink;
import com.example.aidong.R;
import com.example.aidong.common.Constant;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.AttributeFilm;
import com.example.aidong.model.AttributeVideo;
import com.example.aidong.model.result.MsgResult;
import com.example.aidong.model.result.NewDynamicResult;
import com.example.aidong.utils.Constants;
import com.example.aidong.utils.FileUtil;
import com.example.aidong.utils.PopupWindowUitls;
import com.example.aidong.utils.photo.Bimp;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.mob.tools.utils.UIHandler;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;


public class TabVideoShowActivity extends BaseActivity implements
        OnClickListener, IHttpCallback, PlatformActionListener, Callback {

    private ImageView img_back;
    private EditText video_title;
    private RelativeLayout add_video;
    private EditText video_describe;
    private CheckBox check_share;
    private Button publish;
    private static final int RESULT_SELECT_VIDEO = 1;
    private String path;
    private static final int REQUEST_SELECT_VIDEO = 2;
    private static final int REQUEST_SELECT_VIDEO_COVER = 3;
    private static final int RESULT_SELECT_COVER = 4;
    private RelativeLayout rl_rotate_bg;
    private ImageView img_progress;
    private AnimationDrawable animationDrawable;
    private Bitmap select_cover;
    private ImageView img_video_cover;
    private LinearLayout linear_video_detail;
    private LinearLayout linear_change_cover;
    private ImageView img_video_play;
    private ImageView img_popup;
    private static final int POST_VIDEO = 5;
    private static final int NEWDYNAMICS = 6;
    private boolean isShare;
    private ShareParams sp;
    private Platform weibo;
    private PopupWindowUitls popup;
    protected byte currentPosition;

    protected static final int MSG_ACTION_CCALLBACK = 7;
    protected static final int REQUEST_PHOTOGRAPH = 8;

    public FileUtil MxFileUtil = FileUtil.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupView();
    }

    protected void setupView() {
        ShareSDK.initSDK(this);
        setContentView(R.layout.layout_tab_video_show);
        initView();
        setClick();
        getIntentFromPreviewViedo();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        video_title = (EditText) findViewById(R.id.video_title);
        add_video = (RelativeLayout) findViewById(R.id.add_video);
        video_describe = (EditText) findViewById(R.id.video_describe);
        check_share = (CheckBox) findViewById(R.id.check_share);
        publish = (Button) findViewById(R.id.publish);
        rl_rotate_bg = (RelativeLayout) findViewById(R.id.rl_rotate_bg);
        img_progress = (ImageView) findViewById(R.id.img_progress);
        linear_video_detail = (LinearLayout) findViewById(R.id.linear_video_detail);
        linear_change_cover = (LinearLayout) findViewById(R.id.linear_change_cover);
        img_video_cover = (ImageView) findViewById(R.id.img_video_cover);
        img_video_play = (ImageView) findViewById(R.id.img_video_play);
        img_popup = (ImageView) findViewById(R.id.img_popup);
        sp = new ShareParams();
    }

    private void setClick() {
        img_back.setOnClickListener(this);
        add_video.setOnClickListener(this);
        publish.setOnClickListener(this);
        img_video_play.setOnClickListener(this);
        linear_change_cover.setOnClickListener(this);
        check_share.setOnClickListener(this);
    }

    private void getIntentFromPreviewViedo() {
        path = getIntent().getStringExtra("path");
        if (path != null) {
            playCompressAnimation();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.add_video:
                startActivityForResult(new Intent(TabVideoShowActivity.this,
                        SelectVideoActivity.class), REQUEST_SELECT_VIDEO);
                break;

            case R.id.img_video_play:
                skipToVideoPlay();
                break;

            case R.id.linear_change_cover:
                showPopupWindow();
                break;

            case R.id.check_share:
                if (isShare) {
                    unshare();
                } else {
                    isShare = true;
                    if (weibo != null) {
                        weibo.setPlatformActionListener(this);
                    }
                }
                break;

            case R.id.publish:
                publishVideo(select_cover, path);
                break;

            default:
                break;
        }

    }

    private void unshare() {
        isShare = false;
        check_share.setChecked(false);
        sp = null;
        weibo = null;
    }

    //	private File file;
    protected String currentAddFileName;

    private void showPopupWindow() {

        popup = new PopupWindowUitls(this);
        popup.showPopup("拍照", true, "照片", true, "视频照片", true, img_popup);
        popup.getBtn_first().setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                popup.getWindow().dismiss();
                //				Intent intent = new Intent();
                //				intent.setAction("android.media.action.IMAGE_CAPTURE");
                //				intent.addCategory("android.intent.category.DEFAULT");
                //
                //				file = new File(
                //						Environment
                //								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                //						System.currentTimeMillis() + ".jpg");
                //				Uri uri = Uri.fromFile(file);
                //
                //				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //				startActivityForResult(intent, REQUEST_PHOTOGRAPH);
                File photoFileFolder = MxFileUtil.getTakePhotoFileFolder();
                if (photoFileFolder == null) {
                    ToastUtil.show(getResources().getString(R.string.invalidSD), TabVideoShowActivity.this);
                } else {
                    currentAddFileName = String.valueOf(System
                            .currentTimeMillis());
                    File photoFile = MxFileUtil.getTakePhotoFile(
                            photoFileFolder, currentAddFileName);
                    if (photoFile != null) {
                        Uri u = Uri.fromFile(photoFile);
                        Intent intent2 = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent2, REQUEST_PHOTOGRAPH);
                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_create_photo_file), TabVideoShowActivity.this);
                    }
                }


            }
        });
        popup.getBtn_second().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳到照片墙
                popup.getWindow().dismiss();
                // Toast.makeText(TabVideoShowActivity.this, "等下会跳到照片墙",
                // Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BaseApp.context, AlbumActivity.class);
                intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                intent.putExtra(Constant.BUNDLE_CLASS, TabVideoShowActivity.class);
                intent.putExtra("videoShow", true);
                startActivityForResult(intent, Constant.RC_SELECTABLUMN);

            }
        });
        popup.getBtn_third().setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.getWindow().dismiss();
                // 跳到选择封面页面
                Intent intent = new Intent(TabVideoShowActivity.this,
                        SelectVideoCoverActivity.class);
                intent.putExtra("path", path);
                startActivityForResult(intent, REQUEST_SELECT_VIDEO_COVER);
            }
        });
    }

    private void publishVideo(Bitmap bitmap, String path) {
        String title = video_title.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = video_describe.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object[]> files = new HashMap<String, Object[]>();
        List<Bitmap> list_bitmap = new ArrayList<Bitmap>();
        list_bitmap.add(bitmap);
        files.put("cover", list_bitmap.toArray());
        List<File> list_film = new ArrayList<File>();
        list_film.add(new File(path));
        files.put("film", list_film.toArray());
        addTask(this,
                new IHttpTask(UrlLink.MYSHOWVIEDOE_URL, initParams(title,
                        content), files, MsgResult.class), HttpConfig.POST,
                POST_VIDEO);
        startFrameAnimation();
    }

    public List<BasicNameValuePair> initParams(String title, String content) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("title", title));
        paramsaaa.add(new BasicNameValuePair("content", content));
        return paramsaaa;
    }

    /**
     * 跳转到视频预览
     */
    private void skipToVideoPlay() {
        AttributeVideo mvideo = new AttributeVideo();
        AttributeFilm film = new AttributeFilm();
        film.setCover("");
        film.setFilm(path);
        mvideo.setNo(0);
        mvideo.setContent("拍摄预览");
        mvideo.setFilm(film);
        Intent i = new Intent(TabVideoShowActivity.this,
                VideoPlayerActivity.class);
        i.putExtra(Constant.BUNDLE_VIDEO_URL, mvideo.getFilm().getFilm());
        i.putExtra("videofilm", mvideo);
        startActivity(i);
    }

    protected void onStop() {
        super.onStop();
        rl_rotate_bg.setClickable(false);
        rl_rotate_bg.setVisibility(View.GONE);
    }

    ;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (!TextUtils.isEmpty(path)) {
                Intent intent = new Intent(TabVideoShowActivity.this,
                        SelectVideoCoverActivity.class);
                intent.putExtra("path", path);
                startActivityForResult(intent, REQUEST_SELECT_VIDEO_COVER);
            }
        }

        ;
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Constant.RC_SELECTABLUMN:

                if (resultCode == RESULT_OK) {
                    if (Bimp.tempSelectBitmap.size() == 1) {
                        select_cover = getBitmapFromLocation(Bimp.tempSelectBitmap
                                .get(0).getImagePath());
                        if (select_cover != null)
                            img_video_cover.setImageBitmap(select_cover);
                    }

                }
                break;
            case REQUEST_PHOTOGRAPH:

                try {
                    File photoFile = MxFileUtil.getTakePhotoFile(
                            MxFileUtil.getTakePhotoFileFolder(),
                            currentAddFileName);
                    Uri u = Uri.parse(photoFile.getAbsolutePath());


                    if (u != null) {
                        select_cover = getBitmapFromLocation(photoFile.getAbsolutePath());
                        if (select_cover != null)
                            img_video_cover.setImageBitmap(select_cover);
                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_origin_pic),this);
                    }
                } catch (NotFoundException e1) {
                    e1.printStackTrace();
                    ToastUtil.show(getResources().getString(
                            R.string.error_no_get_origin_pic),this);
                }


                break;
            case REQUEST_SELECT_VIDEO:
                if (resultCode == RESULT_SELECT_VIDEO) {
                    path = data.getStringExtra("path");
                    // 首先播放动画
                    playCompressAnimation();
                }

                break;
            case REQUEST_SELECT_VIDEO_COVER:
                if (resultCode == RESULT_SELECT_COVER) {
                    try {
                        select_cover = (Bitmap) data
                                .getParcelableExtra("select_cover");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (select_cover != null) {
                        add_video.setVisibility(View.INVISIBLE);
                        linear_video_detail.setVisibility(View.VISIBLE);
                        img_video_cover.setImageBitmap(select_cover);
                        publish.setVisibility(View.VISIBLE);

                    }

                }
                break;

            default:
                break;
        }

    }

    private Bitmap getBitmapFromLocation(String imagePath) {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        Options option = new Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, option);
        int imageHeight = option.outHeight;
        int imageWidth = option.outWidth;
        if (imageHeight == -1 || imageWidth == -1) {
            return null;
        }
        // 计算缩放比例
        int scaleX = imageWidth / width;
        int scaleY = imageHeight / height;
        int scale = 1;
        if (scaleX >= scaleY && scaleX > scale)
            scale = scaleX;
        else if (scaleY > scaleX && scaleY > scale)
            scale = scaleY;
        System.out.println(scaleX + "  " + scaleY + "  " + scale);
        // 缩放显示照片
        option.inJustDecodeBounds = false;
        option.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);

        return bitmap;
    }

    /**
     * 压缩动画，并跳转到选择封面界面
     */
    private void playCompressAnimation() {
        startFrameAnimation();

        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        handler.sendEmptyMessageDelayed(0, duration);
    }

    private void startFrameAnimation() {
        rl_rotate_bg.setVisibility(View.VISIBLE);
        rl_rotate_bg.setClickable(true);
        img_progress.setBackgroundResource(R.drawable.progress);
        animationDrawable = (AnimationDrawable) img_progress.getBackground();
        img_progress.post(new Runnable() {

            @Override
            public void run() {
                animationDrawable.start();
            }
        });
    }

    public List<BasicNameValuePair> paramsinit2(String mxid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", "" + mxid));
        return paramsaaa;
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {

        switch (requestCode) {

            case POST_VIDEO:
                rl_rotate_bg.setClickable(false);
                rl_rotate_bg.setVisibility(View.GONE);
                MsgResult msgResult = (MsgResult) data;
                if (msgResult.getCode() == 1) {
                    Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                    addTask(this,
                            new IHttpTask(
                                    UrlLink.DYNAMICSLATEST_URL,
                                    paramsinit2(""
                                            + BaseApp.mInstance.getUser().getMxid()),
                                    NewDynamicResult.class), HttpConfig.GET,
                            NEWDYNAMICS);
                } else {
                    rl_rotate_bg.setClickable(false);
                    rl_rotate_bg.setVisibility(View.GONE);
                    Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
                }

                break;
            case NEWDYNAMICS:

                NewDynamicResult dynamicResult = (NewDynamicResult) data;
                if (dynamicResult.getCode() == 1) {
                    if (dynamicResult.getData() != null
                            && dynamicResult.getData().getDynamic() != null) {
                        if (isShare) {
                            StringBuffer buffer = new StringBuffer();
                            // 正式平台 http://www.e-mxing.com/share/
                            buffer.append(BaseUrlLink.SHARE_URL);
                            String titleUrl = buffer.toString();
                            sp.setTitleUrl(titleUrl);
                            if (BaseApp.mInstance.isLogin()) {
                                if (video_describe.getText().toString().length() > 30) {
                                    sp.setText("我的美型号"
                                            + BaseApp.mInstance.getUser().getMxid()
                                            + ",我在美型健身app发布了最新动态,求围观,求100个赞:\""
                                            + video_describe.getText().toString()
                                            .substring(0, 30)
                                            + BaseUrlLink.WAP_URL + "(来自#美型#)");
                                } else {
                                    sp.setText("我的美型号"
                                            + BaseApp.mInstance.getUser().getMxid()
                                            + ",我在美型健身app发布了最新动态,求围观,求100个赞:\""
                                            + video_describe.getText().toString()
                                            + BaseUrlLink.WAP_URL + "(来自#美型#)");
                                }
                            }
                            if (dynamicResult.getData().getDynamic().getImage() != null
                                    || dynamicResult.getData().getDynamic()
                                    .getImage().equals("")) {
                                sp.setImageUrl(dynamicResult.getData().getDynamic()
                                        .getImage());
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeResource(
                                        getResources(), R.drawable.icon_albm);
                                String path = FileUtil.stringPath(bitmap,
                                        Constants.FILE_FOLDER, "logo.jpg");
                                sp.setImagePath(path);
                            }
                            weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                            weibo.share(sp);
                        }
                        //					if (lastClass != null) {
                        //						Intent i = new Intent(getApplicationContext(),
                        //								lastClass);
                        //						i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //						startActivity(i);
                        //					} else {

                        finish();
                    }
                }


                break;

            default:
                break;
        }

    }

    @Override
    public void onError(String reason, int requestCode) {

        switch (requestCode) {
            case POST_VIDEO:
                rl_rotate_bg.setClickable(false);
                rl_rotate_bg.setVisibility(View.GONE);
                Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
                break;
            case NEWDYNAMICS:

                break;

            default:
                break;
        }

    }

    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        unshare();
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;

        if (platform.getName().equals(SinaWeibo.NAME)) {

        }
        UIHandler.sendMessage(msg, this);
        System.out.println(res);
        // 获取资料
        platform.getDb().getUserName();// 获取用户名字
        platform.getDb().getUserIcon(); // 获取用户头像
        platform.getDb().getToken();
        platform.getDb().getUserId();
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        unshare();
        isShare = true;
        check_share.setChecked(true);
        UIHandler.sendMessage(msg, this);
        // 分享失败的统计
        ShareSDK.logDemoEvent(4, platform);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // 失败
                Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();

            }
            break;
            case 3: {
                // 取消
                Toast.makeText(this, "取消····", Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

}
