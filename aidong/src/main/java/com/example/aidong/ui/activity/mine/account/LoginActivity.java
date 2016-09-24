package com.example.aidong.ui.activity.mine.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.BaseApp;
import com.example.aidong.R;
import com.example.aidong.utils.common.Constant;
import com.example.aidong.utils.common.MXLog;
import com.example.aidong.utils.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.entity.model.UserCoach;
import com.example.aidong.entity.model.result.FriendsResult;
import com.example.aidong.entity.model.result.LoginResult;
import com.example.aidong.entity.model.result.MsgResult;
import com.example.aidong.utils.SharePrefUtils;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.mob.tools.utils.UIHandler;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements OnClickListener,
		Callback, IHttpCallback, PlatformActionListener {
	String TAG = "LoginActivity";
	private EditText mEditUserName, mPassWord;
	private ImageView btnLoginWeixin; 
    private ImageView btnLoginQQ;
    private ImageView btnLoginSina;
	
	private RelativeLayout layoutLogin;
	private Button btnLogin;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int LOGIN_CODE = 0;
	private static final int FRIENDS_CODE = 1;
	private static final int LOGINSNS = 3; 
	private static final int STEALTH_CODE = 4;
	private static final int FIND_UPLOAD_CODE = 5;

	private long exitTime = 0;
	private ImageView btnExit;
	private Intent intent;
	private TextView textForgetPassword;
	OnekeyShare onekeyShare;
	private ProgressDialog dialog_weixin;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_login);
		ShareSDK.initSDK(this);
		initView();
		boolean isInstall = isInstalled(this, "com.tencent.mm");
		if (isInstall) {
			btnLoginWeixin.setVisibility(View.VISIBLE);
		} else {
			btnLoginWeixin.setVisibility(View.GONE);
		}
		
		
		
	}

	/**
	 * 判断应用是否已安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	private boolean isInstalled(Context context, String packageName) {
		boolean hasInstalled = false;
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> list = pm
				.getInstalledPackages(PackageManager.PERMISSION_GRANTED);
		for (PackageInfo p : list) {
			if (packageName != null && packageName.equals(p.packageName)) {
				hasInstalled = true;
				break;
			}
		}
		return hasInstalled;
	}

	protected void initData() {
		findViewById(R.id.textRegist).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent.setClass(LoginActivity.this,
								SMSRegistActivity.class);
						startActivity(intent);
					}
				});

	}

	private void initView() {
		intent = new Intent();
		textForgetPassword = (TextView) findViewById(R.id.textForgetPassword);
		btnLoginWeixin = (ImageView) findViewById(R.id.btnLoginWeixin); 
		btnLoginQQ=(ImageView)findViewById(R.id.btnLoginQQ);
		btnLoginSina=(ImageView)findViewById(R.id.btnLoginSina);
		mEditUserName = (EditText) findViewById(R.id.editUsername);
		mEditUserName.setInputType(InputType.TYPE_CLASS_NUMBER);
		mPassWord = (EditText) findViewById(R.id.password);
		btnExit = (ImageView) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);
//		loadingDialog = new LoadingDialog(this, R.string.tip_logining);
		layoutLogin = (RelativeLayout) findViewById(R.id.layoutLogin);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		textForgetPassword.setOnClickListener(this); 
		
		 
		btnLoginQQ.setOnClickListener(this);
		btnLoginSina.setOnClickListener(this);
		btnLoginWeixin.setOnClickListener(this);
		
		
		layoutLogin.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					return imm.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(), 0);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		});

	}

	public void setLoadingDialog(int string) {
		dialog = ProgressDialog.show(this, "提示", getResources().getString(string));
	}

	public void stoploadingDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
//		platform = null;
		switch (v.getId()) {
		case R.id.btnLogin:
			if (!verification())
				return;
			SharePrefUtils.putString(this,"loginname",
					mEditUserName.getText().toString());
			setLoadingDialog(R.string.tip_logining);
			addTask(LoginActivity.this, new IHttpTask(UrlLink.LOGIN_URL,
					paramsinit(), LoginResult.class), HttpConfig.POST,
					LOGIN_CODE);
			break;
		case R.id.btnExit:
			finish();
			break;
		case R.id.textForgetPassword:
			intent.setClass(getApplicationContext(),
					PasswordForgetActivity.class);
			startActivity(intent);
			break;
		case R.id.btnLoginQQ: 
			platform = ShareSDK.getPlatform(QQ.NAME);
			platform.SSOSetting(false);
			MXLog.i(TAG, "QQ登录");
			break;
		case R.id.btnLoginSina:
			platform = ShareSDK.getPlatform(SinaWeibo.NAME);
			MXLog.i(TAG, "新浪登录");
			platform.SSOSetting(false);
			break;
		case R.id.btnLoginWeixin:
			platform = ShareSDK.getPlatform(Wechat.NAME);
			// showHttpToast("暂时无法验证微信签名");
			MXLog.i(TAG, "微信登录");

			dialog_weixin = ProgressDialog.show(this, "提示", "读取中...");
			
			break;
		}
		if (platform != null) {
			platform.setPlatformActionListener(this);
			platform.removeAccount(true);
			platform.showUser(null);
			ShareSDK.removeCookieOnAuthorize(true);// 清理cookie
       }
	}

	public List<BasicNameValuePair> paramsinit() {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();

		BasicNameValuePair pair1 = new BasicNameValuePair("username",
				mEditUserName.getText().toString());
		BasicNameValuePair pair2 = new BasicNameValuePair("password", mPassWord
				.getText().toString());
		paramsaaa.add(pair1);
		paramsaaa.add(pair2);
		return paramsaaa;
	}

	private boolean verification() {
		if (mEditUserName.getText() == null
				|| mEditUserName.getText().toString().equals("")) {
			mEditUserName.setError(Html
					.fromHtml("<font color=#808183>请输入账号</font>"));
			return false;
		}
		// else if(!Utils.isAccount(username_edit.getText().toString())){
		// username_edit.setError(Html
		// .fromHtml("<font color=#808183>账号输入不正确</font>"));
		// return false;
		// }
		if (mPassWord.getText() == null
				|| mPassWord.getText().toString().equals("")) {
			mPassWord.setError(Html
					.fromHtml("<font color=#808183>请输入密码</font>"));
			return false;
		}

		return true;
	}

	@Override
	public void onGetData(Object data, int requestCode, String response) {
		stopLoading();
		switch (requestCode) {
		case LOGIN_CODE:
			LoginResult res = (LoginResult) data;
			if (res.getCode() == 1) {
				// MXLog.out(""+res.getData().getCoach().getName());
				
				if(BaseApp.mInstance.getUser()==null){
					BaseApp.mInstance.setUser(res.getData().getUser());
					SharePrefUtils.setLogin(this,true);
					addTask(this, new IHttpTask(UrlLink.FIND_UPLOAD_URL, new ArrayList<BasicNameValuePair>(), MsgResult.class), HttpConfig.PUT, FIND_UPLOAD_CODE);
					addTask(this, new IHttpTask(UrlLink.PRIVACY_URL, paramsstealth(), MsgResult.class), HttpConfig.PUT, STEALTH_CODE);
				} else if(BaseApp.mInstance.getUser().getMxid()!=res.getData().getUser().getMxid()){
					BaseApp.mInstance.setUser(res.getData().getUser());
					SharePrefUtils.setLogin(this,true);
					addTask(this, new IHttpTask(UrlLink.FIND_UPLOAD_URL, new ArrayList<BasicNameValuePair>(), MsgResult.class), HttpConfig.PUT, FIND_UPLOAD_CODE);
					addTask(this, new IHttpTask(UrlLink.PRIVACY_URL, paramsstealth(), MsgResult.class), HttpConfig.PUT, STEALTH_CODE);
				}else{
					BaseApp.mInstance.setUser(res.getData().getUser());
					SharePrefUtils.setLogin(this,true);
				}
				
				
//				BaseApp.mInstance.getParamsHelper().setPreInt("islogin", 1);
				// Intent i = new Intent(this, HomeActivity.class);
				// i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(i);
				chatLogin("" + res.getData().getUser().getMxid(), "123456");
				finish();
			}
			break;
		case FRIENDS_CODE:
			FriendsResult fres = (FriendsResult) data;
			if (fres.getCode() == 1) {
				if (fres.getData().getProfiles() != null
						&& fres.getData().getProfiles().size() > 0) {

					Map<String, UserCoach> userlist = new HashMap<String, UserCoach>();
					for (UserCoach username : fres.getData().getProfiles()) {
						username.setUsername(username.getMxid() + "");
						setUserHearder(username.getMxid() + "", username);

						userlist.put(username.getMxid() + "", username);
					}
					// 存入内存
//					BaseApp.mInstance.setContactList(userlist);
//					// 存入db
//					UserDao dao = new UserDao(this);
//					List<UserCoach> users = new ArrayList<UserCoach>(
//							userlist.values());
//					dao.saveContactList(users);
				}
			}

			break;  
			
		case LOGINSNS: 
			LoginResult reslogin = (LoginResult) data;
			if (reslogin.getCode() == 1) {
				// MXLog.out(""+res.getData().getCoach().getName());
				BaseApp.mInstance.setUser(reslogin.getData().getUser());
				SharePrefUtils.setLogin(this,true);
				
				addTask(this, new IHttpTask(UrlLink.FIND_UPLOAD_URL, new ArrayList<BasicNameValuePair>(), MsgResult.class), HttpConfig.PUT, FIND_UPLOAD_CODE);
				addTask(this, new IHttpTask(UrlLink.PRIVACY_URL, paramsstealth(), MsgResult.class), HttpConfig.PUT, STEALTH_CODE);
				
				chatLogin("" + reslogin.getData().getUser().getMxid(), "123456");
				finish();
			}			
		break;
		}
}
	public List<BasicNameValuePair> paramsstealth() {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("stealth",
				""+0));
		return paramsaaa;
	}

	public List<BasicNameValuePair> paramsinitLoginSns(String sns_name, String sns_id, String name,String gender, String avatar, String birthday) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("sns_name", sns_name));
		paramsaaa.add(new BasicNameValuePair("sns_id", sns_id));
		paramsaaa.add(new BasicNameValuePair("name", name));
		paramsaaa.add(new BasicNameValuePair("gender", gender));
		paramsaaa.add(new BasicNameValuePair("avatar", avatar));
		paramsaaa.add(new BasicNameValuePair("birthday", birthday));

		
		return paramsaaa;
	}
 
	 
//	public List<BasicNameValuePair> paramsinitOtherLgoinSns()
//	{
//		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
//	
//		
//		
//		return params;
//	}
	
	
	
	
	
	@Override
	public void onError(String reason, int requestCode) {
		stopLoading();

	}

	private void stopLoading() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				stoploadingDialog();
			}
		});
	}

	public void chatLogin(final String currentUsername,
			final String currentPassword) {
//		// 调用sdk登陆方法登陆聊天服务器
//		EMChatManager.getInstance().login(currentUsername, currentPassword,
//				new EMCallBack() {
//
//					@Override
//					public void onSuccess() {
//
//						// 登陆成功，保存用户名密码
//						BaseApp.mInstance.setChatUserName(currentUsername);
//						BaseApp.mInstance.setChatPassword(currentPassword);
//
//						try {
//							// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
//							// ** manually load all local groups and
//							EMGroupManager.getInstance().loadAllGroups();
//							EMChatManager.getInstance().loadAllConversations();
//							// 处理好友和群组
//							initializeContacts();
//						} catch (Exception e) {
//							e.printStackTrace();
//							// 取好友或者群聊失败，不让进入主页面
//							// runOnUiThread(new Runnable() {
//							// public void run() {
//							// pd.dismiss();
//							// DemoApplication.getInstance().logout(null);
//							// Toast.makeText(getApplicationContext(),
//							// R.string.login_failure_failed, 1).show();
//							// }
//							// });
//							// return;
//						}
//						// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
//						boolean updatenick = EMChatManager.getInstance()
//								.updateCurrentUserNick(
//										BaseApp.mInstance.getUser().getName());
//						if (!updatenick) {
//							Log.e("LoginActivity",
//									"update current UserCoach nick fail");
//						}
//
//					}
//
//					@Override
//					public void onProgress(int progress, String status) {
//					}
//
//					@Override
//					public void onError(final int code, final String message) {
//						// if (!progressShow) {
//						// return;
//						// }
//						// runOnUiThread(new Runnable() {
//						// public void run() {
//						// pd.dismiss();
//						// Toast.makeText(getApplicationContext(),
//						// getString(R.string.Login_failed) + message,
//						// Toast.LENGTH_SHORT).show();
//						// }
//						// });
//					}
//				});
	}

//	private void initializeContacts() throws EaseMobException {
//		Map<String, UserCoach> userlist = new HashMap<String, UserCoach>();
//		UserDao dao = new UserDao(this);
//		userlist = dao.getContactList();
//		final List<String> usernames = EMContactManager.getInstance()
//				.getContactUserNames();
//		if (userlist != null && userlist.size() != 0) {
//
//			// 存入内存
//			BaseApp.mInstance.setContactList(userlist);
//		} else {
//			userlist = new HashMap<String, UserCoach>();
//			for (String username : usernames) {
//				UserCoach UserCoach = new UserCoach();
//				UserCoach.setUsername(username);
//				setUserHearder(username, UserCoach);
//				userlist.put(username, UserCoach);
//			}
//			BaseApp.mInstance.setContactList(userlist);
//		}
//
//		addTask(LoginActivity.this, new IHttpTask(UrlLink.FRIENDS_URL,
//				friendsParamsinit(usernames), FriendsResult.class),
//				HttpConfig.GET, FRIENDS_CODE);
//
//		// 存入db
//
//		// List<UserCoach> users = new ArrayList<UserCoach>(userlist.values());
//		// dao.saveContactList(users);
//	}

	public List<BasicNameValuePair> friendsParamsinit(List<String> usernames) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		String mxidsStr = "";
		for (int i = 0; i < usernames.size(); i++) {
			if (i != usernames.size() - 1) {
				mxidsStr += usernames.get(i) + ",";
			} else {
				mxidsStr += usernames.get(i);
			}
		}

		paramsaaa.add(new BasicNameValuePair("mxids", mxidsStr));
		return paramsaaa;
	}

	/**
	 * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
	 * 
	 * @param username
	 * @param UserCoach
	 */
	public static void setUserHearder(String username, UserCoach UserCoach) {
		String headerName = null;
		if (!TextUtils.isEmpty(UserCoach.getNick())) {
			headerName = UserCoach.getNick();
		} else if (!TextUtils.isEmpty(UserCoach.getUsername())) {
			headerName = UserCoach.getUsername();
		} else {
			headerName = "" + UserCoach.getMxid();
		}
		UserCoach.setUsername("" + UserCoach.getMxid());
		if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
			UserCoach.setHeader("");
		} else if (Character.isDigit(headerName.charAt(0))) {
			UserCoach.setHeader("#");
		} else {
//			UserCoach.setHeader(HanziToPinyin.getInstance()
//					.get(headerName.substring(0, 1)).get(0).target.substring(0,
//					1).toUpperCase());
//			char header = UserCoach.getHeader().toLowerCase().charAt(0);
//			if (header < 'a' || header > 'z') {
//				UserCoach.setHeader("#");
//			}
		}
	}

	//
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK
	// && event.getAction() == KeyEvent.ACTION_DOWN) {
	// if ((System.currentTimeMillis() - exitTime) > 2000) {
	// Toast.makeText(getApplicationContext(), "再按一次退出程序",
	// Toast.LENGTH_SHORT).show();
	// exitTime = System.currentTimeMillis();
	// } else {
	// finish();
	// System.exit(0);
	// }
	// return true;
	// }
	// return super.onKeyDown(keyCode, event);
	// }

	@Override
	public void onCancel(Platform platform, int action) {
		if (dialog_weixin != null) {
			dialog_weixin.dismiss();
		}
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
		Log.e(TAG, "onCancel");
	}

	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (dialog_weixin != null) {
			dialog_weixin.dismiss();
		}
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;

		if (platform.getName().equals(QQ.NAME)) {
			sns_name = "QQ";
		}
		if (platform.getName().equals(Wechat.NAME)) {
			sns_name = "WeChat";
		}
		if (platform.getName().equals(SinaWeibo.NAME)) {
			sns_name = "sina";
		}

		sns_userid = platform.getDb().getUserId();
		sns_username = platform.getDb().getUserName();
		sns_usericon = platform.getDb().getUserIcon();
		sns_usergender = platform.getDb().getUserGender();


		addTask(LoginActivity.this,
				new IHttpTask(UrlLink.LOGINSNS_URL, paramsinitLoginSns(
						sns_name, sns_userid, sns_username,
						sns_usergender, sns_usericon, sns_userbirthday),
						LoginResult.class), HttpConfig.POST, LOGINSNS);




//		String str="ID: "+sns_userid+";\n"+
//		           "用户名： "+sns_username+";\n"+
//		  	         "用户头像地址："+sns_usericon;
//		Log.e("userinfo------", str);
//		System.out.println(res);
//		UIHandler.sendMessage(msg, this);

		Log.e(TAG, "onComplete");
	}








	@Override
	public void onError(Platform platform, int action, Throwable t) {
			if (dialog_weixin != null) {
				dialog_weixin.dismiss();
			}

		t.printStackTrace();
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
		// 分享失败的统计
		ShareSDK.logDemoEvent(4, platform);
		Log.e(TAG, "errer");
	}

	Platform platform = null;
	String sns_name = "";
	String sns_userid = "";
	String sns_username = "";
	String sns_usericon = "";
	String sns_usergender = "";
	String sns_userbirthday = "";

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.arg1) {
		case 1: {
			// 成功
			// Toast.makeText(LoginActivity.this, "成功",
			// Toast.LENGTH_SHORT).show();
			setLoadingDialog(R.string.tip_logining);
			addTask(LoginActivity.this,
					new IHttpTask(UrlLink.LOGINSNS_URL, paramsinitLoginSns(
							sns_name, sns_userid, sns_username,
							sns_usergender, sns_usericon, sns_userbirthday),
							LoginResult.class), HttpConfig.POST, LOGINSNS);
		}
			break;
		case 2: {
//			 失败
			Throwable t = (Throwable) msg.obj;
			 Toast.makeText(LoginActivity.this, "失败:"+t.getMessage(),
			 Toast.LENGTH_SHORT).show();
//			 Throwable t = (Throwable) msg.obj;
//			 if (t != null && t.getMessage() != null) {
//			 showHttpToast(t.getMessage());
//			 } else {
//			 showHttpToast(R.string.error_login);
//			 }

		}
			break;
		case 3: {
			// 取消
			// Toast.makeText(LoginActivity.this, "取消····", Toast.LENGTH_SHORT)
			// .show();
		}
			break;
		}
		return false;
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (dialog_weixin != null) {
			dialog_weixin.dismiss();
		}
	}
}
