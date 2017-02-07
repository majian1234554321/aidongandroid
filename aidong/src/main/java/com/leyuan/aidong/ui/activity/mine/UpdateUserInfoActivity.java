package com.leyuan.aidong.ui.activity.mine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ProfileBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.adapter.StringAdapter;
import com.leyuan.aidong.ui.activity.mine.view.AddressPopupWindow;
import com.leyuan.aidong.widget.customview.ExtendTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 修改用户资料
 * Created by song on 2017/2/6.
 */
public class UpdateUserInfoActivity extends BaseActivity implements View.OnClickListener, AddressPopupWindow.OnConfirmAddressListener {
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

                break;
            case R.id.gender:
                showGenderDialog();
                break;
            case R.id.identify:
                showIdentifyDialog();
                break;
            case R.id.signature:
                startActivity(new Intent(this,UpdateSignatureActivity.class));
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

    private void showGenderDialog() {
        View view = View.inflate(this,R.layout.dialog_gender,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }

    private void showIdentifyDialog() {
        View view = View.inflate(this,R.layout.dialog_identify,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }

    private void showAddressPopupWindow(){
        if(addressPopupWindow == null){
            addressPopupWindow = new AddressPopupWindow(this);
            addressPopupWindow.setOnConfirmAddressListener(this);
        }
        addressPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
    }

    private int years = 1990, mothers = 00, days = 01;
    private Calendar start_calender = Calendar.getInstance();
    private Calendar new_calenser = Calendar.getInstance();
    private void showBirthdayDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.time_dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar temp = Calendar.getInstance();
                        temp.set(year, monthOfYear, dayOfMonth);


                        if (temp.after(new_calenser)) {
                            Toast.makeText(UpdateUserInfoActivity.this, "不能大于当前时间",Toast.LENGTH_LONG).show();
                        } else {
                            start_calender.set(year, monthOfYear, dayOfMonth);
                            years = year;
                            mothers = monthOfYear;
                            days = dayOfMonth;
                        }
                    }
                }, years, mothers, days);
        dialog.show();
    }

    private void showHeightDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_height,null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter stringAdapter = new StringAdapter();
        stringAdapter.setData(generateHeightData());
        recyclerView.setAdapter(stringAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }

    private void showWeightDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_weight,null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter stringAdapter = new StringAdapter();
        stringAdapter.setData(generateWeightData());
        recyclerView.setAdapter(stringAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }

    private void showFrequencyDialog(){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_frequency,null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StringAdapter stringAdapter = new StringAdapter();
        stringAdapter.setData(generateFrequencyData());
        recyclerView.setAdapter(stringAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }

    @Override
    public void onAddressConfirm(String province, String city, String area) {

    }

    private List<String> generateHeightData(){
        List<String> height = new ArrayList<>();
        for (int i = 150; i < 200; i++) {
            height.add(String.valueOf(i));
        }
        return height;
    }

    private List<String> generateWeightData(){
        List<String> weight = new ArrayList<>();
        for (int i = 40; i < 100; i++) {
            weight.add(String.valueOf(i));
        }
        return weight;
    }

    private List<String> generateFrequencyData(){
        List<String> frequency = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            frequency.add(i +"周/每次");
        }
        return frequency;
    }
}
