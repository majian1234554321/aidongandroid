package com.example.aidong.activity.subject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.SubjectListContentAdapter;
import com.example.aidong.view.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc1 on 2016/6/23.
 */
public class SubjectListActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {
    private ImageView img_subject_list_back, img_subject_list_type_cancle, img_subject_list_filter_tip_1, img_subject_list_filter_tip_2, img_subject_list_filter_tip_3;
    private TextView txt_subject_list_type_name, txt_subject_list_filter_name_1, txt_subject_list_filter_name_2, txt_subject_list_filter_name_3;
    private LinearLayout layout_subject_list_filter_1, layout_subject_list_filter_2, layout_subject_list_filter_3;
    private MyListView list_subject_list_content;
    private PullToRefreshScrollView scrollview;

    private SubjectListContentAdapter contentAdapter;

    private List<String> contentList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        img_subject_list_back = (ImageView) findViewById(R.id.img_subject_list_back);
        img_subject_list_type_cancle = (ImageView) findViewById(R.id.img_subject_list_type_cancle);
        img_subject_list_filter_tip_1 = (ImageView) findViewById(R.id.img_subject_list_filter_tip_1);
        img_subject_list_filter_tip_2 = (ImageView) findViewById(R.id.img_subject_list_filter_tip_2);
        img_subject_list_filter_tip_3 = (ImageView) findViewById(R.id.img_subject_list_filter_tip_3);
        txt_subject_list_type_name = (TextView) findViewById(R.id.txt_subject_list_type_name);
        txt_subject_list_filter_name_1 = (TextView) findViewById(R.id.txt_subject_list_filter_name_1);
        txt_subject_list_filter_name_2 = (TextView) findViewById(R.id.txt_subject_list_filter_name_2);
        txt_subject_list_filter_name_3 = (TextView) findViewById(R.id.txt_subject_list_filter_name_3);
        layout_subject_list_filter_1 = (LinearLayout) findViewById(R.id.layout_subject_list_filter_1);
        layout_subject_list_filter_2 = (LinearLayout) findViewById(R.id.layout_subject_list_filter_2);
        layout_subject_list_filter_3 = (LinearLayout) findViewById(R.id.layout_subject_list_filter_3);
        list_subject_list_content = (MyListView) findViewById(R.id.list_subject_list_content);
        scrollview = (PullToRefreshScrollView) findViewById(R.id.scrollview);
        scrollview.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void initData() {

        for (int i = 0; i < 8; i++) {
            contentList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        contentAdapter = new SubjectListContentAdapter(SubjectListActivity.this, contentList);
        list_subject_list_content.setAdapter(contentAdapter);


    }

    private void setClick() {
        scrollview.setOnRefreshListener(this);
        img_subject_list_back.setOnClickListener(this);
        list_subject_list_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SubjectListActivity.this, SubjectDetailActivity.class);
                startActivity(intent
                );
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_subject_list_back:
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
