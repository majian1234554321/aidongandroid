package com.example.aidong.activity.home.subject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.SubjectFilterTypeAdapter;
import com.example.aidong.adapter.SubjectTimeAdapter;
import com.example.aidong.view.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/23.
 */
public class SubjectFilterActivity extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2{
    private ImageView img_subject_filter_back;
    private TextView txt_subject_filter_title;
    private RecyclerView recycler_subject_filter_time;
    private MyListView list_subject_filter_content;
    private PullToRefreshScrollView scrollview;

    private SubjectTimeAdapter timeAdapte;
    private SubjectFilterTypeAdapter typeAdapter;

    private List<String> timeList = new ArrayList<String>();
    private List<String> typeList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_filter);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        img_subject_filter_back = (ImageView) findViewById(R.id.img_subject_filter_back);
        txt_subject_filter_title = (TextView) findViewById(R.id.txt_subject_filter_title);
        recycler_subject_filter_time = (RecyclerView) findViewById(R.id.recycler_subject_filter_time);
        recycler_subject_filter_time.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SubjectFilterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recycler_subject_filter_time.setLayoutManager(layoutManager);
        list_subject_filter_content = (MyListView) findViewById(R.id.list_subject_filter_content);
        scrollview = (PullToRefreshScrollView)findViewById(R.id.scrollview);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            timeList.add("asdasda");
        }
        timeAdapte = new SubjectTimeAdapter(SubjectFilterActivity.this, timeList);
        recycler_subject_filter_time.setAdapter(timeAdapte);
        for (int i = 0; i < 8; i++) {
            typeList.add("asdasda");
        }
        typeAdapter = new SubjectFilterTypeAdapter(SubjectFilterActivity.this, typeList);
        list_subject_filter_content.setAdapter(typeAdapter);


    }

    private void setClick() {
        scrollview.setOnRefreshListener(this);
        img_subject_filter_back.setOnClickListener(this);
        list_subject_filter_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectFilterActivity.this, SubjectListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_subject_filter_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        scrollview.onRefreshComplete();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        scrollview.onRefreshComplete();
    }
}
