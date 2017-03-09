package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.UserInfoPhotoAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.entity.data.UserInfoData;
import com.leyuan.aidong.module.chat.db.DemoDBManager;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mine.fragment.UserDynamicFragment;
import com.leyuan.aidong.ui.mine.fragment.UserInfoFragment;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserInfoActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.List;

import static com.leyuan.aidong.R.id.tv_message;
import static com.leyuan.aidong.utils.Constant.REQUEST_LOGIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_VIDEO;


/**
 * 用户资料
 * Created by song on 2016/12/27.
 */
public class UserInfoActivity extends BaseActivity implements UserInfoActivityView, View.OnClickListener,
        SmartTabLayout.TabProvider, UserInfoPhotoAdapter.OnItemClickListener {
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivEdit;
    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private RelativeLayout otherEmptyPhotoLayout;
    private RelativeLayout selfEmptyPhotoLayout;
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
    private TextView tvMessage;

    private String userId;
    private boolean isCoach = false;
    private boolean isFollow = false;
    private boolean isSelf = false;
    private UserInfoData userInfoData;
    private UserInfoPhotoAdapter wallAdapter;
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
        userInfoPresent = new UserInfoPresentImpl(this, this);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            isSelf = isSelf(userId);
        }
        initView();
        setListener();
        userInfoPresent.getUserInfo(switcherLayout, userId);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
        switcherLayout = new SwitcherLayout(this, contentLayout);
        otherEmptyPhotoLayout = (RelativeLayout) findViewById(R.id.rl_other_empty);
        selfEmptyPhotoLayout = (RelativeLayout) findViewById(R.id.rl_self_empty);
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        dvAvatar = (ImageView) findViewById(R.id.dv_avatar);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMessage = (TextView) findViewById(tv_message);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        ivFollowOrPublish = (ImageView) findViewById(R.id.iv_follow_or_publish);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        contactLayout = (LinearLayout) findViewById(R.id.ll_contact);
        tvAppoint = (TextView) findViewById(R.id.tv_coach);
        wallAdapter = new UserInfoPhotoAdapter(this, isSelf);
        rvPhoto.setAdapter(wallAdapter);
        rvPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        rvPhoto.addItemDecoration(new SpacesItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin), 4));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivEdit.setOnClickListener(this);
        tvAddImage.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        ivFollowOrPublish.setOnClickListener(this);
        wallAdapter.setListener(this);
    }

    @Override
    public void updateUserInfo(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
        setView();
        setFragments();
    }

    private void setView(){
        GlideLoader.getInstance().displayCircleImage(userInfoData.getProfile().getAvatar(), dvAvatar);
        tvName.setText(userInfoData.getProfile().getName());
        tvSignature.setText(userInfoData.getProfile().getSignature());
        if(isSelf){
            tvTitle.setText("我的资料");
            ivFollowOrPublish.setBackgroundResource(R.drawable.icon_mine_publish);
            if(userInfoData.getPhotoWall().isEmpty()){
                selfEmptyPhotoLayout.setVisibility(View.VISIBLE);
            }else {
                wallAdapter.setData(userInfoData.getPhotoWall());
            }
            ivEdit.setVisibility(View.VISIBLE);
            contactLayout.setVisibility(View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0,
                    (int) getResources().getDimension(R.dimen.dp_0));
        }else {
            tvTitle.setText("TA的资料");
            ivFollowOrPublish.setBackgroundResource(isFollow
                    ?  R.drawable.icon_following : R.drawable.icon_follow);
            if(userInfoData.getPhotoWall().isEmpty()){
                otherEmptyPhotoLayout.setVisibility(View.VISIBLE);
                contactLayout.setVisibility(View.VISIBLE);
            }else {
                wallAdapter.setData(userInfoData.getPhotoWall());
            }
            ivEdit.setVisibility(View.GONE);
            contactLayout.setVisibility(View.VISIBLE);
            tvAppoint.setVisibility(isCoach ? View.VISIBLE : View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0,
                    (int) getResources().getDimension(R.dimen.pref_50dp));
        }
    }

    private void setFragments(){
        FragmentPagerItems pages = new FragmentPagerItems(this);
        UserDynamicFragment dynamicFragment = new UserDynamicFragment();
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass(),
                new Bundler().putString("userId", userId).get()));
        pages.add(FragmentPagerItem.of(null, userInfoFragment.getClass(),
                new Bundler().putParcelable("profile", userInfoData.getProfile()).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_edit:
                if(userInfoData != null) {
                    showEditDialog();
                }
                break;
            case R.id.tv_add_image:
                UpdatePhotoWallActivity.start(this, userInfoData.getPhotoWall());
                break;
            case R.id.tv_message:
                if(App.mInstance.isLogin()) {
//                EmFriendManager.getInstance().addFriend(userId, "meimei");
                    ProfileBean profile = userInfoData.getProfile();
                    EaseUser user = new EaseUser(profile.getId());
                    user.setNickname(profile.getName());
                    user.setAvatar(profile.getAvatar());
                    DemoDBManager.getInstance().saveContact(user);
                    startActivity(new Intent(this, EMChatActivity.class).
                            putExtra(EaseConstant.EXTRA_USER_ID, userId));
                }else {
                    startActivityForResult(new Intent(this, LoginActivity.class),REQUEST_LOGIN);
                }
                break;
            case R.id.iv_follow_or_publish:
                if(App.mInstance.isLogin()) {
                    if (isSelf) {
                        publishDynamic();
                    } else if (isFollow) {
                        userInfoPresent.cancelFollow(userId);
                    } else {
                        userInfoPresent.addFollow(userId);
                    }
                }else {
                    startActivityForResult(new Intent(this, LoginActivity.class),REQUEST_LOGIN);
                }
                break;
            default:
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
                            UpdateUserInfoActivity.start(UserInfoActivity.this, userInfoData.getProfile());
                        } else {
                            UpdatePhotoWallActivity.start(UserInfoActivity.this, userInfoData.getPhotoWall());
                        }
                    }
                })
                .show();
    }


    //todo
    private void publishDynamic() {
        new MaterialDialog.Builder(this)
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
    }

    private void takePhotos() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_PHOTO);
    }

    private void takeVideo() {
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == Constant.REQUEST_LOGIN){
                isSelf = isSelf(userId);
                userInfoPresent.getUserInfo(switcherLayout, userId);
            }else if(requestCode == REQUEST_PHOTO || requestCode == REQUEST_VIDEO){
                PublishDynamicActivity.start(this, requestCode == REQUEST_PHOTO, Boxing.getResult(data));
            }
        }
    }

    @Override
    public void onAddImageItemClick() {

    }

    @Override
    public void onPreviewImage(List<String> urls, List<Rect> rectList, int currPosition) {
        PhotoBrowseInfo info = PhotoBrowseInfo.create(urls, rectList, currPosition);
        PhotoBrowseActivity.start(this, info);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            isFollow = true;
            ivFollowOrPublish.setBackgroundResource(R.drawable.icon_following);
        } else {
            Toast.makeText(this, "关注失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            isFollow = false;
            ivFollowOrPublish.setBackgroundResource(R.drawable.icon_follow);
        } else {
            Toast.makeText(this, "取消关注失败", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isSelf(String otherId) {
        return !TextUtils.isEmpty(otherId) && App.mInstance.getUser() != null &&
                otherId.equals(String.valueOf(App.mInstance.getUser().getId()));
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_user_info, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.infoTab);
        text.setText(campaignTab[position]);
        return tabView;
    }
}
