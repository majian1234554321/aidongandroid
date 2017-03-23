package com.leyuan.aidong.ui.home.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.SearchHistoryAdapter;
import com.leyuan.aidong.entity.SearchHistoryBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.SearchResultFragment;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchActivityView;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;

import io.realm.Realm;

/**
 * 搜索
 * Created by song on 2016/9/12.
 * //todo  搜索模块需要重构......
 */
public class SearchActivity extends BaseActivity implements SearchActivityView{
    private ImageView ivBack;
    private EditText etSearch;
    private FrameLayout frameLayout;
    private SwitcherLayout switcherLayout;
    private RecyclerView recyclerView;
    private SearchHistoryAdapter historyAdapter;
    private Realm realm;
    private SearchPresent searchPresent;
    private boolean isFirst = true;
    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fadeInAnimations();
        setStatusBar();
        setContentView(R.layout.activity_search);
        realm = Realm.getDefaultInstance();
        searchPresent = new SearchPresentImpl(this,this,realm);
        initView();
        setListener();
        searchPresent.getSearchHistory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm != null){
            realm.close();
        }
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etSearch = (EditText) findViewById(R.id.et_search);
        frameLayout = (FrameLayout) findViewById(R.id.fl_container);
        recyclerView = (RecyclerView) findViewById(R.id.rv_search_history);
        switcherLayout = new SwitcherLayout(this,recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new SearchHistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);
    }

    private void setListener(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compatFinish();
            }
        });
        historyAdapter.setItemClickListener(new SearchHistoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String keyword) {
                etSearch.setText(keyword);
                etSearch.setSelection(keyword.length());
                searchPresent.commonLoadData(switcherLayout,keyword);
            }

            @Override
            public void onDeleteHistory() {
                searchPresent.deleteAllHistory();
                historyAdapter.notifyDataSetChanged();
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(TextUtils.isEmpty(etSearch.getText())) {
                        Toast.makeText(SearchActivity.this, R.string.input_content, Toast.LENGTH_LONG).show();
                    }else {
                        KeyBoardUtil.closeKeyboard(etSearch,SearchActivity.this);
                        String keyword = etSearch.getText().toString();
                        if(isFirst) {
                            searchPresent.commonLoadData(switcherLayout, keyword);
                        }else {
                            searchResultFragment.refreshSelectedData(keyword);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void setHistory(List<SearchHistoryBean> histories) {
        historyAdapter.setData(histories);
    }


    @Override
    public void setSearchResult(Object o) {
        //todo json转换
        String key = o.toString().substring(0, o.toString().indexOf("="));
        int index;
        if(key.contains("gym")){
            index = 0;
        }else if(key.contains("course")){
            index = 1;
        }else if(key.contains("nutrition")){
            index = 2;
        }else if(key.contains("equipment")){
            index = 3;
        }else if(key.contains("campaign")){
            index = 4;
        }else {
            index = 5;
        }
        String keyword = etSearch.getText().toString();
        searchResultFragment = SearchResultFragment.newInstance(index, keyword);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_container, searchResultFragment).commit();
        frameLayout.setVisibility(View.VISIBLE);
        searchPresent.insertHistory(keyword);
        isFirst = false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setStatusBar(){
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public String updateKeyword(){
        return etSearch.getText().toString();
    }

    public void closeKeybord(){
        KeyBoardUtil.closeKeyboard(etSearch,SearchActivity.this);
    }
}
