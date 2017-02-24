package com.leyuan.aidong.ui.home.activity;

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

import java.util.List;

import io.realm.Realm;

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
    private Realm realm;
    private SearchPresent searchPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fadeInAnimations();
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
                KeyBoardUtil.closeKeyboard(etSearch,SearchActivity.this);
                SearchResultFragment fragment = SearchResultFragment.newInstance(keyword);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fl_container,fragment).commit();
                frameLayout.setVisibility(View.VISIBLE);
                searchPresent.insertHistory(keyword);
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
                        SearchResultFragment fragment = SearchResultFragment.newInstance(keyword);
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.fl_container,fragment).commit();
                        frameLayout.setVisibility(View.VISIBLE);
                        searchPresent.insertHistory(keyword);
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


}
