package com.example.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .adapter.home.CourseCategoryAdapter;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.CategoryListBean;
import com.example.aidong .entity.CourseTypeListBean;
import com.example.aidong .http.subscriber.BaseSubscriber;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mvp.model.CourseModel;
import com.example.aidong .ui.mvp.model.impl.CourseModelImpl;
import com.example.aidong .widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课类型界面
 * Created by song on 2017/4/12.
 */
public class CourseCategoryActivity extends BaseActivity{
    private CourseCategoryAdapter adapter;
    private List<CategoryBean> sourceData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_type);

        initView();
        initData();
    }


    private void initView(){
        SimpleTitleBar titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseCategoryAdapter(this);
        recyclerView.setAdapter(adapter);

        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(){
        CourseModel courseModel = new CourseModelImpl(this);
        courseModel.getCourseVideoTypeList(new BaseSubscriber<CourseTypeListBean>(this) {
            @Override
            public void onNext(CourseTypeListBean courseTypeListBean) {
                if(courseTypeListBean != null && courseTypeListBean.getCouses() != null){
                    sourceData =  courseTypeListBean.getCouses();

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
                }
            }
        });
    }
}
