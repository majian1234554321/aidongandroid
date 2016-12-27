package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.adapter.DynamicAdapter;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BaseFragment implements SportCircleFragmentView{
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DynamicAdapter dynamicAdapter;
    private List<DynamicBean> dynamicBeanList = new ArrayList<>();

    private DynamicPresent dynamicPresent;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_circle,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dynamicPresent = new DynamicPresentImpl(getContext(),this);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic);
        dynamicAdapter = new DynamicAdapter(getContext());
        dynamicAdapter.setData(dynamicBeanList);
        recyclerView.setAdapter(dynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void updateRecyclerView(List<DynamicBean> dynamicBeanList) {

    }

    @Override
    public void showEndFooterView() {

    }


    {
        for (int i = 0; i < 7; i++) {
            DynamicBean b = new DynamicBean();
            b.content = "内容" + i;
            List<String> image = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                image.add("http://ww2.sinaimg.cn/mw690/006uFQHgjw1fb54ut8kc8j30zk0qowmb.jpg");
            }
            b.image =  image;
            DynamicBean.Publisher p = b.new Publisher();
            p.name = "song";
            p.avatar = "https://www.baidu.com/img/bd_logo1.png";
            b.publisher = p;
            b.published_at = "2016-8-8";

            DynamicBean.LikeUser likeUser = b.new LikeUser();
            List<DynamicBean.LikeUser.Item> likeUserItem = new ArrayList<>();
            likeUser.count = i+"";
            for (int j = 0; j < i; j++) {
                DynamicBean.LikeUser.Item item = likeUser.new Item();
                item.avatar = "https://static.dingtalk.com/media/lADOfNttL80JsM0NtA_3508_2480.jpg_620x10000q90g.jpg";
                likeUserItem.add(item);
            }
            likeUser.item = likeUserItem;
            b.like_user = likeUser;

            DynamicBean.Comment comment = b.new Comment();
            List<DynamicBean.Comment.Item> commentItem = new ArrayList<>();
            comment.count = i +"";
            for (int j = 0; j < i; j++) {
                DynamicBean.Comment.Item item = comment.new Item();
                item.content = "评论内容" + j;
                DynamicBean.Comment.Item.Publisher pp = item.new Publisher();
                pp.name = "评论者名字" + j;
                item.publisher = pp;
                commentItem.add(item);
            }
            comment.item = commentItem;
            b.comment = comment;
            dynamicBeanList.add(b);
        }
    }
}
