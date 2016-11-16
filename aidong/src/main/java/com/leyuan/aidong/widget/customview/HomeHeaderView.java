package com.leyuan.aidong.widget.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.NurtureActivity;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.utils.ImageLoadConfig;
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
    private ViewPagerIndicator indicator;

    private List<View> imageList = new ArrayList<>();
    private List<String> adList = new ArrayList<>();
    private DisplayImageOptions options;
    private ImageLoader loader;


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
        indicator = (ViewPagerIndicator)header.findViewById(R.id.vp_indicator);

        header.findViewById(R.id.tv_course).setOnClickListener(this);
        header.findViewById(R.id.tv_nurture).setOnClickListener(this);
        header.findViewById(R.id.tv_food).setOnClickListener(this);
        header.findViewById(R.id.tv_activity).setOnClickListener(this);
        header.findViewById(R.id.tv_equipment).setOnClickListener(this);
        header.findViewById(R.id.tv_competition).setOnClickListener(this);


        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        //HomeViewPagerAdapter pagerAdapter = new HomeViewPagerAdapter(imageList);
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
        //viewPager.addOnPageChangeListener(new OnPageChangeListenerImpl());
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_course:

                break;
            case R.id.tv_nurture:
                intent = new Intent(context, NurtureActivity.class);
                context.startActivity(intent);
                break;
            case R.id.tv_food:
                break;
            case R.id.tv_activity:
                break;
            case R.id.tv_equipment:
                break;
            case R.id.tv_competition:
                break;
        }
    }


}
