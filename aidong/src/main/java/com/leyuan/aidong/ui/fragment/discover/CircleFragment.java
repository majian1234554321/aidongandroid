package com.leyuan.aidong.ui.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.adapter.DynamicAdapter;
import com.leyuan.aidong.ui.activity.home.ImagePreviewActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BaseFragment implements SportCircleFragmentView{
   // private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DynamicAdapter dynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList = new ArrayList<>();

    private int currPage = 1;
    private DynamicPresent dynamicPresent;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dynamicPresent = new DynamicPresentImpl(getContext(),this);
       // refreshLayout = (SwipeRefreshLayout) view.findViewById(refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic);
        dynamicAdapter = new DynamicAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(dynamicAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicAdapter.setData(dynamicList);
        dynamicAdapter.setHandleDynamicListener(new HandleDynamicListener());
        recyclerView.addOnScrollListener(onScrollListener);
       // dynamicPresent.pullToRefreshData();



    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
               // present.requestMoreRecommendData(recommendView,pageSize,currPage);
            }
        }
    };

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
            if(i == 4){
                image.add("http://ww4.sinaimg.cn/mw690/61ecbb3djw1fb91u8cc60j20ku0xc7ct.jpg");
                image.add("http://ww2.sinaimg.cn/mw690/006uFQHgjw1fb54ut8kc8j30zk0qowmb.jpg");
                image.add("http://ww1.sinaimg.cn/mw690/005EbyOWjw1fbedmg8hqsj30c464t1kx.jpg");
                image.add("http://ww2.sinaimg.cn/mw690/61ecbb3djw1fbdm5afmycg207r04anpd.gif");
            }else {
                for (int j = 0; j < i; j++) {
                    image.add("http://ww2.sinaimg.cn/mw690/006uFQHgjw1fb54ut8kc8j30zk0qowmb.jpg");
                }
            }
            b.image =  image;
            DynamicBean.Publisher p = b.new Publisher();
            p.name = "song";
            p.avatar = "http://ww4.sinaimg.cn/mw690/61ecbb3djw1fb91u8cc60j20ku0xc7ct.jpg";
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
            dynamicList.add(b);
            dynamicList.add(b);
            dynamicList.add(b);
        }
    }

   private class HandleDynamicListener implements DynamicAdapter.OnHandleDynamicListener{

       @Override
       public void onAvatarClickListener() {

       }

       @Override
       public void onImageClickListener(View view,int itemPosition, int imagePosition) {
           Intent i = new Intent(getContext(), ImagePreviewActivity.class);
           ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                   (getActivity(), view,ViewCompat.getTransitionName(view));
           i.putExtra("urls",(ArrayList)dynamicList.get(itemPosition).image);
           i.putExtra("position",imagePosition);
           startActivity(i, optionsCompat.toBundle());
           //ImagePreviewActivity.start(context,(ArrayList<String>) data,position);
       }

       @Override
       public void onVideoClickListener() {

       }

       @Override
       public void onShowMoreLikeClickListener() {

       }

       @Override
       public void onShowMoreCommentClickListener() {

       }

       @Override
       public void onLikeClickListener() {

       }

       @Override
       public void onCommonClickListener() {

       }

       @Override
       public void onShareClickListener() {

       }
   }
}
