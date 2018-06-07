package com.example.aidong.ui.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.StoreListAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.VenuesDetailBean;
import com.example.aidong .entity.course.CourseFilterBean;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.activity.VenuesSubbranchActivity;
import com.example.aidong .ui.home.activity.GoodsListActivity;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong .ui.home.fragment.CourseListFragmentNew;
import com.example.aidong .ui.home.fragment.Fragment1;
import com.example.aidong .ui.mvp.presenter.VenuesPresent;
import com.example.aidong .ui.mvp.presenter.impl.VenuesPresentImpl;
import com.example.aidong .ui.mvp.view.VenuesDetailFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SharePrefUtils;
import com.example.aidong .utils.TelephoneManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.example.aidong.R.id.tv_price_separator;
import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2018/1/9.
 */

public class StoreDetailActivity extends BaseActivity implements View.OnClickListener, SmartTabLayout.TabProvider, VenuesDetailFragmentView {
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
    private LinearLayout llTicket, layout_address;
    private LinearLayout llOtherSubStore;
    private TextView txtSubStore;
    private TextView txtSubStoreNum;
    private RecyclerView rvOtherSubStore;
    private TextView txtStoreFacilities;
    private LinearLayout layoutStoreInnerFacility;
    private ImageView ivParking;
    private ImageView ivWifi;
    private ImageView ivBath;
    private ImageView ivFood, img_address;
    private TextView txtRelateCourse;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;


    private List<String> days;
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();

    //详情相关
    private String id;
    private VenuesDetailBean venues;
    private SharePopupWindow sharePopupWindow;
    private StoreListAdapter venuesAdapter;
    private String store;


    public static void start(Context context, String id) {
        Intent starter = new Intent(context, StoreDetailActivity.class);
        starter.putExtra("id", id);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        initView();
        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }

        VenuesPresent venuesPresent = new VenuesPresentImpl(this, this);
        venuesPresent.getVenuesDetail(id);
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        rlContent = (RelativeLayout) findViewById(R.id.rl_content);
        banner = (BGABanner) findViewById(R.id.banner);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvPriceSeparator = (TextView) findViewById(tv_price_separator);
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
        layout_address = (LinearLayout) findViewById(R.id.layout_address);

        img_address = (ImageView) findViewById(R.id.img_address);
        ivParking = (ImageView) findViewById(R.id.iv_parking);
        ivWifi = (ImageView) findViewById(R.id.iv_wifi);
        ivBath = (ImageView) findViewById(R.id.iv_bath);
        ivFood = (ImageView) findViewById(R.id.iv_food);






        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        rvOtherSubStore.setLayoutManager(layoutManager);
        rvOtherSubStore.setHasFixedSize(true);
        rvOtherSubStore.setNestedScrollingEnabled(false);
        rvOtherSubStore.setLayoutManager(new LinearLayoutManager(this));
       venuesAdapter = new StoreListAdapter(this);
        rvOtherSubStore.setAdapter(venuesAdapter);


        img_address.setOnClickListener(this);
        layout_address.setOnClickListener(this);
        llNurture.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
        llHealthyFood.setOnClickListener(this);
        llTicket.setOnClickListener(this);
        txtSubStoreNum.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

    }


    @Override
    public void setVenuesDetail(VenuesDetailBean venues) {
        this.venues = venues;
        tvName.setText(venues.getName());
        if (venues.getPrice() == null) {
            tvPrice.setText("");
            tvPriceSeparator.setText("");
        } else {
            tvPrice.setText(venues.getPrice() + "元起");
        }

        tvDistance.setText(venues.getDistanceFormat());
        txtAddress.setText(venues.city + venues.getArea());
        txtAddressDetail.setText(venues.getAddress());

        if (venues.getBrother() != null && !venues.getBrother().isEmpty()) {
            llOtherSubStore.setVisibility(View.VISIBLE);
            txtSubStoreNum.setText("共" + venues.getBrother().size() + "家分店");
            venuesAdapter.setData(venues.getBrother().size() > 2 ? venues.getBrother().subList(0, 2) : venues.getBrother());
            venuesAdapter.notifyDataSetChanged();
        } else {
            llOtherSubStore.setVisibility(View.GONE);
        }

        if (venues.getService() != null) {
            ivParking.setImageResource(venues.getService().contains("1") ? R.drawable.icon_parking :
                    R.drawable.icon_parking_gray);
            ivWifi.setImageResource(venues.getService().contains("2") ? R.drawable.icon_wifi :
                    R.drawable.icon_wifi_gray);
            ivBath.setImageResource(venues.getService().contains("3") ? R.drawable.icon_bath :
                    R.drawable.icon_bath_gray);
            ivFood.setImageResource(venues.getService().contains("4") ? R.drawable.icon_food :
                    R.drawable.icon_food_gray);
        }


        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((String) model), (ImageView) view);
            }
        });

        banner.setData(venues.getPhoto(), null);

        CourseFilterBean courseFilterConfig = SharePrefUtils.getCourseFilterConfig(this);
        if (courseFilterConfig != null) {
            store = courseFilterConfig.getStoreByVenuesBean(venues.getBrand_name(), venues.getName());
        }
        initRelateCourse();
    }

    private void initRelateCourse() {
        txtRelateCourse = (TextView) findViewById(R.id.txt_relate_course);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_user);

        days = DateUtils.getSevenDate();
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {

            CourseListFragmentNew courseFragment = new CourseListFragmentNew();
            pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("store", store).get()
            ));
        }

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);


        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new Fragment1());


      //  MyAdapter adapter = new MyAdapter(getSupportFragmentManager(),fragments);



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
                    CourseListFragmentNew page = (CourseListFragmentNew) adapter.getPage(position);
                    page.scrollToTop();
                }
            }
        });

        tabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {

            }
        });
    }




    public class  MyAdapter  extends FragmentPagerAdapter{

        public List<Fragment> list;

        public MyAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.list =list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).toString();
        }
    }

    @Override
    public void onClick(View view) {

        llNurture = (LinearLayout) findViewById(R.id.ll_nurture);
        llEquipment = (LinearLayout) findViewById(R.id.ll_equipment);
        llHealthyFood = (LinearLayout) findViewById(R.id.ll_healthy_food);
        llTicket = (LinearLayout) findViewById(R.id.ll_ticket);

        if (venues == null) return;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                String image = "";

                if (venues.getPhoto() != null && !venues.getPhoto().isEmpty()) {
                    image = venues.getPhoto().get(0);
                }
                sharePopupWindow.showAtBottom(venues.getName() + Constant.I_DONG_FITNESS, venues.getIntroduce(),
                        image, ConstantUrl.URL_SHARE_GYM + venues.getId());

                break;

            case R.id.ll_nurture:
                GoodsListActivity.start(this, GOODS_NUTRITION, "营养品", venues.getId());
                break;
            case R.id.ll_equipment:
                GoodsListActivity.start(this, GOODS_EQUIPMENT, "装备", venues.getId());
                break;
            case R.id.ll_healthy_food:

                GoodsListActivity.start(this, GOODS_FOODS, "健康餐饮", venues.getId());

                break;
            case R.id.ll_ticket:

                GoodsListActivity.start(this, GOODS_TICKET, "票务赛事", venues.getId());

                break;

            case R.id.img_bt_telephone:
                TelephoneManager.callImmediate(this, venues.getTel());

                break;
            case R.id.img_address:
            case R.id.layout_address:
                MapActivity.start(this, "门店地址", venues.getName(), venues.getAddress(), venues.getCoordinate().getLat() + "", venues.getCoordinate().getLng() + "");
                break;
            case R.id.txt_sub_store_num:
                VenuesSubbranchActivity.start(this, venues.getBrother());
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
