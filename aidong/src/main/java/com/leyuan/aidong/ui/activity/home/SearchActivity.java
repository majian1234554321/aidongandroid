package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.greendao.SearchHistory;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.SearchHistoryAdapter;
import com.leyuan.aidong.ui.fragment.home.SearchResultFragment;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchActivityView;

import java.util.List;

/**
 * 搜索
 * Created by song on 2016/9/12.
 */
public class SearchActivity extends BaseActivity implements SearchActivityView{
    private EditText etSearch;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private SearchHistoryAdapter historyAdapter;

    private SearchPresent searchPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        etSearch = (EditText) findViewById(R.id.et_search);
        frameLayout = (FrameLayout) findViewById(R.id.fl_container);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);

        searchPresent = new SearchPresentImpl(this,this);
        for (int i = 0; i < 15; i++) {
            searchPresent.insertHistory( i + "" );
        }

        searchPresent.getSearchHistory();

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                SearchResultFragment fragment = new SearchResultFragment();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fl_container,fragment).commit();
                frameLayout.setVisibility(View.VISIBLE);

                return false;
            }
        });

    }

    @Override
    public void setHistory(List<SearchHistory> historyList) {
        historyAdapter.setData(historyList);
    }


}
