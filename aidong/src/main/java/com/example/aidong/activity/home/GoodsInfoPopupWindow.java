package com.example.aidong.activity.home;

import android.content.Context;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong.view.BasePopupWindow;

/**
 * 商品详情页选择商品信息弹框
 * Created by song on 2016/9/13.
 */
public class GoodsInfoPopupWindow extends BasePopupWindow{
    private Context context;


    public GoodsInfoPopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_goods_detail,null);
        setContentView(view);


    }


}
