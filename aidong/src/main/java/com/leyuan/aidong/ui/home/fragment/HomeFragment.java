package com.leyuan.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.discover.fragment.CircleFragment;
import com.leyuan.aidong.ui.home.activity.LocationActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;

/**
 * Created by user on 2017/12/25.
 */
public class HomeFragment extends BaseFragment implements SmartTabLayout.TabProvider, View.OnClickListener {

    private TextView tvLocation;
    private ImageView ivSearch;

    private List<View> allTabView = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE)) {

                tabLayout.getTabAt(2).findViewById(R.id.tv_tab_tip).setVisibility(View.VISIBLE);

            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE)) {

                tabLayout.getTabAt(2).findViewById(R.id.tv_tab_tip).setVisibility(View.GONE);

            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_EXIT_LOGIN)) {

                tabLayout.getTabAt(2).findViewById(R.id.tv_tab_tip).setVisibility(View.GONE);

            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_LOGIN_SUCCESS)) {

                tabLayout.getTabAt(2).findViewById(R.id.tv_tab_tip).setVisibility(App.getInstance().getCMDCirleDynamicBean() == null ||
                        App.getInstance().getCMDCirleDynamicBean().isEmpty() ? View.GONE : View.VISIBLE);

            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {

                tvLocation.setText(App.getInstance().getSelectedCity());

            }


        }
    };
    private ArrayList<BaseMedia> selectedMedia;
    private SmartTabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        filter.addAction(Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);

        tvLocation.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());

        pages.add(FragmentPagerItem.of(null, HomeRecommendFragment.class));
        pages.add(FragmentPagerItem.of(null, HomeAttentionFragment.class));
        pages.add(FragmentPagerItem.of(null, HomePlazaFragment.class));
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //reset tip dot
//                View tip = tabLayout.getTabAt(position).findViewById(R.id.tv_tab_tip);
//                tip.setVisibility(View.GONE);

                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        tvLocation.setText(App.getInstance().getSelectedCity());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_SELECT_PHOTO:
                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);
                    break;
                case REQUEST_SELECT_VIDEO:
                    selectedMedia = Boxing.getResult(data);
                    if (selectedMedia != null && selectedMedia.size() > 0) {
                        int duration = TrimVideoUtil.VIDEO_MAX_DURATION;

                        if (selectedMedia.get(0) instanceof VideoMedia) {
                            VideoMedia media = (VideoMedia) selectedMedia.get(0);
                            duration = (int) (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1);
                            Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.getDuration());
                        }
                        Logger.i("TrimmerActivity", "onActivityResult  durantion = " + duration);

                        TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), duration, Constant.REQUEST_VIDEO_TRIMMER);

//                        TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                    }
                    break;
                case Constant.REQUEST_VIDEO_TRIMMER:


                    Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));

                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC);
                    break;

                case REQUEST_PUBLISH_DYNAMIC:

                    //有问题，要改
                    Fragment page = adapter.getPage(1);
                    if (page instanceof CircleFragment) {
                        ((CircleFragment) page).refreshData();
                    }

                    break;
            }

        }
//        if (requestCode == REQUEST_SELECT_PHOTO || requestCode == REQUEST_SELECT_VIDEO) {
//            PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
//                    Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);
//
//            //ClipPhotosActivity.start(getContext(),Boxing.getResult(data));
//        } else if (requestCode == REQUEST_SELECT_VIDEO) {
//            selectedMedia = Boxing.getResult(data);
//            if (selectedMedia != null && selectedMedia.size() > 0) {
//                TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
//            }
//
//        } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
//            Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
//            if (selectedMedia != null && selectedMedia.size() > 0) {
//
//                selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
//
//                uploadVideo();
//
//
//            } else if (requestCode == REQUEST_PUBLISH_DYNAMIC) {
//                Fragment page = adapter.getPage(1);
//                if (page instanceof CircleFragment) {
//                    ((CircleFragment) page).refreshData();
//                }
//            }
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(selectCityReceiver);
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_home_text_with_notification, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
        tip.setWidth(DensityUtil.dp2px(getActivity(), 8));
        tip.setHeight(DensityUtil.dp2px(getActivity(), 8));
        tip.setVisibility(View.GONE);
//        if(position == 0){
//            text.setText(R.string.tab_sport_circle);
//            text.setTypeface(Typeface.DEFAULT_BOLD);
//        }else {
//            text.setText(R.string.tab_discover);
//        }


        String[] campaignTab = getResources().getStringArray(R.array.homeTab);
        text.setText(campaignTab[position]);
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (position == 2) {
            tip.setVisibility(App.getInstance().getCMDCirleDynamicBean() == null ||
                    App.getInstance().getCMDCirleDynamicBean().isEmpty() ? View.GONE : View.VISIBLE);
            tip.setText("");
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                if (App.mInstance.isLogin()) {
                    new MaterialDialog.Builder(getContext())
                            .items(R.array.mediaType)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    if (position == 0) {
                                        takePhotos();
                                    } else {
                                        takeVideo();
                                    }
                                }
                            }).show();
                } else {
                    ToastGlobal.showLong("请先登录再来发帖");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.tv_location:
                startActivity(new Intent(getContext(), LocationActivity.class));
                break;
            default:
                break;
        }
    }

    private void takePhotos() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(getContext(), BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
    }

    private void takeVideo() {
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(getContext(), BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
    }

}
