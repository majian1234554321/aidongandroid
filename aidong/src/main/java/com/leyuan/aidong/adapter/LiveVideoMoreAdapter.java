package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.LiveDateFilterUtil;

import java.util.ArrayList;

/**
 * Created by user on 2017/5/5.
 */
public class LiveVideoMoreAdapter extends RecyclerView.Adapter<LiveVideoMoreAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
    private HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener;
    private LayoutInflater inflater;

    public LiveVideoMoreAdapter(Context context, ArrayList<LiveVideoInfo> livingVideos,
                                HomeVideoAdapter.OnSoonLiveVideoClickListener onSoonLiveVideoClickListener) {
        this.context = context;
        this.onSoonLiveVideoClickListener = onSoonLiveVideoClickListener;
        this.livingVideos = livingVideos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_live_video_date_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final LiveVideoInfo info = livingVideos.get(position);
        GlideLoader.getInstance().displayImage(info.getLiveCover(), holder.img_live_bg);
        holder.txt_author.setText("" + info.getLiveAuthor());
        holder.txt_live_name.setText("" + info.getLiveName());

        int timeRemain = LiveDateFilterUtil.compareTime(info.getLiveBeginTime());
        if (timeRemain > 0) {
            holder.txt_count.setText(LiveDateFilterUtil.convertSecondsToDayHms(timeRemain));
        } else {
            holder.txt_count.setText("直播已开始");
        }
        holder.img_calendar.setVisibility(View.GONE);
        holder.txt_clock.setVisibility(View.VISIBLE);

        holder.img_live_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSoonLiveVideoClickListener != null) {
                    onSoonLiveVideoClickListener.onSoonLivingVideoCLick(info);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (livingVideos == null)
            return 0;
        return livingVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_live_bg;
        private ImageView img_calendar;
        private TextView txt_clock;
        private TextView txt_count;
        private TextView txt_author;
        private TextView txt_live_name;

        public ViewHolder(View view) {
            super(view);
            img_live_bg = (ImageView) view.findViewById(R.id.img_live_bg);
            img_calendar = (ImageView) view.findViewById(R.id.img_calendar);
            txt_clock = (TextView) view.findViewById(R.id.txt_clock);
            txt_count = (TextView) view.findViewById(R.id.txt_count);
            txt_author = (TextView) view.findViewById(R.id.txt_author);
            txt_live_name = (TextView) view.findViewById(R.id.txt_live_name);
        }
    }
}
