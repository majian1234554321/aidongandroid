package com.example.aidong.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 显示地址弹框基类
 * 此基类主要用来读取本地省市县json信息
 * Created by song on 2016/9/20.
 */
public class BaseAddressPopupWindow extends BasePopupWindow{
    private Context context;

    protected String[] provinceData; // 省份
    protected String[] citiesData;   // 城市
    protected String[] areaData;     // 地区

    protected Map<String, String[]> citiesDataMap = new HashMap<>();  // 存储省对应的所有市
    protected Map<String, String[]> areaDataMap = new HashMap<>();    // 存储市对应的所有区

    protected String currentProvinceName;
    protected String currentCityName;
    protected String currentDistrictName;

    public BaseAddressPopupWindow(Context context) {
        super(context);
        this.context = context;
        loadAddressFromLocal();
    }

    /**
     * 开始解析城市数据
     */
    protected void loadAddressFromLocal() {
        String cityJson = initData();
        if (!TextUtils.isEmpty(cityJson)) {
            try {
                JSONObject object = new JSONObject(cityJson);
                JSONArray array = object.getJSONArray("citylist");

                // 获取省份的数量
                provinceData = new String[array.length()];
                String mProvinceStr = null;
                // 循环遍历
                for (int i = 0; i < array.length(); i++) {

                    // 循环遍历省份，并将省保存在mProvinceDatas[]中
                    JSONObject mProvinceObject = array.getJSONObject(i);
                    if (mProvinceObject.has("p")) {
                        mProvinceStr = mProvinceObject.getString("p");
                        provinceData[i] = mProvinceStr;
                    } else {
                        provinceData[i] = "unknown province";
                    }

                    JSONArray cityArray;
                    String mCityStr = null;
                    try {
                        // 循环遍历省对应下的城市
                        cityArray = mProvinceObject.getJSONArray("c");
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                    try {
                        JSONArray jsonArray = cityArray.getJSONObject(0).getJSONArray("a");
                        citiesData = new String[cityArray.length()];
                        for (int j = 0; j < cityArray.length(); j++) {
                            // 循环遍历城市，并将城市保存在mCitiesDatas[]中
                            JSONObject mCityObject = cityArray.getJSONObject(j);
                            if (mCityObject.has("n")) {
                                mCityStr = mCityObject.getString("n");
                                citiesData[j] = mCityStr;
                            } else {
                                citiesData[j] = "unknown city";
                            }
                            // 循环遍历地区
                            JSONArray areaArray;
                            try {
                                // 判断是否含有第三级区域划分，若没有，则跳出本次循环，重新执行循环遍历城市
                                areaArray = mCityObject.getJSONArray("a");
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            }
                            // 执行下面过程则说明存在第三级区域
                            areaData = new String[areaArray.length()];
                            for (int m = 0; m < areaArray.length(); m++) {
                                // 循环遍历第三级区域，并将区域保存在mAreaDatas[]中
                                JSONObject areaObject = areaArray.getJSONObject(m);
                                if (areaObject.has("s")) {
                                    areaData[m] = areaObject.getString("s");
                                } else {
                                    areaData[m] = "unknown area";
                                }
                            }
                            // 存储市对应的所有第三级区域
                            areaDataMap.put(mCityStr, areaData);
                        }
                    } catch (JSONException e) {
                        citiesData = new String[1];
                        mCityStr = mProvinceStr + "市";
                        citiesData[0] = mCityStr;
                        // 执行下面过程则说明存在第三级区域
                        areaData = new String[cityArray.length()];
                        for (int m = 0; m < cityArray.length(); m++) {

                            // 循环遍历第三级区域，并将区域保存在mAreaDatas[]中
                            JSONObject areaObject = cityArray.getJSONObject(m);
                            if (areaObject.has("n")) {
                                areaData[m] = areaObject.getString("n");
                            } else {
                                areaData[m] = "unknown area";
                            }
                        }
                        // 存储市对应的所有第三级区域
                        areaDataMap.put(mCityStr, areaData);
                    }
                    // 存储省份对应的所有市
                    citiesDataMap.put(mProvinceStr, citiesData);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从asset目录下读取城市json文件转化为String类型
     */
    private String initData() {
        StringBuffer sb = new StringBuffer();
        AssetManager mAssetManager = context.getAssets();
        try {
            InputStream is = mAssetManager.open("city.json");
            byte[] data = new byte[is.available()];
            int len = -1;
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "gb2312"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
