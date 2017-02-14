package com.leyuan.aidong.ui.mine.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingCropOption;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.utils.BoxingFileHelper;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.view.AddressPopupWindow;
import com.leyuan.aidong.widget.ExtendTextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * 修改用户资料
 * Created by song on 2017/2/6.
 */
public class UpdateUserInfoActivity extends BaseActivity implements View.OnClickListener, AddressPopupWindow.OnConfirmAddressListener {
    private static final int REQUEST_CODE = 1024;
    private LinearLayout rootLayout;
    private ImageView ivBack;
    private TextView tvFinish;
    private SimpleDraweeView dvAvatar;
    private ExtendTextView nickname;
    private ExtendTextView gender;
    private ExtendTextView identify;
    private ExtendTextView signature;
    private ExtendTextView address;
    private ExtendTextView birthday;
    private ExtendTextView zodiac;
    private ExtendTextView height;
    private ExtendTextView weight;
    private ExtendTextView bmi;
    private ExtendTextView frequency;

    private ProfileBean profileBean;
    private AddressPopupWindow addressPopupWindow;

    public static void start(Context context, ProfileBean profileBean) {
        Intent starter = new Intent(context, UpdateUserInfoActivity.class);
        starter.putExtra("profileBean",profileBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        if(getIntent() != null){
            profileBean = getIntent().getParcelableExtra("profileBean");
        }

        initView();
        setListener();
    }

    private void initView(){
        rootLayout = (LinearLayout) findViewById(R.id.ll_root);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        dvAvatar = (SimpleDraweeView) findViewById(R.id.dv_avatar);
        nickname = (ExtendTextView) findViewById(R.id.nickname);
        gender = (ExtendTextView) findViewById(R.id.gender);
        identify = (ExtendTextView) findViewById(R.id.identify);
        signature = (ExtendTextView) findViewById(R.id.signature);
        address = (ExtendTextView) findViewById(R.id.address);
        birthday = (ExtendTextView) findViewById(R.id.birthday);
        zodiac = (ExtendTextView) findViewById(R.id.zodiac);
        height = (ExtendTextView) findViewById(R.id.height);
        weight = (ExtendTextView) findViewById(R.id.weight);
        bmi = (ExtendTextView) findViewById(R.id.bmi);
        frequency = (ExtendTextView) findViewById(R.id.frequency);

        nickname.setRightContent(App.mInstance.getUser().getUsername());
        gender.setRightContent(profileBean.getGender());
        identify.setRightContent("健身爱好者");
        signature.setRightContent(profileBean.getSignature());
        address.setRightContent(profileBean.getProvince() + profileBean.getCity() +
                profileBean.getArea());
        birthday.setRightContent(profileBean.getBirthday());
        zodiac.setRightContent(profileBean.getZodiac());
        height.setRightContent(profileBean.getHeight());
        weight.setRightContent(profileBean.getWeight());
        bmi.setRightContent(profileBean.getBmi());
        frequency.setRightContent(profileBean.getFrequency());
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        dvAvatar.setOnClickListener(this);
        gender.setOnClickListener(this);
        identify.setOnClickListener(this);
        signature.setOnClickListener(this);
        address.setOnClickListener(this);
        birthday.setOnClickListener(this);
        height.setOnClickListener(this);
        weight.setOnClickListener(this);
        bmi.setOnClickListener(this);
        frequency.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:

                break;
            case R.id.dv_avatar:
                updateAvatar();
                break;
            case R.id.gender:
                showGenderDialog();
                break;
            case R.id.identify:
                showIdentifyDialog();
                break;
            case R.id.signature:
                showSignatureDialog();
                break;
            case R.id.address:
                showAddressPopupWindow();
                break;
            case R.id.birthday:
                showBirthdayDialog();
                break;
            case R.id.height:
                showHeightDialog();
                break;
            case R.id.weight:
                showWeightDialog();
                break;
            case R.id.bmi:

                break;
            case R.id.frequency:
                showFrequencyDialog();
                break;
            default:
                break;
        }
    }

    private void updateAvatar(){
        String cachePath = BoxingFileHelper.getCacheDir(this);
        if (TextUtils.isEmpty(cachePath)) {
            Toast.makeText(getApplicationContext(), R.string.storage_deny, Toast.LENGTH_SHORT).show();
            return;
        }
        Uri destUri = new Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.jpg", System.currentTimeMillis()))
                .build();
        BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                .withCropOption(new BoxingCropOption(destUri))
                .needCamera();
        Boxing.of(singleCropImgConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_CODE);
    }

    private void showGenderDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_gender)
                .items(R.array.gender)
                .itemsCallbackSingleChoice(0,new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        gender.setRightContent(text.toString());
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void showIdentifyDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_gender)
                .items(R.array.identify)
                .itemsCallbackSingleChoice(0,new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        identify.setRightContent(text.toString());
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void showSignatureDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_signature)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 20)
                .positiveText(R.string.sure)
                .input(getString(R.string.confirm_signature_hint), signature.getText(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        signature.setRightContent(input.toString());
                    }
                })
                .show();
    }

    private void showAddressPopupWindow(){
        if(addressPopupWindow == null){
            addressPopupWindow = new AddressPopupWindow(this);
            addressPopupWindow.setOnConfirmAddressListener(this);
        }
        addressPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
    }

    private int years = 1990;
    private int mothers = 0;
    private int days = 1;
    private Calendar startCalender = Calendar.getInstance();
    private Calendar newCalender = Calendar.getInstance();
    private void showBirthdayDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.MD_Light,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar temp = Calendar.getInstance();
                        temp.set(year, monthOfYear, dayOfMonth);
                        if (temp.after(newCalender)) {
                            Toast.makeText(UpdateUserInfoActivity.this, "不能大于当前时间",Toast.LENGTH_LONG).show();
                        } else {
                            startCalender.set(year, monthOfYear, dayOfMonth);
                            years = year;
                            mothers = monthOfYear;
                            days = dayOfMonth;
                            birthday.setRightContent(years +"年" + (mothers + 1) + "月" + days + "日");
                        }
                    }
                }, years, mothers, days);
        dialog.show();
    }

    private void showHeightDialog(){
        new MaterialDialog.Builder(this).title(R.string.confirm_height)
                .items(R.array.height)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        height.setRightContent(text.toString());
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    private void showWeightDialog(){
        new MaterialDialog.Builder(this).title(R.string.confirm_weight)
                .items(R.array.weight)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        weight.setRightContent(text.toString());
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    private void showFrequencyDialog(){
        new MaterialDialog.Builder(this).title(R.string.confirm_frequency)
                .items(R.array.frequency)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        frequency.setRightContent(text.toString());
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void onAddressConfirm(String province, String city, String area) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<BaseMedia> medias = Boxing.getResult(data);
            if(medias != null && !medias.isEmpty()){
                dvAvatar.setImageURI("file://" + medias.get(0).getPath());
               // BoxingMediaLoader.getInstance().displayThumbnail(dvAvatar, medias.get(0).getPath(), 70,70);
            }
        }
    }

}
