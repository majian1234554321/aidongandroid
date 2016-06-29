package com.example.aidong.activity.home.subject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.SubjectDetailNumAdapter;
import com.example.aidong.adapter.SubjectDetailTopAdapter;
import com.example.aidong.view.CircleImageView;
import com.example.aidong.view.CustomViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/24.
 */
public class SubjectDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_subject_detail_price, txt_subject_detail_title, txt_subject_detail_classname, txt_subject_detail_time, txt_subject_detail_nickname, txt_subject_detail_addr, txt_subject_detail_jianjie, txt_subject_detail_num, txt_subject_detail_maxnum, txt_subject_detail_baoming;
    private ImageView img_subject_detail_back, img_subject_detail_guanzhu, img_subject_detail_share;
    private RecyclerView recycler_subject_detail_yibao;
    private CircleImageView img_subject_detail_head;
    private LinearLayout layout_subject_detail_addr, layout_subject_detail_tip;
    private CustomViewPager viewpager_subject_detail_top;
    private PullToRefreshScrollView scrollview;

    private SubjectDetailTopAdapter topAdapter;
    private SubjectDetailNumAdapter numAdapter;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    private List<View> imageViews = new ArrayList<View>(); // 滑动的图片集合
    private List<String> adList = new ArrayList<String>();
    private List<String> numList = new ArrayList<String>();


    protected DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectdetail);
        initView();
        initData();
        setClick();
    }


    private void initView() {
        txt_subject_detail_price = (TextView) findViewById(R.id.txt_subject_detail_price);
        txt_subject_detail_title = (TextView) findViewById(R.id.txt_subject_detail_title);
        txt_subject_detail_classname = (TextView) findViewById(R.id.txt_subject_detail_classname);
        txt_subject_detail_time = (TextView) findViewById(R.id.txt_subject_detail_time);
        txt_subject_detail_nickname = (TextView) findViewById(R.id.txt_subject_detail_nickname);
        txt_subject_detail_addr = (TextView) findViewById(R.id.txt_subject_detail_addr);
        txt_subject_detail_jianjie = (TextView) findViewById(R.id.txt_subject_detail_jianjie);
        txt_subject_detail_num = (TextView) findViewById(R.id.txt_subject_detail_num);
        txt_subject_detail_maxnum = (TextView) findViewById(R.id.txt_subject_detail_maxnum);
        txt_subject_detail_baoming = (TextView) findViewById(R.id.txt_subject_detail_baoming);
        img_subject_detail_back = (ImageView) findViewById(R.id.img_subject_detail_back);
        img_subject_detail_guanzhu = (ImageView) findViewById(R.id.img_subject_detail_guanzhu);
        img_subject_detail_share = (ImageView) findViewById(R.id.img_subject_detail_share);
        recycler_subject_detail_yibao = (RecyclerView) findViewById(R.id.recycler_subject_detail_yibao);
        recycler_subject_detail_yibao.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubjectDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_subject_detail_yibao.setLayoutManager(layoutManager);
        img_subject_detail_head = (CircleImageView) findViewById(R.id.img_subject_detail_head);
        layout_subject_detail_addr = (LinearLayout) findViewById(R.id.layout_subject_detail_addr);
        layout_subject_detail_tip = (LinearLayout) findViewById(R.id.layout_subject_detail_tip);
        viewpager_subject_detail_top = (CustomViewPager) findViewById(R.id.viewpager_subject_detail_top);
        scrollview = (PullToRefreshScrollView) findViewById(R.id.scrollview);
        scrollview.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            adList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        initViewpager();
        for (int i = 0; i < 5; i++) {
            numList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        numAdapter = new SubjectDetailNumAdapter(SubjectDetailActivity.this, numList);
        recycler_subject_detail_yibao.setAdapter(numAdapter);
    }

    private void setClick() {
        img_subject_detail_back.setOnClickListener(this);
        txt_subject_detail_baoming.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_subject_detail_back:
                finish();
                break;
            case R.id.txt_subject_detail_baoming:
                Intent intent = new Intent(SubjectDetailActivity.this,PayInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void initViewpager() {
        options = new DisplayImageOptions.Builder()
                //		.showImageOnLoading(R.drawable.icon_picture)
                //		.showImageForEmptyUri(R.drawable.icon_picture)
                //		.showImageOnFail(R.drawable.icon_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        imageViews.clear();
        for (int i = 0; i < adList.size(); i++) {
            final int p = i;
            View view = LayoutInflater.from(SubjectDetailActivity.this).inflate(R.layout.item_home_ad, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_ad_content);
            ImageLoader.getInstance().displayImage(adList.get(i),
                    imageView, options);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            imageViews.add(view);
        }
        //将点点加入到ViewGroup中
        tips = new ImageView[adList.size()];
        layout_subject_detail_tip.removeAllViews();
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(SubjectDetailActivity.this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            layout_subject_detail_tip.addView(imageView, layoutParams);
        }
        topAdapter = new SubjectDetailTopAdapter(SubjectDetailActivity.this, imageViews);
        //设置Adapter
        viewpager_subject_detail_top.setAdapter(topAdapter);
        //设置监听，主要是设置点点的背景
        viewpager_subject_detail_top.setOnPageChangeListener(new MyPageChangeListener());
        //        viewpager_ad.setCurrentItem(imageViews.size() * 100);

        if (adList.size() == 1) {
            viewpager_subject_detail_top.setScanScroll(false);
            layout_subject_detail_tip.setVisibility(View.GONE);
        } else {
            viewpager_subject_detail_top.setScanScroll(true);
            layout_subject_detail_tip.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;


        public void onPageSelected(int position) {
            setImageBackground(position % imageViews.size());
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
}
