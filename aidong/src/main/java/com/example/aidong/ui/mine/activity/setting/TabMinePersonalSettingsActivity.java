package com.example.aidong.ui.mine.activity.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .module.chat.manager.EmChatLoginManager;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.WebViewActivity;
import com.example.aidong .ui.mine.activity.BingdingCommunityActivity;
import com.example.aidong .ui.mine.activity.PrivacyActivity;
import com.example.aidong .ui.mine.activity.account.ChangePasswordActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.LoginPresenter;
import com.example.aidong .ui.mvp.view.LoginExitView;
import com.example.aidong .ui.mvp.view.RequestCountInterface;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DataCleanManager;
import com.example.aidong .utils.Md5Utils;
import com.example.aidong .utils.RequestResponseCount;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;

public class TabMinePersonalSettingsActivity extends BaseActivity implements LoginExitView, RequestCountInterface {
    private ImageView layout_tab_mine_personal_settings_title_img_back;
    private RelativeLayout layout_tab_mine_personal_settings_update_password_rel;
    private Intent intent;
    private RelativeLayout layout_tab_mine_feedback_rel;
    private RelativeLayout layout_tab_mine_help_rel;
    private RelativeLayout layout_tab_mine_personal_settings_contactus_rel;
    private RelativeLayout layout_tab_mine_personal_settings_binding_mobile_phone_rel;
    private RelativeLayout layout_tab_mine_personal_settings_Privacy_rel;
    private RelativeLayout layout_tab_mine_personal_settings_binding_third_party_community_rel;
    private RelativeLayout layout_tab_mine_personal_settings_clear_the_cache_rel;
    private RelativeLayout layout_tab_mine_personal_settings_message_reminder_rel;
    private Button button_personal_settings_unlogin;
    private TextView layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt;
    private static final int LOGINOUT = 0;
    private static final int CUSTOMERSERVICEPHONE = 1;
    private TextView settings_contactus_txt;
    private String mobile;
    //    private String mobile = "";
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_tab_mine_personal_settings);
        init();
    }

    protected void initData() {
        setClickListener();
        data();
    }

    private void init() {
        layout_tab_mine_personal_settings_message_reminder_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_message_reminder_rel);
        button_personal_settings_unlogin = (Button) findViewById(R.id.button_personal_settings_unlogin);
        layout_tab_mine_personal_settings_update_password_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_update_password_rel);
        layout_tab_mine_personal_settings_title_img_back = (ImageView) findViewById(R.id.layout_tab_mine_personal_settings_title_img_back);
        layout_tab_mine_feedback_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_feedback_rel);
        layout_tab_mine_help_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_help_rel);
        layout_tab_mine_personal_settings_contactus_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_contactus_rel);
        layout_tab_mine_personal_settings_binding_mobile_phone_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_binding_mobile_phone_rel);
        layout_tab_mine_personal_settings_Privacy_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_Privacy_rel);
        layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt = (TextView) findViewById(R.id.layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt);
        layout_tab_mine_personal_settings_binding_third_party_community_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_binding_third_party_community_rel);
        layout_tab_mine_personal_settings_clear_the_cache_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_personal_settings_clear_the_cache_rel);
        settings_contactus_txt = (TextView) findViewById(R.id.settings_contactus_txt);
        intent = new Intent();
    }

    private void setClickListener() {
        layout_tab_mine_personal_settings_message_reminder_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (App.mInstance.isLogin()) {
                            intent.setClass(getApplicationContext(),
                                    TabMineMessageReminderActivity.class);
                            startActivity(intent);
                        } else {
                            intent.setClass(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
        layout_tab_mine_personal_settings_clear_the_cache_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clean();
                        ToastGlobal.showShort(R.string.cacheclear);
                    }
                });
        layout_tab_mine_personal_settings_binding_third_party_community_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (App.mInstance.isLogin()) {
                            intent.setClass(getApplicationContext(),
                                    BingdingCommunityActivity.class);
                            startActivity(intent);
                        } else {
                            intent.setClass(
                                    TabMinePersonalSettingsActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }

                    }
                });


        layout_tab_mine_personal_settings_Privacy_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (App.mInstance.isLogin()) {
                            intent.setClass(getApplicationContext(),
                                    PrivacyActivity.class);
                            startActivity(intent);
                        } else {
                            intent.setClass(
                                    TabMinePersonalSettingsActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
        layout_tab_mine_personal_settings_title_img_back
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        layout_tab_mine_personal_settings_update_password_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (App.getInstance().isLogin()) {
                            intent.setClass(getApplicationContext(),
                                    ChangePasswordActivity.class);
                            startActivity(intent);
                        } else {
                            intent.setClass(getApplicationContext(),
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
        layout_tab_mine_feedback_rel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getInstance().isLogin()) {
                    WebViewActivity.start(TabMinePersonalSettingsActivity.this, "意见反馈", ConstantUrl.URL_FEEDBACK +
                            App.getInstance().getUser().getId() + "&&key=" + Md5Utils.createMd(App.getInstance().getToken()));
                } else {
                    UiManager.activityJump(TabMinePersonalSettingsActivity.this, LoginActivity.class);
                }

            }
        });
        layout_tab_mine_help_rel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), TabHelpActivity.class);
                startActivity(intent);
            }
        });

        layout_tab_mine_personal_settings_contactus_rel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = settings_contactus_txt.getText().toString().trim();
                if (!TextUtils.isEmpty(tel)) {
                    TelephoneManager.callImmediate(TabMinePersonalSettingsActivity.this, tel);
                } else {
                    ToastGlobal.showShort("电话错误");
                }
            }
        });

        button_personal_settings_unlogin.setEnabled(true);
        button_personal_settings_unlogin
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                TabMinePersonalSettingsActivity.this);
                        builder.setMessage("退出后你将无法收到他人消息,别人也将无法找到你,确定继续？");
                        builder.setTitle("退出登录");
                        builder.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialo0g,
                                            int which) {
                                        loginOut();

                                    }
                                });
                        builder.setNegativeButton("取消", null);
                        builder.show();
                    }
                });
        button_personal_settings_unlogin.setBackgroundResource(R.drawable.shape_radius_origin);
    }

    private void data() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindMobile();
    }

    /**
     * 判断绑定手机号
     */
    private void bindMobile() {
        if (App.getInstance().isLogin()) {
            mobile = App.getInstance().getUser().getMobile();
            if (!TextUtils.isEmpty(mobile)) {
                layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt
                        .setText(mobile);
            }
            button_personal_settings_unlogin.setVisibility(View.VISIBLE);

        } else {
            layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt
                    .setText("未绑定");
            button_personal_settings_unlogin.setVisibility(View.GONE);
        }
        layout_tab_mine_personal_settings_binding_mobile_phone_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!App.getInstance().isLogin()) {
                            UiManager.activityJump(TabMinePersonalSettingsActivity.this, LoginActivity.class);
                        } else if (TextUtils.isEmpty(mobile)) {
                            UiManager.activityJump(TabMinePersonalSettingsActivity.this, PhoneBindingActivity.class);
                        } else {
                            UiManager.activityJump(TabMinePersonalSettingsActivity.this, PhoneUnBindingActivity.class);
                        }
                    }
                });
    }




    private void clean() {
        DataCleanManager.clearAllCache(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int request, int result, Intent arg2) {
        if (result == RESULT_OK) {
//            App.mInstance.getUser().setBindMobile(true);
            layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt
                    .setText(R.string.the_bound);
        }
        super.onActivityResult(request, result, arg2);
    }

    private void loginOut() {



        RequestResponseCount requestResponse = new RequestResponseCount(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.setOnRequestResponse(requestResponse);
        loginPresenter.setExitLoginListener(this);
        loginPresenter.exitLogin();

    }

    @Override
    public void onExitLoginResult(boolean result) {
        if (result) {
            App.getInstance().exitLogin();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_EXIT_LOGIN));

            CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(App.getInstance());
            coursePresentNew.getCourseFilterConfig();

            EmChatLoginManager.loginOut();
            //App.getInstance().clearCMDMessage();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE));

            Intent intent = new Intent(TabMinePersonalSettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestCount(int requestCount) {

    }
}
