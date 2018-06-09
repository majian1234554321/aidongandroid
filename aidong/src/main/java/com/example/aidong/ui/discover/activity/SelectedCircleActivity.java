package com.example.aidong.ui.discover.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.SelectCircleAdapter;
import com.example.aidong .entity.CampaignBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.view.SearchHeaderView;
import com.example.aidong .ui.mvp.presenter.CirclePrensenter;
import com.example.aidong .ui.mvp.view.SelectedCircleView;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .widget.CommonTitleLayout;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/9.
 */

public class SelectedCircleActivity extends BaseActivity implements SelectedCircleView {

    private CommonTitleLayout layoutTitle;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private SwitcherLayout switcherLayout;
    SelectCircleAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    CirclePrensenter circlePrensenter;


    boolean isSearch;
    private String keyword;

    private TextView tv,txt_search_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_location_activity2);


        final EditText    etSearch = (EditText) findViewById(R.id.et_search);
        tv = findViewById(R.id.tv);
        txt_search_title = findViewById(R.id.txt_search_title);





        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);




                    isSearch = true;
                    currPage = 1;
                    keyword = etSearch.getText().toString().trim();

                    DialogUtils.showDialog(SelectedCircleActivity.this, "", true);
                    circlePrensenter.searchCircle(keyword);

                    return true;
                }

                return false;
            }
        });






        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        initSwipeRefreshLayout();
        initRecyclerView();
        initSwitcherLayout();
        circlePrensenter = new CirclePrensenter(this);
        circlePrensenter.setSelectedCircleListener(this);
        circlePrensenter.getRecommendCircle();

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                if (isSearch) {
                    circlePrensenter.searchCircle(keyword);
                } else {
                    circlePrensenter.getRecommendCircle();
                }

            }
        });
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initRecyclerView() {
        adapter = new SelectCircleAdapter(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);




    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
        }
    };



    @Override
    public void onGetRecommendCircle(ArrayList<CampaignBean> items) {
        adapter.setData(items);
        tv.setVisibility(View.GONE);
        txt_search_title.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchCircleResult(ArrayList<CampaignBean> items) {
        DialogUtils.dismissDialog();



        txt_search_title.setVisibility(View.GONE);
        tv.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        refreshLayout.setVisibility(View.VISIBLE);



        adapter.setData(items);
    }

    @Override
    public void hideHeadItemView() {
        txt_search_title.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.GONE);
    }
}
