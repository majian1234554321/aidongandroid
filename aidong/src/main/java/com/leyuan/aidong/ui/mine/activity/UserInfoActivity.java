package com.leyuan.aidong.ui.mine.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.UserDynamicFragment;
import com.leyuan.aidong.ui.mine.fragment.UserInfoFragment;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserInfoActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户资料
 * Created by song on 2016/12/27.
 */
public class UserInfoActivity extends BaseActivity implements SmartTabLayout.TabProvider ,UserInfoActivityView, View.OnClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivEdit;

    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private TextView tvAddImage;
    private RecyclerView rvPhoto;
    private SimpleDraweeView dvAvatar;
    private TextView tvName;
    private TextView tvSignature;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private List<View> allTabView = new ArrayList<>();
    private UserInfoPresent userInfoPresent;
    private String id;
    private ProfileBean profileBean;
    private ArrayList<ImageBean> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userInfoPresent = new UserInfoPresentImpl(this,this);
        id = String.valueOf(App.mInstance.getUser().getId());
        initView();
        setListener();
        userInfoPresent.getUserInfo(switcherLayout,id);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        dvAvatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvAddImage.setOnClickListener(this);
    }


    @Override
    public void updateUserInfo(UserInfoData userInfoData) {
        profileBean = userInfoData.getProfile();
        dvAvatar.setImageURI(profileBean.getAvatar());
        tvName.setText(profileBean.getName());
        tvSignature.setText(profileBean.getSignature());

        FragmentPagerItems pages = new FragmentPagerItems(this);
        UserDynamicFragment dynamicFragment = new UserDynamicFragment();
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass()));
        pages.add(FragmentPagerItem.of(null, userInfoFragment.getClass(),
                new Bundler().putParcelable("profile",profileBean).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                }
            }
        });
    }


    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_user_info, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.infoTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit:
                UpdateUserInfoActivity.start(this,profileBean);
                break;
            case R.id.tv_add_image:
                UpdatePhotoWall.start(this,photos);
                break;
        }
    }
}
