package com.leyuan.aidong.ui.mine.activity.setting;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.chat.manager.EmChatLoginManager;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.WebViewActivity;
import com.leyuan.aidong.ui.mine.activity.BingdingCommunityActivity;
import com.leyuan.aidong.ui.mine.activity.PrivacyActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.utils.DataCleanManager;
import com.leyuan.aidong.utils.MyDbUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;

import java.util.HashMap;

public class TabMinePersonalSettingsActivity extends BaseActivity {
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
        onClick();
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

    private void onClick() {
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
                        ToastUtil.show(getResources().getString(
                                R.string.cacheclear), TabMinePersonalSettingsActivity.this);
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
        if (App.mInstance.isLogin()) {
            button_personal_settings_unlogin
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Builder builder = new Builder(
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
                                            intent.setClass(
                                                    TabMinePersonalSettingsActivity.this,
                                                    MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }

                                    });
                            builder.setNegativeButton("取消", null);
                            builder.show();
                        }
                    });
        } else {
            button_personal_settings_unlogin.setClickable(false);
            button_personal_settings_unlogin.setEnabled(false);
            button_personal_settings_unlogin.setBackgroundColor(getResources()
                    .getColor(R.color.color_light_white));

        }

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
                        if (App.mInstance.isLogin()) {
                            intent.setClass(getApplicationContext(),
                                    TabMineChangePasswordActivity.class);
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

                WebViewActivity.start(TabMinePersonalSettingsActivity.this, "意见反馈",
                        "http://192.168.100.142:4567/customer_service");
            }
        });
        layout_tab_mine_help_rel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(), TabHelpActivity.class);
                startActivity(intent);
            }
        });

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

//
//        if (App.mInstance.isLogin()) {
//            mobile = App.mInstance.getUser().getMobile();
//        }
//        if (!TextUtils.isEmpty(mobile)) {
//            layout_tab_mine_personal_settings_binding_mobile_phone_unbound_txt
//                    .setText(mobile);
//            layout_tab_mine_personal_settings_binding_mobile_phone_rel
//                    .setClickable(false);
//        }
//
//        if (layout_tab_mine_personal_settings_binding_mobile_phone_rel
//                .isClickable()) {
//            layout_tab_mine_personal_settings_binding_mobile_phone_rel
//                    .setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (App.mInstance.isLogin()) {
////                                intent = new Intent(
////                                        TabMinePersonalSettingsActivity.this,
////                                        TabMineBingdingMobilePhoneActivity.class);
////                                startActivity(intent);
//                                UiManager.activityJump(TabMinePersonalSettingsActivity.this, PhoneBindingActivity.class);
//                            } else {
//                                intent.setClass(
//                                        TabMinePersonalSettingsActivity.this,
//                                        LoginActivity.class);
//                                startActivity(intent);
//                            }
//                        }
//                    });
//        }
    }


    private void loginOut() {
        try {
            MyDbUtils.clearZanmap();
            App.mInstance.exitLogin();
            EmChatLoginManager.loginOut();


//            BaseApp.mInstance.getParamsHelper().setPreInt("islogin", 0);
//            BaseApp.mInstance.logout(null);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("token", App.mInstance.getUser().getToken());
         /*   addTask(this, new IHttpTask(UrlLink.LOGOUT_URL, map,
                            new ArrayList<BasicNameValuePair>(), MsgResult.class),
                    HttpConfig.DELETE, LOGINOUT);

*/
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
