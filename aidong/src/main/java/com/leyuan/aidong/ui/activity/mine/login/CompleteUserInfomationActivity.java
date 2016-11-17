package com.leyuan.aidong.ui.activity.mine.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.view.AddressPopupWindow;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.ActionSheetDialog;
import com.leyuan.aidong.widget.CommonTitleLayout;

import static com.leyuan.aidong.R.id.layout_title;
import static com.leyuan.aidong.R.id.txt_change_avatar;

/**
 * Created by user on 2016/11/1.
 */

public class CompleteUserInfomationActivity extends BaseActivity implements View.OnClickListener {
    private CommonTitleLayout layoutTitle;
    private SimpleDraweeView imgAvatar;
    private TextView txtChangeAvatar;
    private RelativeLayout relNickname;
    private AddressPopupWindow addressPopup;
    private ScrollView rootLayout;
    Button btn_gender,btn_city,btn_sign,btn_identify,btn_birthday,btn_constellation,btn_height
            ,btn_weight,btn_exercise,btn_bmi;
    private String nickname,avatar,gender,birthday,signature,sport,province,city,area,height,weight
            ,bust,waist,hip,chart_site,frequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_information);
        initView();
    }

    private void initView() {
        rootLayout = (ScrollView)findViewById(R.id.ll_root);
        layoutTitle = (CommonTitleLayout) findViewById(layout_title);
        imgAvatar = (SimpleDraweeView) findViewById(R.id.img_avatar);
        txtChangeAvatar = (TextView) findViewById(txt_change_avatar);
        relNickname = (RelativeLayout) findViewById(R.id.rel_nickname);

        btn_gender = (Button)findViewById(R.id.btn_gender);
        btn_city = (Button)findViewById(R.id.btn_city);
        btn_sign = (Button)findViewById(R.id.btn_sign);
        btn_identify = (Button)findViewById(R.id.btn_identify);
        btn_birthday = (Button)findViewById(R.id.btn_birthday);
        btn_constellation = (Button)findViewById(R.id.btn_constellation);
        btn_height = (Button)findViewById(R.id.btn_height);
        btn_weight = (Button)findViewById(R.id.btn_weight);
        btn_exercise = (Button)findViewById(R.id.btn_exercise);
        btn_bmi = (Button)findViewById(R.id.btn_bmi);

        layoutTitle.setLeftIconListener(this);
        layoutTitle.setRightTextListener(this);
        findViewById(R.id.img_avatar).setOnClickListener(this);
        findViewById(txt_change_avatar).setOnClickListener(this);
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
                if(verify()){

                }

                break;
            case R.id.img_avatar:
                showSelectImageDialog();

                break;
            case txt_change_avatar:
                showSelectImageDialog();

                break;
            case R.id.btn_gender:
                showSelectGenderDialog();

                break;
            case R.id.btn_city:
                if(addressPopup == null){
                    addressPopup = new AddressPopupWindow(this);
                    addressPopup.setOnConfirmAddressListener(new AddressPopupWindow.OnConfirmAddressListener() {
                        @Override
                        public void onAddressConfirm(String p, String c, String a) {
                            province = p;
                            city = c;
                            area = a;
                            btn_city.setText(province+"-"+city+"-"+area);
                        }
                    });
                }
                addressPopup.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
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

    private boolean verify() {
        nickname = getEditNickname().getText().toString().trim();
        if(TextUtils.isEmpty(nickname)){
            ToastUtil.showConsecutiveShort("请输入昵称");
            return false;
        }
        if(TextUtils.isEmpty(gender)){
            ToastUtil.showConsecutiveShort("请选择性别");
            return false;
        }
        if(TextUtils.isEmpty(province)){
            ToastUtil.showConsecutiveShort("请选择城市");
            return false;
        }


        return true;
    }

    private void showSelectImageDialog() {
         ActionSheetDialogBuild("请选择")
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {


                    }
                })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {

                    }
                })
                .show();
    }

    private void showSelectGenderDialog() {
        ActionSheetDialogBuild("请选择")
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        gender=String.valueOf(which);
                        btn_gender.setText("男");

                    }
                })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        gender=String.valueOf(which);
                        btn_gender.setText("女");
                    }
                })
                .show();
    }

    private ActionSheetDialog ActionSheetDialogBuild(String title){
        return  new ActionSheetDialog( this).builder()
                .setTitle(title)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false);
    }

}
