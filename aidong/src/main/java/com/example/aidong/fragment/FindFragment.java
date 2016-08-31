package com.example.aidong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.discover.FilterAroundPeopleActivity;
import com.example.aidong.adapter.FoundFragmentAdapter;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;
import com.example.aidong.view.CustomViewPager;

import java.util.ArrayList;

/**
 * 发现界面
 * @author song
 */
public class FindFragment extends BaseFragment{

    private TabLayout tabLayout;
    private CustomViewPager viewpager;
    private TextView tvSelect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_found, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSelect = (TextView) view.findViewById(R.id.tv_select);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewpager = (CustomViewPager) view.findViewById(R.id.viewpager);

        ArrayList<Fragment> fragments = new ArrayList<>();
        FindVenuesFragment venuesFragment = new FindVenuesFragment();
        FindPeopleFragment peopleFragment = new FindPeopleFragment();
        fragments.add(venuesFragment);
        fragments.add(peopleFragment);

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.find_venues));
        titles.add(getString(R.string.find_people));

        viewpager.setAdapter(new FoundFragmentAdapter(getChildFragmentManager(),fragments,titles));
        viewpager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setOnTabSelectedListener(new SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(tabLayout.getTabAt(0))){
                    viewpager.setCurrentItem(0);
                    tvSelect.setVisibility(View.GONE);
                }else{
                    viewpager.setCurrentItem(1);
                    tvSelect.setVisibility(View.VISIBLE);
                }
            }
        });

        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterAroundPeopleActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
