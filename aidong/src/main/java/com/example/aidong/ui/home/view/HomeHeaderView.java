package com.example.aidong.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.example.aidong.R;
import com.example.aidong .adapter.home.HomeCourseAdapter;
import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.home.activity.CourseCategoryActivity;
import com.example.aidong .ui.store.StoreDetailActivity;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * the header of home page
 * Created by song on 2017/2/21.
 */
public class HomeHeaderView extends RelativeLayout{
    private Context context;
    private BGABanner banner;
    private LinearLayout sportsLayout;
    private MarqueeView marqueeView;
    private LinearLayout courseLayout;
    private List<VenuesBean> venuesBeanList = new ArrayList<>();
    private HomeCourseAdapter courseAdapter;

    public HomeHeaderView(Context context) {
        this(context,null,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context){
        this.context = context;
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_home,this,true);
        banner = (BGABanner) headerView.findViewById(R.id.banner);
        sportsLayout = (LinearLayout) headerView.findViewById(R.id.ll_sport_history);
        marqueeView = (MarqueeView) headerView.findViewById(R.id.marquee_view);
        courseLayout = (LinearLayout) headerView.findViewById(R.id.ll_course);
        RecyclerView rvCourse = (RecyclerView) headerView.findViewById(R.id.rv_course);
        TextView tvMoreCourse = (TextView) headerView.findViewById(R.id.tv_more_course);

        marqueeView.setAnimInAndOut(R.anim.top_in, R.anim.bottom_out);
        marqueeView.setInterval(5000);

        courseAdapter = new HomeCourseAdapter(context);
        rvCourse.setAdapter(courseAdapter);
        rvCourse.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((BannerBean)model).getImage(), (ImageView)view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
           @Override
           public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
               ((MainActivity)context).toTargetActivity((BannerBean)model);
           }
        });

        tvMoreCourse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CourseCategoryActivity.class));
            }
        });
    }

    public void setHomeBannerData(List<BannerBean> bannerBeanList){
        if (bannerBeanList != null && !bannerBeanList.isEmpty()) {
            banner.setVisibility(View.VISIBLE);
            banner.setAutoPlayAble(bannerBeanList.size() > 1);
            banner.setData(bannerBeanList, null);
        } else {
            banner.setVisibility(View.GONE);
        }
    }


    public void setSportHistory(List<VenuesBean> list){
        venuesBeanList.clear();
        if(list != null) {
            venuesBeanList.addAll(list);
        }
        if( venuesBeanList.isEmpty()){
            sportsLayout.setVisibility(GONE);
        }else {
            sportsLayout.setVisibility(VISIBLE);
            MarqueeFactory <RelativeLayout, VenuesBean> marqueeFactory = new VenuesMarqueeFactory(context);
            marqueeFactory.setData(venuesBeanList);
            marqueeView.setMarqueeFactory(marqueeFactory);
            marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<RelativeLayout, VenuesBean>() {
                @Override
                public void onItemClickListener(MarqueeFactory.ViewHolder<RelativeLayout, VenuesBean> holder) {
                    StoreDetailActivity.start(context,holder.data.getId());
                }
            });
            if(venuesBeanList.size() > 1 ) {
                marqueeView.startFlipping();
            }else {
                marqueeView.stopFlipping();
            }
        }
    }

    public void setCourseRecyclerView(List<CategoryBean> courseBeanList){
        if (courseBeanList != null && !courseBeanList.isEmpty()){
            courseAdapter.setData(courseBeanList);
            courseLayout.setVisibility(View.VISIBLE);
        }else{
            courseLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!marqueeView.isFlipping() && venuesBeanList.size() > 1){
            marqueeView.startFlipping();
        }
    }
}
