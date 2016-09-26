package com.leyuan.aidong.ui.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.customview.UISwitchButton;

public class BingdingCommunityActivity extends BaseActivity implements Callback{
	private ImageView mlayout_bingding_community_title_img;
	private UISwitchButton mswitch_weibo;
//	OnekeyShare onekeyShare;
	private static final int MSG_ACTION_CCALLBACK = 2;
//	Platform platform = null;
	private SharedPreferences mSharedPreferences;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}
	protected void setupView() {
		setContentView(R.layout.layout_binding_third_party_community);
//		ShareSDK.initSDK(this);
		init();
		
	}
	private void init() {
		mlayout_bingding_community_title_img = (ImageView) findViewById(R.id.layout_bingding_community_title_img);
		mSharedPreferences = getSharedPreferences("BingdingThree",
				Context.MODE_PRIVATE);
		mswitch_weibo = (UISwitchButton) findViewById(R.id.switch_weibo);
		mswitch_weibo.setChecked(mSharedPreferences.getBoolean("state", false));
	}
	protected void initData() {
		onClick();
	}
	private void onClick() {
		mlayout_bingding_community_title_img
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mswitch_weibo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (!isChecked) {
//					platform.removeAccount();
//					ShareSDK.removeCookieOnAuthorize(true);// 清理cookie
					// showHttpToast(R.string.cancel_bangding);
				} else {
//					platform = ShareSDK.getPlatform(SinaWeibo.NAME);
//					if (platform != null) {
//						platform.setPlatformActionListener(BingdingCommunityActivity.this);
//						platform.showUser(null);
//					}
				}
				Editor editor = mSharedPreferences.edit();
				editor.putBoolean("state", isChecked);
				editor.commit();
			}
		});
	}
//	@Override
//	public void onCancel(Platform platform, int action) {
//		Message msg = new Message();
//		msg.what = MSG_ACTION_CCALLBACK;
//		msg.arg1 = 3;
//		msg.arg2 = action;
//		msg.obj = platform;
//		UIHandler.sendMessage(msg, this);
//	}
//
//	@Override
//	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
//		Message msg = new Message();
//		msg.what = MSG_ACTION_CCALLBACK;
//		msg.arg1 = 1;
//		msg.arg2 = action;
//		msg.obj = platform;
//
//		if (platform.getName().equals(SinaWeibo.NAME)) {
//		}
//
//		UIHandler.sendMessage(msg, this);
//		System.out.println(res);
//		// 获取资料
//		platform.getDb().getUserName();// 获取用户名字
//		platform.getDb().getUserIcon(); // 获取用户头像
//		platform.getDb().getToken();
//		platform.getDb().getUserId();
//	}
//
//	@Override
//	public void onError(Platform platform, int action, Throwable t) {
//		Message msg = new Message();
//		msg.what = MSG_ACTION_CCALLBACK;
//		msg.arg1 = 2;
//		msg.arg2 = action;
//		msg.obj = t;
//		UIHandler.sendMessage(msg, this);
//
//		// 分享失败的统计
//		ShareSDK.logDemoEvent(4, platform);
//
//	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
		case 1: {
			// 成功
			Toast.makeText(BingdingCommunityActivity.this, "绑定成功!",
					Toast.LENGTH_SHORT).show();
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean("state", true);
			editor.commit();
			mswitch_weibo.setChecked(true);
		}
			break;
		case 2: {
			// 失败
			Toast.makeText(BingdingCommunityActivity.this, "绑定失败",
					Toast.LENGTH_SHORT).show();
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean("state", false);
			editor.commit();
			mswitch_weibo.setChecked(false);
		}
			break;
		case 3: {
			// 取消
			Toast.makeText(BingdingCommunityActivity.this, "取消绑定",
					Toast.LENGTH_SHORT).show();
			Editor editor = mSharedPreferences.edit();
			editor.putBoolean("state", false);
			editor.commit();
			mswitch_weibo.setChecked(false);
		}
			break;
		}
		return false;
	}

}
