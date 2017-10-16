package com.leyuan.aidong.ui.mine.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.DeviceManager;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.PermissionManager;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonCancelListener;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogDoubleButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/9/27.
 */
public class MyMemberCardActivity extends BaseActivity {
    private static final java.lang.String TAG = "MyMemberCardActivity";
    private static final int REQUEST_PERMISSION_CODE = 103;
    private WebView mWebView;
    private ImageView imgLoading;
    private PermissionManager permissionManager;
    private PermissionManager.OnCheckPermissionListener premissionListener = new PermissionManager.OnCheckPermissionListener() {
        @Override
        public void checkOver() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_member_card);

        imgLoading = (ImageView) findViewById(R.id.img_loading);
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, imgLoading);
        initWebView();
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.clearCache(true);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据 v

        webSettings.setSupportZoom(true);

        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setDomStorageEnabled(true);

        webSettings.setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 70) {
                    imgLoading.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.i(TAG, "mWebView.loadUrl onPageFinished");

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtil.i(TAG,"over url = " + url);
////                view.loadUrl(url);
//
//                return false;
//            }
        });
        Logger.i(TAG, "mWebView.loadUrl start");
        mWebView.addJavascriptInterface(new MyJSInterface(MyMemberCardActivity.this), "android");
//
        Map<String, String> map = new HashMap<>();
        map.put("mobile", App.getInstance().getUser().getMobile());
        mWebView.loadUrl("http://opentest.aidong.me/app/cards", map);
    }

    @Override
    // 设置回退
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下返回键并且webview界面可以返回
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            String currentUrl = mWebView.copyBackForwardList().getCurrentItem().getUrl();
            Logger.i(TAG, "current url = " + currentUrl);
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyJSInterface {
        Context mContext;

        public MyJSInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public String getDevice() {
            return "android";
        }

        @JavascriptInterface
        public String getVersion() {

            return App.getInstance().getVersionName();
        }

        @JavascriptInterface
        public String getDeviceName() {

            return DeviceManager.getPhoneBrand();
        }

        @JavascriptInterface
        public String selectAddress() {

            return "return selectAddress from android ";
        }

        @JavascriptInterface
        public void scan() {
            Logger.i(TAG, "scan invoked");

            String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
            if (ContextCompat.checkSelfPermission(MyMemberCardActivity.this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MyMemberCardActivity.this, permission)) {
                    ActivityCompat.requestPermissions(MyMemberCardActivity.this, new String[]{permission}, REQUEST_PERMISSION_CODE);
                } else {
                    showPermissionDailog(permission, "请打开读取权限,以正常使用应用");
                }
            } else {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MyMemberCardActivity.this);
                intentIntegrator.setPrompt("请将二维码置于方框内");
                // 开始扫描
                intentIntegrator.initiateScan();
            }
        }

        @JavascriptInterface
        public void finish() {

            Logger.i(TAG, "finish invoked");
            MyMemberCardActivity.this.finish();
        }

    }

    private void showPermissionDailog(final String permission, final String hint) {
        new DialogDoubleButton(MyMemberCardActivity.this)
                .setContentDesc(hint)
                .setRightButton("确定")
                .setBtnCancelListener(new ButtonCancelListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        dialog.dismiss();
                        ToastGlobal.showLong("读取权限被禁用，请点击确定打开读取权限以正常使用扫描功能");
                    }
                })
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        ActivityCompat.requestPermissions(MyMemberCardActivity.this, new String[]{permission}, REQUEST_PERMISSION_CODE);
                        dialog.dismiss();

                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MyMemberCardActivity.this);
            intentIntegrator.setPrompt("请将二维码置于方框内");
            // 开始扫描
            intentIntegrator.initiateScan();
        }else {
            ToastGlobal.showLong("读取权限被禁用，需要手动打开，请进入设置应用管理打开权限");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                //调H5方法传递扫描结果
                Logger.i(TAG, "扫描内容 : " + result.getContents());
//                http://opentest.aidong.me/scan_code?c=7&s=57&key=0546290113b7bc06b46d9de621775b1c
                mWebView.loadUrl("javascript:scanResult('" + result.getContents() + "')");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
