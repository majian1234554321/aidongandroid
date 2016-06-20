package com.example.aidong.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aidong.R;
import com.example.aidong.utils.SharePopTool;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;

/**
 * Created by Administrator on 2016/6/20.
 */
public class LoginUser extends Activity implements View.OnClickListener {

    private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
    private IWXAPI msgApi;
    private String APP_ID = "wx365ab323b9269d30";
    private Button button_qq, button_weixin, button_weibo, button_clean,button_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {


        button_qq = (Button) findViewById(R.id.button_qq);
        button_weixin = (Button) findViewById(R.id.button_weixin);
        button_weibo = (Button) findViewById(R.id.button_weibo);
        button_clean = (Button) findViewById(R.id.button_clean);
        button_share = (Button) findViewById(R.id.button_share);

        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(APP_ID);
        //参数1为当前Activity， 参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1104718858",
                "v0grfw3LV42iGCc0");
        qqSsoHandler.addToSocialSDK();

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, "wx365ab323b9269d30", "4bdcdc546ee89c4301b52a97509b055a");
        wxHandler.addToSocialSDK();

        //设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());


        button_qq.setOnClickListener(this);
        button_weixin.setOnClickListener(this);
        button_weibo.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_qq) {

            login(SHARE_MEDIA.QQ);

        } else if (v.getId() == R.id.button_weixin) {
            if (!msgApi.isWXAppInstalled()) {
                ToastUtil.show("未安装微信", LoginUser.this);
                return;
            } else {
                login(SHARE_MEDIA.WEIXIN);
            }
        } else if (v.getId() == R.id.button_weibo) {
            login(SHARE_MEDIA.SINA);

        } else if (v.getId() == R.id.button_clean) {
            mController.deleteOauth(LoginUser.this, SHARE_MEDIA.QQ,
                    new SocializeListeners.SocializeClientListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onComplete(int status, SocializeEntity entity) {
                            if (status == 200) {

                            } else {

//                                        Toast.makeText(SetUpActivity.this, "退出失败",
//                                                Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }else  if(v.getId()==R.id.button_share){

            SharePopTool sharePopTool = new SharePopTool(LoginUser.this, null, "", mController, "", "", "来自爱动健身APP" );
            sharePopTool.showChoseBox();



        }

    }


    private void login(SHARE_MEDIA platform) {

        mController.doOauthVerify(this, platform, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
//                Toast.makeText(LogInActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
//                Toast.makeText(LogInActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Bundle value, SHARE_MEDIA platform) {
//                Toast.makeText(LogInActivity.this, "授权完成", Toast.LENGTH_SHORT).show();

                String uid = value.getString("uid");
//                System.out.println("-----" + uid);
                //获取相关授权信息
                mController.getPlatformInfo(LoginUser.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {


                        } else {
                            Log.d("TestData", "发生错误：" + status);
                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastUtil.show("取消", LoginUser.this);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
                requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
