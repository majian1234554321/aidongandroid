package com.example.aidong.ui.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.adapter.MyGridAdapter;
import com.example.aidong .adapter.discover.StoreListAdapter;
import com.example.aidong .entity.VenuesDetailBean;
import com.example.aidong .entity.course.CourseFilterBean;
import com.example.aidong .ui.discover.activity.VenuesSubbranchActivity;
import com.example.aidong .ui.home.activity.GoodsListActivity;
import com.example.aidong.ui.home.activity.GoodsListActivity2;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong.ui.home.activity.NurtureActivity;
import com.example.aidong.ui.home.view.StoreHeaderView;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SharePrefUtils;
import com.example.aidong.utils.SystemInfoUtils;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong.widget.MyGridView;
import com.example.aidong .widget.vertical.VerticalScrollView;

import cn.bingoogolapple.bgabanner.BGABanner;

import static android.view.View.GONE;
import static com.example.aidong.R.id.tv_price_separator;
import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;


public class Fragment_ScrollView extends Fragment implements View.OnClickListener {

    private VerticalScrollView scrollView;
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
    private LinearLayout layout_address;
    private ImageView img_address;
    private StoreListAdapter venuesAdapter;
    private ImageView ivParking;
    private ImageView ivWifi;
    private ImageView ivBath;
    private ImageView ivFood;
    private TextView txt_relate_course;
    private MyGridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scrollview, container, false);
        scrollView = (VerticalScrollView) rootView.findViewById(R.id.scrollView);

        //设置删除线
        // oldTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner = (BGABanner) view.findViewById(R.id.banner);


        txt_relate_course = (TextView) view.findViewById(R.id.txt_relate_course);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvPriceSeparator = (TextView) view.findViewById(tv_price_separator);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        txtAddressDetail = (TextView) view.findViewById(R.id.txt_address_detail);
        view.findViewById(R.id.img_bt_telephone).setOnClickListener(this);
        layoutRelateGoods = (LinearLayout) view.findViewById(R.id.layout_relate_goods);
        llNurture = (LinearLayout) view.findViewById(R.id.ll_nurture);
        llEquipment = (LinearLayout) view.findViewById(R.id.ll_equipment);
        llHealthyFood = (LinearLayout) view.findViewById(R.id.ll_healthy_food);
        llTicket = (LinearLayout) view.findViewById(R.id.ll_ticket);
        llOtherSubStore = (LinearLayout) view.findViewById(R.id.ll_other_sub_store);
        txtSubStore = (TextView) view.findViewById(R.id.txt_sub_store);
        txtSubStoreNum = (TextView) view.findViewById(R.id.txt_sub_store_num);
        rvOtherSubStore = (RecyclerView) view.findViewById(R.id.rv_other_sub_store);
        txtStoreFacilities = (TextView) view.findViewById(R.id.txt_store_facilities);
        layoutStoreInnerFacility = (LinearLayout) view.findViewById(R.id.layout_store_inner_facility);
        layout_address = (LinearLayout) view.findViewById(R.id.layout_address);


        img_address = (ImageView)  view.findViewById(R.id.img_address);
        ivParking = (ImageView) view.findViewById(R.id.iv_parking);
        ivWifi = (ImageView) view.findViewById(R.id.iv_wifi);
        ivBath = (ImageView) view.findViewById(R.id.iv_bath);
        ivFood = (ImageView) view.findViewById(R.id.iv_food);

        gridView = view.findViewById(R.id.gridview);

        setDataChange(getContext());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), NurtureActivity.class);
//                intent.putExtra("selectPosition",position);
//                startActivity(intent);



                GoodsListActivity2.start(getContext(), "", 0,position, SystemInfoUtils.getMarketPartsBean(getContext()).get(position).category_id+"");



            }
        });




        img_address.setOnClickListener(this);
        layout_address.setOnClickListener(this);
        llNurture.setOnClickListener(this);
        llEquipment.setOnClickListener(this);
        llHealthyFood.setOnClickListener(this);
        llTicket.setOnClickListener(this);
        txtSubStoreNum.setOnClickListener(this);




        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        rvOtherSubStore.setLayoutManager(layoutManager);
        rvOtherSubStore.setHasFixedSize(true);
        rvOtherSubStore.setNestedScrollingEnabled(false);

        venuesAdapter = new StoreListAdapter(getContext());
        rvOtherSubStore.setAdapter(venuesAdapter);


    }



    public void setDataChange(Context context) {
        if (SystemInfoUtils.getMarketPartsBean(context)!=null) {
            MyGridAdapter gridAdapter = new MyGridAdapter(context, SystemInfoUtils.getMarketPartsBean(context));
            gridView.setAdapter(gridAdapter);

        }else {
            gridView.setVisibility(GONE);
        }
    }



    public VenuesDetailBean venues;

    public void goTop() {
        scrollView.goTop();
    }

    public void setData(VenuesDetailBean data) {
        this.venues = data;


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
            llOtherSubStore.setVisibility(GONE);
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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_bt_telephone:
                TelephoneManager.callImmediate(getActivity(), venues.getTel());
                break;

            case R.id.ll_nurture:
                GoodsListActivity.start(getContext(), GOODS_NUTRITION, "营养品", venues.getId());
                break;
            case R.id.ll_equipment:
                GoodsListActivity.start(getContext(), GOODS_EQUIPMENT, "装备", venues.getId());
                break;
            case R.id.ll_healthy_food:

                GoodsListActivity.start(getContext(), GOODS_FOODS, "健康餐饮", venues.getId());

                break;
            case R.id.ll_ticket:

                GoodsListActivity.start(getContext(), GOODS_TICKET, "票务赛事", venues.getId());

                break;

            case R.id.img_address:
            case R.id.layout_address:
                MapActivity.start(getContext(), "门店地址", venues.getName(), venues.getAddress(), venues.getCoordinate().getLat() + "", venues.getCoordinate().getLng() + "");
                break;
            case R.id.txt_sub_store_num:
                VenuesSubbranchActivity.start(getContext(), venues.getBrother());
                break;
        }
    }
}
