package com.example.aidong.simcpux;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/9/14.
 */
public class WXPayUtil {

    private Context context;
    private static final String TAG = "WXPayUtil";
    public static final String APP_ID = "wx365ab323b9269d30";
    public static final String MCH_ID = "1266307801";
    public static final String API_KEY = "zxcvbnm123asdfghjkl456qwertyuiop";
    public static String orderId;
    public static String out_trade_no;
    public static String orderType;
    //    public static Reservation_Information reservation_information;
    private PayReq req;
    private final IWXAPI msgApi;
    private Map<String, String> resultunifiedorder;
    private StringBuffer sb;
    private int total_fee;
    private String body, mch_id, api_key;
    private String notify_url = "www.baidu.com";
    private String time_start, time_expire;

    public WXPayUtil(Context context, String api_key, String orderId) {
        this.context = context;
        this.api_key = api_key;
        this.orderId = orderId;
        this.out_trade_no = genOutTradNo();
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(APP_ID);
    }

    public void prePay() {
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    private Dialog dialog;

    //    public void wattingDialog() {
    //        if (dialog != null) {
    //            dialog.dismiss();
    //            dialog = null;
    //        }
    //        dialog = DialogTools.wattingDialog(context, context.getResources().getString(R.string.loading_text), true);
    //        dialog.show();
    //    }

    @SuppressLint("LongLogTag")
    public void prePay(int total_fee, String body, String mch_id, String orderType) {
        Log.v(TAG, "total_fee = " + total_fee);
        Log.v(TAG, "body = " + body);
        Log.v(TAG, "mch_id = " + mch_id);
        this.total_fee = total_fee;
        this.body = body;
        this.mch_id = mch_id;
        this.orderType = orderType;
        //        wattingDialog();
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }


    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(api_key);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(api_key);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            //            if (dialog != null) {
            //                dialog.dismiss();
            //            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            resultunifiedorder = result;
            genPayReq();
            sendPayReq();
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {

            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();

            Log.e("orion", entity);

            byte[] buf = Util.httpPost(url, entity);

            String content = new String(buf);
            Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    //
    @SuppressLint("LongLogTag")
    private String genProductArgs() {
        StringBuffer xml = new StringBuffer();
        timeNew();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", APP_ID));
            packageParams.add(new BasicNameValuePair("body", body));
            packageParams.add(new BasicNameValuePair("mch_id", mch_id));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            //            packageParams.add(new BasicNameValuePair("time_start", time_start));
            //            packageParams.add(new BasicNameValuePair("time_expire", time_expire));
            packageParams.add(new BasicNameValuePair("notify_url", notify_url));
            packageParams.add(new BasicNameValuePair("out_trade_no", orderId));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", total_fee + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));


            String xmlstring = toXml(packageParams);
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
            //            return xmlstring;

        } catch (Exception e) {
            Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
            return null;
        }
    }

    public void timeNew() {
        Date date = new Date();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        time_start = sDateFormat.format(date);
        Date date2 = new Date(date.getTime() + 6 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        time_expire = simpleDateFormat.format(date2);
    }

    private void genPayReq() {

        req.appId = APP_ID;
        req.partnerId = mch_id;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        Log.e("orion", signParams.toString());

    }

    private void sendPayReq() {
        if (!msgApi.isWXAppInstalled()) {
            //            ToastTools.showToast(context, "未安装微信");
            return;
        }
        if (!msgApi.isWXAppSupportAPI())
            //            ToastTools.showToast(context, "当前版本不支持微信支付");
            msgApi.registerApp(APP_ID);
        msgApi.sendReq(req);
    }
}
