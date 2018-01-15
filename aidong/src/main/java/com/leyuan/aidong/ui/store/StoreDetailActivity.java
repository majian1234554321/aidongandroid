package com.leyuan.aidong.ui.store;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.CourseListFragmentNew;
import com.leyuan.aidong.utils.DateUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by user on 2018/1/9.
 */

public class StoreDetailActivity extends BaseActivity implements View.OnClickListener, SmartTabLayout.TabProvider {
    private ImageView ivBack;
    private ImageView ivShare;
    private RelativeLayout rlContent;
    private BGABanner banner;
    private TextView tvName;
    private TextView tvPrice;
    private TextView tvPriceSeparator;
    private TextView tvDistance;
    private TextView txtAddress;
    private TextView txtAddressDetail;
    private LinearLayout layoutRelateGoods;
    private LinearLayout llNurture;
    private LinearLayout llEquipment;
    private LinearLayout llHealthyFood;
    private LinearLayout llTicket;
    private LinearLayout llOtherSubStore;
    private TextView txtSubStore;
    private TextView txtSubStoreNum;
    private RecyclerView rvOtherSubStore;
    private TextView txtStoreFacilities;
    private LinearLayout layoutStoreInnerFacility;
    private ImageView ivParking;
    private ImageView ivWifi;
    private ImageView ivBath;
    private ImageView ivFood;
    private TextView txtRelateCourse;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> days;
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        rlContent = (RelativeLayout) findViewById(R.id.rl_content);
        banner = (BGABanner) findViewById(R.id.banner);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPriceSeparator = (TextView) findViewById(R.id.tv_price_separator);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        txtAddress = (TextView) findViewById(R.id.txt_address);
        txtAddressDetail = (TextView) findViewById(R.id.txt_address_detail);
        findViewById(R.id.img_bt_telephone).setOnClickListener(this);
        layoutRelateGoods = (LinearLayout) findViewById(R.id.layout_relate_goods);
        llNurture = (LinearLayout) findViewById(R.id.ll_nurture);
        llEquipment = (LinearLayout) findViewById(R.id.ll_equipment);
        llHealthyFood = (LinearLayout) findViewById(R.id.ll_healthy_food);
        llTicket = (LinearLayout) findViewById(R.id.ll_ticket);
        llOtherSubStore = (LinearLayout) findViewById(R.id.ll_other_sub_store);
        txtSubStore = (TextView) findViewById(R.id.txt_sub_store);
        txtSubStoreNum = (TextView) findViewById(R.id.txt_sub_store_num);
        rvOtherSubStore = (RecyclerView) findViewById(R.id.rv_other_sub_store);
        txtStoreFacilities = (TextView) findViewById(R.id.txt_store_facilities);
        layoutStoreInnerFacility = (LinearLayout) findViewById(R.id.layout_store_inner_facility);
        ivParking = (ImageView) findViewById(R.id.iv_parking);
        ivWifi = (ImageView) findViewById(R.id.iv_wifi);
        ivBath = (ImageView) findViewById(R.id.iv_bath);
        ivFood = (ImageView) findViewById(R.id.iv_food);
        txtRelateCourse = (TextView) findViewById(R.id.txt_relate_course);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_user);

        days = DateUtils.getSevenDate();
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {
            CourseListFragmentNew courseFragment = new CourseListFragmentNew();
            pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("category", "").get()
            ));

        }

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

                    //reset fragment
                    CourseListFragmentNew page = (CourseListFragmentNew) adapter.getPage(position);
                    page.scrollToTop();
//                    filterView.animate().translationY(0).setInterpolator
//                            (new DecelerateInterpolator(2)).start();
                }
            }
        });

        tabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bt_telephone:
                break;
        }
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_course_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        text.setText(DateUtils.getCourseSevenDate().get(position));
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
