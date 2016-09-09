package com.example.aidong.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.FoundFragmentAdapter;
import com.example.aidong.fragment.CampaignFragment;
import com.example.aidong.interfaces.SimpleOnTabSelectedListener;

import java.util.ArrayList;

/**
 * 活动
 * Created by song on 2016/9/9.
 */
public class CampaignActivity extends BaseActivity{
    private static  final int CAMPAIGN_FREE = 0;
    private static  final int CAMPAIGN_PAY = 1;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tvPost;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        tvPost = (TextView)findViewById(R.id.tv_post);

        ArrayList<Fragment> fragments = new ArrayList<>();
        CampaignFragment free = new CampaignFragment();
        CampaignFragment pay = new CampaignFragment();

        free.setArguments(CampaignFragment.FREE);
        pay.setArguments(CampaignFragment.PAY);

        fragments.add(free);
        fragments.add(pay);

        ArrayList<String> titles = new ArrayList<>();
        titles.add(getString(R.string.campaign_free));
        titles.add(getString(R.string.campaign_pay));

        viewPager.setAdapter(new FoundFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        tvPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CampaignActivity.this,PastCampaignActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.setOnTabSelectedListener(new SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(tabLayout.getTabAt(CAMPAIGN_FREE))){
                    viewPager.setCurrentItem(CAMPAIGN_FREE);
                }else{
                    viewPager.setCurrentItem(CAMPAIGN_PAY);
                }
            }
        });
    }
}
