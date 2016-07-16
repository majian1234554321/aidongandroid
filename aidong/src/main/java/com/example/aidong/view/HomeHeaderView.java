package com.example.aidong.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.aidong.R;
import com.example.aidong.adapter.HomeViewPagerAdapter;
import com.example.aidong.utils.ImageLoadConfig;
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
    private List<View> viewList = new ArrayList<>();
    private List<String> adList = new ArrayList<>();
    private DisplayImageOptions options;

    public HomeHeaderView(Context context) {
        this(context,null,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    public void init(Context context) {
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
            adList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }

        for (int i = 0; i < adList.size(); i++) {
            ImageView view = new ImageView(context);
            ImageLoader.getInstance().displayImage(adList.get(i),view,options);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            viewList.add(view);
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
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            dotLayout.addView(imageView, layoutParams);
        }
        HomeViewPagerAdapter pagerAdapter = new HomeViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_course:
                break;
            case R.id.tv_nurture:
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
}
