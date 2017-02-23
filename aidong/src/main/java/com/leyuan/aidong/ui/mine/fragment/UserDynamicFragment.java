package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.viewholder.FiveImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.FourImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.OneImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.SixImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.ThreeImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.TwoImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserDynamicFragmentView;
import com.leyuan.aidong.utils.DynamicType;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.rv_dynamic;

/**
 * 用户资料--动态
 * Created by song on 2017/1/16.
 */
public class UserDynamicFragment extends BaseFragment implements UserDynamicFragmentView{
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;

    private int currPage = 1;
    private UserInfoPresent userInfoPresent;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dynamic,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = String.valueOf(App.mInstance.getUser().getId());
        userInfoPresent = new UserInfoPresentImpl(getContext(),this);
        initRecyclerView(view);
        userInfoPresent.commonLoadDynamic(id);
    }


    private void initRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(rv_dynamic);
        dynamicList = new ArrayList<>();
        dynamicList = new ArrayList<>();
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(getContext());
        builder.addType(VideoViewHolder.class, DynamicType.VIDEO, R.layout.vh_dynamic_video)
                .addType(OneImageViewHolder.class, DynamicType.ONE_IMAGE, R.layout.vh_dynamic_one_photo)
                .addType(TwoImageViewHolder.class, DynamicType.TWO_IMAGE, R.layout.vh_dynamic_two_photos)
                .addType(ThreeImageViewHolder.class, DynamicType.THREE_IMAGE, R.layout.vh_dynamic_three_photos)
                .addType(FourImageViewHolder.class, DynamicType.FOUR_IMAGE, R.layout.vh_dynamic_four_photos)
                .addType(FiveImageViewHolder.class, DynamicType.FIVE_IMAGE, R.layout.vh_dynamic_five_photos)
                .addType(SixImageViewHolder.class, DynamicType.SIX_IMAGE, R.layout.vh_dynamic_six_photos)
                .showLikeAndCommentLayout(true);
        circleDynamicAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(circleDynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
                userInfoPresent.requestMoreDynamic(id,recyclerView,pageSize,currPage);
            }
        }
    };


    @Override
    public void updateDynamic(List<DynamicBean> dynamicBeanList) {
        dynamicList.addAll(dynamicBeanList);
        circleDynamicAdapter.updateData(dynamicList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyLayout() {

    }
}
