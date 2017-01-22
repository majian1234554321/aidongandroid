package com.leyuan.aidong.ui.fragment.video;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.HomeVideoAdapter;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.entity.video.LiveVideoListResult;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.video.LiveDetailActivity;
import com.leyuan.aidong.ui.activity.video.LivingVideoActivity;
import com.leyuan.aidong.ui.activity.video.VideoDetailActivity;
import com.leyuan.aidong.ui.activity.video.WatchOfficeActivity;
import com.leyuan.aidong.utils.common.Common;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class VideoHomeFragment extends BaseFragment implements HomeVideoAdapter.OnLivingVideoCLickListener,
        HomeVideoAdapter.OnPlaybackClickListener, HomeVideoAdapter.OnSoonLiveVideoClickListener,
        HomeVideoAdapter.OnVideoClickListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private HomeVideoAdapter adapter;
    private int page;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_home, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageCurrent", String.valueOf(page));
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_LIVING_VIDEO_HOME, params, callback);
    }


    private void getMoreDataFromInter() {
        page++;
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageCurrent", String.valueOf(page));
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_LIVING_VIDEO_HOME, params, callbackMore);
    }

    private RequestCallBack<String> callback = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            swipeRefreshLayout.setRefreshing(false);
            try {
                LiveVideoListResult result = new Gson().fromJson(responseInfo.result, LiveVideoListResult.class);
                if (result != null && result.getCode() == 1 && result.getResult() != null) {
                    adapter.refreshData(result.getResult());
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendEmptyMessageDelayed(COUNT_DOWN, 1000);
                    //                    ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
                    //                    livingVideos.add(new LiveVideoInfo(1, "颠三倒四", "大多数", "http:\\/\\/7xvyvy.com1.z0.glb.clouddn.com\\/\\/fit\\/livevideo\\/cover\\/07b9fb16-f33f-40b8-a9d7-59c51c7eb3ea.jpg?imageMogr2\\/thumbnail\\/750x750\\/interlace\\/1", "", "", 123213, ""));
                    //                    livingVideos.add(new LiveVideoInfo(1, "颠三倒四", "大多数", "http:\\/\\/7xvyvy.com1.z0.glb.clouddn.com\\/\\/fit\\/livevideo\\/cover\\/07b9fb16-f33f-40b8-a9d7-59c51c7eb3ea.jpg?imageMogr2\\/thumbnail\\/750x750\\/interlace\\/1", "", "", 123213, ""));
                    //                    livingVideos.add(new LiveVideoInfo(1, "颠三倒四", "大多数", "http:\\/\\/7xvyvy.com1.z0.glb.clouddn.com\\/\\/fit\\/livevideo\\/cover\\/07b9fb16-f33f-40b8-a9d7-59c51c7eb3ea.jpg?imageMogr2\\/thumbnail\\/750x750\\/interlace\\/1", "", "", 123213, ""));
                    //
                    //                    ArrayList<LiveVideoSoonInfo> liveVideoSoonInfos = new ArrayList<>();
                    //                    liveVideoSoonInfos.add(new LiveVideoSoonInfo("今天", livingVideos));
                    //                    liveVideoSoonInfos.add(new LiveVideoSoonInfo("明天", livingVideos));
                    //                    liveVideoSoonInfos.add(new LiveVideoSoonInfo("后天", livingVideos));
                    //
                    //                    adapter.refreshData(livingVideos, liveVideoSoonInfos);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            swipeRefreshLayout.setRefreshing(false);

            //            ArrayList<LiveVideoInfo> livingVideos = new ArrayList<>();
            //            livingVideos.add(new LiveVideoInfo(1,"颠三倒四","大多数","","","",123213,""));
            //            livingVideos.add(new LiveVideoInfo(1,"颠三倒四","大多数","","","",123213,""));
            //            livingVideos.add(new LiveVideoInfo(1,"颠三倒四","大多数","","","",123213,""));
            //
            //            ArrayList<LiveVideoSoonInfo> liveVideoSoonInfos = new ArrayList<>();
            //            liveVideoSoonInfos.add(new LiveVideoSoonInfo("今天",livingVideos));
            //            liveVideoSoonInfos.add(new LiveVideoSoonInfo("明天",livingVideos));
            //            liveVideoSoonInfos.add(new LiveVideoSoonInfo("后天",livingVideos));
            //
            //            adapter.refreshData(livingVideos,liveVideoSoonInfos);


        }
    };


    private RequestCallBack<String> callbackMore = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            swipeRefreshLayout.setRefreshing(false);
            try {
                LiveVideoListResult result = new Gson().fromJson(responseInfo.result, LiveVideoListResult.class);
                if (result != null && result.getCode() == 1 && result.getResult() != null && result.getResult().getLiveVideoMore() != null && result.getResult().getLiveVideoMore().size() > 0) {
                    adapter.addMoreData(result.getResult().getLiveVideoMore());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            swipeRefreshLayout.setRefreshing(false);
        }
    };

//    @Override
//    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        if (direction == SwipyRefreshLayoutDirection.TOP) {
//            getDataFromInter();
//        } else {
//            getMoreDataFromInter();
//        }
//    }

    @Override
    public void onRefresh() {
        getDataFromInter();
    }

    @Override
    public void onLivingVideoCLick(LiveVideoInfo info) {
        Intent intent = new Intent(getActivity(), LivingVideoActivity.class);
        intent.putExtra(Common.LIVE_INFO, info);
        startActivity(intent);
    }

    @Override
    public void onSpecialClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Common.VIDEO_TYPE, 0);
        startActivity(intent);
    }

    @Override
    public void onDeepIntoClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Common.VIDEO_TYPE, 1);
        startActivity(intent);

    }

    @Override
    public void onCelebrityClick() {
        Intent intent = new Intent(getActivity(), WatchOfficeActivity.class);
        intent.putExtra(Common.VIDEO_TYPE, 2);
        startActivity(intent);
    }

    @Override
    public void onSoonLivingVideoCLick(int liveId) {
        Intent intent = new Intent(getActivity(), LiveDetailActivity.class);
        intent.putExtra(Common.LIVE_ID, liveId);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onVideoClick(int id) {
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
