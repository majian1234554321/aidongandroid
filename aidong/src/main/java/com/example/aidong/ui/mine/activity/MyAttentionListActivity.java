package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.fragment.MyAttentionCampaignListFragment;
import com.example.aidong .ui.mine.fragment.MyAttentionCourseListFragment;
import com.example.aidong .ui.mine.fragment.MyAttentionUserListFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class MyAttentionListActivity extends BaseActivity implements SmartTabLayout.TabProvider {

    private List<View> allTabView = new ArrayList<>();

    public static void start(Context context, int position) {
        Intent intent = new Intent(context,MyAttentionListActivity.class);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position",0);
        setContentView(R.layout.activity_circle_list);

        final SmartTabLayout tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(this);

        pages.add(FragmentPagerItem.of(null,MyAttentionCourseListFragment.class));
        pages.add(FragmentPagerItem.of(null,MyAttentionCampaignListFragment.class));
        pages.add(FragmentPagerItem.of(null,MyAttentionUserListFragment.class,new Bundler().
                putString("type", "coaches").putString("type_cancel","coach").get()));

        pages.add(FragmentPagerItem.of(null,MyAttentionUserListFragment.class,new Bundler().
                putString("type", "follows").putString("type_cancel","user").get()));
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                //reset tip dot
//                View tip = tabLayout.getTabAt(position).findViewById(R.id.tv_tab_tip);
//                tip.setVisibility(View.GONE);

                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
                }
            }
        });

        viewPager.setCurrentItem(position);
        findViewById(R.id.img_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_text_attention, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
//        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
//        tip.setVisibility(View.GONE);

        String[] campaignTab = getResources().getStringArray(R.array.attentinList);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }


}
