package com.leyuan.aidong.ui.fragment.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 搜索结果
 * Created by song on 2016/9/18.
 */
public class SearchResultFragment extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String keyword;

    public static SearchResultFragment newInstance(String searchContent){
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchContent);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_search_result,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager)view.findViewById(R.id.vp_search_result);

        ArrayList<Fragment> fragments = new ArrayList<>();
        SearchVenuesFragment venues =  SearchVenuesFragment.newInstance(keyword);
        SearchCourseFragment course = new SearchCourseFragment();
        SearchFoodFragment food = new SearchFoodFragment();
        SearchCampaignFragment campaign = new SearchCampaignFragment();
        SearchUserFragment user = new SearchUserFragment();
        fragments.add(venues);
        fragments.add(course);
        fragments.add(food);
        fragments.add(campaign);
        fragments.add(user);
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.searchTab));
        viewPager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
