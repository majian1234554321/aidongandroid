package com.leyuan.aidong.ui.discover.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.DateAdapter;
import com.leyuan.aidong.adapter.discover.VenuesCourseAdapter;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.entity.data.CourseData;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCourseFragmentView;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.CustomLayoutManager;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;


/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class VenuesCourseFragment extends BaseFragment implements VenuesCourseFragmentView, DateAdapter.ItemClickListener {
    private static final java.lang.String TAG = "VenuesCourseFragment";
    private SwitcherLayout switcherLayout;
    private VenuesCourseAdapter courseAdapter;
    private String id;
    private List<String> days;
    private VenuesPresent venuesPresent;
    private RecyclerView dateView;
    private DateAdapter dateAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        days = DateUtils.getSevenDate();
        venuesPresent = new VenuesPresentImpl(getContext(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
        initView(view);
        venuesPresent.getCoursesFirst(switcherLayout, id);
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);
        switcherLayout = new SwitcherLayout(getContext(), recyclerView);
        courseAdapter = new VenuesCourseAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(courseAdapter);

        dateView = (RecyclerView) view.findViewById(R.id.rv_date);
        dateAdapter = new DateAdapter();
        dateView.setLayoutManager(new CustomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dateView.setAdapter(dateAdapter);
        dateAdapter.setData(days);
        dateAdapter.setItemClickListener(this);
    }

    @Override
    public void onGetCoursesFirst(CourseData courseData) {
        if (courseData != null && courseData.getCourse() != null && !courseData.getCourse().isEmpty()) {
            int position = days.indexOf(courseData.getDate());
            Logger.i(TAG, " position = " + position);
            courseAdapter.setData(courseData.getCourse());
            if (position > 0) {
                dateAdapter.setSelectedPosition(position);
                dateView.scrollBy(dateView.getChildAt(1).getLeft() * (position - 1), 0);
            }
        }
    }

    @Override
    public void setCourses(List<CourseBean> courseBeanList) {
        courseAdapter.setData(courseBeanList);
    }

    @Override
    public void onItemClick(int position) {
        venuesPresent.getCourses(switcherLayout, id, days.get(position));
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(),R.layout.empty_course,null);
        switcherLayout.addCustomView(view,"empty");
        switcherLayout.showCustomLayout("empty");
    }
}
