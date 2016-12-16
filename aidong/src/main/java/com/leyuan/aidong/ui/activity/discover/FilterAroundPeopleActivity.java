package com.leyuan.aidong.ui.activity.discover;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.FilterAroundPeopleAdapter;
import com.leyuan.aidong.adapter.TagAdapter;
import com.leyuan.aidong.entity.model.TagBean;
import com.leyuan.aidong.widget.customview.ActionSheet;
import com.leyuan.aidong.widget.customview.MultiGridView;
import com.leyuan.aidong.widget.customview.PickerView;
import com.leyuan.aidong.widget.wheelcity.ArrayWheelAdapter;
import com.leyuan.aidong.widget.wheelcity.LoadAddressDataActivity;
import com.leyuan.aidong.widget.wheelcity.OnWheelChangedListener;
import com.leyuan.aidong.widget.wheelcity.WheelView;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 筛选附近的人
 * @author song
 */
@Deprecated
public class FilterAroundPeopleActivity extends LoadAddressDataActivity implements View.OnClickListener,OnWheelChangedListener{

    //top bar
    private TextView tvCancel;
    private TextView tvBack;

    private RelativeLayout root;
    private RelativeLayout rlSex;
    private TextView tvSex;
    private RelativeLayout rlAddress;
    private TextView tvAddress;
    private RelativeLayout rlAge;
    private TextView tvAge;
    private RelativeLayout rlHeight;
    private TextView tvHeight;
    private RelativeLayout rlConstellation;
    private TextView tvConstellation;
    private RelativeLayout rlBwh;
    private TextView tvBwh;
    private RelativeLayout rlTimes;
    private TextView tvTimes;

    //tag
    private MultiGridView gvCharacterTag;
    private MultiGridView gvSportTag;
    private MultiGridView gvBodyTag;
    private List<TagBean> characterTagList = new ArrayList<>();
    private List<TagBean> sportTagList = new ArrayList<>();
    private List<TagBean> bodyTagList = new ArrayList<>();
    private TagAdapter characterTagAdapter;
    private TagAdapter sportTagAdapter;
    private TagAdapter bodyTagAdapter;

    private Button btSure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_people);
        setTheme(R.style.ActionSheetStyleIOS7);
        init();
        setOnClickListener();
    }

    private void init(){
        root = (RelativeLayout)findViewById(R.id.relativeLayout_main);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvBack = (TextView) findViewById(R.id.tv_back);
        rlSex = (RelativeLayout) findViewById(R.id.rl_sex);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        rlAddress = (RelativeLayout) findViewById(R.id.rl_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        rlAge = (RelativeLayout) findViewById(R.id.rl_age);
        tvAge = (TextView) findViewById(R.id.tv_age);
        rlHeight = (RelativeLayout) findViewById(R.id.rl_height);
        tvHeight = (TextView) findViewById(R.id.tv_height);
        rlConstellation = (RelativeLayout) findViewById(R.id.rl_constellation);
        tvConstellation = (TextView) findViewById(R.id.tv_constellation);
        rlBwh = (RelativeLayout) findViewById(R.id.rl_bwh);
        tvBwh = (TextView) findViewById(R.id.tv_bwh);
        rlTimes = (RelativeLayout) findViewById(R.id.rl_times);
        tvTimes = (TextView) findViewById(R.id.tv_times);
        gvCharacterTag = (MultiGridView) findViewById(R.id.gv_character_tag);
        gvSportTag = (MultiGridView) findViewById(R.id.gv_sport_tag);
        gvBodyTag = (MultiGridView) findViewById(R.id.gv_body_tag);
        btSure = (Button) findViewById(R.id.bt_sure);

        initTagData();

        characterTagAdapter = new TagAdapter(this);
        gvCharacterTag.setAdapter(characterTagAdapter);
        sportTagAdapter = new TagAdapter(this);
        gvSportTag.setAdapter(sportTagAdapter);
        bodyTagAdapter = new TagAdapter(this);
        gvBodyTag.setAdapter(bodyTagAdapter);
        characterTagAdapter.setList(characterTagList);
        sportTagAdapter.setList(sportTagList);
        bodyTagAdapter.setList(bodyTagList);
    }

    private void initTagData() {
        String[] characterTag = getResources().getStringArray(R.array.characterTag);
        for (int i = 0;i<characterTag.length;i++){
            TagBean bean = new TagBean();
            bean.name = characterTag[i];
            bean.selected = false;
            characterTagList.add(bean);
        }

        String[] sportTag = getResources().getStringArray(R.array.sportTag);
        for (int i = 0;i<sportTag.length;i++){
            TagBean bean = new TagBean();
            bean.name = sportTag[i];
            bean.selected = false;
            sportTagList.add(bean);
        }

        String[] bodyTag = getResources().getStringArray(R.array.bodyTag);
        for (int i = 0;i<bodyTag.length;i++){
            TagBean bean = new TagBean();
            bean.name = bodyTag[i];
            bean.selected = false;
            bodyTagList.add(bean);
        }
    }

    private void setOnClickListener(){
        rlSex.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
        rlAge.setOnClickListener(this);
        rlBwh.setOnClickListener(this);
        rlHeight.setOnClickListener(this);
        rlConstellation.setOnClickListener(this);
        rlTimes.setOnClickListener(this);
        btSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String[] data;
        switch (view.getId()){
            case R.id.rl_sex:
                data = getResources().getStringArray(R.array.sex);
                showDialog(data);
                break;
            case R.id.rl_address:
                showAddressPopupWindow();
                break;
            case R.id.rl_age:
                showListPopup(Arrays.asList(getResources().getStringArray(R.array.age)));
                break;
            case R.id.rl_height:
                showListPopup(Arrays.asList(getResources().getStringArray(R.array.height)));
                break;
            case R.id.rl_constellation:
                showListPopup(Arrays.asList(getResources().getStringArray(R.array.constellation)));
                break;
            case R.id.rl_bwh:       //三围
                showBwhPopupWindow();
                break;
            case R.id.rl_times:
                showListPopup(Arrays.asList(getResources().getStringArray(R.array.sportTimes)));
                break;
            case R.id.bt_sure:
                break;
            default:
                break;
        }
    }

    private void showDialog(final String[] date){
        ActionSheet sheet = new ActionSheet(this);
        sheet.setCancelButtonTitle("cancel");
        sheet.addItems(date);
        sheet.setItemClickListener(new ActionSheet.MenuItemClickListener() {
            @Override
            public void onItemClick(int itemPosition) {
                ToastUtil.show("点击了" + date[itemPosition],FilterAroundPeopleActivity.this);
            }
        });
        sheet.setCancelableOnTouchMenuOutside(true);
        sheet.showMenu();
    }



    private PopupWindow listPopup;

    private void showListPopup(final List<String> list){
        View view = getLayoutInflater().inflate(R.layout.popup_list, null);
        ListView listView = (ListView) view.findViewById(R.id.lv_container);
        TextView tvCancel = (TextView)view.findViewById(R.id.tv_cancel);
        FilterAroundPeopleAdapter adapter = new FilterAroundPeopleAdapter();
        listView.setAdapter(adapter);
        adapter.setList(list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.show("click" + list.get(i),FilterAroundPeopleActivity.this);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPopup.dismiss();
            }
        });

        listPopup = new PopupWindow(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        listPopup.setFocusable(true);
        listPopup.setContentView(view);
        listPopup.setAnimationStyle(R.style.popup_style);
        listPopup.setBackgroundDrawable(new BitmapDrawable());
        if (listPopup.isShowing()) {
            listPopup.dismiss();
        } else {
            listPopup.showAtLocation(root, Gravity.BOTTOM, 0, 0);
        }
    }

    private PopupWindow addressPopup;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvDistrict;

    private void showAddressPopupWindow(){
        View view = getLayoutInflater().inflate(R.layout.popup_address, null);
        wvProvince = (WheelView) view.findViewById(R.id.wv_province);
        wvCity = (WheelView) view.findViewById(R.id.wv_city);
        wvDistrict = (WheelView) view.findViewById(R.id.wv_district);
        TextView sure = (TextView) view.findViewById(R.id.btn_confirm);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append(currentProvinceName).append("/").append(currentCityName).append("/").append(currentDistrictName);
                tvAddress.setText(sb);
                addressPopup.dismiss();
            }
        });
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressPopup.dismiss();
            }
        });

        ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addressPopup.dismiss();
            }
        });

        wvProvince.addChangingListener(this);
        wvCity.addChangingListener(this);
        wvDistrict.addChangingListener(this);
        fillAddressData();

        addressPopup = new PopupWindow(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        addressPopup.setFocusable(true);
        addressPopup.setContentView(view);
        addressPopup.setAnimationStyle(R.style.popup_style);
        addressPopup.setBackgroundDrawable(new BitmapDrawable());
        if (addressPopup.isShowing()) {
            addressPopup.dismiss();
        } else {
            addressPopup.showAtLocation(root, Gravity.BOTTOM, 0, 0);
        }
    }

    private void fillAddressData() {
        loadAddressFromLocal();
        wvProvince.setViewAdapter(new ArrayWheelAdapter<>(FilterAroundPeopleActivity.this, provinceData));
        wvProvince.setVisibleItems(7);
        wvCity.setVisibleItems(7);
        wvDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    private void updateCities() {
        int pCurrent = wvProvince.getCurrentItem();
        currentProvinceName = provinceData[pCurrent];
        String[] cities = citiesDataMap.get(currentProvinceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        wvCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
        wvCity.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = wvCity.getCurrentItem();
        currentCityName =  citiesDataMap.get(currentProvinceName)[pCurrent];
        String[] areas =  areaDataMap.get(currentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        wvDistrict.setViewAdapter(new ArrayWheelAdapter<>(this, areas));
        wvDistrict.setCurrentItem(0);
        currentDistrictName = areas[0];
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == wvProvince) {
            updateCities();
        } else if (wheel == wvCity) {
            updateAreas();
        } else if (wheel == wvDistrict) {
            ToastUtil.show(currentDistrictName,this);
            currentDistrictName = areaDataMap.get(currentCityName)[newValue];
        }
    }


    private PopupWindow bwhPopup;
    private PickerView pvBust;
    private PickerView pvWaist;
    private PickerView pvHit;
    private TextView popupBwh;
    private String bust = "";
    private String waist = "";
    private String hit = "";

    private void showBwhPopupWindow(){
        View view = getLayoutInflater().inflate(R.layout.popup_bwh, null);
        pvBust = (PickerView) view.findViewById(R.id.pv_bust);
        pvWaist = (PickerView) view.findViewById(R.id.pv_waist);
        pvHit = (PickerView) view.findViewById(R.id.pv_hip);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);
        popupBwh = (TextView) view.findViewById(R.id.tv_bwh);

        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bwhPopup.dismiss();
                if (TextUtils.isEmpty(bust)) {
                    bust = "86-91";
                }
                if (TextUtils.isEmpty(waist)) {
                    waist = "86-91";
                }
                if (TextUtils.isEmpty(hit)) {
                    hit = "86-91";
                }
                tvBwh.setText(new StringBuffer().append(bust).append("/").append(waist).append("/").append(hit));
            }
        });

        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bwhPopup.dismiss();
            }
        });

        ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bwhPopup.dismiss();
            }
        });

        fillBwhData();
        bwhPopup = new PopupWindow(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        bwhPopup.setFocusable(true);
        bwhPopup.setContentView(view);
        bwhPopup.setAnimationStyle(R.style.popup_style);
        bwhPopup.setBackgroundDrawable(new BitmapDrawable());
        if (bwhPopup.isShowing()) {
            bwhPopup.dismiss();
        } else {
            bwhPopup.showAtLocation(root, Gravity.BOTTOM, 0, 0);
        }
    }

    private void fillBwhData() {
        List<String> bustData = new ArrayList<>();
        List<String> waistData = new ArrayList<>();
        List<String> hitData = new ArrayList<>();

        for (int i = 50; i < 120; i++) {
            int a = i + 5;
            bustData.add(i + "-" + a);
            waistData.add(i + "-" + a);
            hitData.add(i + "-" + a);
            i = i + 5;
        }

        pvBust.setData(bustData);
        pvBust.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String str) {
                bust = str;
                popupBwh.setText(new StringBuffer().append(getString(R.string.bust))
                        .append(bust).append(getString(R.string.waist)).append(waist)
                        .append(getString(R.string.hit)).append(hit));
            }
        });

        pvWaist.setData(waistData);
        pvWaist.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String str) {
                waist = str;
                popupBwh.setText(new StringBuffer().append(getString(R.string.bust))
                        .append(bust).append(getString(R.string.waist)).append(waist)
                        .append(getString(R.string.hit)).append(hit));
            }
        });

        pvHit.setData(hitData);
        pvHit.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String str) {
                hit = str;
                popupBwh.setText(new StringBuffer().append(getString(R.string.bust))
                        .append(bust).append(getString(R.string.waist)).append(waist)
                        .append(getString(R.string.hit)).append(hit));
            }
        });
    }

}
