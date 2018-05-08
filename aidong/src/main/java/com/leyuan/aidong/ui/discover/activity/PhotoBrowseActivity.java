package com.leyuan.aidong.ui.discover.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.services.DownLoadImageService;
import com.leyuan.aidong.services.ImageDownLoadCallBack;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.discover.view.DotIndicator;
import com.leyuan.aidong.utils.ImageSaveUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.BaseAnimCloseViewPager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 */
public class PhotoBrowseActivity extends AppCompatActivity {


    private int firstDisplayImageIndex = 0;
    private boolean newPageSelected = false;
    private ImageView mCurImage;
    private BaseAnimCloseViewPager imageViewPager;
    private List<String> pictureList;

    PagerAdapter adapter;

    boolean canDrag =false;
    private DotIndicator dotIndicator;
    private DownLoadImageService service;

    public static void start(Activity context, PhotoBrowseInfo info,View view) {
        if (info == null || !info.isValided()) return;
        Intent intent = new Intent(context, PhotoBrowseActivity.class);
        intent.putExtra("photoinfo", info);




        ActivityOptionsCompat compat = null;
        if (view == null) {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context);
        } else {
            compat = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view,
                    "tansition_view");
        }
        ActivityCompat.startActivity(context, intent, compat.toBundle());


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_photo_browse);
        initView();
    }

    public void initView() {


        PhotoBrowseInfo photoBrowseInfo = getIntent().getParcelableExtra("photoinfo");
        pictureList = photoBrowseInfo.getPhotoUrls();
        dotIndicator = (DotIndicator) findViewById(R.id.dot_indicator);
        TextView save = (TextView) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onDownLoad(pictureList.get(dotIndicator.getCurrentSelection()  ));


            }
        });
        dotIndicator.init(this, photoBrowseInfo.getPhotosCount());
        dotIndicator.setCurrentSelection(photoBrowseInfo.getCurrentPhotoPosition());




        firstDisplayImageIndex = photoBrowseInfo.getCurrentPhotoPosition();

        imageViewPager = (BaseAnimCloseViewPager) findViewById(R.id.viewpager);
        setViewPagerAdapter();

        setEnterSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                ViewGroup layout = (ViewGroup) imageViewPager.findViewWithTag(imageViewPager.getCurrentItem());
                if (layout == null) {
                    return;
                }
                View sharedView = layout.findViewById(R.id.image_view);
                sharedElements.clear();
                sharedElements.put("tansition_view", sharedView);
            }
        });
    }

    private void setViewPagerAdapter() {
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return pictureList == null ? 0 : pictureList.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View layout = (View) object;
                container.removeView(layout);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View layout;
                layout = LayoutInflater.from(PhotoBrowseActivity.this).inflate(R.layout.layout_browse, null);
//                layout.setOnClickListener(onClickListener);
                container.addView(layout);
                layout.setTag(position);
//                layout.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        try {
//                            new DialogDoubleButton(PhotoBrowseActivity.this)
//                                    .setContentDesc("点击确定保存图片到相册")
//                                    .setBtnOkListener(new ButtonOkListener() {
//                                        @Override
//                                        public void onClick(BaseDialog dialog) {
//
//                                            boolean result = ImageSaveUtils.saveImageToGallery(PhotoBrowseActivity.this,
//                                                    returnBitMap(pictureList.get(position)));
//
//                                            if (result) {
//                                                ToastGlobal.showLong(R.string.save_success);
//                                            } else {
//                                                ToastGlobal.showLong(R.string.save_fail);
//                                            }
//                                            dialog.dismiss();
//                                        }
//                                    }).show();
//                            return true;
//                        }catch (Exception e){
//                            e.printStackTrace();
//                            return false;
//                        }
//                    }
//                });

                if (position == firstDisplayImageIndex) {
                    onViewPagerSelected(position);
                }

                return layout;

            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };

        imageViewPager.setAdapter(adapter);
        imageViewPager.setOffscreenPageLimit(1);
        imageViewPager.setCurrentItem(firstDisplayImageIndex);
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0f && newPageSelected) {
                    newPageSelected = false;
                    onViewPagerSelected(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                newPageSelected = true;
                dotIndicator.setCurrentSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imageViewPager.setiAnimClose(new BaseAnimCloseViewPager.IAnimClose() {
            @Override
            public boolean canDrag() {
                return canDrag;
            }

            @Override
            public void onPictureClick() {
                finishAfterTransition();
            }

            @Override
            public void onPictureRelease(View view) {
                finishAfterTransition();
            }
        });
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finishAfterTransition();
        }
    };


    private void onViewPagerSelected(int position) {
        updateCurrentImageView(position);
        setImageView(pictureList.get(position));
    }


    /**
     * 设置图片
     *
     * @param path
     */
    private void setImageView(final String path) {
        if (mCurImage.getDrawable() != null)//判断是否已经加载了图片，避免闪动
            return;
        if (TextUtils.isEmpty(path)) {
            mCurImage.setBackgroundColor(Color.GRAY);
            return;
        }
        canDrag = false;
        Glide.with(this).load(path).into(mCurImage);
    }

    // 初始化每个view的image
    protected void updateCurrentImageView(final int position) {
        View currentLayout = imageViewPager.findViewWithTag(position);
        if (currentLayout == null) {
            ViewCompat.postOnAnimation(imageViewPager, new Runnable() {

                @Override
                public void run() {
                    updateCurrentImageView(position);
                }
            });
            return;
        }
        mCurImage = (ImageView) currentLayout.findViewById(R.id.image_view);
        imageViewPager.setCurrentShowView(mCurImage);
    }


    @Override
    public void finishAfterTransition() {
        Intent intent = new Intent();
        intent.putExtra("index", imageViewPager.getCurrentItem());
        setResult(RESULT_OK, intent);
        super.finishAfterTransition();
    }


    public Bitmap returnBitMap(final String url){
        final Bitmap[] bitmap = new Bitmap[1];
        new Thread(new Runnable() {



            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap[0];
    }



    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10086:
                    Toast.makeText(App.context, "保存成功", Toast.LENGTH_SHORT).show();
                    break;

                case 10085:
                    Toast.makeText(App.context, "保存失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void onDownLoad(String url) {
        // 在这里执行图片保存方法
// 图片保存失败
        service = new DownLoadImageService(getApplicationContext(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {
                    }
                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法
                        Message message = new Message();
                        message.what = 10086;
                        handler.sendMessageDelayed(message, 1000);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        Message message = new Message();
                        message.what = 10085;
                        handler.sendMessageDelayed(message, 1000);
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (service!=null) {
            handler.removeCallbacks(service);
        }

    }
}
