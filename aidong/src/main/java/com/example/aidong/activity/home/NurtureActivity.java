package com.example.aidong.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.NurtureAdapter;
import com.example.aidong.model.bean.NurtureBean;
import com.example.aidong.utils.Constants;
import com.example.aidong.utils.NetworkUtils;
import com.example.aidong.view.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.view.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.view.endlessrecyclerview.HeaderSpanSizeLookup;
import com.example.aidong.view.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong.view.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.view.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 营养品界面
 * @author song
 */
public class NurtureActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private int page = 1;
    private List<NurtureBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private NurtureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurture_and_equipment);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView)findViewById(R.id.rv_recommend);

        getNurtureData(Constants.NORMAL_LOAD);

        initSwipeRefreshLayout();
        initRecyclerView();
        //initHeaderView();
    }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,
                R.color.refresh_green, R.color.refresh_orange, R.color.refresh_red);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    private void initRecyclerView() {
        adapter = new NurtureAdapter(data);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, View.inflate(this,R.layout.header_nurture,null));
    }

    private void initHeaderView(){
        View header = View.inflate(this,R.layout.header_nurture,null);
       /* RecyclerView rvType = (RecyclerView) header.findViewById(R.id.rv_type);
        List<TypeOfNurtureBean> list = new ArrayList<>();
        for(int i = 0; i <10; i++){
            TypeOfNurtureBean bean = new TypeOfNurtureBean();
            if(i%2 == 0){
                bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                bean.setName("张三");
            }else{
                bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                bean.setName("李源");
            }
            list.add(bean);
        }
        TypeOfNurtureAdapter a = new TypeOfNurtureAdapter(list);
        rvType.setAdapter(a);
        a.notifyDataSetChanged();*/
        RecyclerViewUtils.setHeaderView(recyclerView, header);
    }

    /**下拉刷新*/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {
            ToastUtil.show("refresh...", NurtureActivity.this);
            page = 1;
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
            getNurtureData(Constants.PULL_DOWN_TO_REFRESH);
        }
    };

    /**加载下一页*/
    public EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            ToastUtil.show("more...", NurtureActivity.this);
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d("@Cundong", "the state is Loading, just wait..");
                return;
            }
            showLoadFooterView(recyclerView,10);
            page ++;
            requestData();

        }
    };



    public void getNurtureData(int requestCode){


        switch (requestCode){
            case Constants.NORMAL_LOAD:
                for(int i = 0; i <10; i++){
                    NurtureBean bean = new NurtureBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("张三");
                        bean.setPrice("1000");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setPrice("88");
                    }
                    data.add(bean);
                }
                break;
            case Constants.PULL_DOWN_TO_REFRESH:

                data.clear();

                for(int i = 0; i <10; i++){
                    NurtureBean bean = new NurtureBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("张三");
                        bean.setPrice("1000");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setPrice("88");
                    }
                    data.add(bean);
                }
                adapter.setData(data);
                swipeRefreshLayout.setRefreshing(false);
                headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case Constants.PULL_UP_LOAD_MORE:

                for(int i = 0; i <10; i++){
                    NurtureBean bean = new NurtureBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("张三");
                        bean.setPrice("1000");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setPrice("88");
                    }
                    data.add(bean);
                }

                adapter.setData(data);
                hideFooterView(recyclerView);
                headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();

                if(page > 3){
                    RecyclerViewStateUtils.setFooterViewState(recyclerView,LoadingFooter.State.TheEnd);
                    return;
                }

                break;
        }
    }

    /**
     * 模拟请求网络
     */
    private void requestData() {

        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //模拟一下网络请求失败的情况
                if(NetworkUtils.isNetAvailable(NurtureActivity.this)) {
                    mHandler.sendEmptyMessage(-1);
                } else {
                    mHandler.sendEmptyMessage(-3);
                }
            }
        }.start();
    }

    private PreviewHandler mHandler = new PreviewHandler(this);

    private  class PreviewHandler extends Handler {

        private WeakReference<NurtureActivity> ref;

        PreviewHandler(NurtureActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final NurtureActivity activity = ref.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            switch (msg.what) {
                case -1:
                    getNurtureData(Constants.PULL_UP_LOAD_MORE);
                    break;
                case -2:
                    //activity.notifyDataSetChanged();
                    break;
                case -3:
                    RecyclerViewStateUtils.setFooterViewState(activity, activity.recyclerView, 10, LoadingFooter.State.NetWorkError, null);
                    break;
            }
        }
    }
}
