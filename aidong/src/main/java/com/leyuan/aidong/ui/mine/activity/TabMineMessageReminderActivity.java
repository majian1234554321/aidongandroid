package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.customview.SwitchButton;


public class TabMineMessageReminderActivity extends BaseActivity {
    private ImageView mlayout_tab_mine_message_reminder_img_back;
    private RelativeLayout mlayout_tab_mine_message_reminder_do_not_disturb_period_rel;
    private SwitchButton mswitch1, mswitch2, mswitch3;
    private Intent intent;
    //	private SharedPreferences mSharedPreferences;
    //	EMChatOptions chatOptions;
    //
    //	ChatHXSDKModel model;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_tab_mine_message_reminder);
        init();
        onClick();
    }

    protected void initData() {

    }

    private void init() {
        mlayout_tab_mine_message_reminder_img_back = (ImageView) findViewById(R.id.layout_tab_mine_message_reminder_img_back);
        mlayout_tab_mine_message_reminder_do_not_disturb_period_rel = (RelativeLayout) findViewById(R.id.layout_tab_mine_message_reminder_do_not_disturb_period_rel);
        mswitch1 = (SwitchButton) findViewById(R.id.switch1);
        mswitch2 = (SwitchButton) findViewById(R.id.switch2);
        mswitch3 = (SwitchButton) findViewById(R.id.switch3);
        //		mSharedPreferences = getSharedPreferences("BingdingThree",
        //				Context.MODE_PRIVATE);
        //		mswitch1.setChecked(mSharedPreferences.getBoolean("state", true));
        //		mswitch2.setChecked(mSharedPreferences.getBoolean("state2", true));
        //		mswitch3.setChecked(mSharedPreferences.getBoolean("state3", true));
        intent = new Intent();
        //		chatOptions = EMChatManager.getInstance().getChatOptions();
        //
        //		model = (ChatHXSDKModel) HXSDKHelper.getInstance().getModel();
        //		mswitch1.setChecked(model.getSettingMsgNotification());
        //		mswitch2.setChecked(model.getSettingMsgSound());
        //		mswitch3.setChecked(model.getSettingMsgVibrate());


    }

    private void onClick() {
        mlayout_tab_mine_message_reminder_img_back
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        mlayout_tab_mine_message_reminder_do_not_disturb_period_rel
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.setClass(TabMineMessageReminderActivity.this,
                                TabMineDoNotDisturbPeriodActivity.class);
                        startActivity(intent);
                    }
                });
        mswitch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (!isChecked) {
                    //					chatOptions.setNotificationEnable(false);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgNotification(false);
                    // chatOptions.setNotifyBySoundAndVibrate(false); //默认为true
                    // 开启新消息提醒
                } else {
                    //					chatOptions.setNotificationEnable(true);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgNotification(true);
                    // chatOptions.setNotifyBySoundAndVibrate(true);
                }
                //				Editor editor = mSharedPreferences.edit();
                //				editor.putBoolean("state", checked);
                //				editor.commit();
            }
        });
        mswitch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (!isChecked) {
                    //					chatOptions.setNoticeBySound(false);
                    //					chatOptions.setNoticeBySound(false);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgSound(false);
                } else {
                    //					chatOptions.setNoticeBySound(true);
                    //					chatOptions.setNoticeBySound(true);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgSound(true);
                }
                //				Editor editor = mSharedPreferences.edit();
                //				editor.putBoolean("state2", checked);
                //				editor.commit();
            }
        });
        mswitch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (!isChecked) {
                    //					chatOptions.setNoticedByVibrate(false);
                    //					chatOptions.setNoticedByVibrate(false);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(false);
                } else {
                    //					chatOptions.setNoticedByVibrate(true);
                    //					chatOptions.setNoticedByVibrate(true);
                    //					EMChatManager.getInstance().setChatOptions(chatOptions);
                    //					HXSDKHelper.getInstance().getModel().setSettingMsgVibrate(true);
                }
                //				Editor editor = mSharedPreferences.edit();
                //				editor.putBoolean("state3", checked);
                //				editor.commit();
            }
        });
    }

}
