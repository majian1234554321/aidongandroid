package com.example.aidong.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.aidong.R;
import com.example.aidong.activity.home.NurtureActivity;
import com.example.aidong.adapter.HomeViewPagerAdapter;
import com.example.aidong.utils.ImageLoadConfig;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页RecyclerView的头布局
 * Created by song on 2016/7/14.
 */
public class HomeHeaderView extends RelativeLayout implements View.OnClickListener{
    private Context context;
    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private ImageView[] dotArray;
    private List<View> imageList = new ArrayList<>();
    private List<String> adList = new ArrayList<>();
    private DisplayImageOptions options;
    private ImageLoader loader;

    private int currentPage;
    private boolean isStop = false;     //是否停止轮播
    private static final  int LOOP_GAP = 5000;

    public HomeHeaderView(Context context) {
        this(context,null,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        loader = ImageLoader.getInstance();
        options = new ImageLoadConfig().getOptions(R.drawable.renzheng);
        View header = inflate(context, R.layout.header_home, this);
        viewPager = (ViewPager) header.findViewById(R.id.vp_home);
        dotLayout = (LinearLayout)header.findViewById(R.id.ll_dot);
        header.findViewById(R.id.tv_course).setOnClickListener(this);
        header.findViewById(R.id.tv_nurture).setOnClickListener(this);
        header.findViewById(R.id.tv_food).setOnClickListener(this);
        header.findViewById(R.id.tv_activity).setOnClickListener(this);
        header.findViewById(R.id.tv_equipmen).setOnClickListener(this);
        header.findViewById(R.id.tv_competition).setOnClickListener(this);

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0){
                adList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
            }else{
                adList.add("http://f.hiphotos.baidu.com/baike/pic/item/6159252dd42a2834b1c7cf5b59b5c9ea15cebf79.jpg");
            }
        }

        int size = adList.size();
        for (int i = 0; i < size; i++) {
            ImageView image = new ImageView(context);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            loader.displayImage(adList.get(i),image,options);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.show("click",context);
                }
            });
            imageList.add(image);
        }

        //将点加入到ViewGroup中
        dotArray = new ImageView[adList.size()];
        dotLayout.removeAllViews();
        for (int i = 0; i < dotArray.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            dotArray[i] = imageView;
            if (i == 0) {
                dotArray[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                dotArray[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                    (new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            dotLayout.addView(imageView, layoutParams);
        }

        HomeViewPagerAdapter pagerAdapter = new HomeViewPagerAdapter(imageList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new OnPageChangeListenerImpl());
        new PointThread().start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_course:
                break;
            case R.id.tv_nurture:
                Intent intent = new Intent(context, NurtureActivity.class);
                context.startActivity(intent);
                break;
            case R.id.tv_food:
                break;
            case R.id.tv_activity:
                break;
            case R.id.tv_equipmen:
                break;
            case R.id.tv_competition:
                break;
        }
    }

    /**ViewPager滑动时更新指示点*/
    private  class OnPageChangeListenerImpl extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            position = position % imageList.size();
            for (int i = 0; i < imageList.size(); i++) {
                if (i == position) {
                    dotArray[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    dotArray[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }
    }

    //TODO handler泄露
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != viewPager) {
                viewPager.setCurrentItem(msg.what);
            }
            super.handleMessage(msg);
        }
    };

    /**焦点图循环 */
    private class PointThread extends Thread {
        @Override
        public void run() {
            while (!isStop) {
                try {
                    Thread.sleep(LOOP_GAP);
                    currentPage++;
                    handler.sendEmptyMessage(currentPage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**停止焦点图循环*/
    public void setStop(boolean stop) {
        isStop = stop;
    }
}
