package com.leyuan.aidong.ui.discover.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.mine.account.LoginActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * 发现
 * Created by song on 2016/11/19.
 */
public class DiscoverHomeFragment extends BaseFragment implements SmartTabLayout.TabProvider{
    public static final int REQUEST_PHOTO = 1;
    public static final int REQUEST_VIDEO = 2;
    private List<View> allTabView = new ArrayList<>();
    private ImageView camera;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover_home,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        camera = (ImageView) view.findViewById(R.id.iv_camera);
        final SmartTabLayout tabLayout = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout.setCustomTabView(this);
        camera.setOnClickListener(cameraClickListener);
        camera.animate().scaleX(0).scaleY(0).setDuration(10);

        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        pages.add(FragmentPagerItem.of(null,DiscoverFragment.class));
        pages.add(FragmentPagerItem.of(null,CircleFragment.class));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setCameraVisible(position == 1);

                //reset tip dot
                View tip = tabLayout.getTabAt(position).findViewById(R.id.tv_tab_tip);
                tip.setVisibility(View.GONE);

                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
                }
            }
        });
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_text_with_notification, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        TextView tip = (TextView) tabView.findViewById(R.id.tv_tab_tip);
        if(position == 0){
            text.setText(R.string.tab_discover);
            text.setTypeface(Typeface.DEFAULT_BOLD);
            tip.setVisibility(View.GONE);
        }else {
            text.setText(R.string.tab_sport_circle);
            tip.setVisibility(View.VISIBLE);
        }
        allTabView.add(tabView);
        return tabView;
    }

    private View.OnClickListener cameraClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(App.mInstance.isLogin()) {
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
                        })
                        .show();
            }else {
                Toast.makeText(getContext(),"请先登陆再来发帖",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        }
    };

    private void takePhotos(){
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(getContext(), BoxingActivity.class).start(this, REQUEST_PHOTO);
    }

    private void takeVideo(){
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(getContext(), BoxingActivity.class).start(this, REQUEST_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            PublishDynamicActivity.start(getContext(),requestCode == REQUEST_PHOTO,Boxing.getResult(data));
        }
    }

    private void setCameraVisible(boolean visible){
        if(visible){
            camera.setVisibility(View.VISIBLE);
            camera.animate().scaleX(1).scaleY(1).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            camera.setVisibility(View.VISIBLE);
                        }
                    }).start();
        }else {
            camera.animate().scaleX(0).scaleY(0).setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            camera.setVisibility(View.GONE);
                        }
                    }).start();
        }
    }
}
