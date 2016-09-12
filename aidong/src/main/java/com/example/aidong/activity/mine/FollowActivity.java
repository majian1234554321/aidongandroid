package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.fragment.mine.FansFragment;
import com.example.aidong.fragment.mine.FollowFragment;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;

import java.util.ArrayList;

/**
 * 关注和粉丝
 * Created by song on 2016/9/10.
 */
public class FollowActivity extends BaseActivity{
    private static  final int FOLLOW = 0;
    private static  final int FANS = 1;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        FollowFragment followFragment = new FollowFragment();
        FansFragment fansFragment = new FansFragment();

        fragments.add(followFragment);
        fragments.add(fansFragment);

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.follow));
        titles.add(
                getString(R.string.fans));

        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(tabLayout.getTabAt(FOLLOW))){
                    viewPager.setCurrentItem(FOLLOW);
                }else {
                    viewPager.setCurrentItem(FANS);
                }
            }
        });
    }
}
