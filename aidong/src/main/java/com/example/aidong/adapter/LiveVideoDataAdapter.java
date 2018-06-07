package com.example.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.video.LiveVideoInfo;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.LiveDateFilterUtil;

import java.util.ArrayList;

public class LiveVideoDataAdapter extends android.widget.BaseAdapter {
    private Context context;
    private ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
    private HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener;


    private boolean countDown;

    public LiveVideoDataAdapter(Context context, ArrayList<LiveVideoInfo> livingVideos, HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener, boolean countDown) {
        this.context = context;
        this.countDown = countDown;
        this.onSoonLiveVideoClickListener = onSoonLiveVideoClickListener;

        if (livingVideos != null) {
            this.livingVideos.addAll(livingVideos);
        }

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
        GlideLoader.getInstance().displayImage(info.getLiveCover(), img_live_bg);
        txt_author.setText("" + info.getLiveAuthor());
        txt_live_name.setText("" + info.getLiveName());

        if (countDown) {
            int timeRemain = LiveDateFilterUtil.compareTime(info.getLiveBeginTime());
            if (timeRemain > 0) {
//                txt_count.setText("" + LiveDateFilterUtil.convertSecondsToHMmSs(timeRemain));

                txt_count.setText(LiveDateFilterUtil.convertSecondsToDayHms(timeRemain));
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
                    onSoonLiveVideoClickListener.onSoonLivingVideoCLick(info);
                }
            }
        });

        return convertView;
    }


}

