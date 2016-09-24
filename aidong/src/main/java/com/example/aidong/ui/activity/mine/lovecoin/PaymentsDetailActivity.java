package com.example.aidong.ui.activity.mine.lovecoin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.adapter.LoveCoinAdapter;
import com.example.aidong.entity.model.IntegralDetailInfo;
import com.example.aidong.entity.model.UserIntegralInfo;
import com.example.aidong.entity.model.result.BaseResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.util.ArrayList;

public class PaymentsDetailActivity extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    private ImageView img_back;
    private PullToRefreshListView listView;
    private View emptyView;

    private LoveCoinAdapter adapter;
    private Gson gson = new Gson();
    private int page =1;
    private ArrayList<IntegralDetailInfo> integralInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_detail);
        initView();
        initData();
        getDataFromHttp(page, callbackRefresh);
    }

    private void initView() {
        img_back = (ImageView) findViewById(R.id.img_back);
        listView = (PullToRefreshListView) findViewById(R.id.list_refresh);
        emptyView = View.inflate(this, R.layout.layout_default_emptey,null);
    }

    private void initData() {
        adapter = new LoveCoinAdapter(this);
        img_back.setOnClickListener(this);
        listView.setEmptyView(emptyView);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(this);
    }

    private void getDataFromHttp(int page, RequestCallBack callback) {
//        RequestParams params = new RequestParams();
//        //        params.addBodyParameter("idongId", "10100");
//        params.addBodyParameter("idongId", SharedPreferencesUtils.getInstance(this).get("user"));
//        params.addBodyParameter("page", String.valueOf(page));
////        if (TextUtils.equals("http://m1.aidong.me/aidong9", Urls.BASE_URL_TEXT)) {
////            new HttpUtils().send(HttpRequest.HttpMethod.POST,
////                    Urls.BASE_URL_TEST_INTEGRAL + "/getIntegralLog", params, callback);
////        } else {
//            new HttpUtils().send(HttpRequest.HttpMethod.POST,
//                    Urls.BASE_URL_TEXT + "/getIntegralLog", params, callback);
////        }

    }

    private RequestCallBack<String> callbackRefresh = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("httpResponse", "success :" + responseInfo.result);
            listView.onRefreshComplete();
            try {
                BaseResult<UserIntegralInfo> result = gson.fromJson(responseInfo.result,
                        new TypeToken<BaseResult<UserIntegralInfo>>() {
                        }.getType());

                if (result.getCode() == 1 && result.getResult() != null) {
                    integralInfos = result.getResult().getLog();
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
            listView.onRefreshComplete();
        }
    };

    private RequestCallBack<String> callbackMore = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Log.i("httpResponse", "success :" + responseInfo.result);
            listView.onRefreshComplete();
            try {
                BaseResult<UserIntegralInfo> result = gson.fromJson(responseInfo.result,
                        new TypeToken<BaseResult<UserIntegralInfo>>() {
                        }.getType());
                if (result.getCode() == 1 && result.getResult() != null) {
                    integralInfos = result.getResult().getLog();
                    if (integralInfos != null) {
                        adapter.addData(integralInfos);
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            listView.onRefreshComplete();
            Log.i("httpResponse", "failure :" + s);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 1;
        getDataFromHttp(page, callbackRefresh);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        getDataFromHttp(page, callbackMore);
    }
}
