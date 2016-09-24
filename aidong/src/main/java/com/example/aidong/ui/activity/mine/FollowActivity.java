package com.example.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.TabFragmentAdapter;
import com.example.aidong.ui.fragment.mine.FollowFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 关注和粉丝
 * Created by song on 2016/9/10.
 */
public class FollowActivity extends BaseActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        ArrayList<Fragment> fragments = new ArrayList<>();
        FollowFragment followFragment = FollowFragment.newInstance(FollowFragment.FOLLOW);
        FollowFragment fansFragment = FollowFragment.newInstance(FollowFragment.FANS);
        fragments.add(followFragment);
        fragments.add(fansFragment);
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.followTab));
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
