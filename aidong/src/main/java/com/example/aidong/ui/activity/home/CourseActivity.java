package com.example.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.home.adapter.CourseAdapter;
import com.example.aidong.entity.CourseBean;
import com.example.aidong.ui.mvp.presenter.CoursePresent;
import com.example.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.example.aidong.ui.mvp.view.CoursesActivityView;
import com.example.aidong.widget.dropdownmenu.DropDownMenu;
import com.example.aidong.widget.dropdownmenu.adapter.ListWithFlagAdapter;
import com.example.aidong.widget.dropdownmenu.adapter.SimpleListAdapter;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 小团体课列表
 * Created by song on 2016/8/17.
 */
public class CourseActivity extends BaseActivity implements CoursesActivityView{

    private DropDownMenu dropDownMenu;
    private View contentView;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private CourseAdapter courseAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CoursePresent present;
    private ArrayList<CourseBean> data = new ArrayList<>();

    private String conditionHeaders[];
    private List<View> popupViews = new ArrayList<>();
    private ListWithFlagAdapter dateAdapter;    //日期适配器
    private ListWithFlagAdapter categoryAdapter;//分类适配器
    private SimpleListAdapter groupAdapter;     //商圈一级列表适配器
    private ListWithFlagAdapter childrenAdapter;//商圈二级列表适配器

    private int day = 3;
    private int category ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        pageSize = 20;
        present = new CoursePresentImpl(this,this);
        initDropDownMenu();
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initDropDownMenu(){
        dropDownMenu = (DropDownMenu)findViewById(R.id.drop_down_menu);
        conditionHeaders = getResources().getStringArray(R.array.courseCondition);

        final ListView dateView = new ListView(this);
        dateAdapter = new ListWithFlagAdapter(this, Arrays.asList("1"));
        dateView.setDividerHeight(0);
        dateView.setAdapter(dateAdapter);

        final ListView categoryView = new ListView(this);
        categoryAdapter = new ListWithFlagAdapter(this, Arrays.asList("1"));
        categoryView.setDividerHeight(0);
        categoryView.setAdapter(categoryAdapter);

        final View addressView = View.inflate(this,R.layout.multi_list_drop_down,null);
        ListView leftListView = (ListView) addressView.findViewById(R.id.lv_left);
        final ListView rightListView = (ListView)addressView.findViewById(R.id.lv_right);
        groupAdapter = new SimpleListAdapter(this,Arrays.asList("1"));
        leftListView.setAdapter(groupAdapter);

        dateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateAdapter.setCheckItem(position);
                day = 4;
                currPage = 1;
                present.pullToRefreshData(category,day);
                //dropDownMenu.setTabText(position == 0 ? conditionHeaders[0] : citys[position]);
                dropDownMenu.closeMenu();
            }
        });

        categoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dateAdapter.setCheckItem(position);
                //dropDownMenu.setTabText(position == 0 ? conditionHeaders[0] : citys[position]);
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

        popupViews.add(dateView);
        popupViews.add(categoryView);
        popupViews.add(addressView);
        contentView = View.inflate(this,R.layout.dropdownmenu_content,null);
        dropDownMenu.setDropDownMenu(Arrays.asList(conditionHeaders), popupViews, contentView);
    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (SwipeRefreshLayout)contentView.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshData(category,day);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(category,day);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView)contentView.findViewById(R.id.recycler_view);
        data = new ArrayList<>();
        courseAdapter = new CourseAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
                present.requestMoreData(recyclerView,pageSize,category,day,currPage);
            }
        }
    };


    @Override
    public void updateRecyclerView(List<CourseBean> courseList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }

            data.addAll(courseList);


        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }

    @Override
    public void showRecyclerView() {

    }

    @Override
    public void hideRecyclerView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
