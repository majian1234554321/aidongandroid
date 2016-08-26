package com.example.aidong.activity.media;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Thumbnails;
import android.provider.MediaStore.Video.VideoColumns;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.R;
import com.example.aidong.common.Constant;
import com.example.aidong.common.MXLog;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.AttributeFilm;
import com.example.aidong.model.AttributeVideo;
import com.example.aidong.model.location.ImageItem;
import com.example.aidong.model.result.MsgResult;
import com.example.aidong.model.result.NewDynamicResult;
import com.example.aidong.utils.FileUtil;
import com.example.aidong.utils.photo.Bimp;
import com.example.aidong.utils.photo.Res;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TabTheIndividualDynaminActivity extends BaseActivity implements
        IHttpCallback, Callback {
    protected ImageView mimageview_the_individual_dynamic_back;
    protected Button button_the_individual_dynamic_release;
    protected PopupWindow window = null;
    public Bitmap bimap;
    protected GridView noScrollgridview;
    protected RelativeLayout ll_popup;
    protected Button bt1, bt2, bt3, bt4;
    protected LinearLayout l1, l2;
    protected LinearLayout l3;
    protected GridAdapter adapter;
    protected View parentView;
    protected String thumbPath;
    protected EditText edittext_the_individual_dynamic;
    protected String currentAddFileName;
    protected byte currentPosition;
    protected boolean uploadVideo = false;
    protected boolean uploadPhoto = false;
    protected boolean isShare;
    protected RelativeLayout individual_dynamic = null;
    protected static final int DYNAMICS = 0;
    protected static final int NEWDYNAMICS = 1;
    protected File cover;
    protected Intent intent;
    protected static final int REQUEST_SELECT_VIDEO = 11;
    /**
     * 视频相关数据
     */
    protected String videoFilePath;
    protected File video;
    protected CheckBox mimageview_the_individual_dynamic_xinlang;
    protected static final int MSG_ACTION_CCALLBACK = 2;
    protected static int MAX_COUNT = 1000;
    protected TextView textview_the_individuality_signature_zishu;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    protected DisplayImageOptions options;
    protected boolean isfirst;
    /**
     * 表示该activity是由自己启动，还是由子类继承后启动的。默认false，即自己启动；ture代表是子类启动，该值由子类去修改
     */
    protected boolean isChild;

    private FileUtil MxFileUtil = FileUtil.getInstance();
    protected ProgressDialog dialog;

//    public void startloadingDialog() {
//        dialog = ProgressDialog.show(this, "提示", "加载中...");
//    }
//
    public void stoploadingDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
//
//    public void setLoadingDialog(String string) {
//        dialog = ProgressDialog.show(this, "提示", string);
//    }
    public void setLoadingDialog(int string) {
        dialog = ProgressDialog.show(this, "提示", getResources().getString(string));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //其实这里什么都不要做
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        MAX_COUNT = 1000;
        isfirst = true;
        switch (getIntent().getIntExtra("type", 0)) {
            case 1:
                Intent intent = new Intent(BaseApp.context, AlbumActivity.class);
                intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                intent.putExtra(Constant.BUNDLE_CLASS,
                        TabTheIndividualDynaminActivity.class);
                //用来标示，如果相册点击了取消或者back键，则结束本界面
                intent.putExtra("shouldFinishLast", true);
                startActivityForResult(intent, Constant.RC_SELECTABLUMN);
                break;
            case 2:
                File photoFileFolder = MxFileUtil.getTakePhotoFileFolder();
                if (photoFileFolder == null) {
                    ToastUtil.show(getResources().getString(R.string.invalidSD), this);
                } else {
                    currentAddFileName = String.valueOf(System
                            .currentTimeMillis());
                    File photoFile = MxFileUtil.getTakePhotoFile(
                            photoFileFolder, currentAddFileName);
                    if (photoFile != null) {
                        Uri u = Uri.fromFile(photoFile);
                        Intent intent2 = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        //					intent2.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        //					intent2.putExtra("return-data", true);
                        //					intent2.addCategory("android.intent.category.DEFAULT");
                    /*
                     * 指定一个路径，可以获得到照片原始尺寸大小图片，
					 * 且onresult中的data会为null，需要从uri中获取图片 若不指定，只能获取到照片缩略图
					 */
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent2, Constant.RC_TAKEPHOTO);
                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_create_photo_file), this);
                    }
                }
                break;
            case 3:
                Intent intent_select_video = new Intent(TabTheIndividualDynaminActivity.this, SelectVideoActivity.class);
                intent_select_video.putExtra("isFromDynamin", true);
                //用来标示，如果相册点击了取消或者back键，则结束本界面
                intent_select_video.putExtra("shouldFinishLast", true);
                startActivityForResult(intent_select_video, REQUEST_SELECT_VIDEO);
                break;

            default:
                break;
        }

        bimap = BitmapFactory.decodeResource(getResources(),
                R.drawable.add_picture);
        bimap = Bimp.zoomImg(bimap, 70, 70);
        Constant.Num = Constant.MAX_PHOTO_DYNAMIC_COUNT;
        // PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(
                R.layout.layout_tab_the_individual_dynamic, null);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_picture)
                .showImageForEmptyUri(R.drawable.icon_picture)
                .showImageOnFail(R.drawable.icon_picture)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        setContentView(parentView);
        //		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        //        if (currentapiVersion >= 14) {
        //                getWindow().getDecorView().setSystemUiVisibility(
        //                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //        }
        init();
        getIntentFromPreviewViedo();
        initData();
    }

    protected void getIntentFromPreviewViedo() {
        path = getIntent().getStringExtra("path");
        if (path != null) {
            playCompressAnimation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rl_rotate_bg != null && rl_rotate_bg.getVisibility() == View.VISIBLE) {
            rl_rotate_bg.setClickable(false);
            rl_rotate_bg.setVisibility(View.GONE);
        }

    }

    public List<BasicNameValuePair> paramsinit(String content) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("content", content));
        return paramsaaa;
    }

    public List<BasicNameValuePair> paramsinit2(String mxid) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("mxid", "" + mxid));
        return paramsaaa;
    }

    protected void initData() {
        onClick();
    }

    private void refreshPopMenuStatus() {
        if (!uploadPhoto && !uploadVideo) {
            bt1.setTextColor(Color.BLACK);
            bt2.setTextColor(Color.BLACK);
            bt3.setTextColor(Color.BLACK);
            bt4.setTextColor(Color.BLACK);
        } else if (uploadPhoto) {
            bt1.setTextColor(Color.BLACK);
            bt2.setTextColor(Color.BLACK);
            if (l3 != null) {
                l3.setVisibility(View.GONE);
            }
            bt3.setTextColor(Color.GRAY);
            bt4.setTextColor(Color.GRAY);
        } else if (uploadVideo) {
            bt1.setTextColor(Color.GRAY);
            bt2.setTextColor(Color.GRAY);
            bt3.setTextColor(Color.BLACK);
            bt4.setTextColor(Color.BLACK);
        }
    }

    protected void freeSelectBitmap() {
        try {
            for (ImageItem item : Bimp.tempSelectBitmap) {
                if (item.getBitmap() != null) {
                    item.getBitmap().recycle();
                    item.setBitmap(null);
                }
            }
            Bimp.tempSelectBitmap.clear();
            System.gc();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void init() {
        Constant.Num = Constant.MAX_PHOTO_DYNAMIC_COUNT;
        //		setMaxPhotoNum(MAX_PHOTO_DYNAMIC_COUNT);
        if (!getIntent().getBooleanExtra("isalbum", false))
            freeSelectBitmap();
        mimageview_the_individual_dynamic_xinlang = (CheckBox) findViewById(R.id.imageview_the_individual_dynamic_xinlang);
        edittext_the_individual_dynamic = (EditText) findViewById(R.id.edittext_the_individual_dynamic);
        button_the_individual_dynamic_release = (Button) findViewById(R.id.button_the_individual_dynamic_release);
        mimageview_the_individual_dynamic_back = (ImageView) findViewById(R.id.imageview_the_individual_dynamic_back);
        individual_dynamic = (RelativeLayout) findViewById(R.id.individual_dynamic);
        textview_the_individuality_signature_zishu = (TextView) findViewById(R.id.textview_the_individuality_signature_zishu);
        intent = new Intent();
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        rl_rotate_bg = (RelativeLayout) findViewById(R.id.rl_rotate_bg);
        img_progress = (ImageView) findViewById(R.id.img_progress);
        edittext_the_individual_dynamic
                .addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        setLeftCount();
                    }
                });
        edittext_the_individual_dynamic
                .setFilters(new InputFilter[]{filter});
        adapter = new GridAdapter(this);
        showOutMenu();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                currentPosition = (byte) position;
                if (uploadVideo) {
                    // 获取本地视频
                    //					selectVideo();
                    skipToVideoPlay();
                    return;
                }
                if (position == Bimp.tempSelectBitmap.size()) {
                    setPopWindow(false);
                    //直接执行跳到相册逻辑,新改的
                    Intent intent = new Intent(BaseApp.context, AlbumActivity.class);
                    intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                    intent.putExtra(Constant.BUNDLE_CLASS,
                            TabTheIndividualDynaminActivity.class);
                    startActivityForResult(intent, Constant.RC_SELECTABLUMN);
                    return;

                } else {
                    if (uploadPhoto) {
                        // 弹出删除相册
                        setPopWindow(true);
                    }
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        0);
                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                        TabTheIndividualDynaminActivity.this,
                        R.anim.activity_translate_in));
                window.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    /**
     * 跳转到视频预览
     */
    protected void skipToVideoPlay() {
        AttributeVideo mvideo = new AttributeVideo();
        AttributeFilm film = new AttributeFilm();
        film.setCover("");
        film.setFilm(path);
        mvideo.setNo(0);
        mvideo.setContent("拍摄预览");
        mvideo.setFilm(film);
        Intent i = new Intent(TabTheIndividualDynaminActivity.this,
                VideoPlayerActivity.class);
        i.putExtra(Constant.BUNDLE_VIDEO_URL, mvideo.getFilm()
                .getFilm());
        i.putExtra("videofilm", mvideo);
        startActivity(i);
    }

    protected void setPopWindow(boolean isDeletePhotoStatus) {
        if (isDeletePhotoStatus) {
            //			l1.setVisibility(View.GONE);
            //			l2.setVisibility(View.GONE);
            if (!isChild) {
                l3.setVisibility(View.GONE);
            }
            bt2.setText(R.string.delete);
            bt2.setTextColor(Color.GRAY);
        } else {
            //			l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.VISIBLE);
            if (!isChild) {
                if (uploadPhoto) {
                    l3.setVisibility(View.GONE);
                } else {
                    l3.setVisibility(View.VISIBLE);
                }
            }
            bt2.setText(R.string.album);
            //			if (uploadPhoto) {
            //				bt2.setTextColor(Color.GRAY);
            //			} else {
            bt2.setTextColor(Color.BLACK);
            //			}
        }
    }

    /**
     * 拍摄视频
     */
    protected void videoMethod() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, Constant.RC_TAKEVIDEO);
    }

    /**
     * 选择视频
     */
    protected void selectVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // The MIME data type filter
        intent.setType("video/*");
        // Only return URIs that can be opened with ContentResolver
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, Constant.RC_SELECTVIDEO);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
            ToastUtil.show(getResources().getString(
                    R.string.error_no_get_local_video_info), this);
        }
    }

    protected int maxLen = 1000;
    InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence src, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int dindex = 0;
            double count = 0;

            while (count <= maxLen && dindex < dest.length()) {
                char c = dest.charAt(dindex++);
                if (c < 128) {
                    count = count + 0.5;
                } else {
                    count = count + 1;
                }
            }

            if (count > maxLen) {
                ToastUtil.show(getResources().getString(R.string.yourlength), TabTheIndividualDynaminActivity.this);
                return dest.subSequence(0, dindex - 1);
            }

            int sindex = 0;
            while (count <= maxLen && sindex < src.length()) {
                char c = src.charAt(sindex++);
                if (c < 128) {
                    count = count + 0.5;
                } else {
                    count = count + 1;
                }
            }

            if (count > maxLen) {
                sindex--;
                ToastUtil.show(getResources().getString(R.string.yourlength), TabTheIndividualDynaminActivity.this);
            }

            return src.subSequence(0, sindex);
        }
    };

    View view;

    private void showOutMenu() {
        //		window = new PopupWindow(TabTheIndividualDynaminActivity.this);
        view = getLayoutInflater().inflate(
                R.layout.popmenu_the_individualdynamic, null);
        ll_popup = (RelativeLayout) view.findViewById(R.id.ll_popup);
        window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, true);
        window.setAnimationStyle(R.style.popuStyle);
        //		window.setBackgroundDrawable(new BitmapDrawable());
        //		window.setWidth(LayoutParams.MATCH_PARENT);
        //		window.setHeight(LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new BitmapDrawable());
        //		window.setFocusable(true);
        //		window.setOutsideTouchable(true);
        //		window.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        bt3 = (Button) view.findViewById(R.id.item_popupwindows_recording);
        bt4 = (Button) view
                .findViewById(R.id.item_popupwindows_the_local_video);
        Button bt5 = (Button) view.findViewById(R.id.item_popupwindows_cancel);

        l1 = (LinearLayout) view.findViewById(R.id.l1);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l3 = (LinearLayout) view.findViewById(R.id.l3);

        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadVideo)
                    return;
                window.dismiss();
                File photoFileFolder = MxFileUtil.getTakePhotoFileFolder();
                if (photoFileFolder == null) {
                    ToastUtil.show(getResources().getString(R.string.invalidSD), TabTheIndividualDynaminActivity.this);
                } else {
                    currentAddFileName = String.valueOf(System
                            .currentTimeMillis());
                    File photoFile = MxFileUtil.getTakePhotoFile(
                            photoFileFolder, currentAddFileName);
                    if (photoFile != null) {
                        Uri u = Uri.fromFile(photoFile);
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        /*
                         * 指定一个路径，可以获得到照片原始尺寸大小图片，
						 * 且onresult中的data会为null，需要从uri中获取图片 若不指定，只能获取到照片缩略图
						 */
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                        startActivityForResult(intent, Constant.RC_TAKEPHOTO);
                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_create_photo_file), TabTheIndividualDynaminActivity.this);
                    }
                }
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadVideo)
                    return;
                window.dismiss();
                if (bt2.getText().equals(
                        getResources().getString(R.string.delete))) {
                    if (uploadPhoto
                            && currentPosition < Bimp.tempSelectBitmap.size()) {
                        //						ImageItem item = Bimp.tempSelectBitmap
                        //								.get(currentPosition);
                        Bimp.tempSelectBitmap.remove(currentPosition);
                        adapter.notifyDataSetChanged();
                        if (Bimp.tempSelectBitmap.size() == 0) {
                            uploadPhoto = false;
                        }
                        //						if (item.getBitmap() != null) {
                        //							item.getBitmap().recycle();
                        //							item.setBitmap(null);
                        //							Bimp.tempSelectBitmap.remove(currentPosition);
                        //							System.gc();
                        //							adapter.notifyDataSetChanged();
                        //							if (Bimp.tempSelectBitmap.size() == 0) {
                        //								uploadPhoto = false;
                        //							}
                        //						}
                    }
                } else {
                    Intent intent = new Intent(BaseApp.context, AlbumActivity.class);
                    intent.putExtra(Constant.BUNDLE_PHOTOWALL_POSITION, currentPosition);
                    intent.putExtra(Constant.BUNDLE_CLASS,
                            TabTheIndividualDynaminActivity.class);
                    startActivityForResult(intent, Constant.RC_SELECTABLUMN);
                }
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //				if (uploadPhoto) {
                //					return;
                //				}
                //				videoMethod();
                window.dismiss();
                Intent intent_select_video = new Intent(TabTheIndividualDynaminActivity.this, SelectVideoActivity.class);
                intent_select_video.putExtra("isFromDynamin", true);
                startActivityForResult(intent_select_video, REQUEST_SELECT_VIDEO);


            }
        });

        bt4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt4.getText().equals(
                        getResources().getString(R.string.delete))) {
                    if (uploadPhoto
                            && currentPosition < Bimp.tempSelectBitmap.size()) {
                        ImageItem item = Bimp.tempSelectBitmap
                                .get(currentPosition);
                        if (item.getBitmap() != null) {
                            item.getBitmap().recycle();
                            item.setBitmap(null);
                            Bimp.tempSelectBitmap.remove(currentPosition);
                            System.gc();
                            adapter.notifyDataSetChanged();
                            if (Bimp.tempSelectBitmap.size() == 0) {
                                uploadPhoto = false;
                            }
                        }
                    }
                } else {
                    selectVideo();
                }
                window.dismiss();
            }
        });
        bt5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ll_popup.clearAnimation();
            }
        });
    }


    private void onClick() {
        individual_dynamic.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), 0);
            }
        });
        mimageview_the_individual_dynamic_xinlang
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isShare) {
                            unshare();
                        } else {
                            isShare = true;
                           //微博分享
                        }
                    }
                });
        mimageview_the_individual_dynamic_back
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (uploadVideo) {
                            Bimp.tempSelectBitmap.clear();
                        }
                        window.dismiss();
                        finish();
                    }
                });
        button_the_individual_dynamic_release
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String individual_dynamic = edittext_the_individual_dynamic
                                .getText().toString();
                        File file[] = null;
                        if (uploadVideo) {
                            publishVideo(individual_dynamic, select_cover, path);
                        } else if (uploadPhoto) {
                            cover = null;
                            video = null;
                            if (Bimp.tempSelectBitmap.size() > 0) {
                                int size = Bimp.tempSelectBitmap.size();
                                if (size > Constant.MAX_PHOTO_DYNAMIC_COUNT) {
                                    size = Constant.MAX_PHOTO_DYNAMIC_COUNT;
                                }
                                file = new File[size];
                                for (int i = 0; i < size; i++) {
                                    file[i] = new File(Bimp.tempSelectBitmap
                                            .get(i).getImagePath());
                                }
                            } else {
                                ToastUtil.show("请选择图片", TabTheIndividualDynaminActivity.this);
                                return;
                            }
                            setLoadingDialog(R.string.tip_sending);

                            Map<String, Object[]> files = new HashMap<String, Object[]>();
                            List<String> list = new ArrayList<String>();
                            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                                list.add(Bimp.tempSelectBitmap.get(i).getImagePath());
                            }

                            files.put("image", list.toArray());

                            /**
                             * 发布动态(content可以与动态图片或者视频兼容一起发送，但动态图片不能与视频一起发送)
                             *
                             * @param content
                             *            -动态内容
                             * @param img
                             *            (0-8)-动态图片
                             * @param cover
                             *            -视频封面
                             * @param film
                             *            -视频文件
                             * @param token
                             */
                            addTask(TabTheIndividualDynaminActivity.this,
                                    new IHttpTask(UrlLink.MYSHOWDYNAMICS_URL,
                                            paramsinit(individual_dynamic), files,
                                            MsgResult.class), HttpConfig.POST,
                                    DYNAMICS);
                        } else {
                            if (video == null) {
                                if (individual_dynamic.equals("")) {
                                    ToastUtil.show(getResources().getString(
                                            R.string.nin_talk), TabTheIndividualDynaminActivity.this);
                                } else {
                                    setLoadingDialog(R.string.tip_sending);
                                    addTask(TabTheIndividualDynaminActivity.this,
                                            new IHttpTask(
                                                    UrlLink.MYSHOWDYNAMICS_URL,
                                                    paramsinit(
                                                            individual_dynamic),
                                                    MsgResult.class),
                                            HttpConfig.POST, DYNAMICS);
                                }
                            } else {
                                setLoadingDialog(R.string.tip_sending);
                                //								addTask(TabTheIndividualDynaminActivity.this,
                                //										new IHttpTask(UrlLink.MYSHOWDYNAMICS_URL,
                                //												paramsinit(individual_dynamic,
                                //														file, cover, video),
                                //												MsgResult.class),
                                //										HttpConfig.POST, DYNAMICS);
                            }
                        }
                    }
                });
    }

    protected void startFrameAnimation() {
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

    protected void publishVideo(String content, Bitmap bitmap, String path) {


        Map<String, Object[]> files = new HashMap<String, Object[]>();
        List<Bitmap> list_bitmap = new ArrayList<Bitmap>();
        list_bitmap.add(bitmap);
        files.put("cover", list_bitmap.toArray());
        List<File> list_film = new ArrayList<File>();
        list_film.add(new File(path));
        files.put("film", list_film.toArray());
        addTask(TabTheIndividualDynaminActivity.this,
                new IHttpTask(UrlLink.MYSHOWDYNAMICS_URL,
                        paramsinit(content), files,
                        MsgResult.class), HttpConfig.POST,
                DYNAMICS);
        Bimp.tempSelectBitmap.clear();
        startFrameAnimation();
    }

    /**
     * 压缩动画，并跳转到选择封面界面
     */
    protected void playCompressAnimation() {
        startFrameAnimation();

        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        handler.sendEmptyMessageDelayed(0, duration);
    }

    private static final int RESULT_SELECT_VIDEO = 1;
    private String path;
    private AnimationDrawable animationDrawable;
    private static final int REQUEST_SELECT_VIDEO_COVER = 12;
    private static final int RESULT_SELECT_COVER = 4;
    private RelativeLayout rl_rotate_bg;
    private ImageView img_progress;
    private Bitmap select_cover;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (!TextUtils.isEmpty(path)) {
                Intent intent = new Intent(TabTheIndividualDynaminActivity.this, SelectVideoCoverActivity.class);
                intent.putExtra("path", path);
                startActivityForResult(intent, REQUEST_SELECT_VIDEO_COVER);
            }
        }

        ;
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.RC_SELECTABLUMN:
                if (resultCode == RESULT_OK) {
                    uploadPhoto = true;
                    uploadVideo = false;
                    refreshPopMenuStatus();
                    adapter.notifyDataSetChanged();
                } else if (resultCode == 202) {
                    finish();
                }
                break;
            case REQUEST_SELECT_VIDEO:
                if (resultCode == RESULT_SELECT_VIDEO) {
                    path = data.getStringExtra("path");
                    //首先播放动画
                    playCompressAnimation();
                } else if (resultCode == 202) {
                    finish();
                }
                break;
            case REQUEST_SELECT_VIDEO_COVER:
                if (resultCode == RESULT_SELECT_COVER) {
                    try {
                        select_cover = (Bitmap) data.getParcelableExtra("select_cover");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (select_cover != null) {
                        uploadVideo = true;
                        uploadPhoto = false;
                        Bimp.tempSelectBitmap.clear();
                        Bimp.tempSelectBitmap.add(new ImageItem());
                        adapter.notifyDataSetChanged();

                    }
                }
                break;
            case Constant.RC_TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    ImageItem takePhoto = new ImageItem();
                    // if (data != null) {
                    // freeBitmap();
                    // bimap = (Bitmap) data.getExtras().getParcelable("data");
                    // }
                    try {
                        File photoFile = MxFileUtil.getTakePhotoFile(
                                MxFileUtil.getTakePhotoFileFolder(),
                                currentAddFileName);
                        //					Uri u = Uri.parse(android.provider.MediaStore.Images.Media
                        //							.insertImage(getContentResolver(),
                        //									photoFile.getAbsolutePath(), null, null));
                        Uri u = Uri.parse(photoFile.getAbsolutePath());
                        if (u != null) {
                            //						int dpSize = (int) getResources().getDimension(
                            //								R.dimen.pref_145dp);
                            //						takePhoto.setBitmap(MxImageUtil.resizeImage(
                            //								photoFile.getAbsolutePath(), dpSize, dpSize));
                            takePhoto.setImagePath(photoFile.getAbsolutePath());
                            Bimp.tempSelectBitmap.add(takePhoto);
                            uploadPhoto = true;
                            uploadVideo = false;
                            refreshPopMenuStatus();
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.show(getResources().getString(
                                    R.string.error_no_get_origin_pic), TabTheIndividualDynaminActivity.this);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_origin_pic), TabTheIndividualDynaminActivity.this);

                    }
                }
                break;
            case Constant.RC_TAKEVIDEO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uriVideo = data.getData();
                        dealVideoInfo(uriVideo);

                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_local_video_info), TabTheIndividualDynaminActivity.this);
                    }
                }
                break;
            case Constant.RC_SELECTVIDEO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri uriVideo = data.getData();
                        dealVideoInfo(uriVideo);
                    } else {
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_local_video_info), TabTheIndividualDynaminActivity.this);
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //		if(currentAddFileName!= null && !isfirst){
        //			ImageItem takePhoto = new ImageItem();
        //			// if (data != null) {
        //			// freeBitmap();
        //			// bimap = (Bitmap) data.getExtras().getParcelable("data");
        //			// }
        //			try {
        //				File photoFile = MxFileUtil.getTakePhotoFile(
        //						MxFileUtil.getTakePhotoFileFolder(),
        //						currentAddFileName);
        ////				Uri u = Uri.parse(android.provider.MediaStore.Images.Media
        ////						.insertImage(getContentResolver(),
        ////								photoFile.getAbsolutePath(), null, null));
        //				Uri u = Uri.parse(photoFile.getAbsolutePath());
        //				if (u != null) {
        ////					int dpSize = (int) getResources().getDimension(
        ////							R.dimen.pref_145dp);
        ////					takePhoto.setBitmap(MxImageUtil.resizeImage(
        ////							photoFile.getAbsolutePath(), dpSize, dpSize));
        //					takePhoto.setImagePath(photoFile.getAbsolutePath());
        //					Bimp.tempSelectBitmap.add(takePhoto);
        //					uploadPhoto = true;
        //					uploadVideo = false;
        //					refreshPopMenuStatus();
        //					adapter.notifyDataSetChanged();
        //				} else {
        //					showShortToast(getResources().getString(
        //							R.string.error_no_get_origin_pic));
        //				}
        //			} catch (Exception e) {
        //				e.printStackTrace();
        //				showShortToast(getResources().getString(
        //						R.string.error_no_get_origin_pic));
        //			}
        //			currentAddFileName =null;
        //		}
        //		isfirst=false;
        System.out.println("集合大小 ： " + Bimp.tempSelectBitmap.size());
        if (Bimp.tempSelectBitmap.size() > 0 && !uploadVideo) {
            uploadPhoto = true;
            uploadVideo = false;
            refreshPopMenuStatus();
            adapter.notifyDataSetChanged();
        }
    }

    private void dealVideoInfo(Uri uriVideo) {
        ContentResolver resolver = getContentResolver();
        String fileType = resolver.getType(uriVideo);
        String filePath = uriVideo.getPath();
        File videoF = new File(filePath);
        // 先根据fileType判断该文件是不是视频文件
        if (fileType == null) {
            fileType = FileUtil.getMimeType(videoF);
            if (fileType.startsWith(FileUtil.VIDEO_3GPP)
                    || fileType.startsWith(FileUtil.VIDEO_MP4)) {
                try {
                    FileInputStream is = new FileInputStream(videoF);
                    // 若视频大于20Mb则提示视频文件过大
                    if (is.available() > 20 * 1024 * 1024) {
                        ToastUtil.show(getResources().getString(
                                R.string.error_no_get_local_video_too_big), TabTheIndividualDynaminActivity.this);
                        return;
                    }
                    videoFilePath = filePath;
                    video = MxFileUtil.loadFile(videoFilePath);
                    // ThumbnailUtils类2.2以上可用
                    uploadVideo = true;
                    uploadPhoto = false;
                    refreshPopMenuStatus();
                    ImageItem item = new ImageItem();
                    item.setBitmap(ThumbnailUtils.createVideoThumbnail(
                            filePath, Thumbnails.MICRO_KIND));
                    if (Bimp.tempSelectBitmap.size() == 1) {
                        Bimp.tempSelectBitmap.set(0, item);
                    } else {
                        Bimp.tempSelectBitmap.add(item);
                    }
                    adapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    ToastUtil.show(getResources().getString(
                            R.string.error_no_get_local_video_info), TabTheIndividualDynaminActivity.this);

                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtil.show(getResources().getString(
                            R.string.error_no_get_local_video_info), TabTheIndividualDynaminActivity.this);
                }
            } else {
                ToastUtil.show(getResources().getString(
                        R.string.error_no_get_local_video_is_not_video), TabTheIndividualDynaminActivity.this);
                return;
            }
        } else if (fileType.startsWith(FileUtil.VIDEO_3GPP)
                || fileType.startsWith(FileUtil.VIDEO_MP4)) {
            Cursor cursor = this.getContentResolver().query(uriVideo, null,
                    null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(VideoColumns._ID));
                int videoSize = cursor.getInt(cursor
                        .getColumnIndex(VideoColumns.SIZE));
                // 若视频大于20Mb则提示视频文件过大
                if (videoSize > 20 * 1024 * 1024) {
                    ToastUtil.show(getResources().getString(
                            R.string.error_no_get_local_video_too_big), TabTheIndividualDynaminActivity.this);
                    return;
                }
                videoFilePath = cursor.getString(cursor
                        .getColumnIndex(VideoColumns.DATA));
                video = MxFileUtil.loadFile(videoFilePath);
                /**
                 * 获取缩略图路径
                 */
                Cursor thumbCursor = managedQuery(
                        Thumbnails.EXTERNAL_CONTENT_URI,
                        Constant.THUMB_COLUMNS, Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    thumbPath = thumbCursor.getString(thumbCursor
                            .getColumnIndex(Thumbnails.DATA));
                    cover = MxFileUtil.loadFile(thumbPath);
                    if (VERSION.SDK_INT < 14) {
                        thumbCursor.close();
                    }
                } else {
                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(
                            filePath, Thumbnails.MINI_KIND);
                    cover = MxFileUtil.saveBitmap(bitmap,
                            String.valueOf(System.currentTimeMillis()));
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    System.gc();
                }
                if (VERSION.SDK_INT < 14) {
                    cursor.close();
                }
                uploadVideo = true;
                uploadPhoto = false;
                refreshPopMenuStatus();
                ImageItem item = new ImageItem();
                item.setBitmap(Thumbnails.getThumbnail(getContentResolver(),
                        id, Thumbnails.MICRO_KIND, null));
                if (Bimp.tempSelectBitmap.size() == 1) {
                    Bimp.tempSelectBitmap.set(0, item);
                } else {
                    Bimp.tempSelectBitmap.add(item);
                }
                adapter.notifyDataSetChanged();
            } else {
                ToastUtil.show(getResources().getString(
                        R.string.error_no_get_local_video_info), TabTheIndividualDynaminActivity.this);
            }
        }
        System.gc();
    }

    private void unshare() {
        isShare = false;
        mimageview_the_individual_dynamic_xinlang.setChecked(false);

    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        stoploadingDialog();
        switch (requestCode) {
            case DYNAMICS:
                MsgResult msgResult = (MsgResult) data;
                if (msgResult.getCode() == 1) {
                    ToastUtil.show(getResources().getString(
                            R.string.tip_sendingSuccess), TabTheIndividualDynaminActivity.this);
                    addTask(TabTheIndividualDynaminActivity.this, new IHttpTask(
                            UrlLink.DYNAMICSLATEST_URL, paramsinit2(""
                            + BaseApp.mInstance.getUser().getMxid()),
                            NewDynamicResult.class), HttpConfig.GET, NEWDYNAMICS);
                }
                break;

            case NEWDYNAMICS:
                NewDynamicResult dynamicResult = (NewDynamicResult) data;
                if (dynamicResult.getCode() == 1) {
                    if (dynamicResult.getData() != null
                            && dynamicResult.getData().getDynamic() != null) {
                        if (isShare) {
                            StringBuffer buffer = new StringBuffer();
                            //分享
                            // 正式平台 http://www.e-mxing.com/share/
                            //                            buffer.append(BaseUrlLink.SHARE_URL);
                            //                            String titleUrl = buffer.toString();
                            //                            sp.setTitleUrl(titleUrl);
                            //                            if (BaseApp.mInstance.isLogin()) {
                            //                                if (edittext_the_individual_dynamic.getText().toString().length() > 30) {
                            //                                    sp.setText("我的美型号" + BaseApp.mInstance.getUser().getMxid() + ",我在美型健身app发布了最新动态,求围观,求100个赞:\"" + edittext_the_individual_dynamic.getText().toString().substring(0, 30) + BaseUrlLink.WAP_URL + "(来自#美型#)");
                            //                                } else {
                            //                                    sp.setText("我的美型号" + BaseApp.mInstance.getUser().getMxid() + ",我在美型健身app发布了最新动态,求围观,求100个赞:\"" + edittext_the_individual_dynamic.getText().toString() + BaseUrlLink.WAP_URL + "(来自#美型#)");
                            //                                }
                            //                            }
                            //                            if (dynamicResult.getData().getDynamic()
                            //                                    .getCover() != null || dynamicResult.getData().getDynamic().getCover().equals("")) {
                            //                                sp.setImageUrl(dynamicResult.getData().getDynamic()
                            //                                        .getCover());
                            //                            } else {
                            //                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                            //                                        R.drawable.place_holder_logo);
                            //                                String path = FileUtil.stringPath(bitmap, Constants.FILE_FOLDER,
                            //                                        "logo.jpg");
                            //                                sp.setImagePath(path);
                            //                            }
                            //                            weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                            //                            weibo.share(sp);
                            //                        }
                            //                        if (lastClass != null) {
                            //                            Intent i = new Intent(getApplicationContext(),
                            //                                    lastClass);
                            //                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //                            startActivity(i);
                            //                        }
                            //                        else {
                            //                            finish();
                            //                        }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String reason, int requestCode) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        freeBitmap();
        freeSelectBitmap2();
    }

    private void freeBitmap() {
        if (bimap != null) {
            bimap.recycle();
            bimap = null;
        }
        System.gc();
    }

    protected void freeSelectBitmap2() {
        try {
            for (ImageItem item : Bimp.tempSelectBitmap) {
                if (item.getBitmap() != null) {
                    item.getBitmap().recycle();
                    item.setBitmap(null);
                }
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            if (uploadPhoto) {
                if (Bimp.tempSelectBitmap.size() >= Constant.MAX_PHOTO_DYNAMIC_COUNT) {
                    return Constant.MAX_PHOTO_DYNAMIC_COUNT;
                }
                return (Bimp.tempSelectBitmap.size() + 1);
            }
            return 1;
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //			if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView
                    .findViewById(R.id.item_grida_image);
            //				convertView.setTag(holder);
            //			} else {
            //				holder = (ViewHolder) convertView.getTag();
            //			}
            if (uploadPhoto) {
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                } else {
                    MXLog.out("aaaa:" + Bimp.tempSelectBitmap.size());
                    MXLog.out("bbbb:" + position);
                    if (position == Bimp.tempSelectBitmap.size()) {
                        MXLog.out("aaaaaaaaaaaaaaaaaaa");
                        holder.image.setImageBitmap(bimap);
                    } else {
                        //						holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(
                        //								position).getBitmap());
                        if (Bimp.tempSelectBitmap.get(
                                position).getImagePath().startsWith("http")) {
                            imageLoader.displayImage(Bimp.tempSelectBitmap.get(
                                    position).getImagePath(), holder.image, options);

                        } else {
                            imageLoader.displayImage("file://" + Bimp.tempSelectBitmap.get(
                                    position).getImagePath(), holder.image, options);
                        }

                    }
                }
            } else if (uploadVideo) {
                if (Bimp.tempSelectBitmap.size() == 1) {
                    holder.image.setImageBitmap(select_cover);
                }
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }
    }

    // @Override
    // public void onCancel(Platform platform, int action) {
    // Message msg = new Message();
    // msg.what = MSG_ACTION_CCALLBACK;
    // msg.arg1 = 3;
    // msg.arg2 = action;
    // msg.obj = platform;
    // unshare();
    // UIHandler.sendMessage(msg, this);
    //
    // }

    // @Override
    // public void onComplete(Platform platform, int action,
    // HashMap<String, Object> res) {
    // Message msg = new Message();
    // msg.what = MSG_ACTION_CCALLBACK;
    // msg.arg1 = 1;
    // msg.arg2 = action;
    // msg.obj = platform;
    //
    // if (platform.getName().equals(SinaWeibo.NAME)) {
    //
    // }
    // UIHandler.sendMessage(msg, this);
    // System.out.println(res);
    // // 获取资料
    // platform.getDb().getUserName();// 获取用户名字
    // platform.getDb().getUserIcon(); // 获取用户头像
    // platform.getDb().getToken();
    // platform.getDb().getUserId();
    //
    // }

    // @Override
    // public void onError(Platform platform, int action, Throwable t) {
    // Message msg = new Message();
    // msg.what = MSG_ACTION_CCALLBACK;
    // msg.arg1 = 2;
    // msg.arg2 = action;
    // msg.obj = t;
    // unshare();
    // isShare = true;
    // mimageview_the_individual_dynamic_xinlang.setChecked(true);
    // UIHandler.sendMessage(msg, this);
    // // 分享失败的统计
    // ShareSDK.logDemoEvent(4, platform);
    // }

    // @Override
    // public boolean handleMessage(Message msg) {
    // switch (msg.arg1) {
    // case 1: {
    // // 成功
    // Toast.makeText(TabTheIndividualDynaminActivity.this, "成功",
    // Toast.LENGTH_SHORT).show();
    // }
    // break;
    // case 2: {
    // // 失败
    // Toast.makeText(TabTheIndividualDynaminActivity.this, "失败",
    // Toast.LENGTH_SHORT).show();
    //
    // }
    // break;
    // case 3: {
    // // 取消
    // Toast.makeText(TabTheIndividualDynaminActivity.this, "取消····",
    // Toast.LENGTH_SHORT).show();
    // }
    // break;
    // }
    // return false;
    // }

    /**
     * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
     *
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    /**
     * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
     */
    private void setLeftCount() {
        textview_the_individuality_signature_zishu.setText(String
                .valueOf((MAX_COUNT - getInputCount())));
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(edittext_the_individual_dynamic.getText()
                .toString());
    }



    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            case 1: {
                // 成功
                Toast.makeText(TabTheIndividualDynaminActivity.this, "成功",
                        Toast.LENGTH_SHORT).show();
            }
            break;
            case 2: {
                // 失败
                Toast.makeText(TabTheIndividualDynaminActivity.this, "失败",
                        Toast.LENGTH_SHORT).show();

            }
            break;
            case 3: {
                // 取消
                Toast.makeText(TabTheIndividualDynaminActivity.this, "取消····",
                        Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (uploadVideo) {
                Bimp.tempSelectBitmap.clear();
            }
            if (window != null) {
                window.dismiss();
            }
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
