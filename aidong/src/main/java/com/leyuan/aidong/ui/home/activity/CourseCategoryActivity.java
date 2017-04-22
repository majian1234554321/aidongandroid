package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseCategoryAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CategoryListBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课类型界面
 * Created by song on 2017/4/12.
 */
public class CourseCategoryActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);

        SimpleTitleBar titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CourseCategoryAdapter adapter = new CourseCategoryAdapter(this);
        recyclerView.setAdapter(adapter);

        List<CategoryBean> sourceData = SystemInfoUtils.getCourseCategory(this);
        if(sourceData == null){
            return;
        }
        int size = sourceData.size() / 3;
        if(sourceData.size() % 3 != 0){
            size ++;
        }

        List<CategoryListBean> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CategoryListBean list = new CategoryListBean();
            List<CategoryBean> categoryBeanList = new ArrayList<>();
            for (int k = 0; k < sourceData.size(); k++) {
                if(k >= i * 3 && k < (i+1) * 3 ){
                    CategoryBean bean = sourceData.get(k);
                    categoryBeanList.add(bean);
                }
            }
            list.setCourseBeanList(categoryBeanList);
            data.add(list);
        }
        adapter.setData(data);


        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
