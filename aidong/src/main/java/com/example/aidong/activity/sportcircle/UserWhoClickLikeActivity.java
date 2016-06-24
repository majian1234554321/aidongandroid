package com.example.aidong.activity.sportcircle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.aidong.BaseActivity;
import com.example.aidong.BaseApp;
import com.example.aidong.R;
import com.example.aidong.activity.account.LoginActivity;
import com.example.aidong.adapter.Adapter_user_who_click_like_list;
import com.example.aidong.common.Constant;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.LikeData;
import com.example.aidong.model.LikeListResult;
import com.example.aidong.model.UserCoach;
import com.example.aidong.utils.ActivityTool;
import com.example.aidong.utils.PullToRefreshUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class UserWhoClickLikeActivity extends BaseActivity implements
        OnRefreshListener2<ListView>, IHttpCallback {

    private static final int LIKE_DOWN = 1;
    private static final int LIKE_UP = 0;
    private PullToRefreshListView listView;
    private Adapter_user_who_click_like_list adapter;
    private int page = 1;

    private ArrayList<LikeData> array_likes = new ArrayList<>();
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        id = getIntent().getIntExtra("id", 0);

        setContentView(R.layout.layout_user_who_click_like);
        //		initTop("点赞的用户", true);
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        PullToRefreshUtil.setPullListView(listView, Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new Adapter_user_who_click_like_list(this);
        listView.setAdapter(adapter);


    }

    protected void initData() {
        addTask(this, new IHttpTask(UrlLink.DYNAMICZAN_URL,
                        initParams(page, id), LikeListResult.class), HttpConfig.GET,
                LIKE_DOWN);
        adapter.setOnUserLikeClickListener(new Adapter_user_who_click_like_list.OnUserLikeClickListener() {

            @Override
            public void onHeaderClick(View v) {
                Object o = v.getTag();
                if (o instanceof UserCoach) {
                    UserCoach user = (UserCoach) o;
                    ActivityTool.startShowActivity(
                            UserWhoClickLikeActivity.this, user);

                }
            }

            @Override
            public void onAddFriendClick(View v) {
                Object o = v.getTag();
                if (o instanceof UserCoach) {
                    final UserCoach user = (UserCoach) o;
                    if (BaseApp.mInstance.isLogin()) {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                String reason = getResources().getString(R.string.app_name);
                                try {
                                    //									EMContactManager.getInstance().addContact(String.valueOf(user.getMxid()), reason);
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            ToastUtil.show(getResources()
                                                    .getString(
                                                            R.string.tip_friendAddSuccess), UserWhoClickLikeActivity.this);
                                        }
                                    });
                                } catch (final Exception e) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            ToastUtil.show(getResources()
                                                    .getString(R.string.error_net), UserWhoClickLikeActivity.this);
                                        }
                                    });
                                }
                            }
                        }).start();
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(UserWhoClickLikeActivity.this,
                                LoginActivity.class);
                        intent.putExtra(Constant.BUNDLE_CLASS, UserWhoClickLikeActivity.class);
                        startActivity(intent);
                    }


                }
            }

        });

    }

    private List<BasicNameValuePair> initParams(int page, int id) {
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", "" + id));
        params.add(new BasicNameValuePair("page", "" + page));

        return params;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        addTask(this, new IHttpTask(UrlLink.DYNAMICZAN_URL,
                        initParams(page, id), LikeListResult.class), HttpConfig.GET,
                LIKE_DOWN);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

        page++;

        addTask(this, new IHttpTask(UrlLink.DYNAMICZAN_URL,
                        initParams(page, id), LikeListResult.class), HttpConfig.GET,
                LIKE_UP);

    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        stopLoading();
        switch (requestCode) {
            case LIKE_DOWN:
                LikeListResult like = (LikeListResult) data;
                if (like.getCode() == 1) {
                    if (like.getData() != null && like.getData().getLike() != null) {
                        array_likes.clear();
                        array_likes.addAll(like.getData().getLike());


                        adapter.freshData(array_likes);
                    }
                }

                break;
            case LIKE_UP:
                LikeListResult like_up = (LikeListResult) data;
                if (like_up.getCode() == 1) {
                    if (like_up.getData() != null
                            && like_up.getData().getLike() != null) {
                        array_likes.addAll(like_up.getData().getLike());
                        //					if (mxid != 0) {
                        //						for (int i = 0; i < array_likes.size(); i++) {
                        //							if(array_likes.get(i).getUser().getMxid() == mxid){
                        //								array_likes.remove(i);
                        //							}
                        //						}
                        //					}
                        adapter.freshData(array_likes);
                    }
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onError(String reason, int requestCode) {
        stopLoading();
    }

    public void stopLoading() {
        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    listView.onRefreshComplete();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
