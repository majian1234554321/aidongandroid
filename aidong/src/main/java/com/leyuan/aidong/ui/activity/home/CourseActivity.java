package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.BusinessCircleDescBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.view.CourseFilterView;
import com.leyuan.aidong.ui.fragment.home.CourseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 小团体课列表
 * Created by song on 2016/10/31.
 */
public class CourseActivity extends BaseActivity{
    private ImageView ivBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CourseFilterView filterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        filterView = (CourseFilterView) findViewById(R.id.view_filter_course);

        filterView.setCategoryList(Arrays.asList(getResources().getStringArray(R.array.characterTag)));
        List<BusinessCircleBean> circleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BusinessCircleBean circleBean = new BusinessCircleBean();
            circleBean.setAreaName("商圈" + i);
            circleBean.setAreaId(i+"");
            List<BusinessCircleDescBean> list = new ArrayList<>();
            for (int i1 = 0; i1 < 10; i1++) {
                BusinessCircleDescBean bean = new BusinessCircleDescBean();
                bean.setAreaName("商圈" + i+ ":" +i1 + "路");
                list.add(bean);
                bean.setAreaId(i+"" + i1);
                circleBean.setDistrict(list);
            }
            circleList.add(circleBean);
        }
        filterView.setCircleList(circleList);

        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CourseFragment f = new CourseFragment();
            fragments.add(f);
        }
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.dateTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setListener(){
        ivBack.setOnClickListener(backListener);
        filterView.setOnFilterClickListener(new MyOnFilterClickListener());
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private class MyOnFilterClickListener implements CourseFilterView.OnFilterClickListener{

        @Override
        public void onCategoryItemClick(int position) {

        }

        @Override
        public void onBusinessCircleItemClick(String address) {

        }
    }
}
