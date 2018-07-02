package com.example.aidong.ui.mine.activity.account;

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
import com.example.aidong.R;
import com.example.aidong .entity.ProfileBean;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingCropOption;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.utils.BoxingFileHelper;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.view.SelectAddressDialog;
import com.example.aidong .ui.mvp.presenter.UserInfoPresent;
import com.example.aidong .ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.example.aidong .ui.mvp.view.UpdateUserInfoActivityView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.Utils;
import com.example.aidong .utils.qiniu.IQiNiuCallback;
import com.example.aidong .utils.qiniu.UploadToQiNiuManager;
import com.example.aidong .widget.ExtendTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.aidong.R.id.birthday;

/**
 * 修改用户资料
 * Created by song on 2017/2/6.
 */
public class CompleteUserInfoActivity extends BaseActivity implements UpdateUserInfoActivityView, View.OnClickListener, SelectAddressDialog.OnConfirmAddressListener {
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

    private UserInfoPresent userInfoPresent;

    public static void start(Context context, ProfileBean profileBean) {
        Intent starter = new Intent(context, CompleteUserInfoActivity.class);
        starter.putExtra("profileBean", profileBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_info);
        userInfoPresent = new UserInfoPresentImpl(this, this);
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
        tvBirthday = (ExtendTextView) findViewById(birthday);
        tvZodiac = (ExtendTextView) findViewById(R.id.zodiac);
        tvHeight = (ExtendTextView) findViewById(R.id.height);
        tvWeight = (ExtendTextView) findViewById(R.id.weight);
        tvBmi = (ExtendTextView) findViewById(R.id.bmi);
        tvFrequency = (ExtendTextView) findViewById(R.id.frequency);
        tvNickname.setRightContent(profileBean.getName() == null ? "请输入昵称" : profileBean.getName());
        tvGender.setRightContent(profileBean.getGender());
        tvIdentify.setRightContent("健身爱好者");
        tvSignature.setRightContent(profileBean.getSignature());
        tvAddress.setRightContent("请选择城市");
        tvBirthday.setRightContent(profileBean.getBirthday());
        tvZodiac.setRightContent(profileBean.getZodiac());
        tvHeight.setRightContent(profileBean.getHeight());
        tvWeight.setRightContent(profileBean.getWeight());
        tvBmi.setRightContent("0");
        tvFrequency.setRightContent(profileBean.getFrequency());
        avatarUrl = profileBean.getAvatar();
        GlideLoader.getInstance().displayRoundAvatarImage(avatarUrl, ivAvatar);

        weight = FormatUtil.parseFloat(profileBean.getWeight());
        height = FormatUtil.parseFloat(profileBean.getHeight());
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvNickname.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        tvGender.setOnClickListener(this);
        tvIdentify.setOnClickListener(this);
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
                if (verify()) {
                    if (TextUtils.isEmpty(avatarPath)) {
                        uploadToServer(null);
                    } else {
                        uploadToQiNiu();
                    }
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
            case R.id.identify:
                showIdentifyDialog();
                break;
            case R.id.signature:
                showSignatureDialog();
                break;
            case R.id.address:
                showAddressDialog();
                break;
            case birthday:
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

    private boolean verify() {


        if (TextUtils.equals(tvNickname.getText().trim(), getResources().getString(R.string.please_input_nickname))
                || TextUtils.isEmpty(tvNickname.getText().trim())) {
            ToastGlobal.showShort(R.string.please_input_nickname);
            return false;
        }
        if (TextUtils.isEmpty(tvGender.getText().trim())) {
            ToastGlobal.showShort("请选择性别");
            return false;
        }
        if (TextUtils.isEmpty(city)) {
            ToastGlobal.showShort("请选择城市");
            return false;
        }

        return true;
    }

    private void showNicknameDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_nickname)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
                        InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                .inputRange(1, 12)
                .positiveText(R.string.sure)
                .input(getString(R.string.confirm_nickname), null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        tvNickname.setRightContent(input.toString());
                    }
                })
                .show();
    }

    private void uploadToQiNiu() {
        UploadToQiNiuManager.getInstance().uploadSingleImage(avatarPath, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                if (urls != null && !urls.isEmpty()) {
                    avatarUrl = urls.get(0);
                    uploadToServer(avatarUrl);
                }
            }

            @Override
            public void onFail() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CompleteUserInfoActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }

    private void uploadToServer(String avatarUrl) {
        userInfoPresent.updateUserInfo(tvNickname.getText(), avatarUrl, tvGender.getText(), tvBirthday.getText(), tvSignature.getText(),
                province, city, area, tvHeight.getText(), tvWeight.getText(), tvFrequency.getText());
    }

    @Override
    public void updateResult(boolean success) {
        if (success) {
            Toast.makeText(CompleteUserInfoActivity.this, "提交成功", Toast.LENGTH_LONG).show();
            sendBroadcast(new Intent(Constant.BROADCAST_ACTION_REGISTER_SUCCESS));

            finish();
        } else {
            Toast.makeText(CompleteUserInfoActivity.this, "提交失败", Toast.LENGTH_LONG).show();
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
        BoxingCropOption boxingCropOption = new BoxingCropOption(destUri);
        boxingCropOption.aspectRatio(1, 1);
        BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                .withCropOption(boxingCropOption)
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
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void showIdentifyDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_identify)
                .items(R.array.identify)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        tvIdentify.setRightContent(text.toString());
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
                            Toast.makeText(CompleteUserInfoActivity.this, "不能大于当前时间", Toast.LENGTH_LONG).show();
                        } else {
                            startCalender.set(year, monthOfYear, dayOfMonth);
                            years = year;
                            mothers = monthOfYear;
                            days = dayOfMonth;
                            tvBirthday.setRightContent(years + "-" + (mothers + 1) + "-" + days);
//                            tvBirthday.setRightContent(years + "年" + (mothers + 1) + "月" + days + "日");
                            tvZodiac.setRightContent(Utils.getConstellation(mothers + 1, days));
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
                        height = FormatUtil.parseFloat(text.toString()) / 100;
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
                        weight = FormatUtil.parseFloat(text.toString());
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
                GlideLoader.getInstance().displayRoundAvatarImage("file://" + avatarPath, ivAvatar);
            }
        }
    }

    private List<String> generateHeightData() {
        List<String> height = new ArrayList<>();
        for (int i = 150; i < 250; i++) {
            height.add(String.valueOf(i));
        }
        return height;
    }

    private List<String> generateWeightData() {
        List<String> weight = new ArrayList<>();
        for (int i = 40; i < 200; i++) {
            weight.add(String.valueOf(i));
        }
        return weight;
    }

    private float height;
    private float weight;

    private void setBMI() {
        if (height != 0f && weight != 0f) {
            tvBmi.setRightContent(String.format(Locale.US,"%.2f", Utils.calBMI(weight, height)));
//            String.valueOf(Utils.calBMI(weight, height)));
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
