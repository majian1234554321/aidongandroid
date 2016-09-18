package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.SearchHistoryAdapter;

/**
 * 搜索
 * Created by song on 2016/9/12.
 */
public class SearchActivity extends BaseActivity{
    private EditText etSearch;
    private RecyclerView recyclerView;
    private SearchHistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = (EditText) findViewById(R.id.et_search);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);

    }
}
