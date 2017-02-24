package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.UserInfoPhotoAdapter;
import com.leyuan.aidong.entity.ImageBean;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.UserDynamicFragment;
import com.leyuan.aidong.ui.mine.fragment.UserInfoFragment;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserInfoActivityView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SwitcherLayout;
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
public class UserInfoActivity extends BaseActivity implements UserInfoActivityView, View.OnClickListener,
        SmartTabLayout.TabProvider{
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivEdit;
    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private RelativeLayout otherNoPhotoLayout;
    private RelativeLayout selfNoPhotoLayout;
    private TextView tvAddImage;
    private RecyclerView rvPhoto;
    private ImageView dvAvatar;
    private TextView tvName;
    private TextView tvSignature;
    private ImageView ivFollowOrPublish;
    private SmartTabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout contactLayout;
    private TextView tvAppoint;

    private UserInfoPhotoAdapter wallAdapter;
    private List<View> allTabView = new ArrayList<>();
    private ArrayList<ImageBean> photos = new ArrayList<>();

    private String userId;
    private boolean isCoach = false;
    private boolean isFollow = false;
    private boolean isSelf = false;

    private ProfileBean profileBean;
    private UserInfoPresent userInfoPresent;

    public static void start(Context context, String userId) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        starter.putExtra("userId", userId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            if(App.mInstance.getUser() == null){
                isSelf = false;
            }else {
                isSelf = String.valueOf(App.mInstance.getUser().getId()).equals(userId);
            }
        }
        userInfoPresent = new UserInfoPresentImpl(this, this);
        initView();
        setListener();
        userInfoPresent.getUserInfo(switcherLayout, userId);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        otherNoPhotoLayout = (RelativeLayout) findViewById(R.id.rl_other_empty);
        selfNoPhotoLayout = (RelativeLayout) findViewById(R.id.rl_self_empty);
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        dvAvatar = (ImageView) findViewById(R.id.dv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        ivFollowOrPublish = (ImageView) findViewById(R.id.iv_follow_or_publish);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        contactLayout = (LinearLayout) findViewById(R.id.ll_contact);
        tvAppoint = (TextView) findViewById(R.id.tv_coach);
        if(isSelf){
            tvTitle.setText("我的资料");
            ivEdit.setVisibility(View.VISIBLE);
            ivFollowOrPublish.setBackgroundResource(R.drawable.icon_mine_publish);
            contactLayout.setVisibility(View.GONE);
            tvAppoint.setVisibility(View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this,46),0,(int)getResources().getDimension(R.dimen.dp_0));
        }else {
            tvTitle.setText("TA的资料");
            ivEdit.setVisibility(View.GONE);
            ivFollowOrPublish.setBackgroundResource(isFollow ?
                    R.drawable.icon_following : R.drawable.icon_follow);
            contactLayout.setVisibility(View.VISIBLE);
            tvAppoint.setVisibility(isCoach ? View.VISIBLE : View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this,46),0,(int)getResources().getDimension(R.dimen.pref_50dp));
        }

        wallAdapter = new UserInfoPhotoAdapter(this,isSelf);
        rvPhoto.setAdapter(wallAdapter);
        rvPhoto.setLayoutManager(new GridLayoutManager(this,4));
        rvPhoto.addItemDecoration(new SpacesItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin), 4));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvAddImage.setOnClickListener(this);
    }

    @Override
    public void updateUserInfo(UserInfoData userInfoData) {
        profileBean = userInfoData.getProfile();
        GlideLoader.getInstance().displayCircleImage(profileBean.getAvatar(), dvAvatar);
        tvName.setText(profileBean.getName());
        tvSignature.setText(profileBean.getSignature());
        if(userInfoData.getPhotoWall().isEmpty()){
            if(isSelf){
                selfNoPhotoLayout.setVisibility(View.VISIBLE);
            }else {
                otherNoPhotoLayout.setVisibility(View.VISIBLE);
            }
        }else {
            wallAdapter.setData(userInfoData.getPhotoWall());
        }
        FragmentPagerItems pages = new FragmentPagerItems(this);
        UserDynamicFragment dynamicFragment = new UserDynamicFragment();
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass(),
                new Bundler().putString("userId",userId).get()));
        pages.add(FragmentPagerItem.of(null, userInfoFragment.getClass(),
                new Bundler().putParcelable("profile", profileBean).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit:
                showEditDialog();
                break;
            case R.id.tv_add_image:
                UpdatePhotoWallActivity.start(this, photos);
                break;
        }
    }

    private void showEditDialog() {
        new MaterialDialog.Builder(this)
                .items(R.array.editTab)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            UpdateUserInfoActivity.start(UserInfoActivity.this, profileBean);
                        } else {
                            UpdatePhotoWallActivity.start(UserInfoActivity.this, photos);
                        }
                    }
                })
                .show();
    }
}
