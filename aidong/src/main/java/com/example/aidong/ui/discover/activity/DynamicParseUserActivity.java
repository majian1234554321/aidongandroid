package com.example.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.UserFollowCacheAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.data.FollowData;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.DynamicParsePresent;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.DynamicParseUserView;
import com.example.aidong .ui.mvp.view.FollowCacheView;
import com.example.aidong .ui.mvp.view.FollowView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态里面点赞的人列表
 * Created by song on 2017/3/23.
 */
public class DynamicParseUserActivity extends BaseActivity implements DynamicParseUserView, FollowView, FollowCacheView, UserFollowCacheAdapter.FollowListener {
    private SimpleTitleBar titleBar;
    private RecyclerView rvUser;
    private RelativeLayout emptyLayout;
    private List<UserBean> data;
    private String dynamicId;

    private DynamicParsePresent dynamicPresent;
    private int page = 1;
    private UserFollowCacheAdapter userAdapter;
    private int position;
    private HeaderAndFooterRecyclerViewAdapter wrapAdapter;
    private FollowPresentImpl present;
    private ArrayList<String> following_ids;
    private int itemClickedPosition;

    public static void start(Context context, String dynamicId) {
        Intent starter = new Intent(context, DynamicParseUserActivity.class);
        starter.putExtra("dynamicId", dynamicId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_parse_user);
        if (getIntent() != null) {
            dynamicId = getIntent().getStringExtra("dynamicId");
        }
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        rvUser = (RecyclerView) findViewById(R.id.rv_user);
        emptyLayout = (RelativeLayout) findViewById(R.id.rl_empty);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserFollowCacheAdapter(this);
        userAdapter.setFollowListener(this);

        wrapAdapter = new HeaderAndFooterRecyclerViewAdapter(userAdapter);

        rvUser.setAdapter(wrapAdapter);
        rvUser.addOnScrollListener(onScrollListener);

        present = new FollowPresentImpl(this);
        present.setFollowListener(this);
        present.setFollowCacheView(this);
        present.getFollowCahceList();


        dynamicPresent = new DynamicParsePresent(this, this);
        dynamicPresent.getLikes(dynamicId, page);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private RecyclerView.OnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (data != null && data.size() >= pageSize) {
                page++;
                dynamicPresent.getLikes(dynamicId, page);
            }

        }
    };

    @Override
    public void onGetUserData(List<UserBean> data, int page) {
        if (following_ids != null && data != null && !data.isEmpty()) {

            for (UserBean user : data) {
                user.followed = FollowData.isFollow(user.getId(), following_ids);
            }
        }

        if (page == 1) {
            this.data = data;
            if (data != null && !data.isEmpty()) {
                userAdapter.setData(data);
                wrapAdapter.notifyDataSetChanged();
                emptyLayout.setVisibility(View.GONE);
            } else {
                emptyLayout.setVisibility(View.VISIBLE);
            }
        } else {
            if (data != null && !data.isEmpty()) {
                this.data.addAll(data);
                userAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onGetFollowCacheList(ArrayList<String> following_ids) {
        this.following_ids = following_ids;

        //假如这个请求后收到,

        if (data != null && !data.isEmpty()) {

            for (UserBean user : data) {
                user.followed = FollowData.isFollow(user.getId(), following_ids);
            }
            userAdapter.setData(data);
        }

    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            data.get(position).followed = true;
            userAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }


//        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.addFollow(data.get(position));
//            userAdapter.notifyDataSetChanged();
//            Toast.makeText(this, R.string.follow_success, Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            data.get(position).followed = false;
            userAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(this, R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }


//        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.removeFollow(data.get(position));
//            userAdapter.notifyDataSetChanged();
//            Toast.makeText(this, R.string.cancel_follow_success, Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }


//    @Override
//    public void onFollowClicked(int position) {
//        this.position = position;
//        if (App.mInstance.isLogin()) {
//            UserBean userBean = data.get(position);
//            if (SystemInfoUtils.isFollow(this, userBean)) {
//                dynamicPresent.cancelFollow(userBean.getId());
//            } else {
//                dynamicPresent.addFollow(userBean.getId());
//            }
//        } else {
//            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
//        }
//    }

    @Override
    public void onAddFollow(String id, int position) {
        if (!App.mInstance.isLogin()) {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            return;
        }
        this.position = position;

        UserBean userBean = data.get(position);
        present.addFollow(id, userBean.getTypeByType());

    }

    @Override
    public void onCancelFollow(String id, int position) {
        if (!App.mInstance.isLogin()) {
            startActivityForResult(new Intent(this, LoginActivity.class), Constant.REQUEST_LOGIN);
            return;
        }


        this.position = position;
        UserBean userBean = data.get(position);
        present.cancelFollow(id, userBean.getTypeByType());
    }

    @Override
    public void onItemClick(UserBean userBean, int position) {
        this.itemClickedPosition = position;
        UserInfoActivity.startForResult(this, userBean.getId(), Constant.REQUEST_USER_INFO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_USER_INFO:

                    this.data.get(itemClickedPosition).followed =
                            data.getBooleanExtra(Constant.FOLLOW, this.data.get(itemClickedPosition).followed);
                    Logger.i("follow", "onActivityResult follow = " + this.data.get(itemClickedPosition).followed);

                    userAdapter.notifyItemChanged(itemClickedPosition);
                    break;
                case  Constant.REQUEST_LOGIN:
                        present.getFollowCahceList();

                    break;
            }
        }

    }
}
