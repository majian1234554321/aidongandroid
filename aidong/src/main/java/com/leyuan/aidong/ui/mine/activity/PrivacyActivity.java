package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.UISwitchButton;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class PrivacyActivity extends BaseActivity  {
    private ImageView layout_tab_mine_personal_setting_privacy_img;
    private UISwitchButton uiswitchButton;
    private static final int PRIVACY = 0;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_privacy);
        init();

    }

    protected void initData() {
        onClick();
    }

    private void init() {
        layout_tab_mine_personal_setting_privacy_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_setting_privacy_img);
        uiswitchButton = (UISwitchButton) findViewById(R.id.switch1);
        mSharedPreferences = getSharedPreferences("BingdingThree",
                Context.MODE_PRIVATE);
        uiswitchButton.setChecked(mSharedPreferences.getBoolean("state", true));
    }

    private void onClick() {
        layout_tab_mine_personal_setting_privacy_img
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        uiswitchButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             /*   if (!isChecked) {
                    addTask(PrivacyActivity.this, new IHttpTask(UrlLink.PRIVACY_URL, paramsinit(1),
                            MsgResult.class), HttpConfig.PUT, PRIVACY);
                } else {
                    addTask(PrivacyActivity.this, new IHttpTask(UrlLink.PRIVACY_URL, paramsinit(0),
                            MsgResult.class), HttpConfig.PUT, PRIVACY);
                }*/
                Editor editor = mSharedPreferences.edit();
                editor.putBoolean("state", isChecked);
                editor.commit();
            }
        });
    }

    public List<BasicNameValuePair> paramsinit(int stealth) {
        List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
        paramsaaa.add(new BasicNameValuePair("stealth", "" + stealth));
        return paramsaaa;
    }

    /*@Override
    public void onGetData(Object data, int requestCode, String response) {
        switch (requestCode) {
            case PRIVACY:
                MsgResult result = (MsgResult) data;
                if (result.getCode() == 1) {
                    ToastUtil.show(getResources().getString(R.string.setupsuccessful), this);
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void onError(String reason, int requestCode) {

    }*/
}
