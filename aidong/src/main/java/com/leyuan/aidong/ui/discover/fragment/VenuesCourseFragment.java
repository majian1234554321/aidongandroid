package com.leyuan.aidong.ui.discover.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.adapter.discover.DateAdapter;
import com.leyuan.aidong.adapter.discover.VenuesCourseAdapter;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCourseFragmentView;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;


/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class VenuesCourseFragment extends BaseFragment implements VenuesCourseFragmentView, DateAdapter.ItemClickListener {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private VenuesCourseAdapter courseAdapter;
    private DateAdapter dateAdapter;
    private String id;
    private String day = "0";
    private VenuesPresent venuesPresent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venuesPresent = new VenuesPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        initView(view);
        venuesPresent.getCourses(switcherLayout,id,day);
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);
        courseAdapter = new VenuesCourseAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);

        RecyclerView dateView = (RecyclerView)view.findViewById(R.id.rv_date);
        dateAdapter = new DateAdapter();
        dateView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        dateView.setAdapter(dateAdapter);
        dateAdapter.setData(DateUtils.getSevenDate());
        dateAdapter.setItemClickListener(this);
    }

    @Override
    public void setCourses(List<CourseBean> courseBeanList) {
        courseAdapter.setData(courseBeanList);
    }

    @Override
    public void onItemClick(int position) {
        day = String.valueOf(position);
        venuesPresent.getCourses(switcherLayout,id,day);
    }
}
