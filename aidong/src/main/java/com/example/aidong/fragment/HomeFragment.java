package com.example.aidong.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.subject.SubjectFilterActivity;
import com.example.aidong.adapter.HomeAdAdapter;
import com.example.aidong.adapter.HomeButtonAdapter;
import com.example.aidong.adapter.HomeTJContentAdapter;
import com.example.aidong.model.HomeTuijianData;
import com.example.aidong.view.CustomViewPager;
import com.example.aidong.view.MultiGridView;
import com.example.aidong.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private View view;
    private TextView txt_home_area;
    private ImageView img_home_seach, img_home_saoma, img_home_ad_tuijian;
    private EditText edt_home_seach_content;
    private LinearLayout layout_home_tip;
    private CustomViewPager viewpager_home_ad;
    private GridView gridview_button;
    private MyListView list_home_tuijian;

    private HomeAdAdapter adAdapter;
    private HomeButtonAdapter buttonAdapter;
    private HomeTJContentAdapter tjContentAdapter;
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    private List<View> imageViews = new ArrayList<View>(); // 滑动的图片集合
    private List<String> adList = new ArrayList<String>();
    private List<String> buttonList = new ArrayList<String>();
    private List<HomeTuijianData> mData = new ArrayList<HomeTuijianData>();
    protected DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //强制取消初始化弹出对话框
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        view = inflater.inflate(R.layout.fragment_home, null);
        initView();
        initData();
        setClick();
        return view;
    }

    private void initView() {
        txt_home_area = (TextView) view.findViewById(R.id.txt_home_area);
        img_home_seach = (ImageView) view.findViewById(R.id.img_home_seach);
        img_home_saoma = (ImageView) view.findViewById(R.id.img_home_saoma);
        edt_home_seach_content = (EditText) view.findViewById(R.id.edt_home_seach_content);
        layout_home_tip = (LinearLayout) view.findViewById(R.id.layout_home_tip);
        viewpager_home_ad = (CustomViewPager) view.findViewById(R.id.viewpager_home_ad);
        gridview_button = (MultiGridView) view.findViewById(R.id.gridview_button);
        img_home_ad_tuijian = (ImageView) view.findViewById(R.id.img_home_ad_tuijian);
        list_home_tuijian = (MyListView) view.findViewById(R.id.list_home_tuijian);

    }

    private void initData() {
        for (int i = 0; i < 5; i++) {
            adList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        initViewpager();
        buttonList.add("小团体课");
        buttonList.add("营养品");
        buttonList.add("健康餐饮");
        buttonList.add("活动");
        buttonList.add("装备");
        buttonList.add("赛事");
        buttonAdapter = new HomeButtonAdapter(getActivity(), buttonList);
        gridview_button.setAdapter(buttonAdapter);
        HomeTuijianData data = new HomeTuijianData();
        data.setType(1);
        mData.add(data);
        HomeTuijianData data1 = new HomeTuijianData();
        data1.setType(2);
        mData.add(data1);
        HomeTuijianData data2 = new HomeTuijianData();
        data2.setType(2);
        mData.add(data2);
        HomeTuijianData data3 = new HomeTuijianData();
        data3.setType(1);
        mData.add(data3);
        tjContentAdapter = new HomeTJContentAdapter(getActivity(), mData);
        list_home_tuijian.setAdapter(tjContentAdapter);
    }

    private void setClick() {
        gridview_button.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), SubjectFilterActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initViewpager() {
        options = new DisplayImageOptions.Builder()
                //		.showImageOnLoading(R.drawable.icon_picture)
                //		.showImageForEmptyUri(R.drawable.icon_picture)
                //		.showImageOnFail(R.drawable.icon_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        imageViews.clear();
        for (int i = 0; i < adList.size(); i++) {
            final int p = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_ad, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_ad_content);
            ImageLoader.getInstance().displayImage(adList.get(i),
                    imageView, options);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            imageViews.add(view);
        }
        //将点点加入到ViewGroup中
        tips = new ImageView[adList.size()];
        layout_home_tip.removeAllViews();
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            layout_home_tip.addView(imageView, layoutParams);
        }
        adAdapter = new HomeAdAdapter(getActivity(), imageViews);
        //设置Adapter
        viewpager_home_ad.setAdapter(adAdapter);
        //设置监听，主要是设置点点的背景
        viewpager_home_ad.setOnPageChangeListener(new MyPageChangeListener());
        //        viewpager_ad.setCurrentItem(imageViews.size() * 100);

        if (adList.size() == 1) {
            viewpager_home_ad.setScanScroll(false);
            layout_home_tip.setVisibility(View.GONE);
        } else {
            viewpager_home_ad.setScanScroll(true);
            layout_home_tip.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author Administrator
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;


        public void onPageSelected(int position) {
            setImageBackground(position % imageViews.size());
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

}
