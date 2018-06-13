package com.example.aidong.ui.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.aidong.ui.MainActivity;
import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.example.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.example.aidong .adapter.mine.UserInfoPhotoAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.PhotoBrowseInfo;
import com.example.aidong .entity.ProfileBean;
import com.example.aidong .entity.data.UserInfoData;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.activity.PhotoBrowseActivity;
import com.example.aidong .ui.discover.activity.PublishDynamicActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mine.fragment.CoachCourseFragment;
import com.example.aidong .ui.mine.fragment.UserDynamicFragment;
import com.example.aidong .ui.mine.fragment.UserInfoFragment;

import com.example.aidong .ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.example.aidong .ui.mvp.view.UserInfoActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.ImageRectUtils;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong .widget.SwitcherLayout;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.aidong.R.id.tv_message;
import static com.example.aidong .utils.Constant.REQUEST_LOGIN;
import static com.example.aidong .utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_PHOTO;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_VIDEO;


/**
 * 用户资料
 * Created by song on 2016/12/27.
 */
public class UserInfoActivity extends BaseActivity implements UserInfoActivityView, View.OnClickListener,
        UserInfoPhotoAdapter.OnItemClickListener {
    public static final int REQUEST_UPDATE_PHOTO = 1024;
    public static final int REQUEST_UPDATE_INFO = 2048;

    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivPublish;
    private SwitcherLayout switcherLayout;
    private RelativeLayout contentLayout;
    private RelativeLayout emptyPhotoLayout;
    private TextView tvAddImage, tv_attention_num, txt_edit;
    private RecyclerView rvPhoto;
    private ImageView ivAvatar;
    private ImageView ivGender;
    private ImageView ivCoach;
    private TextView tvName;
    private TextView tvSignature;
    private ImageView ivFollowOrEdit;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    //    private LinearLayout contactLayout;
//    private TextView tvCall;
    private TextView tvMessage;

    private String userId;
    private boolean isSelf = false;
    private UserInfoData userInfoData;
    private UserInfoPhotoAdapter wallAdapter;
    private UserInfoPresentImpl userInfoPresent;

    private FragmentPagerItemAdapter adapter;
    private boolean needRefreshFragment = false;
    private String contact;
    private ArrayList<BaseMedia> selectedMedia;

    public static void start(Context context, String userId) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        starter.putExtra("userId", userId);
        context.startActivity(starter);
    }


    public static void start(Context context, String userId, String intro) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        starter.putExtra("userId", userId);
        starter.putExtra("intro", intro);
        context.startActivity(starter);
    }

    public static void startForResult(Activity context, String userId, int request_code) {
        Intent starter = new Intent(context, UserInfoActivity.class);
        starter.putExtra("userId", userId);
        context.startActivityForResult(starter, request_code);
    }

    public static void startForResult(Fragment fragment, String userId, int request_code) {
        Intent starter = new Intent(fragment.getContext(), UserInfoActivity.class);
        starter.putExtra("userId", userId);
        fragment.startActivityForResult(starter, request_code);
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
        ivPublish = (ImageView) findViewById(R.id.iv_publish);
        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
        switcherLayout = new SwitcherLayout(this, contentLayout);
        emptyPhotoLayout = (RelativeLayout) findViewById(R.id.rl_self_empty);
        tvAddImage = (TextView) findViewById(R.id.tv_add_image);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        ivAvatar = (ImageView) findViewById(R.id.dv_avatar);
        ivGender = (ImageView) findViewById(R.id.iv_gender);
        ivCoach = (ImageView) findViewById(R.id.iv_coach_flag);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvMessage = (TextView) findViewById(tv_message);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        ivFollowOrEdit = (ImageView) findViewById(R.id.iv_follow_or_edit);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_user);
//        contactLayout = (LinearLayout) findViewById(R.id.ll_contact);
//        tvCall = (TextView) findViewById(R.id.tv_call);
        tv_attention_num = (TextView) findViewById(R.id.tv_attention_num);
        txt_edit = (TextView) findViewById(R.id.txt_edit);

        wallAdapter = new UserInfoPhotoAdapter(this, isSelf);
        rvPhoto.setAdapter(wallAdapter);
        rvPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        rvPhoto.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.photo_wall_margin), 4));
    }

    private void setListener() {
        txt_edit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivPublish.setOnClickListener(this);
        tvAddImage.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
//        tvCall.setOnClickListener(this);
        ivFollowOrEdit.setOnClickListener(this);
        wallAdapter.setListener(this);
        ivAvatar.setOnClickListener(this);
    }

    String intro = "";

    @Override
    public void updateUserInfo(UserInfoData userInfoData) {
        this.userInfoData = userInfoData;
        userId = userInfoData.getProfile().getId();

        if (!TextUtils.isEmpty(userInfoData.getProfile().personal_intro)) {
            intro = userInfoData.getProfile().personal_intro;
        } else {
            intro = "NODATA";
        }
        setView();
        setFragments();
    }

    private void setView() {
        GlideLoader.getInstance().displayRoundAvatarImage(userInfoData.getProfile().getAvatar(), ivAvatar);
        tvName.setText(userInfoData.getProfile().getName());
        tv_attention_num.setText(userInfoData.getProfile().follows_count + "人关注");

        tvSignature.setText(TextUtils.isEmpty(userInfoData.getProfile().getSignature())
                ? "这个人很懒，什么都没有留下" : userInfoData.getProfile().getSignature());
        ivGender.setBackgroundResource("0".equals(userInfoData.getProfile().getGender())
                ? R.drawable.icon_man : R.drawable.icon_woman);
        ivCoach.setVisibility("Coach".equalsIgnoreCase(userInfoData.getProfile().getUserType())
                ? View.VISIBLE : View.GONE);
        if (isSelf) {

            tvTitle.setText("我的资料");
//            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_edit_red);
            ivFollowOrEdit.setVisibility(View.GONE);
            txt_edit.setVisibility(View.VISIBLE);


            if (!userInfoData.getPhotoWall().isEmpty()) {
                wallAdapter.setData(userInfoData.getPhotoWall());
                emptyPhotoLayout.setVisibility(View.GONE);
            } else {
                emptyPhotoLayout.setVisibility(View.VISIBLE);
            }
            ivPublish.setVisibility(View.VISIBLE);
//            contactLayout.setVisibility(View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0, (int) getResources().getDimension(R.dimen.dp_0));
            tvMessage.setVisibility(View.GONE);
        } else {
            tvTitle.setText("TA的资料");
            ivFollowOrEdit.setBackgroundResource(userInfoData.getProfile().followed
                    ? R.drawable.icon_followed : R.drawable.icon_follow);
            ivFollowOrEdit.setVisibility(View.VISIBLE);
            txt_edit.setVisibility(View.GONE);

            if (!userInfoData.getPhotoWall().isEmpty()) {
                wallAdapter.setData(userInfoData.getPhotoWall());
            }
            ivPublish.setVisibility(View.GONE);
//            contactLayout.setVisibility(View.GONE);
//            tvCall.setVisibility("Coach".equalsIgnoreCase(userInfoData.getProfile().getUserType())
//                    ? View.VISIBLE : View.GONE);
            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0, (int) getResources().getDimension(R.dimen.dp_0));
//            contentLayout.setPadding(0, DensityUtil.dp2px(this, 46), 0, (int) getResources().getDimension(R.dimen.pref_50dp));
            tvMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setFragments() {
        if (needRefreshFragment) {
            needRefreshFragment = false;
            Fragment page = adapter.getPage(1);
            if (page instanceof UserInfoFragment) {
                ((UserInfoFragment) page).refreshUserInfo(userInfoData.getProfile());
            }
        } else {
            FragmentPagerItems pages = new FragmentPagerItems(this);
            UserDynamicFragment dynamicFragment = new UserDynamicFragment();
            UserInfoFragment userInfoFragment = new UserInfoFragment();


            if (Constant.COACH.equals(userInfoData.getProfile().getUserTypeByUserType())) {

                pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass(),
                        new Bundler().putString("userId", userId).putString("intro", intro).get()));

                CoachCourseFragment courseFragment = new CoachCourseFragment();
                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                        new Bundler().putString("mobile", userInfoData.getProfile().mobile).get()));
            } else {
                pages.add(FragmentPagerItem.of(null, dynamicFragment.getClass(),
                        new Bundler().putString("userId", userId).get()));
            }


            pages.add(FragmentPagerItem.of(null, userInfoFragment.getClass(),
                    new Bundler().putParcelable("profile", userInfoData.getProfile()).get()));
            adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
            viewPager.setAdapter(adapter);
            // tabLayout.setCustomTabView(this);


            tabLayout.setupWithViewPager(viewPager);


            String[] campaignTab;

            if (userInfoData != null && userInfoData.getProfile() != null && Constant.COACH.equals(userInfoData.getProfile().getUserTypeByUserType())) {
                campaignTab = getResources().getStringArray(R.array.coachInfoTab);
            } else {
                campaignTab = getResources().getStringArray(R.array.infoTab);
            }

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                if (tabLayout != null && tabLayout.getTabAt(i) != null)
                    tabLayout.getTabAt(i).setText(campaignTab[i]);

            }


        }
    }









    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:

                finishSetResult();

                break;
            case R.id.dv_avatar:

                List<String> urls = new ArrayList<>();
                urls.add(userInfoData.getProfile().getAvatar());
                List<Rect> viewLocalRect = new ArrayList<>();
                viewLocalRect.add(ImageRectUtils.getDrawableBoundsInView(ivAvatar));
//                PhotoBrowseInfo info = PhotoBrowseInfo.create(urls, viewLocalRect, 0);
//                PhotoBrowseActivity.start(this, info,ivAvatar);


                ImageView[]  imageViews = new ImageView[urls.size()];

                for (int i = 0; i < urls.size(); i++) {
                    imageViews[i] = (ImageView) ivAvatar;
                }


                ImageShowActivity.startImageActivity(this, imageViews, urls.toArray(new String[urls.size()]), 0);


                break;

            case R.id.iv_publish:
                publishDynamic();
                break;

            case R.id.tv_add_image:
                toUpdatePhotoWallActivity();
                break;
            case R.id.tv_message:
                if (App.mInstance.isLogin()) {
                    ProfileBean profile = userInfoData.getProfile();
                  //  EMChatActivity.start(this, userId, profile.getName(), profile.getAvatar());


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        // 权限未被授予
                        Toast.makeText(this, "请去设置页面开启相机权限", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("TAG", "相机权限已经被受理，开始预览相机！");
                        EMChatActivity.start(this, userId, profile.getName(), profile.getAvatar());
                    }
























                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
                }
                break;
            case R.id.iv_follow_or_edit:
                if (App.mInstance.isLogin()) {
                    if (isSelf) {
                        showEditDialog();
                    } else if (userInfoData.getProfile().followed) {
                        userInfoPresent.cancelFollow(userId, userInfoData.getProfile().getUserTypeByUserType());
                    } else {
                        userInfoPresent.addFollow(userId, userInfoData.getProfile().getUserTypeByUserType());
                    }
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
                }
                break;
            case R.id.txt_edit:
                if (App.mInstance.isLogin()) {
                    if (isSelf) {
                        showEditDialog();
                    }
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
                }

//            case R.id.tv_call:
//                if (TextUtils.isEmpty(userInfoData.getProfile().getPhone())) {
//                    ToastGlobal.showLong("该教练没有录入电话号码");
//                    return;
//                }
//                showCallUpDialog(userInfoData.getProfile().getPhone());
//                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishSetResult();
    }

    private void finishSetResult() {

//        Logger.i("follow","finishSetResult follow = " +userInfoData.getProfile().followed);
        Intent intentResult = new Intent();
        if (userInfoData != null) {
            intentResult.putExtra(Constant.FOLLOW, userInfoData.getProfile().followed);
        }
        setResult(RESULT_OK, intentResult);
        finish();
    }

    private void showCallUpDialog(final String phoneNum) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(getString(R.string.confirm_call_up), phoneNum))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TelephoneManager.callImmediate(UserInfoActivity.this, phoneNum);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void showEditDialog() {
        new MaterialDialog.Builder(this)
                .items(R.array.editTab)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            Intent intent = new Intent(UserInfoActivity.this, UpdateUserInfoActivity.class);
                            intent.putExtra("profileBean", userInfoData.getProfile());
                            startActivityForResult(intent, REQUEST_UPDATE_INFO);
                        } else {
                            toUpdatePhotoWallActivity();
                        }
                    }
                })
                .show();
    }


    private void toUpdatePhotoWallActivity() {
        Intent intent = new Intent(UserInfoActivity.this, UpdatePhotoWallActivity.class);
        intent.putParcelableArrayListExtra("photos",
                (ArrayList<? extends Parcelable>) userInfoData.getPhotoWall());
        startActivityForResult(intent, REQUEST_UPDATE_PHOTO);
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
        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
    }

    private void takeVideo() {
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_LOGIN) {
                isSelf = isSelf(userId);
                userInfoPresent.getUserInfo(switcherLayout, userId);
            } else if (requestCode == REQUEST_SELECT_PHOTO) {
                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);
            } else if (requestCode == REQUEST_PUBLISH_DYNAMIC) {
                Fragment page = adapter.getPage(0);
                if (page instanceof UserDynamicFragment) {
                    ((UserDynamicFragment) page).refreshDynamic();
                }
            } else if (requestCode == REQUEST_UPDATE_PHOTO) {
                resetPhotoWall();
                userInfoPresent.getUserInfo(userId);
            } else if (requestCode == REQUEST_UPDATE_INFO) {
                needRefreshFragment = true;
                userInfoPresent.getUserInfo(userId);
            } else if (requestCode == REQUEST_SELECT_VIDEO) {
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

//                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC);
                }
            }
        }
    }

    private void resetPhotoWall() {
        userInfoData.getPhotoWall().clear();
        wallAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPreviewPhotoWallImage(List<String> urls, List<Rect> rectList, int currPosition,View view) {
        PhotoBrowseInfo info = PhotoBrowseInfo.create(urls, rectList, currPosition);
        PhotoBrowseActivity.start(this, info,view);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.addFollow(new UserBean(userId));
            userInfoData.getProfile().followed = true;
            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_followed);
        } else {
            Toast.makeText(this, "关注失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            userInfoData.getProfile().followed = false;
//            SystemInfoUtils.removeFollow(new UserBean(userId));
            ivFollowOrEdit.setBackgroundResource(R.drawable.icon_follow);
        } else {
            Toast.makeText(this, "取消关注失败", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isSelf(String otherId) {
        return !TextUtils.isEmpty(otherId) && App.mInstance.getUser() != null &&
                otherId.equals(String.valueOf(App.mInstance.getUser().getId()));
    }
















}
