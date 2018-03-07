//package com.leyuan.aidong.ui.mine.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.Parcelable;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.leyuan.aidong.R;
//import com.leyuan.aidong.adapter.mine.UserInfoPhotoAdapter;
//import com.leyuan.aidong.entity.BaseBean;
//import com.leyuan.aidong.entity.PhotoBrowseInfo;
//import com.leyuan.aidong.entity.ProfileBean;
//import com.leyuan.aidong.entity.data.UserInfoData;
//import com.leyuan.aidong.module.photopicker.boxing.Boxing;
//import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
//import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
//import com.leyuan.aidong.module.photopicker.boxing_impl.view.SpacesItemDecoration;
//import com.leyuan.aidong.ui.App;
//import com.leyuan.aidong.ui.BaseActivity;
//import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
//import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
//import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
//import com.leyuan.aidong.ui.mine.fragment.CoachCourseFragment;
//import com.leyuan.aidong.ui.mine.fragment.UserDynamicFragment;
//import com.leyuan.aidong.ui.mine.fragment.UserInfoFragment;
//import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
//import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
//import com.leyuan.aidong.ui.mvp.view.UserInfoActivityView;
//import com.leyuan.aidong.utils.Constant;
//import com.leyuan.aidong.utils.DensityUtil;
//import com.leyuan.aidong.utils.GlideLoader;
//import com.leyuan.aidong.utils.ImageRectUtils;
//import com.leyuan.aidong.utils.Logger;
//import com.leyuan.aidong.utils.TelephoneManager;
//import com.leyuan.aidong.utils.ToastGlobal;
//import com.leyuan.aidong.widget.SwitcherLayout;
//import com.ogaclejapan.smarttablayout.SmartTabLayout;
//import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
//import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
//import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
//import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.leyuan.aidong.R.id.tv_message;
//import static com.leyuan.aidong.utils.Constant.REQUEST_LOGIN;
//import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
//import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
//import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;
//
//
///**
// * 用户资料
// * Created by song on 2016/12/27.
// */
//@Deprecated
//public class CoachInfoActivity extends BaseActivity implements UserInfoActivityView, View.OnClickListener,
//        SmartTabLayout.TabProvider, UserInfoPhotoAdapter.OnItemClickListener {
//    public static final int REQUEST_UPDATE_PHOTO = 1024;
//    public static final int REQUEST_UPDATE_INFO = 2048;
//
//    private ImageView ivBack;
//    private TextView tvTitle;
//    private ImageView ivPublish;
//    private SwitcherLayout switcherLayout;
//    private RelativeLayout contentLayout;
//    private RelativeLayout emptyPhotoLayout;
//    private TextView tvAddImage;
//    private RecyclerView rvPhoto;
//    private ImageView ivAvatar;
//    private ImageView ivGender;
//    private ImageView ivCoach;
//    private TextView tvName;
//    private TextView tvSignature;
//    private ImageView ivFollowOrEdit;
//    private SmartTabLayout tabLayout;
//    private ViewPager viewPager;
//    private LinearLayout contactLayout;
//    private TextView tvCall;
//    private TextView tvMessage;
//
//    private String userId;
//    private boolean isSelf = false;
//    private UserInfoData userInfoData;
//    private UserInfoPhotoAdapter wallAdapter;
//    private UserInfoPresent userInfoPresent;
//
//    private FragmentPagerItemAdapter adapter;
//    private boolean needRefreshFragment = false;
//    private String contact;
//
//    public static void start(Context context, String userId) {
//        Intent starter = new Intent(context, CoachInfoActivity.class);
//        starter.putExtra("userId", userId);
//        context.startActivity(starter);
//    }
//
//    public static void startForResult(Activity context, String userId, int request_code) {
//        Intent starter = new Intent(context, CoachInfoActivity.class);
//        starter.putExtra("userId", userId);
//        context.startActivityForResult(starter, request_code);
//    }
//
//    public static void startForResult(Fragment fragment, String userId, int request_code) {
//        Intent starter = new Intent(fragment.getContext(), CoachInfoActivity.class);
//        starter.putExtra("userId", userId);
//        fragment.startActivityForResult(starter, request_code);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_info);
//        userInfoPresent = new UserInfoPresentImpl(this, this);
//        if (getIntent() != null) {
//            userId = getIntent().getStringExtra("userId");
//            isSelf = isSelf(userId);
//        }
//        initView();
//        setListener();
//        userInfoPresent.getUserInfo(userId);
//
//    }
//
//    private void initView() {
//        ivBack = (ImageView) findViewById(R.id.iv_back);
//        tvTitle = (TextView) findViewById(R.id.tv_title);
//        ivPublish = (ImageView) findViewById(R.id.iv_publish);
//        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
//        switcherLayout = new SwitcherLayout(this, contentLayout);
//        emptyPhotoLayout = (RelativeLayout) findViewById(R.id.rl_self_empty);
//        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
//        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
//        ivAvatar = (ImageView) findViewById(R.id.dv_avatar);
//        ivGender = (ImageView) findViewById(R.id.iv_gender);
//        ivCoach = (ImageView) findViewById(R.id.iv_coach_flag);
//        tvName = (TextView) findViewById(R.id.tv_name);
//        tvMessage = (TextView) findViewById(tv_message);
//        tvSignature = (TextView) findViewById(R.id.tv_signature);
//        ivFollowOrEdit = (ImageView) findViewById(R.id.iv_follow_or_edit);
//        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
//        viewPager = (ViewPager) findViewById(R.id.vp_user);
//        contactLayout = (LinearLayout) findViewById(R.id.ll_contact);
//        tvCall = (TextView) findViewById(R.id.tv_call);
//        wallAdapter = new UserInfoPhotoAdapter(this, isSelf);
//        rvPhoto.setAdapter(wallAdapter);
//        rvPhoto.setLayoutManager(new GridLayoutManager(this, 4));
//        rvPhoto.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin), 4));
//    }
//
//    private void setListener() {
//        ivBack.setOnClickListener(this);
//        ivPublish.setOnClickListener(this);
//        tvAddImage.setOnClickListener(this);
//        tvMessage.setOnClickListener(this);
//        tvCall.setOnClickListener(this);
//        ivFollowOrEdit.setOnClickListener(this);
//        wallAdapter.setListener(this);
//        ivAvatar.setOnClickListener(this);
//    }
//
//    @Override
//    public void updateUserInfo(UserInfoData userInfoData) {
//        this.userInfoData = userInfoData;
//        userId = userInfoData.getProfile().getId();
//        setView();
//        setFragments();
//    }
//
//    private void setView() {
//        GlideLoader.getInstance().displayCircleImage(userInfoData.getProfile().getAvatar(), ivAvatar);
//        tvName.setText(userInfoData.getProfile().getName());
//        tvSignature.setText(TextUtils.isEmpty(userInfoData.getProfile().getSignature())
//                ? "这个人很懒，什么都没有留下" : userInfoData.getProfile().getSignature());
//        ivGender.setBackgroundResource("0".equals(userInfoData.getProfile().getGender())
//                ? R.drawable.icon_man : R.drawable.icon_woman);
//        ivCoach.setVisibility("Coach".equalsIgnoreCase(userInfoData.getProfile().getUserType())
//                ? View.VISIBLE : View.GONE);
//        if (isSelf) {
//            tvTitle.setText("我的资料");
//            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_edit_red);
//            if (!userInfoData.getPhotoWall().isEmpty()) {
//                wallAdapter.setData(userInfoData.getPhotoWall());
//                emptyPhotoLayout.setVisibility(View.GONE);
//            } else {
//                emptyPhotoLayout.setVisibility(View.VISIBLE);
//            }
//            ivPublish.setVisibility(View.VISIBLE);
//            contactLayout.setVisibility(View.GONE);
//            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0, (int) getResources().getDimension(R.dimen.dp_0));
//        } else {
//            tvTitle.setText("TA的资料");
//            ivFollowOrEdit.setBackgroundResource(userInfoData.getProfile().followed
//                    ? R.drawable.icon_followed : R.drawable.icon_follow);
//            if (!userInfoData.getPhotoWall().isEmpty()) {
//                wallAdapter.setData(userInfoData.getPhotoWall());
//            }
//            ivPublish.setVisibility(View.GONE);
//            contactLayout.setVisibility(View.VISIBLE);
//            tvCall.setVisibility("Coach".equalsIgnoreCase(userInfoData.getProfile().getUserType())
//                    ? View.VISIBLE : View.GONE);
//            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0, (int) getResources().getDimension(R.dimen.pref_50dp));
//        }
//    }
//
//    private void setFragments() {
//        if (needRefreshFragment) {
//            needRefreshFragment = false;
//            Fragment page = adapter.getPage(1);
//            if (page instanceof UserInfoFragment) {
//                ((UserInfoFragment) page).refreshUserInfo(userInfoData.getProfile());
//            }
//        } else {
//            FragmentPagerItems pages = new FragmentPagerItems(this);
//            UserDynamicFragment dynamicFragment = new UserDynamicFragment();
//
//            UserInfoFragment userInfoFragment = new UserInfoFragment();
//            pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass(),
//                    new Bundler().putString("userId", userId).get()));
//
//
//            if (Constant.COACH.equals(userInfoData.getProfile().getUserTypeByUserType())) {
//                CoachCourseFragment courseFragment = new CoachCourseFragment();
//                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
//                        new Bundler().putString("mobile", userInfoData.getProfile().mobile).get()));
//            }
//
//
//            pages.add(FragmentPagerItem.of(null, userInfoFragment.getClass(),
//                    new Bundler().putParcelable("profile", userInfoData.getProfile()).get()));
//            adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
//            viewPager.setAdapter(adapter);
//            tabLayout.setCustomTabView(this);
//            tabLayout.setViewPager(viewPager);
//        }
//    }
//
//
//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//        finishSetResult();
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
//
//    }
//
//    private void finishSetResult() {
//        Intent intentResult = new Intent();
//
//        Logger.i("follow", "finishSetResult follow = " + userInfoData.getProfile().followed);
//        intentResult.putExtra(Constant.FOLLOW, userInfoData.getProfile().followed);
//        setResult(RESULT_OK, intentResult);
//        finish();
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_back:
//                finishSetResult();
//                break;
//            case R.id.dv_avatar:
//                List<String> urls = new ArrayList<>();
//                urls.add(userInfoData.getProfile().getAvatar());
//                List<Rect> viewLocalRect = new ArrayList<>();
//                viewLocalRect.add(ImageRectUtils.getDrawableBoundsInView(ivAvatar));
//                PhotoBrowseInfo info = PhotoBrowseInfo.create(urls, viewLocalRect, 0);
//                PhotoBrowseActivity.start(this, info);
//                break;
//            case R.id.iv_publish:
//                publishDynamic();
//                break;
//            case R.id.tv_add_image:
//                toUpdatePhotoWallActivity();
//                break;
//            case R.id.tv_message:
//                if (App.mInstance.isLogin()) {
//                    ProfileBean profile = userInfoData.getProfile();
//                    EMChatActivity.start(this, userId, profile.getName(), profile.getAvatar());
//                } else {
//                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
//                }
//                break;
//            case R.id.iv_follow_or_edit:
//                if (App.mInstance.isLogin()) {
//                    if (isSelf) {
//                        showEditDialog();
//                    } else if (userInfoData.getProfile().followed) {
//                        userInfoPresent.cancelFollow(userId, userInfoData.getProfile().getUserTypeByUserType());
//                    } else {
//                        userInfoPresent.addFollow(userId, userInfoData.getProfile().getUserTypeByUserType());
//                    }
//                } else {
//                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
//                }
//                break;
//            case R.id.tv_call:
//                if (TextUtils.isEmpty(userInfoData.getProfile().getPhone())) {
//                    ToastGlobal.showLong("该教练没有录入电话号码");
//                    return;
//                }
//                showCallUpDialog(userInfoData.getProfile().getPhone());
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    private void showCallUpDialog(final String phoneNum) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(String.format(getString(R.string.confirm_call_up), phoneNum))
//                .setCancelable(true)
//                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        TelephoneManager.callImmediate(CoachInfoActivity.this, phoneNum);
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//        builder.show();
//    }
//
//    private void showEditDialog() {
//        new MaterialDialog.Builder(this)
//                .items(R.array.editTab)
//                .itemsCallback(new MaterialDialog.ListCallback() {
//                    @Override
//                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
//                        if (position == 0) {
//                            Intent intent = new Intent(CoachInfoActivity.this, UpdateUserInfoActivity.class);
//                            intent.putExtra("profileBean", userInfoData.getProfile());
//                            startActivityForResult(intent, REQUEST_UPDATE_INFO);
//                        } else {
//                            toUpdatePhotoWallActivity();
//                        }
//                    }
//                })
//                .show();
//    }
//
//
//    private void toUpdatePhotoWallActivity() {
//        Intent intent = new Intent(CoachInfoActivity.this, UpdatePhotoWallActivity.class);
//        intent.putParcelableArrayListExtra("photos",
//                (ArrayList<? extends Parcelable>) userInfoData.getPhotoWall());
//        startActivityForResult(intent, REQUEST_UPDATE_PHOTO);
//    }
//
//    //todo
//    private void publishDynamic() {
//        new MaterialDialog.Builder(this)
//                .items(R.array.mediaType)
//                .itemsCallback(new MaterialDialog.ListCallback() {
//                    @Override
//                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
//                        if (position == 0) {
//                            takePhotos();
//                        } else {
//                            takeVideo();
//                        }
//                    }
//                })
//                .show();
//    }
//
//    private void takePhotos() {
//        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
//        multi.needCamera().maxCount(6).isNeedPaging();
//        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
//    }
//
//    private void takeVideo() {
//        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
//        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == Constant.REQUEST_LOGIN) {
//                isSelf = isSelf(userId);
//                userInfoPresent.getUserInfo(switcherLayout, userId);
//            } else if (requestCode == REQUEST_SELECT_PHOTO || requestCode == REQUEST_SELECT_VIDEO) {
//                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
//                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);
//            } else if (requestCode == REQUEST_PUBLISH_DYNAMIC) {
//                Fragment page = adapter.getPage(0);
//                if (page instanceof UserDynamicFragment) {
//                    ((UserDynamicFragment) page).refreshDynamic();
//                }
//            } else if (requestCode == REQUEST_UPDATE_PHOTO) {
//                resetPhotoWall();
//                userInfoPresent.getUserInfo(userId);
//            } else if (requestCode == REQUEST_UPDATE_INFO) {
//                needRefreshFragment = true;
//                userInfoPresent.getUserInfo(userId);
//            }
//        }
//    }
//
//    private void resetPhotoWall() {
//        userInfoData.getPhotoWall().clear();
//        wallAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onPreviewPhotoWallImage(List<String> urls, List<Rect> rectList, int currPosition) {
//        PhotoBrowseInfo info = PhotoBrowseInfo.create(urls, rectList, currPosition);
//        PhotoBrowseActivity.start(this, info);
//    }
//
//    @Override
//    public void addFollowResult(BaseBean baseBean) {
//        if (baseBean.getStatus() == Constant.OK) {
////            SystemInfoUtils.addFollow(new UserBean(userId));
//            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_followed);
//            userInfoData.getProfile().followed = true;
//
//        } else {
//            Toast.makeText(this, "关注失败", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    public void cancelFollowResult(BaseBean baseBean) {
//        if (baseBean.getStatus() == Constant.OK) {
////            SystemInfoUtils.removeFollow(new UserBean(userId));
//            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_follow);
//            userInfoData.getProfile().followed = false;
//        } else {
//            Toast.makeText(this, "取消关注失败", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private boolean isSelf(String otherId) {
//        return !TextUtils.isEmpty(otherId) && App.mInstance.getUser() != null &&
//                otherId.equals(String.valueOf(App.mInstance.getUser().getId()));
//    }
//
//    @Override
//    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
//        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_user_info, container, false);
//        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
//        String[] campaignTab = getResources().getStringArray(R.array.coachInfoTab);
//        text.setText(campaignTab[position]);
//        return tabView;
//    }
//
//}
