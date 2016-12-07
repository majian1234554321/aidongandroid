package com.leyuan.aidong.ui.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.GymBrandBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.GymBrandAdapter;
import com.leyuan.aidong.ui.activity.discover.adapter.VenuesAdapter;
import com.leyuan.aidong.ui.activity.home.SearchActivity;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.dropdownmenu.DropDownMenu;
import com.leyuan.aidong.widget.dropdownmenu.adapter.ListWithFlagAdapter;
import com.leyuan.aidong.widget.dropdownmenu.adapter.SimpleListAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 发现-场馆
 * Created by song on 2016/8/29.
 */
public class DiscoverVenuesActivity extends BaseActivity implements DiscoverVenuesActivityView{
    private ImageView ivBack;
    private ImageView ivSearch;
    private DropDownMenu dropDownMenu;

    private View contentView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private SwitcherLayout switcherLayout;

    private int currPage = 1;
    private VenuesAdapter venuesAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<VenuesBean> data = new ArrayList<>();

    private String conditionHeaders[];
    private List<View> popupViews = new ArrayList<>();
    private GymBrandAdapter brandAdapter;        //品牌适配器
    private ListWithFlagAdapter categoryAdapter;     //类型适配器
    private SimpleListAdapter circleLeftAdapter;     //商圈一级列表适配器
    private ListWithFlagAdapter circleRightAdapter;  //商圈二级列表适配器

    private List<GymBrandBean> brandList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();

    private VenuesPresent venuesPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_venues);
        venuesPresent = new VenuesPresentImpl(this,this);
        venuesPresent.getGymBrand();
        venuesPresent.getBusinessCircle();
        initView();
        setListener();
        venuesPresent.commonLoadData(switcherLayout);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        initDropDownMenu();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void setListener(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscoverVenuesActivity.this, SearchActivity.class));
            }
        });
    }

    private void initDropDownMenu(){
        dropDownMenu = (DropDownMenu)findViewById(R.id.drop_down_menu);
        conditionHeaders = getResources().getStringArray(R.array.venuesCondition);

        final ListView brandView = new ListView(this);
        brandAdapter = new GymBrandAdapter(this, brandList);
        brandView.setDividerHeight(0);
        brandView.setAdapter(brandAdapter);

        final View circleView = View.inflate(this,R.layout.multi_list_drop_down,null);
        ListView leftListView = (ListView) circleView.findViewById(R.id.lv_left);
        final ListView rightListView = (ListView)circleView.findViewById(R.id.lv_right);
        circleLeftAdapter = new SimpleListAdapter(this,Arrays.asList("1"));
        leftListView.setAdapter(circleLeftAdapter);

        final ListView categoryView = new ListView(this);
        categoryAdapter = new ListWithFlagAdapter(this, categoryList);
        categoryView.setDividerHeight(0);
        categoryView.setAdapter(categoryAdapter);

        brandView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                brandAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? conditionHeaders[0] : brandList.get(position).getName());
                dropDownMenu.closeMenu();
            }
        });

        leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.setCheckItem(position);
                dropDownMenu.setTabText(position == 0 ? conditionHeaders[2] : categoryList.get(position));
                dropDownMenu.closeMenu();
            }
        });

        popupViews.add(brandView);
        popupViews.add(circleView);
        popupViews.add(categoryView);
        contentView = View.inflate(this,R.layout.dropdownmenu_content,null);
        dropDownMenu.setDropDownMenu(Arrays.asList(conditionHeaders), popupViews, contentView);
    }


    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)contentView.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this,refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                venuesPresent.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        venuesAdapter = new VenuesAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(venuesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                venuesPresent.requestMoreData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void setGymBrand(List<GymBrandBean> gymBrandBeanList) {
        brandList = gymBrandBeanList;
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {

    }

    @Override
    public void updateRecyclerView(List<VenuesBean> venuesBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }


    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }


}
