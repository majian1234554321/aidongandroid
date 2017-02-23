package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.FollowFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 关注和粉丝
 * Created by song on 2016/9/10.
 */
public class FollowActivity extends BaseActivity implements SmartTabLayout.TabProvider {
    private List<View> allTabView = new ArrayList<>();
    private ImageView ivBack;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private int position;

    public static void start(Context context,int position) {
        Intent starter = new Intent(context, FollowActivity.class);
        starter.putExtra("position",position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        if(getIntent() != null){
            position = getIntent().getIntExtra("position",0);
        }
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (SmartTabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);

        FragmentPagerItems pages = new FragmentPagerItems(this);
        FollowFragment followFragment = new FollowFragment();
        FollowFragment fansFragment = new FollowFragment();
        pages.add(FragmentPagerItem.of(null, followFragment.getClass(),
                new Bundler().putString("type", FollowFragment.FOLLOW).get()));
        pages.add(FragmentPagerItem.of(null,fansFragment.getClass(),
                new Bundler().putString("type", FollowFragment.FANS).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.followTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
