package com.leyuan.aidong.ui.activity.mine.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.customview.BaseAddressPopupWindow;
import com.leyuan.aidong.widget.wheelcity.ArrayWheelAdapter;
import com.leyuan.aidong.widget.wheelcity.OnWheelChangedListener;
import com.leyuan.aidong.widget.wheelcity.WheelView;

/**
 * 新建地址选择地址弹框
 * Created by song on 2016/9/20.
 */
public class AddressPopupWindow extends BaseAddressPopupWindow implements View.OnClickListener,OnWheelChangedListener{
    private Context context;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvDistrict;
    private TextView sure;
    private TextView cancel;
    private OnConfirmAddressListener listener;

    public AddressPopupWindow(Context context) {
        super(context);
        this.context = context;
        initView();
        setListener();
        setWheelView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.popup_chooes_address,null);
        setContentView(view);
        wvProvince = (WheelView) view.findViewById(R.id.wv_province);
        wvCity = (WheelView) view.findViewById(R.id.wv_city);
        wvDistrict = (WheelView) view.findViewById(R.id.wv_district);
        sure = (TextView) view.findViewById(R.id.btn_confirm);
        cancel = (TextView) view.findViewById(R.id.tv_cancel);
    }

    private void setListener() {
        sure.setOnClickListener(this);
        cancel.setOnClickListener(this);
        wvProvince.addChangingListener(this);
        wvCity.addChangingListener(this);
        wvDistrict.addChangingListener(this);
    }


    private void setWheelView() {
        wvProvince.setViewAdapter(new ArrayWheelAdapter<>(context, provinceData));
        wvProvince.setVisibleItems(8);
        wvCity.setVisibleItems(8);
        wvDistrict.setVisibleItems(8);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        switch (wheel.getId()){
            case  R.id.wv_province:
                updateCities();
                break;
            case  R.id.wv_city:
                updateAreas();
                break;
            case  R.id.wv_district:
                currentDistrictName = areaDataMap.get(currentCityName)[newValue];
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                if(listener!=null){
                    listener.onAddressConfirm(currentProvinceName,currentCityName,currentDistrictName);
                }
                dismiss();
                break;
        }
    }

    private void updateCities() {
        int pCurrent = wvProvince.getCurrentItem();
        currentProvinceName = provinceData[pCurrent];
        String[] cities = citiesDataMap.get(currentProvinceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        wvCity.setViewAdapter(new ArrayWheelAdapter<>(context, cities));
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
        wvDistrict.setViewAdapter(new ArrayWheelAdapter<>(context, areas));
        wvDistrict.setCurrentItem(0);
        currentDistrictName = areas[0];
    }

    public  void setOnConfirmAddressListener(OnConfirmAddressListener listener){
        this.listener = listener;
    }

    public interface OnConfirmAddressListener{
        void onAddressConfirm( String province, String city, String area);
    }
}
