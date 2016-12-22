package com.leyuan.aidong.ui.activity.home;

import android.annotation.TargetApi;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.greendao.SearchHistory;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.SearchHistoryAdapter;
import com.leyuan.aidong.ui.fragment.home.SearchResultFragment;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchActivityView;
import com.leyuan.aidong.utils.KeyBoardUtil;

import java.util.ArrayList;
import java.util.List;




//import com.leyuan.aidong.entity.greendao.DaoMaster;

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
    private List<SearchHistory> historyList = new ArrayList<>();

    {
        for (int i = 0; i < 5; i++) {
            SearchHistory h = new SearchHistory();
            h.setKeyword("搜索历史" + i);
            historyList.add(h);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_search);

//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "search_history");
//        db = helper.getWritableDatabase();
        SearchPresent searchPresent = new SearchPresentImpl(this,this,db);
        initView();
        setListener();


        historyAdapter.setData(historyList);
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
        historyAdapter.setItemClickListener(new SearchHistoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String keyword) {
                etSearch.setText(keyword);
                etSearch.setSelection(keyword.length());
                KeyBoardUtil.closeKeybord(etSearch,SearchActivity.this);
                SearchResultFragment fragment = SearchResultFragment.newInstance(keyword);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fl_container,fragment).commit();
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDeleteHistory() {
                historyList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    if(TextUtils.isEmpty(etSearch.getText())){
                        Toast.makeText(SearchActivity.this,R.string.input_content,Toast.LENGTH_LONG).show();
                        return true;
                    }
                    KeyBoardUtil.closeKeybord(etSearch,SearchActivity.this);
                    String keyword = etSearch.getText().toString();
                    SearchResultFragment fragment = SearchResultFragment.newInstance(keyword);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide bottomSlide = new Slide();
        bottomSlide.setDuration(1000);
        bottomSlide.excludeTarget(android.R.id.statusBarBackground,true);
        bottomSlide.excludeTarget(R.id.ll_search_1,true);
        bottomSlide.setSlideEdge(Gravity.BOTTOM);

   /*     TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(bottomSlide);
        Slide topSlide = new Slide();
        topSlide.setDuration(1000);
        topSlide.excludeTarget(R.id.rl_content,true);
        topSlide.excludeTarget(android.R.id.statusBarBackground,true);
        topSlide.setSlideEdge(Gravity.TOP);
        transitionSet.addTransition(topSlide);*/

        // slide.addTarget(R.id.rl_content);
        getWindow().setEnterTransition(bottomSlide);

        /*Fade fade = new Fade();
        fade.setMode(MODE_OUT);
        fade.setDuration(500);
        getWindow().setExitTransition(fade);*/
    }
}
