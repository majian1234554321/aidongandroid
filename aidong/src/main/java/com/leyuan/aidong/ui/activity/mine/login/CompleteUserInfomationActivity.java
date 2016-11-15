package com.leyuan.aidong.ui.activity.mine.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.CommonTitleLayout;

import static com.leyuan.aidong.R.id.layout_title;

/**
 * Created by user on 2016/11/1.
 */

public class CompleteUserInfomationActivity extends BaseActivity implements View.OnClickListener {
    private CommonTitleLayout layoutTitle;
    private SimpleDraweeView imgAvatar;
    private TextView txtChangeAvatar;
    private RelativeLayout relNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_information);
        initView();


    }

    private void initView() {
        layoutTitle = (CommonTitleLayout) findViewById(layout_title);
        imgAvatar = (SimpleDraweeView) findViewById(R.id.img_avatar);
        txtChangeAvatar = (TextView) findViewById(R.id.txt_change_avatar);
        relNickname = (RelativeLayout) findViewById(R.id.rel_nickname);
        layoutTitle.setLeftIconListener(this);
        layoutTitle.setRightTextListener(this);
        findViewById(R.id.img_avatar).setOnClickListener(this);
        findViewById(R.id.txt_change_avatar).setOnClickListener(this);
        findViewById(R.id.btn_gender).setOnClickListener(this);
        findViewById(R.id.btn_city).setOnClickListener(this);
        findViewById(R.id.btn_sign).setOnClickListener(this);
        findViewById(R.id.btn_identify).setOnClickListener(this);
        findViewById(R.id.btn_birthday).setOnClickListener(this);
        findViewById(R.id.btn_constellation).setOnClickListener(this);
        findViewById(R.id.btn_height).setOnClickListener(this);
        findViewById(R.id.btn_weight).setOnClickListener(this);
        findViewById(R.id.btn_exercise).setOnClickListener(this);
        findViewById(R.id.btn_bmi).setOnClickListener(this);
    }

    private EditText getEditNickname(){
        return (EditText) findViewById(R.id.edit_nickname);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.txt_right:
                //调接口

                break;
            case R.id.img_avatar:
                break;
            case R.id.txt_change_avatar:
                break;
            case R.id.btn_gender:
                break;
            case R.id.btn_city:
                break;
            case R.id.btn_sign:
                break;
            case R.id.btn_identify:
                break;
            case R.id.btn_birthday:
                break;
            case R.id.btn_constellation:
                break;
            case R.id.btn_height:
                break;
            case R.id.btn_weight:
                break;
            case R.id.btn_exercise:
                break;
            case R.id.btn_bmi:
                break;
        }
    }
}
