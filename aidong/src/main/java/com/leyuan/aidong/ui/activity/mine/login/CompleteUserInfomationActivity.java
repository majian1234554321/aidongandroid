package com.leyuan.aidong.ui.activity.mine.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.view.AddressPopupWindow;
import com.leyuan.aidong.ui.mvp.presenter.impl.CompleteUserPresenter;
import com.leyuan.aidong.ui.mvp.view.CompleteUserViewInterface;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.widget.ActionSheetDialog;
import com.leyuan.aidong.widget.CommonTitleLayout;
import com.leyuan.commonlibrary.manager.UiManager;
import com.leyuan.commonlibrary.util.CameraUtils;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.leyuan.aidong.R.id.layout_title;
import static com.leyuan.aidong.R.id.txt_change_avatar;

/**
 * Created by user on 2016/11/1.
 */

public class CompleteUserInfomationActivity extends BaseActivity implements View.OnClickListener, CompleteUserViewInterface{
    private CommonTitleLayout layoutTitle;
    private SimpleDraweeView imgAvatar;
    private TextView txtChangeAvatar;
    private RelativeLayout relNickname;
    private AddressPopupWindow addressPopup;
    private ScrollView rootLayout;
    Button btn_gender,btn_city,btn_sign,btn_identify,btn_birthday,btn_constellation,btn_height
            ,btn_weight,btn_exercise,btn_bmi;
    private String nickname,filepath;
//            ,avatar,gender,birthday,signature,sport,province,city,area,height,weight
//            ,bust,waist,hip,chart_site,frequency;

    private CompleteUserPresenter presenter;
    private Map<String,String > params = new HashMap<>();
    private Calendar calender_current = Calendar.getInstance();
    private String[] zodiacs =  {"白羊座","金牛座","双子座","巨蟹座","狮子座","处女座",
            "天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座"};
    private File tempFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_information);
        presenter = new CompleteUserPresenter(this,this);
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
                    presenter.completeUserInfo(params,filepath);
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
                        public void onAddressConfirm(String province, String city, String area) {
//                            province = p;
//                            city = c;
//                            area = a;
                            btn_city.setText(province+"-"+city+"-"+area);
                            params.put("province",province);
                            params.put("city",city);
                            params.put("area",area);
                        }
                    });
                }
                addressPopup.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_sign:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.BUNDLE_CONTENT,btn_sign.getText().toString().trim());
                UiManager.activityJump(this,bundle,XiuGaiGeXinQianMing.class,Constant.REQUEST_CODE_SIGN);

                break;
            case R.id.btn_identify:
                showSelectIdentifyDialog();
                break;
            case R.id.btn_birthday:
                showSelectBirthdayDialog();
                break;
            case R.id.btn_constellation:
                showSelectConstellationDialog();

                break;
            case R.id.btn_height:
                showSelectHeightDialog();
                break;
            case R.id.btn_weight:
                showSelectWeightDialog();

                break;
            case R.id.btn_exercise:
                showSelectExerciseDialog();
                break;
            case R.id.btn_bmi:

                break;
        }
    }

    private void showSelectExerciseDialog() {
        ActionSheetDialog dialog =   ActionSheetDialogBuild("请选择");
        for(int i = 1; i <= 7; i++){
            dialog.addSheetItem("一周"+i+"次", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    btn_exercise.setText("一周"+which+"次");
                    params.put("frequency","一周"+which+"次");
                }
            });
        }
        dialog.show();
    }

    private void showSelectWeightDialog() {
        ActionSheetDialog dialog =   ActionSheetDialogBuild("请选择");
        for(int i = 40; i <=100; i++){
            dialog.addSheetItem(i+"kg", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    btn_weight.setText((which+39)+"kg");
                    params.put("weight",(which+39)+"kg");
                }
            });
        }
        dialog.show();
    }

    private void showSelectHeightDialog() {
        ActionSheetDialog dialog =   ActionSheetDialogBuild("请选择");
        for(int i = 150; i <= 210; i++){
            dialog.addSheetItem(i+"cm", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    btn_height.setText((which+149)+"cm");
                    params.put("height",(which+149)+"cm");
                }
            });
        }
        dialog.show();
    }

    private void showSelectConstellationDialog() {
        ActionSheetDialog dialog =   ActionSheetDialogBuild("请选择");
        for(int i = 0; i < zodiacs.length; i++){
            dialog.addSheetItem(zodiacs[i], ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                   btn_constellation.setText(zodiacs[which -1]);
                    params.put("zodiac",zodiacs[which -1]);
                }
            });
        }
        dialog.show();
    }

    private void showSelectBirthdayDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.time_dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar temp = Calendar.getInstance();
                        temp.set(year, monthOfYear, dayOfMonth);
                        if (temp.after(calender_current)) {
                            ToastUtil.showConsecutiveShort("不能大于当前时间");
                            return;
                        }

                          String birthday =  DateUtils.calendarToStr(temp, DateUtils.yyyyMMDD);
                            if(birthday != null){
                                btn_birthday.setText(birthday);
                                params.put("birthday",birthday);
                            }
                    }
                }, 1980, 00, 01);
        dialog.show();

    }

    private void showSelectIdentifyDialog() {
            ActionSheetDialogBuild("请选择")
                    .addSheetItem("会员", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            btn_identify.setText("会员");
                            params.put("type","0");
                        }
                    })
                    .addSheetItem("教练", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            btn_identify.setText("教练");
                            params.put("type","1");
                        }
                    })
                    .show();
    }

    private boolean verify() {
        nickname = getEditNickname().getText().toString().trim();
        if(TextUtils.isEmpty(nickname)){
            ToastUtil.showConsecutiveShort("请输入昵称");
            return false;
        }
        if(TextUtils.isEmpty(params.get("gender"))){
            ToastUtil.showConsecutiveShort("请选择性别");
            return false;
        }
        if(TextUtils.isEmpty(params.get("province"))){
            ToastUtil.showConsecutiveShort("请选择城市");
            return false;
        }
        params.put("nickname",nickname);

        return true;
    }

    private void showSelectImageDialog() {
         ActionSheetDialogBuild("请选择")
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tempFile = new File("/mnt/sdcard/aidong/", "aidong_pic_" + CameraUtils.createTempPhotoFileName());
                        CameraUtils.startCameraForResult(CompleteUserInfomationActivity.this,  Uri.fromFile(tempFile),Constant.REQUEST_CODE_CAMERA);

                    }
                })
                .addSheetItem("相册", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        tempFile = new File("/mnt/sdcard/aidong/", "aidong_pic_" + CameraUtils.createTempPhotoFileName());
                        CameraUtils.startPhotosForResult(CompleteUserInfomationActivity.this, Constant.REQUEST_CODE_PICTURE);
                    }
                })
                .show();
    }

    private void showSelectGenderDialog() {
        ActionSheetDialogBuild("请选择")
                .addSheetItem("男", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
//                        gender=String.valueOf(which);
                        btn_gender.setText("男");
                        params.put("gender","0");

                    }
                })
                .addSheetItem("女", ActionSheetDialog.SheetItemColor.Black, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
//                        gender=String.valueOf(which);
                        btn_gender.setText("女");
                        params.put("gender","1");
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

    @Override
    public void OnCompletUserInfoCallBack(boolean success) {
        if(success){
            ToastUtil.showConsecutiveShort("更新资料成功");
            finish();
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constant.REQUEST_CODE_SIGN:
                if(data != null){
                    String sign = data.getStringExtra(Constant.PERSION_SIGN);
                    if(!TextUtils.isEmpty(sign)){
                        params.put("signature",sign);
                        btn_sign.setText(sign);
                    }
                }
                break;
            case Constant.REQUEST_CODE_CAMERA:
                if(resultCode == RESULT_OK){
                    CameraUtils.startPhotoZoom(this,Uri.fromFile(tempFile),Uri.fromFile(tempFile),Constant.REQUEST_CODE_CUT);
                }
                break;
            case Constant.REQUEST_CODE_CUT:
                if(resultCode == RESULT_OK&& data != null){
                    Bitmap photo = CameraUtils.sentPicToNext(data,Uri.fromFile(tempFile),this);
                    imgAvatar.setImageBitmap(photo);
                }
                break;
            case Constant.REQUEST_CODE_PICTURE:
                if(resultCode == RESULT_OK&& data != null){
                    CameraUtils.startPhotoZoom(this,data.getData(),Uri.fromFile(tempFile),Constant.REQUEST_CODE_CUT);
                }
                break;
        }
    }
}
