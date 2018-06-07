package com.example.aidong.ui.mine.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .widget.BaseAddressDialog;
import com.example.aidong .widget.wheelview.ArrayWheelAdapter;
import com.example.aidong .widget.wheelview.OnWheelChangedListener;
import com.example.aidong .widget.wheelview.WheelView;

/**
 * 新建地址选择地址弹框
 * Created by song on 2016/9/20.
 */
public class SelectAddressDialog extends BaseAddressDialog implements View.OnClickListener,OnWheelChangedListener{
    private Context context;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvDistrict;
    private TextView sure;
    private TextView cancel;
    private OnConfirmAddressListener listener;

    public SelectAddressDialog(Context context) {
        this(context,0);
    }

    public SelectAddressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        initView();
        setListener();
        setWheelView();
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_chooes_address,null);
        setContentView(view);
      //  setTitle(context.getString(R.string.select_address));
        wvProvince = (WheelView) view.findViewById(R.id.wv_province);
        wvCity = (WheelView) view.findViewById(R.id.wv_city);
        wvDistrict = (WheelView) view.findViewById(R.id.wv_district);
        sure = (TextView) view.findViewById(R.id.tv_sure);
        cancel = (TextView) view.findViewById(R.id.tv_cancel_join);
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
                currDistrictName = areaDataMap.get(currCityName)[newValue];
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel_join:
                dismiss();
                break;
            case R.id.tv_sure:
                if(listener!=null){
                    listener.onAddressConfirm(currProvinceName, currCityName, currDistrictName);
                }
                dismiss();
                break;
        }
    }

    private void updateCities() {
        int pCurrent = wvProvince.getCurrentItem();
        currProvinceName = provinceData[pCurrent];
        String[] cities = citiesDataMap.get(currProvinceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        wvCity.setViewAdapter(new ArrayWheelAdapter<>(context, cities));
        wvCity.setCurrentItem(0);
        updateAreas();
    }

    private void updateAreas() {
        int pCurrent = wvCity.getCurrentItem();
        currCityName =  citiesDataMap.get(currProvinceName)[pCurrent];
        String[] areas =  areaDataMap.get(currCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        wvDistrict.setViewAdapter(new ArrayWheelAdapter<>(context, areas));
        wvDistrict.setCurrentItem(0);
        currDistrictName = areas[0];
    }

    public  void setOnConfirmAddressListener(OnConfirmAddressListener listener){
        this.listener = listener;
    }

    public interface OnConfirmAddressListener{
        void onAddressConfirm(String province, String city, String area);
    }
}
