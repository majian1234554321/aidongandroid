package com.leyuan.aidong.ui.video.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.mvp.presenter.impl.VideoPresenterImpl;
import com.leyuan.aidong.ui.video.fragment.SpecialTopicFragment;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;



public class WatchOfficeActivity extends FragmentActivity {

    private TextView txt_special_topic, tag_special_topic, txt_celebrity, tag_celebrity, txt_thorough, tag_thorough;
    private ViewPager viewPager;
    private LinearLayout linear_special_topic, linear_celebrity, linear_thorough;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int video_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        video_type = getIntent().getIntExtra(Constant.VIDEO_TYPE, 0);
        setContentView(R.layout.activity_watch_office);
        initView();
        initData();
    }

    private void initView() {
        linear_special_topic = (LinearLayout) findViewById(R.id.linear_special_topic);
        linear_celebrity = (LinearLayout) findViewById(R.id.linear_celebrity);
        linear_thorough = (LinearLayout) findViewById(R.id.linear_thorough);

        txt_special_topic = (TextView) findViewById(R.id.txt_special_topic);
        tag_special_topic = (TextView) findViewById(R.id.tag_special_topic);
        txt_celebrity = (TextView) findViewById(R.id.txt_celebrity);
        tag_celebrity = (TextView) findViewById(R.id.tag_celebrity);
        txt_thorough = (TextView) findViewById(R.id.txt_thorough);
        tag_thorough = (TextView) findViewById(R.id.tag_thorough);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void initData() {
        findViewById(R.id.imageView_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFragments.add(SpecialTopicFragment.newInstance(VideoPresenterImpl.family));
        mFragments.add(SpecialTopicFragment.newInstance(VideoPresenterImpl.professional));
        mFragments.add(SpecialTopicFragment.newInstance(VideoPresenterImpl.celebrity));
        Logger.i("liveVideo", "video_type = " + video_type);
        changeTitleTag(video_type);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });


        viewPager.setCurrentItem(video_type);

        linear_special_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        linear_thorough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        linear_celebrity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        viewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            changeTitleTag(i);

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private void changeTitleTag(int i) {

        switch (i) {
            case 0:
                txt_special_topic.setTextColor(getResources().getColor(R.color.white));
                tag_special_topic.setBackgroundResource(R.color.text_veiw_red);

                txt_thorough.setTextColor(getResources().getColor(R.color.text_gray));
                tag_thorough.setBackgroundResource(R.color.black);

                txt_celebrity.setTextColor(getResources().getColor(R.color.text_gray));
                tag_celebrity.setBackgroundResource(R.color.black);
                break;
            case 1:
                txt_special_topic.setTextColor(getResources().getColor(R.color.text_gray));
                tag_special_topic.setBackgroundResource(R.color.black);

                txt_thorough.setTextColor(getResources().getColor(R.color.white));
                tag_thorough.setBackgroundResource(R.color.text_veiw_red);

                txt_celebrity.setTextColor(getResources().getColor(R.color.text_gray));
                tag_celebrity.setBackgroundResource(R.color.black);
                break;
            case 2:
                txt_special_topic.setTextColor(getResources().getColor(R.color.text_gray));
                tag_special_topic.setBackgroundResource(R.color.black);

                txt_thorough.setTextColor(getResources().getColor(R.color.text_gray));
                tag_thorough.setBackgroundResource(R.color.black);

                txt_celebrity.setTextColor(getResources().getColor(R.color.white));
                tag_celebrity.setBackgroundResource(R.color.text_veiw_red);
                break;
        }
    }
    


}
