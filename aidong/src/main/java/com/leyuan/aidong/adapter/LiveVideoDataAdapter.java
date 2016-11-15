package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.utils.LiveDateFilterUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class LiveVideoDataAdapter extends android.widget.BaseAdapter {
    private Context context;
    private ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
    private HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions mOptions;
    private boolean countDown;

    public LiveVideoDataAdapter(Context context, ArrayList<LiveVideoInfo> livingVideos, HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener, boolean countDown) {
        this.context = context;
        this.countDown = countDown;
        this.onSoonLiveVideoClickListener = onSoonLiveVideoClickListener;

        if (livingVideos != null) {
            this.livingVideos.addAll(livingVideos);
        }
        mOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.img_default)
                .showImageOnFail(R.drawable.img_default)
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return livingVideos.size();
    }

    @Override
    public Object getItem(int position) {
        return livingVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.item_live_video_date_list, null);
        ImageView img_live_bg = (ImageView) convertView.findViewById(R.id.img_live_bg);
        ImageView img_calendar = (ImageView) convertView.findViewById(R.id.img_calendar);
        TextView txt_count = (TextView) convertView.findViewById(R.id.txt_count);
        TextView txt_clock = (TextView) convertView.findViewById(R.id.txt_clock);
        TextView txt_author = (TextView) convertView.findViewById(R.id.txt_author);
        TextView txt_live_name = (TextView) convertView.findViewById(R.id.txt_live_name);
        final LiveVideoInfo info = livingVideos.get(position);
        mImageLoader.displayImage(info.getLiveCover(), img_live_bg, mOptions);
        txt_author.setText("" + info.getLiveAuthor());
        txt_live_name.setText("" + info.getLiveName());

        if (countDown) {
            int timeRemain = LiveDateFilterUtil.compareTime(info.getLiveBeginTime());
            if (timeRemain > 0) {
                txt_count.setText("" + LiveDateFilterUtil.convertSecondsToHMmSs(timeRemain));
            } else {
                txt_count.setText("直播已开始");
            }
            img_calendar.setVisibility(View.GONE);
            txt_clock.setVisibility(View.VISIBLE);

            //            txt_count.setText(""+LiveDateFilterUtil.countDownTime(info.getLiveBeginTime()));
        } else {
            img_calendar.setVisibility(View.VISIBLE);
            txt_clock.setVisibility(View.GONE);
            txt_count.setText("" + info.getLiveBeginTime().substring(11));
        }

        img_live_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSoonLiveVideoClickListener != null) {
                    onSoonLiveVideoClickListener.onSoonLivingVideoCLick(info.getLiveId());
                }
            }
        });

        return convertView;
    }


}
