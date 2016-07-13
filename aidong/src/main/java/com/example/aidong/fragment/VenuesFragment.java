package com.example.aidong.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.venues.VenuesDetailsActivity;
import com.example.aidong.adapter.ListViewPinPaiAdapter;
import com.example.aidong.adapter.VenuesAdapter;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.result.VenuesInfo;
import com.example.aidong.utils.PullToRefreshUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-场馆
 */
public class VenuesFragment extends BaseFragment implements OnRefreshListener2<ListView>, View.OnClickListener,IHttpCallback {

    private static final int PULL_DOWN_TO_REFRESH = 1;
    private static final int PULL_UP_LOAD_MORE = 2;

    private int page = 1;
    private VenuesAdapter adapter;
    private PullToRefreshListView listView;
    private List<VenuesInfo> venueList = new ArrayList<>();

    private PopupWindow pop;
    private LinearLayout linearLayout;
    private TextView tvLogo, tvStore, tvSelect;
    private List<String> popList = new ArrayList<>();

    private String city_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.arena_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        tvLogo = (TextView) getView().findViewById(R.id.tv_logo);
        tvStore = (TextView) getView().findViewById(R.id.tv_store);
        tvSelect = (TextView) getView().findViewById(R.id.tv_select);
        linearLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);
        listView = (PullToRefreshListView) getView().findViewById(R.id.list);
        tvLogo.setOnClickListener(this);
        tvStore.setOnClickListener(this);
        tvSelect.setOnClickListener(this);

        PullToRefreshUtil.setPullListView(listView, Mode.BOTH);
        listView.setOnRefreshListener(this);

        if (null == city_id || city_id.equals("")) {
            city_id = "1211000000";
        }

        listView.setOnRefreshListener(this);
        adapter = new VenuesAdapter(getActivity());
        //adapter.setList(venueList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), VenuesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        getVenues();
    }

    private  void getVenues(){


       // RequestParams params = new RequestParams();
       //params.addQueryStringParameter("areaId", city_id);
        JSONObject object = new JSONObject();
        try{
            object.put("areaId",city_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        addTask(this,new IHttpTask("http://m1.aidong.me/aidong9/getmststoresifting.action",object,VenuesInfo.class), HttpConfig.POST,PULL_DOWN_TO_REFRESH);

       /* MyHttpUtils http = new MyHttpUtils();
        http.send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/getmststoresifting.action", params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        try {

                            Logger.i("venues, city_id = " + responseInfo.result);
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            String code = jsonObject.optString("code");
                            String msg = jsonObject.optString("msg");

                            if (code.equals("1")) {
                                JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                                if (circleList != null) {
                                    circleList.clear();
                                }
                                JSONArray jsonArray = jsonObject1.optJSONArray("regions");
                                if (jsonArray.length() > 0) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        CircleInfo circleInfo = new CircleInfo();
                                        JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                                        circleInfo.setAreaId(jsonObject2.optString("areaId"));
                                        circleInfo.setAreaName(jsonObject2.optString("areaName"));
                                        JSONArray jsonArray1 = jsonObject2.optJSONArray("district");
                                        List<CircleXXInfo> circleXXList = new ArrayList<CircleXXInfo>();
                                        if (jsonArray1.length() > 0) {
                                            for (int ii = 0; ii < jsonArray1.length(); ii++) {
                                                JSONObject jsonObject3 = jsonArray1.optJSONObject(ii);
                                                CircleXXInfo circleXXInfo = new CircleXXInfo();
                                                circleXXInfo.setAreaId(jsonObject3.optString("areaId"));
                                                circleXXInfo.setAreaName(jsonObject3.optString("areaName"));
                                                circleXXList.add(circleXXInfo);
                                            }
                                        }
                                        circleInfo.setDistrict(circleXXList);
                                        circleList.add(circleInfo);
                                    }
                                }
                                if (brandList != null) {
                                    brandList.clear();
                                }
                                JSONArray jsonArray1 = jsonObject1.optJSONArray("brand");
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    BrandInfo brandInfo = new BrandInfo();
                                    brandInfo.parseJson(jsonArray1.optJSONObject(i));
                                    brandList.add(brandInfo);
                                }
                                if (teseList != null) {
                                    teseList.clear();
                                }
                                JSONArray jsonArray2 = jsonObject1.optJSONArray("feature");
                                for (int i = 0; i < jsonArray2.length(); i++) {
                                    FeatureInfo featureInfo = new FeatureInfo();
                                    featureInfo.parseJson(jsonArray2.optJSONObject(i));
                                    teseList.add(featureInfo);
                                }
                                txt_venues_tap_1.setText(brandList.get(0).getChName());
                                txt_venues_tap_2.setText(circleList.get(0).getDistrict().get(0).getAreaName());
                                txt_venues_tap_3.setText(teseList.get(0).getChName());
                                if (brandList.size() > 0) {
                                    brandId = brandList.get(0).getBrandId();
                                } else {
                                    brandId = "";
                                }

                                if (circleList.size() > 0) {
                                    regionId = circleList.get(0).getAreaId();
                                } else {
                                    regionId = "";
                                }

                                if (circlexxList.size() > 0) {
                                    districtId = circlexxList.get(0).getAreaId();
                                } else {
                                    districtId = "";
                                }

                                if (teseList.size() > 0) {
                                    feature = teseList.get(0).getCode();
                                } else {
                                    feature = "";
                                }

                                page = 1;
                                list();

                            } else {
                                ToastTools.showToast(getActivity(), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(HttpException error, String msg) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        ToastTools.showToast(getActivity(), getResources().getString(R.string.failure_text));
                    }
                }

        );*/
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
        //stopLoading();
        switch (requestCode) {
            case PULL_DOWN_TO_REFRESH:
                Log.d("111",data.toString());
               /* FoundDynamicResult result_down = (FoundDynamicResult) data;
                if (result_down.getCode() ==1) {
                    if (result_down.getData() != null) {
                        if (result_down.getData().getDynamic() !=null) {
                            date.clear();
                            date.addAll(result_down.getData().getDynamic());
                            adapter.freshData(date);
                        }
                    }
                }*/
                break;

            case PULL_UP_LOAD_MORE:
               /* FoundDynamicResult result_up = (FoundDynamicResult) data;
                if (result_up.getCode() ==1) {
                    if (result_up.getData() != null) {
                        if (result_up.getData().getDynamic() !=null) {
                            date.addAll(result_up.getData().getDynamic());
                            adapter.freshData(date);
                        }
                    }
                }*/
                break;
            default:
                break;
        }
    }

    @Override
    public void onError(String reason, int requestCode) {
        //stopLoading();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        ToastUtil.show("下拉刷新",getActivity());
        //listView.onRefreshComplete();
    }


    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        ToastUtil.show("上拉加载",getActivity());
        //listView.onRefreshComplete();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_logo:
                foodNamePop();
                break;
            case R.id.tv_store:
                storePop();
                break;
            case R.id.tv_select:
                foodNamePop();
                break;
        }
    }

    private void getdate() {
        for (int i = 0; i < 10; i++) {
            popList.add(i + "");
        }
    }

    private void foodNamePop() {
        getdate();
        if (pop == null || !pop.isShowing()) {

            View layout = getActivity().getLayoutInflater().inflate(R.layout.layout_filter_popup, null);
            final ListView listView = (ListView) layout.findViewById(R.id.listView_filter_area);
            final ListViewPinPaiAdapter adapter = new ListViewPinPaiAdapter(getActivity(), popList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pop.dismiss();
                }
            });
            ImageView cancleImg = (ImageView) layout.findViewById(R.id.img_pop_down);
            cancleImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop.dismiss();
                }
            });
            pop = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            pop.setBackgroundDrawable(new BitmapDrawable());

            pop.setOutsideTouchable(true);

            pop.setFocusable(true);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
            pop.showAsDropDown(linearLayout, 0, 2);
        }
    }

    private void storePop() {
        if (pop == null || !pop.isShowing()) {
            View layout = getActivity().getLayoutInflater().inflate(R.layout.pop_store, null);
            final ListView leftListView = (ListView) layout.findViewById(R.id.lv_left);
            final ListView rightListView = (ListView) layout.findViewById(R.id.lv_right);
            final ListViewPinPaiAdapter adapter = new ListViewPinPaiAdapter(getActivity(), popList);
            leftListView.setAdapter(adapter);

            leftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pop.dismiss();
                }
            });

            rightListView.setAdapter(adapter);
            rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pop.dismiss();
                }
            });

            /*ImageView cancleImg = (ImageView) layout.findViewById(R.id.img_pop_down);
            cancleImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPop.dismiss();
                }
            });*/
            pop = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pop.setBackgroundDrawable(new BitmapDrawable());
            pop.setOutsideTouchable(true);
            pop.setFocusable(true);
            pop.showAsDropDown(linearLayout, 0, 2);
            pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });

        }
    }
}
