package com.leyuan.aidong.ui.activity.mine.lovecoin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.LoveCoinAdapter;
import com.leyuan.aidong.entity.model.IntegralDetailInfo;
import com.leyuan.aidong.entity.model.UserIntegralInfo;
import com.leyuan.aidong.entity.model.result.BaseResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;

public class LoveCoinActivity extends BaseActivity implements View.OnClickListener {

    private ImageView img_back;
    private TextView txt_use_help;
    private ListView list;
    private Button button_withdraw;

    private View head_view;
    private TextView txt_total_coin;
    private TextView txt_usable_coin;
    private TextView txt_unavailable_coin;
    private RelativeLayout rel_payment_detail;

    private LoveCoinAdapter adapter;
    private Gson gson = new Gson();
    private ArrayList<IntegralDetailInfo> integralInfos;

    private int valueLoveCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_coin);
        initView();
        initData();
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        txt_use_help = (TextView) findViewById(R.id.txt_use_help);
        list = (ListView) findViewById(R.id.list);
        button_withdraw = (Button) findViewById(R.id.button_withdraw);
        head_view = View.inflate(this, R.layout.head_love_coin_activity, null);
        txt_total_coin = (TextView) head_view.findViewById(R.id.txt_total_coin);
        txt_usable_coin = (TextView) head_view.findViewById(R.id.txt_usable_coin);
        txt_unavailable_coin = (TextView) head_view.findViewById(R.id.txt_unavailable_coin);
        rel_payment_detail = (RelativeLayout) head_view.findViewById(R.id.rel_payment_detail);
    }

    private void initData() {
        img_back.setOnClickListener(this);
        txt_use_help.setOnClickListener(this);
        button_withdraw.setOnClickListener(this);
        rel_payment_detail.setOnClickListener(this);
        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromHttp();
    }

    private void getDataFromHttp() {
//        RequestParams params = new RequestParams();
//        params.addBodyParameter("idongId", SharedPreferencesUtils.getInstance(this).get("user"));
//        //        params.addBodyParameter("idongId", "10100");
////        if (TextUtils.equals("http://m1.aidong.me/aidong9", Urls.BASE_URL_TEXT)) {
////            new HttpUtils().send(HttpRequest.HttpMethod.POST,
////                    Urls.BASE_URL_TEST_INTEGRAL + "/getIntegral", params, callback);
////        } else {
//            new HttpUtils().send(HttpRequest.HttpMethod.POST,
//                    Urls.BASE_URL_TEXT + "/getIntegral", params, callback);
////        }

    }

    private RequestCallBack<String> callback = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("httpResponse", "success :" + responseInfo.result);
            try {
                BaseResult<UserIntegralInfo> result = gson.fromJson(responseInfo.result,
                        new TypeToken<BaseResult<UserIntegralInfo>>() {
                        }.getType());
                if (result.getCode() == 1 && result.getResult() != null) {
                    UserIntegralInfo userIntegralInfo = result.getResult();
                    txt_usable_coin.setText("" + userIntegralInfo.getHas());
                    txt_unavailable_coin.setText("" + userIntegralInfo.getProcess());
                    txt_total_coin.setText("" + (userIntegralInfo.getHas() + userIntegralInfo.getProcess()));
                    valueLoveCoin = userIntegralInfo.getHas();
                    integralInfos = userIntegralInfo.getLog();
                    if (integralInfos != null) {
                        adapter.freshData(integralInfos);
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.i("httpResponse", "failure :" + s);
        }
    };

    private void initList() {
        adapter = new LoveCoinAdapter(this);
        list.addHeaderView(head_view);
        View view = new View(this);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        view.setLayoutParams(params);
        list.addFooterView(view);
        list.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_use_help:
                startActivity(new Intent(this, LoveCoinHelpActivity.class));
                break;
            case R.id.button_withdraw:
                Intent withDrawIntent = new Intent(this, WithDrawActivity.class);
                withDrawIntent.putExtra("valueLoveCoin", valueLoveCoin / 10);
                startActivity(withDrawIntent);
                break;
            case R.id.rel_payment_detail:
                startActivity(new Intent(this, PaymentsDetailActivity.class));
                break;
        }
    }

}
