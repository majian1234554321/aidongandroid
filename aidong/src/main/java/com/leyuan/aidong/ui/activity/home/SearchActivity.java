package com.leyuan.aidong.ui.activity.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.leyuan.aidong.R;
//import com.leyuan.aidong.entity.greendao.DaoMaster;
import com.leyuan.aidong.entity.greendao.SearchHistory;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.SearchHistoryAdapter;
import com.leyuan.aidong.ui.fragment.home.SearchResultFragment;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchActivityView;
import com.leyuan.aidong.utils.KeyBoardUtil;

import java.util.List;

/**
 * 搜索
 * Created by song on 2016/9/12.
 */
public class SearchActivity extends BaseActivity implements SearchActivityView{
    private ImageView ivBack;
    private EditText etSearch;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private SearchHistoryAdapter historyAdapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "search_history");
//        db = helper.getWritableDatabase();
        SearchPresent searchPresent = new SearchPresentImpl(this,this,db);
        initView();
        setListener();
        searchPresent.getSearchHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(db != null){
            db.close();
        }
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etSearch = (EditText) findViewById(R.id.et_search);
        frameLayout = (FrameLayout) findViewById(R.id.fl_container);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);
    }

    private void setListener(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etSearch.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    KeyBoardUtil.closeKeybord(etSearch,SearchActivity.this);
                    SearchResultFragment fragment = new SearchResultFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fl_container,fragment).commit();
                    frameLayout.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setHistory(List<SearchHistory> historyList) {
        historyAdapter.setData(historyList);
    }
}
