package com.leyuan.aidong.ui.video.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.HomeVideoAdapter;
import com.leyuan.aidong.entity.video.LiveHomeResult;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.entity.video.LiveVideoSoonInfo;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.impl.LivePresenterImpl;
import com.leyuan.aidong.ui.mvp.view.LiveHomeView;
import com.leyuan.aidong.ui.video.activity.LiveDetailActivity;
import com.leyuan.aidong.ui.video.activity.LivingVideoActivity;
import com.leyuan.aidong.ui.video.activity.VideoDetailActivity;
import com.leyuan.aidong.ui.video.activity.WatchOfficeActivity;
import com.leyuan.aidong.utils.Constant;


public class VideoHomeFragment extends BaseFragment implements HomeVideoAdapter.OnLivingVideoCLickListener,
        HomeVideoAdapter.OnPlaybackClickListener, HomeVideoAdapter.OnSoonLiveVideoClickListener,
        HomeVideoAdapter.OnVideoClickListener, SwipeRefreshLayout.OnRefreshListener, LiveHomeView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private HomeVideoAdapter adapter;
    private int page;
    private LivePresenterImpl presenter;

    private static final int COUNT_DOWN = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COUNT_DOWN:
                    adapter.notifyCountDown();
                    mHandler.sendEmptyMessageDelayed(COUNT_DOWN, 1000);
                    break;
            }
        }
    };

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            onRefresh();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new LivePresenterImpl(getActivity(), this);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        initData();
    }

    private void initData() {
        swipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new HomeVideoAdapter(getActivity(), this, this, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromInter();
    }

    private void getDataFromInter() {
        page = 1;
        presenter.getLiveHome();
    }


    private void getMoreDataFromInter() {
        page++;
    }

    @Override
    public void onRefresh() {
        getDataFromInter();
    }

    @Override
    public void onLivingVideoCLick(LiveVideoInfo info) {
        Intent intent = new Intent(getActivity(), LivingVideoActivity.class);
//        intent.putExtra(Common.LIVE_INFO, info);
        intent.putExtra(Constant.LIVE_INFO, info);
        startActivity(intent);
    }

    @Override
    public void onSpecialClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Constant.VIDEO_TYPE, 0);
        startActivity(intent);
    }

    @Override
    public void onDeepIntoClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Constant.VIDEO_TYPE, 1);
        startActivity(intent);

    }

    @Override
    public void onCelebrityClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Constant.VIDEO_TYPE, 2);
        startActivity(intent);
    }

    @Override
    public void onSoonLivingVideoCLick(LiveVideoInfo liveInfo) {
        Intent intent = new Intent(getActivity(), LiveDetailActivity.class);
        intent.putExtra(Constant.LIVE_INFO, liveInfo);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }

    @Override
    public void onVideoClick(int id) {
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onGetLiveHomeData(LiveHomeResult.LiveHome liveHome) {
        swipeRefreshLayout.setRefreshing(false);
        if (liveHome != null) {
            adapter.refreshData(liveHome.getNow(),
                    LiveVideoSoonInfo.createMoreLive(liveHome.getMore()), liveHome.getEmpty());
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(COUNT_DOWN, 1000);
        }
    }
}
