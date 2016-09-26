package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.discover.adapter.DateAdapter;
import com.leyuan.aidong.ui.activity.discover.adapter.VenuesCourseAdapter;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCourseFragmentView;

import java.util.List;

/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class VenuesCourseFragment extends BaseFragment implements VenuesCourseFragmentView{

    private VenuesCourseAdapter courseAdapter;
    private RecyclerView dateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        VenuesPresent present = new VenuesPresentImpl(getContext(),this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);
        courseAdapter = new VenuesCourseAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);

        dateView = (RecyclerView)view.findViewById(R.id.rv_date);
        DateAdapter dateAdapter = new DateAdapter();
        dateView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        dateView.setAdapter(dateAdapter);

        present.getCourses(1);
    }

    @Override
    public void setCourses(List<CourseBean> courseBeanList) {
        courseAdapter.setData(courseBeanList);
    }
}
