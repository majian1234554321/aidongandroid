package com.example.aidong.activity.home.food;

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
import com.example.aidong.adapter.FoodHomeContentAdapter;
import com.example.aidong.adapter.FoodHomeTopAdapter;
import com.example.aidong.adapter.FoodHomeTuiJianAdapter;
import com.example.aidong.view.CustomViewPager;
import com.example.aidong.view.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/28.
 */
public class FoodHomeActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {

    private LinearLayout layout_foodhome_tip, layout_foodhome_tuijian, layout_home_select;
    private RecyclerView recycler_foodhome_tuijian;
    private MyListView list_foodhome_content;
    private TextView txt_foodhome_type_name;
    private CustomViewPager viewpager_foodhome_top;
    private ImageView img_foodhome_back, img_foodhome_type_cancle;
    private PullToRefreshScrollView scrollview;

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    private List<View> imageViews = new ArrayList<View>(); // 滑动的图片集合
    private List<String> adList = new ArrayList<String>();

    private List<String> tuijianList = new ArrayList<String>();
    private List<String> contentList = new ArrayList<String>();

    protected DisplayImageOptions options;

    private FoodHomeTuiJianAdapter tuiJianAdapter;
    private FoodHomeTopAdapter topAdapter;
    private FoodHomeContentAdapter contentAdapter;

    private boolean isTuijian = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodhome);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        layout_foodhome_tip = (LinearLayout) findViewById(R.id.layout_foodhome_tip);
        layout_foodhome_tuijian = (LinearLayout) findViewById(R.id.layout_foodhome_tuijian);
        layout_home_select = (LinearLayout) findViewById(R.id.layout_home_select);
        recycler_foodhome_tuijian = (RecyclerView) findViewById(R.id.recycler_foodhome_tuijian);
        viewpager_foodhome_top = (CustomViewPager) findViewById(R.id.viewpager_foodhome_top);
        recycler_foodhome_tuijian.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FoodHomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_foodhome_tuijian.setLayoutManager(layoutManager);
        list_foodhome_content = (MyListView) findViewById(R.id.list_foodhome_content);
        txt_foodhome_type_name = (TextView) findViewById(R.id.txt_foodhome_type_name);
        img_foodhome_back = (ImageView) findViewById(R.id.img_foodhome_back);
        img_foodhome_type_cancle = (ImageView) findViewById(R.id.img_foodhome_type_cancle);
        scrollview = (PullToRefreshScrollView) findViewById(R.id.scrollview);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);
        layout_foodhome_tuijian.setFocusable(true);
        layout_foodhome_tuijian.setFocusableInTouchMode(true);
        layout_foodhome_tuijian.requestFocus();

    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            adList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        initViewpager();
        for (int i = 0; i < 5; i++) {
            tuijianList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        tuiJianAdapter = new FoodHomeTuiJianAdapter(FoodHomeActivity.this, tuijianList);
        recycler_foodhome_tuijian.setAdapter(tuiJianAdapter);
        for (int i = 0; i < 8; i++) {
            contentList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        contentAdapter = new FoodHomeContentAdapter(FoodHomeActivity.this, contentList);
        list_foodhome_content.setAdapter(contentAdapter);
    }

    private void setClick() {
        scrollview.setOnRefreshListener(this);
        img_foodhome_back.setOnClickListener(this);
        img_foodhome_type_cancle.setOnClickListener(this);
//        scrollview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int scrollY = 0, oldScrollY = 0;
//                boolean first = true;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        if (first) {
//                            oldScrollY = v.getScrollY();
//                            first = false;
//                            System.out.println("++++++++++++++++++++++++++oldScrollY = " + oldScrollY);
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        first = true;
//                        scrollY = v.getScrollY();// 记录下手指抬起时ScrollY值
//                        System.out.println("++++++++++++++++++++++++++scrollY = " + scrollY);
//                        if (isTuijian) {
//                            if (scrollY > oldScrollY) {
//                                isTuijian = false;
//                                layout_foodhome_tuijian.setVisibility(View.GONE);
//                            }
//                        }
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
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
            View view = LayoutInflater.from(FoodHomeActivity.this).inflate(R.layout.item_home_ad, null);
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
        layout_foodhome_tip.removeAllViews();
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(FoodHomeActivity.this);
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
            layout_foodhome_tip.addView(imageView, layoutParams);
        }
        topAdapter = new FoodHomeTopAdapter(FoodHomeActivity.this, imageViews);
        //设置Adapter
        viewpager_foodhome_top.setAdapter(topAdapter);
        //设置监听，主要是设置点点的背景
        viewpager_foodhome_top.setOnPageChangeListener(new MyPageChangeListener());
        //        viewpager_ad.setCurrentItem(imageViews.size() * 100);

        if (adList.size() == 1) {
            viewpager_foodhome_top.setScanScroll(false);
            layout_foodhome_tip.setVisibility(View.GONE);
        } else {
            viewpager_foodhome_top.setScanScroll(true);
            layout_foodhome_tip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        scrollview.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (isTuijian) {
            isTuijian = false;
            layout_foodhome_tuijian.setVisibility(View.GONE);
        }
        scrollview.onRefreshComplete();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_foodhome_back:
                finish();
                break;
            case R.id.img_foodhome_type_cancle:
                layout_home_select.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
