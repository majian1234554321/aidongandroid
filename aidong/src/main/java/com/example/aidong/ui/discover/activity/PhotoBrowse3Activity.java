//package com.example.aidong.ui.discover.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.bumptech.glide.Glide;
//import com.example.aidong.R;
//import com.example.aidong .entity.PhotoBrowseInfo;
//import com.example.aidong .ui.BaseActivity;
//import com.example.aidong .ui.discover.view.DotIndicator;
//import com.example.aidong .ui.discover.view.GalleryPhotoView;
//import com.example.aidong .utils.ImageSaveUtils;
//import com.example.aidong .utils.Logger;
//import com.example.aidong .utils.ToastGlobal;
//import com.example.aidong .widget.BaseAnimCloseViewPager;
//import com.example.aidong .widget.HackyViewPager;
//import com.example.aidong .widget.ScaleViewPager;
//import com.example.aidong .widget.dialog.BaseDialog;
//import com.example.aidong .widget.dialog.ButtonOkListener;
//import com.example.aidong .widget.dialog.DialogDoubleButton;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;
//
//
///**
// * 爱动圈动态图片预览
// */
//public class PhotoBrowse3Activity extends BaseActivity {
//    private static final String TAG = "PhotoBrowseActivity";
//
//    private ScaleViewPager photoViewpager;
//    private View blackBackground;
//    private DotIndicator dotIndicator;//小圆点指示器
//    private List<GalleryPhotoView> viewBuckets;
//    private PhotoBrowseInfo photoBrowseInfo;
//    private InnerPhotoViewerAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_browse);
//        preInitData();
//        initView();
//    }
//
//    private void preInitData() {
//        photoBrowseInfo = getIntent().getParcelableExtra("photoinfo");
//        viewBuckets = new LinkedList<>();
//        final int photoCount = photoBrowseInfo.getPhotosCount();
//        for (int i = 0; i < photoCount; i++) {
//            final GalleryPhotoView photoView = new GalleryPhotoView(this);
////            photoView.setCleanOnDetachedFromWindow(false);
////            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
////                @Override
////                public void onViewTap(View view, float x, float y) {
////                    finish();
////                }
////            });
//
//            photoView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    try {
//                        new DialogDoubleButton(PhotoBrowse3Activity.this)
//                                .setContentDesc("点击确定保存图片到相册")
//                                .setBtnOkListener(new ButtonOkListener() {
//                                    @Override
//                                    public void onClick(BaseDialog dialog) {
//                                        photoView.setDrawingCacheEnabled(true);
//                                        boolean result = ImageSaveUtils.saveImageToGallery(PhotoBrowse3Activity.this,
//                                                photoView.getDrawingCache());
//                                        photoView.setDrawingCacheEnabled(false);
//                                        if (result) {
//                                            ToastGlobal.showLong(R.string.save_success);
//                                        } else {
//                                            ToastGlobal.showLong(R.string.save_fail);
//                                        }
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//                        return true;
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        return false;
//                    }
//                }
//            });
//
//            viewBuckets.add(photoView);
//        }
//    }
//
//    boolean canDrag =false;
//    private void initView() {
//        photoViewpager = (ScaleViewPager) findViewById(R.id.viewpager);
//        //blackBackground = findViewById(R.id.view_background);
//        dotIndicator = (DotIndicator) findViewById(R.id.dot_indicator);
//
//        dotIndicator.init(this, photoBrowseInfo.getPhotosCount());
//        dotIndicator.setCurrentSelection(photoBrowseInfo.getCurrentPhotoPosition());
//
//        adapter = new InnerPhotoViewerAdapter(this);
//        photoViewpager.setAdapter(adapter);
//       // photoViewpager.setLocked(photoBrowseInfo.getPhotosCount() == 1);
//        photoViewpager.setCurrentItem(photoBrowseInfo.getCurrentPhotoPosition());
//        photoViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                dotIndicator.setCurrentSelection(position);
//            }
//        });
//
//
//        photoViewpager.setiAnimClose(new BaseAnimCloseViewPager.IAnimClose() {
//            @Override
//            public boolean canDrag() {
//                return canDrag;
//            }
//
//            @Override
//            public void onPictureClick() {
//                finishAfterTransition();
//            }
//
//            @Override
//            public void onPictureRelease(View view) {
//                finishAfterTransition();
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (viewBuckets != null && !viewBuckets.isEmpty()) {
//            for (PhotoView photoView : viewBuckets) {
//                photoView.destroy();
//            }
//        }
//        super.onDestroy();
//    }
//
//    //=============================================================Tools method
//    public static void start(Activity from, PhotoBrowseInfo info) {
//        if (info == null || !info.isValided()) return;
//        Intent intent = new Intent(from, PhotoBrowseActivity.class);
//        intent.putExtra("photoinfo", info);
//        from.startActivity(intent);
//        //禁用动画
//       // from.overridePendingTransition(0, 0);
//    }
//
//    @Override
//    public void finish() {
//        final GalleryPhotoView currentPhotoView = viewBuckets.get(photoViewpager.getCurrentItem());
//        if (currentPhotoView == null) {
//            Logger.e(TAG, "childView is null");
//            super.finish();
//            return;
//        }
//        dotIndicator.setVisibility(View.GONE);
//        final Rect endRect = photoBrowseInfo.getViewLocalRects().get(photoViewpager.getCurrentItem());
//        currentPhotoView.playExitAnimal(endRect, blackBackground, new GalleryPhotoView.OnExitAnimalEndListener() {
//            @Override
//            public void onExitAnimalEnd() {
//                PhotoBrowse3Activity.super.finish();
//                overridePendingTransition(0, 0);
//            }
//        });
//    }
//
//    //=============================================================InnerAdapter
//
//    private class InnerPhotoViewerAdapter extends PagerAdapter {
//        private Context context;
//        private boolean isFirstInitlize;
//
//        public InnerPhotoViewerAdapter(Context context) {
//            this.context = context;
//            isFirstInitlize = true;
//        }
//
//        @Override
//        public int getCount() {
//            return photoBrowseInfo.getPhotosCount();
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            GalleryPhotoView photoView = viewBuckets.get(position);
//            String photoUrl = photoBrowseInfo.getPhotoUrls().get(position);
//            Glide.with(PhotoBrowse3Activity.this).load(photoUrl).placeholder(R.drawable.img_default).error(R.drawable.img_default).into(photoView);
//            container.addView(photoView);
//            return photoView;
//        }
//
//        @Override
//        public void setPrimaryItem(ViewGroup container, final int position, final Object object) {
//            if (isFirstInitlize && object instanceof GalleryPhotoView && position == photoBrowseInfo.getCurrentPhotoPosition()) {
//                isFirstInitlize = false;
//                canDrag = false;
//                final GalleryPhotoView targetView = (GalleryPhotoView) object;
//                final Rect startRect = photoBrowseInfo.getViewLocalRects().get(position);
//                targetView.playEnterAnimal(startRect, null);
//            }
//            super.setPrimaryItem(container, position, object);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//    }
//
//
//
//}
