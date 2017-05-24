package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.PrivacyPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.PrivacyActivityView;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.UISwitchButton;


public class PrivacyActivity extends BaseActivity implements PrivacyActivityView {
    private ImageView layout_tab_mine_personal_setting_privacy_img;
    private UISwitchButton uiswitchButton;
    private PrivacyPresenterImpl privacyPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        privacyPresenter = new PrivacyPresenterImpl(this, this);

        setContentView(R.layout.layout_privacy);

        layout_tab_mine_personal_setting_privacy_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_setting_privacy_img);
        uiswitchButton = (UISwitchButton) findViewById(R.id.switch1);
        DialogUtils.showDialog(PrivacyActivity.this, "", true);
        privacyPresenter.getHideSetting();

        layout_tab_mine_personal_setting_privacy_img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onHideSelfSuccess(boolean success) {
        DialogUtils.dismissDialog();
        if (success) {
            ToastUtil.showShort(this, "设置成功");
        }
    }

    @Override
    public void onGetHideSetting(boolean hide) {
        DialogUtils.dismissDialog();
        uiswitchButton.setChecked(!hide);
        uiswitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DialogUtils.showDialog(PrivacyActivity.this, "", true);
                privacyPresenter.hideSelf();
//                privacyPresenter.hideSelf(isChecked?0:1);
            }
        });
    }
}
