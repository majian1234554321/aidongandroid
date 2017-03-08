package com.leyuan.aidong.ui.video.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.DeepIntoAdapter;
import com.leyuan.aidong.entity.video.SpecialTopicInfo;
import com.leyuan.aidong.ui.video.activity.VideoDetailActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.CustomLayoutManager;


public class DeepIntoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float alpha_d;
    private float item_normal_font_size;
    private float item_max_font_size;
    private float font_size_d;

    private int current_sroll_state;
    private int first_complete_visible_position;

    private Gson gson = new Gson();
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout layout_refresh;
    private DeepIntoAdapter adapter;
    private LinearLayout layout_video_empty;

    private CustomLayoutManager mLinearLayoutManager;
    public static int scrollDirection = 1; //1向上 0 向下
    private boolean isLoading;
    private int lastVisibleItem;

    private int page = 1;
    private String city_id;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("select_ctiy")) {
                city_id = intent.getStringExtra("id");
                //接受到城市切换，更新列表
                getDataFromInter();
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        item_max_height = (int) getResources().getDimension(R.dimen.video_item_max_height);
        item_normal_height = (int) getResources().getDimension(R.dimen.video_item_normal_height);
        item_normal_font_size = getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);
        font_size_d = item_max_font_size - item_normal_font_size;
        alpha_d = item_max_alpha - item_normal_alpha;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_special_topic, container, false);
        layout_refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.layout_refresh);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        layout_video_empty = (LinearLayout) rootView.findViewById(R.id.layout_video_empty);

        layout_refresh.setOnRefreshListener(this);

        initReceiver();
        initRecyclerView();
        getDataFromInter();
        return rootView;
    }

    private void initReceiver() {
        if (null == city_id || city_id.equals("")) {
            city_id = "1211000000";
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("select_ctiy");
        getActivity().registerReceiver(receiver, filter);
    }

    private void initRecyclerView() {

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Logger.i("mRecyclerView h = " + mRecyclerView.getHeight() + ",  item_max_height" + item_max_height);
                adapter.setRootViewHeight(mRecyclerView.getHeight() - item_max_height);

            }
        });

        mLinearLayoutManager = new CustomLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(false);

        adapter = new DeepIntoAdapter(getActivity(), item_listener);
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Logger.i("old_scroll_state = " + current_sroll_state + ",   new state = " + newState);

                if (current_sroll_state != newState && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int completelyPostion = ((CustomLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    int firstPostion = ((CustomLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    Logger.i("firstPostion = " + firstPostion + ",   completelyPostion = " + completelyPostion);
                    if (completelyPostion != firstPostion) {
                        if (scrollDirection == 0) {
                            View v = recyclerView.getChildAt(0);
                            Logger.i(" == 0  v.getTop = " + v.getTop());
                            recyclerView.smoothScrollBy(0, v.getTop());
                        } else {
                            View v = recyclerView.getChildAt(1);
                            Logger.i("== 1  v.getTop = " + v.getTop());
                            recyclerView.smoothScrollBy(0, v.getTop());
                        }
                    }
                    if(!isLoading &&scrollDirection ==1 && lastVisibleItem +1 >= mLinearLayoutManager.getItemCount()){
                        getMoreDataFromInter();
                    }
                }

                current_sroll_state = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    scrollDirection = 1;
                } else {
                    scrollDirection = 0;
                }

                Logger.i("dy = " + dy);
                first_complete_visible_position = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                RecyclerView.ViewHolder firstHolder = recyclerView.findViewHolderForPosition(first_complete_visible_position);
                if (firstHolder != null && firstHolder instanceof DeepIntoAdapter.ViewHolder) {
                    DeepIntoAdapter.ViewHolder holder = (DeepIntoAdapter.ViewHolder) firstHolder;
                    if (holder.getItemViewType() == DeepIntoAdapter.TYPE_ITEM) {

                        int height = holder.relView.getLayoutParams().height;
                        if (height + dy <= item_max_height && height + dy >= item_normal_height) {
                            holder.relView.getLayoutParams().height = height + dy;
                            holder.relView.setLayoutParams(holder.relView.getLayoutParams());
                            //                            holder.mark.setAlpha(holder.mark.getAlpha() + dy * alpha_d / item_normal_height);
                            //                            holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            //                                    holder.txt_type.getTextSize() + dy * font_size_d / item_normal_height);

                            float size = holder.txt_type.getTextSize() + dy * font_size_d / item_normal_height;
                            if (size > item_max_font_size) {
                                holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
                            } else if (size < item_normal_font_size) {
                                holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                            } else {
                                holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                            }
                            holder.txt_course.setAlpha(valueAlpha(height));
                            holder.txt_belong.setAlpha(valueAlpha(height));
                        }
                    }
                }
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
            }
        });

        mRecyclerView.setOnTouchListener(touchListener);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                int completelyPostion = ((CustomLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                int firstPostion = ((CustomLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                Logger.i("firstPostion = " + firstPostion + ",   completelyPostion = " + completelyPostion);
                if (completelyPostion != firstPostion) {
                    if (scrollDirection == 0) {
                        View view = mRecyclerView.getChildAt(0);
                        Logger.i(" == 0  v.getTop = " + view.getTop());
                        mRecyclerView.smoothScrollBy(0, view.getTop());
                    } else {
                        View view = mRecyclerView.getChildAt(1);
                        Logger.i("== 1  v.getTop = " + view.getTop());
                        mRecyclerView.smoothScrollBy(0, view.getTop());
                    }
                    return true;
                }

            }
            return false;

        }
    };

    private DeepIntoAdapter.OnItemClickListener item_listener = new DeepIntoAdapter.OnItemClickListener() {
        @Override
        public void OnClick(int position, View itemView, SpecialTopicInfo info) {
            int first_complete_visible_position = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
            if (position == first_complete_visible_position) {
                //                ToastTools.makeShortText("跳");
                Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
                intent.putExtra("id", info.getId());
                intent.putExtra("phase", info.getLatest().getPhase()-1);
                getActivity().startActivity(intent);
            } else {
                View v = mRecyclerView.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
                Logger.i("top = " + v.getTop());
                mRecyclerView.smoothScrollBy(0, v.getTop());
            }
            Logger.i("onclick --- position = " + position);
        }
    };

    private void getDataFromInter() {
      /*  page = 1;
        adapter.setFirst(true);
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageCurrent", String.valueOf(page));
        params.addBodyParameter("list", "family");
        params.addBodyParameter("areaId", city_id);
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/getVideoList.action", params, callback);*/
    }

   /* private RequestCallBack<String> callback = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
//            ToastTools.show("刷新成功",getActivity());
            layout_refresh.setRefreshing(false);

            Logger.i("" + responseInfo.result);
//            try {
//                BaseResult<VideoListResult> result = gson.fromJson(responseInfo.result,
//                        new TypeToken<BaseResult<VideoListResult>>() {
//                        }.getType());
//                if (result.getCode() == 1 && result.getResult() != null && result.getResult().getVideo() != null) {
//                    ArrayList<SpecialTopicInfo> videos = result.getResult().getVideo();
//                    scrollDirection = 1;
//                    adapter.freshData(videos);
//                    if (videos.size() > 0) {
//                        mRecyclerView.setVisibility(View.VISIBLE);
//                        layout_video_empty.setVisibility(View.GONE);
//                    } else {
//                        mRecyclerView.setVisibility(View.INVISIBLE);
//                        layout_video_empty.setVisibility(View.VISIBLE);
//                    }
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            layout_refresh.setRefreshing(false);

        }
    };*/

  /*  private RequestCallBack<String> callbackMore = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            isLoading = false;
            layout_refresh.setRefreshing(false);
            Logger.i("callbackMore" + responseInfo.result);
//            try {
//                BaseResult<VideoListResult> result = gson.fromJson(responseInfo.result,
//                        new TypeToken<BaseResult<VideoListResult>>() {
//                        }.getType());
//                if (result.getCode() == 1 && result.getResult() != null && result.getResult().getVideo() != null && result.getResult().getVideo().size() > 0) {
//                    ArrayList<SpecialTopicInfo> videos = result.getResult().getVideo();
//                    adapter.addData(videos);
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }

        }*/

       /* @Override
        public void onFailure(HttpException e, String s) {
            isLoading = false;
            layout_refresh.setRefreshing(false);

        }
    };*/


    private int valueColor(int height) {
        if (height >= item_max_height) {
            return 0xffffffff;
        } else {
            return 0xffffff + ((height - item_normal_height) / item_max_height * 255) * 16 * 16 * 16 * 16 * 16 * 16;
        }

    }

    private float valueAlpha(int height) {
        if (height < (item_normal_height + 200)) {
            return 0;
        } else if (height > (item_max_height - 200)) {
            return 1;
        }
        return height / (item_max_height - 200);


    }


//    @Override
//    public void onRefresh(SwipyRefreshLayoutDirection direction) {
//        if (direction == SwipyRefreshLayoutDirection.TOP) {
//            getDataFromInter();
//
//        } else {
//            getMoreDataFromInter();
//        }
//    }

    private void getMoreDataFromInter() {
     /*   isLoading = true;
        page++;
        RequestParams params = new RequestParams();
        params.addBodyParameter("pageCurrent", String.valueOf(page));
        params.addBodyParameter("list", "family");
        params.addBodyParameter("areaId", city_id);
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/getVideoList.action", params, callbackMore);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    @Override
    public void onRefresh() {
        getDataFromInter();
    }
}
