package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.LiveHomeResult;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.entity.video.LiveVideoListResult;
import com.leyuan.aidong.entity.video.LiveVideoSoonInfo;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.LiveDateFilterUtil;
import com.leyuan.aidong.widget.MyListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeVideoAdapter extends RecyclerView.Adapter<HomeVideoAdapter.ViewHolder> {

    private ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
    private ArrayList<LiveVideoSoonInfo> liveDateArray = new ArrayList<>();
    private LiveVideoInfo liveHome;
    private Context context;

    private OnLivingVideoCLickListener mOnLivingVideoCLickListener;
    private OnPlaybackClickListener mOnPlaybackClickListener;
    private OnSoonLiveVideoClickListener mOnSoonLiveVideoClickListener;
    private OnVideoClickListener mOnVideoClickListener;

    public static final int TYPE_HEADER = 1, TYPE_ITEM = 2;


    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Calendar calendar = Calendar.getInstance();
    private LiveVideoDataAdapter liveMoreAdapter;
    private ArrayList<View> livingViews = new ArrayList<>();

    public HomeVideoAdapter(Context context, OnLivingVideoCLickListener livingVideoCLickListener,
                            OnPlaybackClickListener onPlaybackClickListener, OnSoonLiveVideoClickListener
                                    onSoonLiveVideoClickListener, OnVideoClickListener onVideoClickListener) {
        this.context = context;
        mOnLivingVideoCLickListener = livingVideoCLickListener;
        mOnPlaybackClickListener = onPlaybackClickListener;
        mOnSoonLiveVideoClickListener = onSoonLiveVideoClickListener;
        mOnVideoClickListener = onVideoClickListener;

    }

    public void refreshData(LiveHomeResult.LiveHome liveHome) {
        this.livingVideos.clear();
        this.liveDateArray.clear();
        this.liveHome = liveHome.getEmpty();
        if (liveHome.getNow() != null)
            this.livingVideos.addAll(liveHome.getNow());


    }

    public void refreshData(ArrayList<LiveVideoInfo> livingVideos, ArrayList<LiveVideoSoonInfo> liveDateArray, LiveVideoInfo empty) {
        this.liveHome = empty;
        this.livingVideos.clear();
        if (livingVideos != null)
            this.livingVideos.addAll(livingVideos);

        this.liveDateArray.clear();
        if (liveDateArray != null)
            this.liveDateArray.addAll(liveDateArray);
        notifyDataSetChanged();
    }

    public void refreshData(LiveVideoListResult.Result result) {
        this.livingVideos.clear();
        this.liveDateArray.clear();
        if (result.getLiveVideo() != null) {
            livingVideos.addAll(result.getLiveVideo());
        }
        if (result.getLiveVideoMore() != null) {
            this.liveDateArray.addAll(result.getLiveVideoMore());
        }
        liveHome = result.getLiveHome();

        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<LiveVideoSoonInfo> liveDateArray) {
        if (liveDateArray != null)
            this.liveDateArray.addAll(liveDateArray);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_HEADER:
                view = View.inflate(context, R.layout.head_live_video_home, null);
                break;
            case TYPE_ITEM:
                view = View.inflate(context, R.layout.item_live_video_home, null);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                initLivingVideoList(holder);

                holder.img_special.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnPlaybackClickListener.onSpecialClick();
                    }
                });

                holder.img_deep_into.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnPlaybackClickListener.onDeepIntoClick();
                    }
                });

                holder.img_celebrity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnPlaybackClickListener.onCelebrityClick();
                    }
                });

                holder.img_play_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnPlaybackClickListener.onSpecialClick();
                    }
                });

                break;
            case TYPE_ITEM:
                LiveVideoSoonInfo info = liveDateArray.get(i - 1);

                holder.txt_live_date.setText("" + LiveDateFilterUtil.compareDate(info.getTime()));

                if (LiveDateFilterUtil.compareDate(info.getTime()).contains("今日")) {
                    liveMoreAdapter = new LiveVideoDataAdapter(context, info.getLiveVideoList(), mOnSoonLiveVideoClickListener, true);
                    holder.list_live.setAdapter(liveMoreAdapter);
                } else {
                    LiveVideoDataAdapter adapter = new LiveVideoDataAdapter(context, info.getLiveVideoList(), mOnSoonLiveVideoClickListener, false);
                    holder.list_live.setAdapter(adapter);
                }

                break;
        }
    }


    private void initLivingViews() {
        for (final LiveVideoInfo info : livingVideos) {
            View itemView = View.inflate(context, R.layout.item_living_video_adpater, null);
            ImageView img_living_bg = (ImageView) itemView.findViewById(R.id.img_living_bg);
            GlideLoader.getInstance().displayImage(info.getLiveCover(), img_living_bg);
            img_living_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLivingVideoCLickListener.onLivingVideoCLick(info);
                }
            });
            ((TextView) itemView.findViewById(R.id.txt_author)).setText("" + info.getLiveAuthor());
            ((TextView) itemView.findViewById(R.id.txt_course)).setText("" + info.getLiveName());
            ((TextView) itemView.findViewById(R.id.txt_viewers)).setText("" + info.getPersonCou());
            livingViews.add(itemView);
        }
    }

    private void initLivingVideoList(final ViewHolder holder) {
        if (livingVideos.size() > 1) {
            holder.viewPager_living.setVisibility(View.VISIBLE);
            holder.layout_single_living.setVisibility(View.GONE);
            holder.img_default_none_live.setVisibility(View.GONE);

            livingViews.clear();

            while (livingViews.size() < 5) {
                initLivingViews();
            }

            holder.viewPager_living.setOffscreenPageLimit(3);
            //            holder.viewPager_living.setPageMargin(30);
            holder.viewPager_living.setAdapter(new BannerAdapter());
            holder.viewPager_living.setCurrentItem(10000);
            holder.rel_living.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return holder.viewPager_living.dispatchTouchEvent(event);
                }
            });
            //            LinearLayoutManager livingManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            //            holder.recycler_living.setLayoutManager(livingManager);
            //            LivingVideosAdapter adapter = new LivingVideosAdapter(context, livingVideos, mOnLivingVideoCLickListener);
            //            holder.recycler_living.setAdapter(adapter);


        } else if (livingVideos.size() == 1) {
            holder.viewPager_living.setVisibility(View.GONE);
            holder.img_default_none_live.setVisibility(View.GONE);
            holder.layout_single_living.setVisibility(View.VISIBLE);

            final LiveVideoInfo info = livingVideos.get(0);
            GlideLoader.getInstance().displayImage(info.getLiveCover(), holder.img_living_bg);
            holder.txt_author.setText("" + info.getLiveAuthor());
            holder.txt_course.setText("" + info.getLiveName());
            holder.txt_viewers.setText("" + info.getPersonCou());
            holder.img_living_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnLivingVideoCLickListener.onLivingVideoCLick(info);
                }
            });
        } else if (liveHome != null) {
            holder.viewPager_living.setVisibility(View.GONE);
            holder.img_default_none_live.setVisibility(View.VISIBLE);
            holder.layout_single_living.setVisibility(View.GONE);
            GlideLoader.getInstance().displayImage(liveHome.getLiveCover(), holder.img_default_none_live);
            holder.img_default_none_live.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnVideoClickListener.onVideoClick(liveHome.getLiveId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return liveDateArray.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void notifyCountDown() {
        if (liveMoreAdapter != null) {
            liveMoreAdapter.notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout layout_living;
        RelativeLayout layout_single_living, rel_living;
        //        RecyclerView recycler_living;
        ViewPager viewPager_living;
        MyListView list_live;
        ImageView img_living_bg, img_living, img_special, img_deep_into, img_celebrity, img_default_none_live, img_play_back;
        TextView txt_author, txt_course, txt_viewers, txt_live_date;

        public ViewHolder(View itemView) {
            super(itemView);
            layout_living = (FrameLayout) itemView.findViewById(R.id.layout_living);
            viewPager_living = (ViewPager) itemView.findViewById(R.id.viewPager_living);
            layout_single_living = (RelativeLayout) itemView.findViewById(R.id.layout_single_living);
            rel_living = (RelativeLayout) itemView.findViewById(R.id.rel_living);
            list_live = (MyListView) itemView.findViewById(R.id.list_live);
            img_living_bg = (ImageView) itemView.findViewById(R.id.img_living_bg);
            img_living = (ImageView) itemView.findViewById(R.id.img_living);
            img_special = (ImageView) itemView.findViewById(R.id.img_special);
            img_deep_into = (ImageView) itemView.findViewById(R.id.img_deep_into);
            img_celebrity = (ImageView) itemView.findViewById(R.id.img_celebrity);
            img_play_back = (ImageView) itemView.findViewById(R.id.img_play_back);

            img_default_none_live = (ImageView) itemView.findViewById(R.id.img_default_none_live);

            txt_author = (TextView) itemView.findViewById(R.id.txt_author);
            txt_course = (TextView) itemView.findViewById(R.id.txt_course);
            txt_viewers = (TextView) itemView.findViewById(R.id.txt_viewers);
            txt_live_date = (TextView) itemView.findViewById(R.id.txt_live_date);

        }
    }


    public interface OnLivingVideoCLickListener {
        void onLivingVideoCLick(LiveVideoInfo liveInfo);
    }

    public interface OnPlaybackClickListener {
        void onSpecialClick();

        void onDeepIntoClick();

        void onCelebrityClick();
    }

    public interface OnSoonLiveVideoClickListener {
        void onSoonLivingVideoCLick(LiveVideoInfo liveInfo);
    }

    public interface OnVideoClickListener {
        void onVideoClick(int id);
    }


    /**
     * ViewPager的适配器
     */
    class BannerAdapter extends PagerAdapter {


        //        private ArrayList<View> views = new ArrayList<>();
        //
        //        public BannerAdapter(ArrayList<View> views) {
        //            this.views.addAll(views);
        //        }

        @Override
        public int getCount() {
            //将count设置成整数最大值，虚拟实现无线轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //            container.removeView(livingViews.get(position % livingViews.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = livingViews.get(position % livingViews.size());
            ViewGroup parent = (ViewGroup) view.getParent();
            //如果当前要显示的view有父布局先将父布局移除（view只能有一个父布局）
            if (parent != null) {
                parent.removeView(view);
            }
            container.addView(view);
            return view;
        }

        //        @Override
        //        public float getPageWidth(int position) {
        //            return 0.8f;
        //        }
    }
}
