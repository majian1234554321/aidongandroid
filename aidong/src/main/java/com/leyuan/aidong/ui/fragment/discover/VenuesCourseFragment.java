package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.adapter.DateAdapter;
import com.leyuan.aidong.ui.activity.discover.adapter.VenuesCourseAdapter;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCourseFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.List;

/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class VenuesCourseFragment extends BaseFragment implements VenuesCourseFragmentView{
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;
    private VenuesCourseAdapter courseAdapter;
    private DateAdapter dateAdapter;
    private VenuesPresent venuesPresent;
    private String id;

    public static VenuesCourseFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        VenuesCourseFragment fragment = new VenuesCourseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        initView(view);
        venuesPresent.getCourses(switcherLayout,id);
    }

    private void initView(View view) {
        venuesPresent = new VenuesPresentImpl(getContext(),this);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(getContext(),contentLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);
        courseAdapter = new VenuesCourseAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setData(null);

        RecyclerView dateView = (RecyclerView)view.findViewById(R.id.rv_date);
        dateAdapter = new DateAdapter();
        dateView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        dateView.setAdapter(dateAdapter);
    }

    @Override
    public void setCourses(List<CourseBean> courseBeanList) {
        courseAdapter.setData(courseBeanList);
        dateAdapter.setData(null);
    }
}
