package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.home.view.DonutProgress;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

/**
 * 图片(支持普通图,gif,长图)预览适配器
 * Created by song on 2016/10/20.
 */
//TODO 新版希望用Fresco替换ImageLoad实现图片的加载,并且去掉多余的gif加载框架
public class ImagePreviewAdapter extends PagerAdapter{
    private Context context;
    private List<String> data;
    private View itemView;
    private DisplayImageOptions options;

    private HandleListener listener;

    public ImagePreviewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }

    public void setListener(HandleListener l) {
        this.listener = l;
    }

    @Override
    public int getCount() {
        if(data != null ){
            return data.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_preview, null);
        RelativeLayout rootView = (RelativeLayout)itemView.findViewById(R.id.root);
        final SubsamplingScaleImageView longImage = (SubsamplingScaleImageView) itemView.findViewById(R.id.image_long);
        final PhotoView normalImage = (PhotoView) itemView.findViewById(R.id.image_normal);
        final GifImageView gifImage = (GifImageView) itemView.findViewById(R.id.image_gif);
        final DonutProgress progress = (DonutProgress) itemView.findViewById(R.id.view_progress);
        setOnClickListener(rootView, normalImage,longImage, gifImage);
        setOnLongClickListener(rootView,position,normalImage,longImage, gifImage);
        ViewCompat.setTransitionName(normalImage, String.valueOf(position) + "transition");
        ViewCompat.setTransitionName(longImage, String.valueOf(position) + "transition");
        ViewCompat.setTransitionName(gifImage, String.valueOf(position) + "transition");
        ImageLoader.getInstance().loadImage(data.get(position), null, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (DiskCacheUtils.findInCache(data.get(position), ImageLoader.getInstance().getDiskCache()) == null) {
                    progress.setVisibility(View.VISIBLE);
                } else {
                    progress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progress.setVisibility(View.GONE);

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                File file = DiskCacheUtils.findInCache(data.get(position), ImageLoader.getInstance().getDiskCache());
                if (data.get(position).endsWith(".gif")) {
                    gifImage.setVisibility(View.VISIBLE);
                    longImage.setVisibility(View.INVISIBLE);
                    normalImage.setVisibility(View.INVISIBLE);
                    displayGif(file, gifImage);
                } else if (bitmap.getHeight() > bitmap.getWidth() * 3) {
                    longImage.setVisibility(View.VISIBLE);
                    gifImage.setVisibility(View.INVISIBLE);
                    normalImage.setVisibility(View.INVISIBLE);
                    displayLongImage(file, longImage);
                } else {
                    normalImage.setVisibility(View.VISIBLE);
                    gifImage.setVisibility(View.INVISIBLE);
                    longImage.setVisibility(View.INVISIBLE);
                    displayNormalImage(bitmap, normalImage);
                }
                progress.setProgress(100);
                progress.setVisibility(View.GONE);

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                progress.setProgress((int) 100.0f * current / total);
            }
        });
        container.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void setOnClickListener(RelativeLayout rootView, PhotoView normalImage, SubsamplingScaleImageView longImage, GifImageView gifImage) {
       /* rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.onSingleTag();
            }
        });*/

        normalImage.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                listener.onSingleTag(view);
            }

            @Override
            public void onOutsidePhotoTap() {
             //   listener.onSingleTag();
            }
        });

        longImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSingleTag(v);
            }
        });

        gifImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.onSingleTag(v);
            }
        });
    }

    private void setOnLongClickListener(final RelativeLayout rootView, final int position, PhotoView photoView,SubsamplingScaleImageView longImg, GifImageView gifImageView) {
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick();
                return false;
            }
        });

        longImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick();
                return false;
            }
        });

        gifImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick();
                return false;
            }
        });
    }



   public interface HandleListener {
       void onSingleTag(View view);
       void  onLongClick();
    }

    private void displayNormalImage(Bitmap bitmap, PhotoView photoView) {
        photoView.setImageBitmap(bitmap);
    }

    private void displayGif(File file, GifImageView gifImage) {
        try {
            GifDrawable gifDrawable = new GifDrawable(file);
            gifImage.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayLongImage(File file,SubsamplingScaleImageView longImg) {
        longImg.setQuickScaleEnabled(true);
        longImg.setZoomEnabled(true);
        longImg.setPanEnabled(true);
        longImg.setDoubleTapZoomDuration(100);
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
        longImg.setImage(ImageSource.uri(file.getAbsolutePath()), new ImageViewState(0, new PointF(0, 0), 0));
    }

}
