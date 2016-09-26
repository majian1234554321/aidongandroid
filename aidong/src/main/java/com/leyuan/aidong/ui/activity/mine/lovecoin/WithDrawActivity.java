package com.leyuan.aidong.ui.activity.mine.lovecoin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.result.BaseResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class WithDrawActivity extends BaseActivity implements View.OnClickListener {
    private ImageView img_back;
    private EditText edit_account;
    private EditText edit_name;
    private EditText edit_money;
    private TextView txt_valid_money;
    private Button button_withdraw;

    private String account;
    private String name;
    private String money;

    private int valueLoveCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        valueLoveCoin = getIntent().getIntExtra("valueLoveCoin", 0);
        initView();
        initData();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        edit_account = (EditText) findViewById(R.id.edit_account);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_money = (EditText) findViewById(R.id.edit_money);
        txt_valid_money = (TextView) findViewById(R.id.txt_valid_money);
        button_withdraw = (Button) findViewById(R.id.button_withdraw);
    }

    private void initData() {
        txt_valid_money.setText("" + valueLoveCoin);
        img_back.setOnClickListener(this);
        button_withdraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.button_withdraw:
                if (verify()) {
                    // invoke http interface here
                    postDataToService(account, name, money);
                }
                break;
        }
    }

    private void postDataToService(String account, String name, String money) {
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("idongId", SharedPreferencesUtils.getInstance(this).get("user"));
//        //        params.addBodyParameter("idongId", "10100");
//        params.addBodyParameter("amount", money);
//        params.addBodyParameter("account", account);
//        params.addBodyParameter("name", name);
//
////        if (TextUtils.equals("http://m1.aidong.me/aidong9", Urls.BASE_URL_TEXT)) {
////            new HttpUtils().send(HttpRequest.HttpMethod.POST,
////                    Urls.BASE_URL_TEST_INTEGRAL + "/withdraw", params, callback);
////        } else {
//            new HttpUtils().send(HttpRequest.HttpMethod.POST,
//                    Urls.BASE_URL_TEXT + "/withdraw", params, callback);
////        }
    }

    private RequestCallBack<String> callback = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("httpResponse", "success :" + responseInfo.result);
            try {
                BaseResult result = new Gson().fromJson(responseInfo.result, BaseResult.class);
                if (result.getCode() == 1) {
                    showDialog("提现申请成功，我们会尽快进行处理", 1);
                } else {
                    showDialog("提现失败," + result.getMessage(), 0);
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    private void showDialog(String message, final int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WithDrawActivity.this);
        builder.setTitle(message);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (code == 1) {
                    WithDrawActivity.this.finish();
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private boolean verify() {
        account = edit_account.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            edit_account.requestFocus();
            edit_account.setError("请输入正确的支付宝账号");
            return false;
        }

        name = edit_name.getText().toString().trim();
        if (TextUtils.isEmpty(name) ) {
            edit_name.requestFocus();
            edit_name.setError("请输入正确的支付宝姓名");
            return false;
        }

        if (name.getBytes().length < 4) {
            edit_name.requestFocus();
            edit_name.setError("请输入正确的支付宝姓名");
            return false;
        }

        money = edit_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            edit_money.requestFocus();
            edit_money.setError("请输入提现整数金额");
            return false;
        }

        try {
            int inputMoney = Integer.parseInt(money);
            if (inputMoney > valueLoveCoin) {
                ToastUtil.show("可提现余额不足，请重新输入",this);
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return true;
    }
}
