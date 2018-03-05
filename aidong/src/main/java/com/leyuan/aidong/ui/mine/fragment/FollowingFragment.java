package com.leyuan.aidong.ui.mine.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.FollowAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.BaseLazyFragment;
import com.leyuan.aidong.ui.mine.activity.CoachInfoActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.FollowPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 关注
 * Created by song on 2016/9/10.
 */
public class FollowingFragment extends BaseLazyFragment implements FollowFragmentView {
    public static final String FOLLOWING = "following_list";

    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<UserBean> data;
    private FollowAdapter followAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private int position;
    private FollowPresent present;
    private int itemClickedPosition;

    @Override
    public View initView() {
        present = new FollowPresentImpl(getContext(), this);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_follow, null);
        initSwipeRefreshLayout(view);
        initSwitcherLayout();
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
        present.commonLoadData(switcherLayout, FOLLOWING);
    }


    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshData(FOLLOWING);
            }
        });
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadData(switcherLayout, FOLLOWING);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_follow);
        data = new ArrayList<>();
        followAdapter = new FollowAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(followAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        followAdapter.setFollowListener(new FollowCallback());
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
//            currPage ++;
//            if (data != null && data.size() >= pageSize) {
//                present.requestMoreData(recyclerView,pageSize,FOLLOWING,currPage);
//            }
        }
    };

    @Override
    public void onRefreshData(List<UserBean> userBeanList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        for (UserBean user : userBeanList) {
            user.followed = true;
        }
        data.clear();
        data.addAll(userBeanList);
        followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreData(List<UserBean> userBeanList) {
        for (UserBean user : userBeanList) {
            user.followed = true;
        }
        data.addAll(userBeanList);
        followAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {

    }


    private class FollowCallback extends FollowAdapter.SimpleFollowListener {
        @Override
        public void onCancelFollow(String id, int position) {
            FollowingFragment.this.position = position;

            UserBean userBean = data.get(position);
            present.cancelFollow(id, userBean.getUserTypeByUserType());
        }

        @Override
        public void onItemClick(UserBean userBean, int position) {
            FollowingFragment.this.itemClickedPosition = position;
            if (Constant.USER.equals(userBean.type)) {
//                UserInfoActivity.start(getContext(), userBean.getId());

                UserInfoActivity.startForResult(FollowingFragment.this, userBean.getId(), Constant.REQUEST_USER_INFO);

            } else {
//                CoachInfoActivity.start(getContext(), userBean.getId());

                CoachInfoActivity.startForResult(FollowingFragment.this, userBean.getId(), Constant.REQUEST_USER_INFO);
            }

        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            UserBean userBean = data.get(position);
            data.remove(userBean);
//            SystemInfoUtils.removeFollow(userBean);
            if (data.isEmpty()) {
                showEmptyView();
            } else {
                followAdapter.notifyDataSetChanged();
            }
            Toast.makeText(getContext(), R.string.cancel_follow_success, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), R.string.cancel_follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(), R.layout.empty_following, null);
        switcherLayout.addCustomView(view, "emptyFollowing");
        switcherLayout.showCustomLayout("emptyFollowing");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_USER_INFO:

                    FollowingFragment.this.data.get(itemClickedPosition).followed =
                            data.getBooleanExtra(Constant.FOLLOW, FollowingFragment.this.data.get(itemClickedPosition).followed);
                    Logger.i("follow", "onActivityResult follow = " + FollowingFragment.this.data.get(itemClickedPosition).followed);

                    if( !FollowingFragment.this.data.get(itemClickedPosition).followed){
                        FollowingFragment.this.data.remove(itemClickedPosition);
                    }
                    followAdapter.notifyItemChanged(itemClickedPosition);
                    break;
            }
        }

    }
}
