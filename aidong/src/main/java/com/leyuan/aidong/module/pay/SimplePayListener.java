package com.leyuan.aidong.module.pay;

import android.content.Context;
import android.widget.Toast;

/**
 * simple pay listener
 * Created by song on 2017/2/22.
 */
public abstract class SimplePayListener implements PayInterface.PayListener{
    private Context context;

    public SimplePayListener(Context context) {
        this.context = context;
    }

    @Override
    public void onFail(String code, Object object) {
        String tip = "支付失败";
        switch (code){
            case "4000":
                tip = "订单支付失败";
                break;
            case "5000":
                tip = "订单重复提交";
                break;
            case "6001":
                tip = "订单取消支付";
                break;
            case "6002":
                tip = "网络连接出错";
                break;
            default:
                break;
        }
        Toast.makeText(context,tip,Toast.LENGTH_LONG).show();

    }
}
