package com.example.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.aidong.R;
import com.example.aidong .ui.BaseActivity;

/**
 * map
 * Created by song on 2017/3/5.
 */
public class MapActivity extends BaseActivity{
    private ImageView ivBack;
    private TextView tvTitle;
    private MapView mapView;
    private BaiduMap map;

    private String title;
    private String shopName;
    private String shopAddress;
    private String lat;
    private String lng;

    public static void start(Context context, String title, String shopName, String shopAddress, String lat, String lng) {
        Intent starter = new Intent(context, MapActivity.class);
        starter.putExtra("title",title);
        starter.putExtra("shopName",shopName);
        starter.putExtra("shopAddress",shopAddress);
        starter.putExtra("lat",lat);
        starter.putExtra("lng",lng);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(getIntent() != null){
            title = getIntent().getStringExtra("title");
            shopName = getIntent().getStringExtra("shopName");
            shopAddress = getIntent().getStringExtra("shopAddress");
            lat = getIntent().getStringExtra("lat");
            lng = getIntent().getStringExtra("lng");
        }

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mapView = (MapView) findViewById(R.id.mapView);

        tvTitle.setText(shopName);

        map = mapView.getMap();
        map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        LatLng point = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        map.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(point, 19));
        View view = View.inflate(this, R.layout.layout_market_map, null);
        TextView txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
        TextView txtStoreAddress = (TextView) view.findViewById(R.id.txt_store_address);
        txtStoreName.setText(shopName);
        txtStoreAddress.setText(shopAddress);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
        OverlayOptions option = new MarkerOptions().position(point) .icon(bitmap);
        map.addOverlay(option);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
