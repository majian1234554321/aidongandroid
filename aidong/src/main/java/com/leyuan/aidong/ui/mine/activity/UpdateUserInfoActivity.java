package com.leyuan.aidong.ui.mine.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingCropOption;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing.utils.BoxingFileHelper;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.view.SelectAddressDialog;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UpdateUserInfoActivityView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadQiNiuManager;
import com.leyuan.aidong.widget.ExtendTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.leyuan.aidong.R.id.zodiac;

/**
 * 修改用户资料
 * Created by song on 2017/2/6.
 */
public class UpdateUserInfoActivity extends BaseActivity implements UpdateUserInfoActivityView,
        View.OnClickListener, SelectAddressDialog.OnConfirmAddressListener {
    private static final int REQUEST_CODE = 1024;
    private ImageView ivBack;
    private TextView tvFinish;
    private ImageView ivAvatar;
    private ExtendTextView tvNickname;
    private ExtendTextView tvGender;
    private ExtendTextView tvIdentify;
    private ExtendTextView tvSignature;
    private ExtendTextView tvAddress;
    private ExtendTextView tvBirthday;
    private ExtendTextView tvZodiac;
    private ExtendTextView tvHeight;
    private ExtendTextView tvWeight;
    private ExtendTextView tvBmi;
    private ExtendTextView tvFrequency;

    private ProfileBean profileBean;
    private SelectAddressDialog addressDialog;
    private String avatarPath;
    private String avatarUrl;
    private String province;
    private String city;
    private String area;
    private String birthday;
    private String gender;
    private String height;
    private String weight;
    private String frequency;
    private String signture;
    private String name;

    private UserInfoPresent userInfoPresent;
    private DecimalFormat df;

    public static void start(Context context, ProfileBean profileBean) {
        Intent starter = new Intent(context, UpdateUserInfoActivity.class);
        starter.putExtra("profileBean", profileBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        userInfoPresent = new UserInfoPresentImpl(this, this);
        df = new java.text.DecimalFormat("#.00");
        if (getIntent() != null) {
            profileBean = getIntent().getParcelableExtra("profileBean");
        }
        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        ivAvatar = (ImageView) findViewById(R.id.dv_avatar);
        tvNickname = (ExtendTextView) findViewById(R.id.nickname);
        tvGender = (ExtendTextView) findViewById(R.id.gender);
        tvIdentify = (ExtendTextView) findViewById(R.id.identify);
        tvSignature = (ExtendTextView) findViewById(R.id.signature);
        tvAddress = (ExtendTextView) findViewById(R.id.address);
        tvBirthday = (ExtendTextView) findViewById(R.id.tv_birthday);
        tvZodiac = (ExtendTextView) findViewById(zodiac);
        tvHeight = (ExtendTextView) findViewById(R.id.height);
        tvWeight = (ExtendTextView) findViewById(R.id.weight);
        tvBmi = (ExtendTextView) findViewById(R.id.bmi);
        tvFrequency = (ExtendTextView) findViewById(R.id.frequency);
        avatarUrl = profileBean.getAvatar();
        GlideLoader.getInstance().displayCircleImage(avatarUrl, ivAvatar);
        tvNickname.setRightContent(profileBean.getName() == null ? "请输入昵称" : profileBean.getName());
        tvGender.setRightContent("0".equals(profileBean.getGender()) ? "男" : "女");
        tvIdentify.setRightContent("coach".equals(profileBean.getUserType()) ? "教练" : "健身爱好者");
        tvSignature.setRightContent(profileBean.getSignature());
        tvAddress.setRightContent(profileBean.getProvince() + profileBean.getCity() + profileBean.getArea());
        tvBirthday.setRightContent(profileBean.getBirthday());
        try {
            String birthday = profileBean.getBirthday();
            if(!TextUtils.isEmpty(birthday)) {
                String mothers = birthday.substring(birthday.indexOf("-") + 1, birthday.lastIndexOf("-"));
                String days = birthday.substring(birthday.lastIndexOf("-") + 1);
                tvZodiac.setRightContent(Utils.getConstellation(FormatUtil.parseInt(mothers), FormatUtil.parseInt(days)));
            }else {
                tvZodiac.setRightContent("");
            }
        }catch (Exception e){
            e.printStackTrace();
            tvZodiac.setRightContent("");
        }
        tvHeight.setRightContent(!TextUtils.isEmpty(profileBean.getHeight())
                ? profileBean.getHeight() + "cm" : "");
        tvWeight.setRightContent(!TextUtils.isEmpty(profileBean.getWeight())
                ? profileBean.getWeight() + "kg" : "");
        tvFrequency.setRightContent(!TextUtils.isEmpty(profileBean.getFrequency())
                ? profileBean.getFrequency()+"周/次" :"");
        DecimalFormat df = new java.text.DecimalFormat("#.00");
        tvBmi.setRightContent(!TextUtils.isEmpty(profileBean.getHeight())&& !TextUtils.isEmpty(profileBean.getWeight())
                ? df.format(Utils.calBMI(FormatUtil.parseFloat(profileBean.getWeight()),
                FormatUtil.parseFloat(profileBean.getHeight())/100)):"");
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvNickname.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        tvSignature.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvHeight.setOnClickListener(this);
        tvWeight.setOnClickListener(this);
        tvBmi.setOnClickListener(this);
        tvFrequency.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                if (!TextUtils.isEmpty(avatarPath)) {
                    uploadToQiNiu();
                } else {
                    uploadToServer(null);
                }
                break;
            case R.id.dv_avatar:
                selectAvatar();
                break;
            case R.id.nickname:
                showNicknameDialog();
                break;
            case R.id.gender:
                showGenderDialog();
                break;
            case R.id.signature:
                showSignatureDialog();
                break;
            case R.id.address:
                showAddressDialog();
                break;
            case R.id.tv_birthday:
                showBirthdayDialog();
                break;
            case R.id.height:
                showHeightDialog();
                break;
            case R.id.weight:
                showWeightDialog();
                break;
            case R.id.frequency:
                showFrequencyDialog();
                break;
            default:
                break;
        }
    }

    private void showNicknameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_nickname)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 12)
                .positiveText(R.string.sure)
                .input(getString(R.string.confirm_nickname), tvNickname.getText(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        name = input.toString();
                        tvNickname.setRightContent(input.toString());
                    }
                })
                .show();
    }

    private void uploadToQiNiu() {
        UploadQiNiuManager.getInstance().uploadSingleImage(avatarPath, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                if (urls != null && !urls.isEmpty()) {
                    String url = urls.get(0);
                    avatarUrl = url.substring(url.indexOf("/") + 1);
                    uploadToServer(avatarUrl);
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(UpdateUserInfoActivity.this, "修改失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadToServer(String avatarUrl) {
        userInfoPresent.updateUserInfo(name, avatarUrl, gender, birthday,
                signture, province, city, area, height, weight, frequency);
    }

    @Override
    public void updateResult(boolean success) {
        if (success) {
            Toast.makeText(UpdateUserInfoActivity.this, "修改成功", Toast.LENGTH_LONG).show();
            setResult(RESULT_OK,null);
            finish();
        } else {
            Toast.makeText(UpdateUserInfoActivity.this, "修改失败", Toast.LENGTH_LONG).show();
        }
    }

    private void selectAvatar() {
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
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        tvGender.setRightContent(text.toString());
                        gender = (which == 0) ? "0" : "1";
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void showSignatureDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_signature)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 20)
                .positiveText(R.string.sure)
                .input(getString(R.string.confirm_signature_hint), tvSignature.getText(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        signture = input.toString();
                        tvSignature.setRightContent(input.toString());
                    }
                })
                .show();
    }

    private void showAddressDialog() {
        if (addressDialog == null) {
            addressDialog = new SelectAddressDialog(this);
            addressDialog.setOnConfirmAddressListener(this);
        }
        addressDialog.show();
    }

    private int years = 1990;
    private int mothers = 0;
    private int days = 1;
    private Calendar startCalender = Calendar.getInstance();
    private Calendar newCalender = Calendar.getInstance();

    private void showBirthdayDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppTheme_AppDate,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar temp = Calendar.getInstance();
                        temp.set(year, monthOfYear, dayOfMonth);
                        if (temp.after(newCalender)) {
                            Toast.makeText(UpdateUserInfoActivity.this, "不能大于当前时间", Toast.LENGTH_LONG).show();
                        } else {
                            startCalender.set(year, monthOfYear, dayOfMonth);
                            years = year;
                            mothers = monthOfYear;
                            days = dayOfMonth;
                            tvBirthday.setRightContent(years + "-" + (mothers + 1) + "-" + days);
                            tvZodiac.setRightContent(Utils.getConstellation(mothers + 1, days));
                            birthday = years +"-"+(mothers + 1)+"-"+days;
                        }
                    }
                }, years, mothers, days);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showHeightDialog() {
        new MaterialDialog.Builder(this).title(R.string.confirm_height)
                .items(generateHeightData())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        height = text.toString();
                        tvHeight.setRightContent(text + "cm");
                        setBMI();
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    private void showWeightDialog() {
        new MaterialDialog.Builder(this).title(R.string.confirm_weight)
                .items(generateWeightData())
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        weight = text.toString();
                        tvWeight.setRightContent(text + "kg");
                        setBMI();
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    private void showFrequencyDialog() {
        new MaterialDialog.Builder(this).title(R.string.confirm_frequency)
                .items(R.array.frequency)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        frequency = text.toString().substring(0,text.toString().lastIndexOf("次"));
                        tvFrequency.setRightContent(text.toString());
                    }
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    @Override
    public void onAddressConfirm(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.area = area;
        StringBuilder sb = new StringBuilder();
        sb.append(province).append(city).append(area);
        tvAddress.setRightContent(sb.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<BaseMedia> medias = Boxing.getResult(data);
            if (medias != null && !medias.isEmpty()) {
                avatarPath = medias.get(0).getPath();
                GlideLoader.getInstance().displayCircleImage("file://" + avatarPath, ivAvatar);
            }
        }
    }

    private List<String> generateHeightData() {
        List<String> height = new ArrayList<>();
        for (int i = 150; i < 200; i++) {
            height.add(String.valueOf(i));
        }
        return height;
    }

    private List<String> generateWeightData() {
        List<String> weight = new ArrayList<>();
        for (int i = 40; i < 100; i++) {
            weight.add(String.valueOf(i));
        }
        return weight;
    }

    private void setBMI() {
        if (FormatUtil.parseFloat(height) != 0f && FormatUtil.parseFloat(weight) != 0f) {
            tvBmi.setRightContent(df.format(Utils.calBMI(FormatUtil.parseFloat(weight),
                    FormatUtil.parseFloat(height)/100)));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresent.release();
        userInfoPresent = null;
        profileBean = null;
    }
}
