package com.leyuan.aidong.ui.mine.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.BaseActivity;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class PasswordRegistActivity extends BaseActivity  {
	private RelativeLayout layout_regist_password = null;
	private TextView textRegistAgree;
	private boolean isLoginSuccess;
	private EditText editNickName, editPswd, editPswdRepeat,editInvite;
	static final String TAG = "PasswordRegistActivity";
	private Intent intent;
	String token;
	private UserCoach user;
	String[] mxid;
	private static final int PERSON = 0;
	private static final int ANDPASSWORD = 1;
	private static final int FRIENDS_CODE = 2;
	private String nickName, passwrod;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_regist_password);
		init();
	}

	protected void initData() {
		onClick();
	}

	public List<BasicNameValuePair> paramsinit2(String mxid) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("mxid", mxid));
		return paramsaaa;
	}

	public List<BasicNameValuePair> paramsinitNickname(String nickName,
			String password) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("name", nickName));
		paramsaaa.add(new BasicNameValuePair("password", password));
		paramsaaa.add(new BasicNameValuePair("invite_code", editInvite.getText().toString()));
		return paramsaaa;
	}

	private void init() {
		intent = new Intent();
		//token = getIntent().getStringExtra(Constant.BUNDLE_TOKEN);
		layout_regist_password = (RelativeLayout) findViewById(R.id.layout_regist_password);
		textRegistAgree = (TextView) findViewById(R.id.textRegistAgree);
		SpannableStringBuilder style = new SpannableStringBuilder(
				getResources().getString(R.string.txtRegistAgree));
		int color = getResources().getColor(R.color.color_light_blue);
		ClickableSpan clickSpan = new NoLineClickSpan(style.toString());
		style.setSpan(clickSpan, 8, 12, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style.setSpan(new ForegroundColorSpan(color), 8, 12,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		textRegistAgree.setText(style);
		textRegistAgree.setMovementMethod(LinkMovementMethod.getInstance());
		editNickName = (EditText) findViewById(R.id.editUsername);
		editPswd = (EditText) findViewById(R.id.editPassword);
		editPswdRepeat = (EditText) findViewById(R.id.editPasswordRepeat);
		editInvite = (EditText) findViewById(R.id.editInvite);
		// loadingDialog = new LoadingDialog(this, R.string.tip_logining);
	}

	private void onClick() {
		layout_regist_password.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		textRegistAgree.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.setClass(getApplicationContext(),
						UserAgreementActivity.class);
				startActivity(intent);
			}
		});
		/*findViewById(R.id.btnBack).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});*/
	/*	findViewById(R.id.btnNext).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (editNickName.getText().toString().trim()
								.equals(Constant.EMPTY_STR)) {
							ToastUtil.show(getResources().getString(
									R.string.error_no_nickname),PasswordRegistActivity.this);
						} else if (editPswd.getText().toString().trim()
								.equals(Constant.EMPTY_STR)) {
							ToastUtil.show(getResources().getString(
									R.string.error_no_password),PasswordRegistActivity.this);
						} else if (editPswd.getText().toString().length() < 6) {
							ToastUtil.show(getResources().getString(
									R.string.error_no_password_six),PasswordRegistActivity.this);
						} else if (editPswdRepeat.getText().toString().trim()
								.equals(Constant.EMPTY_STR)) {

							ToastUtil.show(getResources().getString(
									R.string.error_no_repeatpassword),PasswordRegistActivity.this);
						} else if (!editPswd
								.getText()
								.toString()
								.trim()
								.equals(editPswdRepeat.getText().toString()
										.trim())) {
							ToastUtil.show(getResources().getString(
									R.string.error_repeat_password),PasswordRegistActivity.this);
						} else {
							setLoadingDialog(R.string.tip_logining);
							nickName = editNickName.getText().toString();
							passwrod = editPswd.getText().toString();
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("token", token);

						*//*	addTask(PasswordRegistActivity.this, new IHttpTask(
									Urls.PERSONALDATA_IRL, map,
									paramsinitNickname(nickName, passwrod),
									MxPersonalDataResult.class),
									HttpConfig.PUT, ANDPASSWORD);*//*
						}
					}
				});*/
	}

	public void setLoadingDialog(int string) {

		dialog = ProgressDialog.show(this, "提示", getResources().getString(string));
	}

	public void stoploadingDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	/*@Override
	public void onGetData(Object data, int requestCode, String response) {
		stoploadingDialog();
		switch (requestCode) {
		case PERSON:
			MxPersonalDataResult person = (MxPersonalDataResult) data;
			if (person.getCode() == 1) {
				isLoginSuccess = true;
				if (person.getData() != null
						&& person.getData().getUser() != null) {
					user.setAddress(person.getData().getUser().getAddress());
					user.setInterests(person.getData().getUser().getInterests());
					user.setOften(person.getData().getUser().getOften());
					user.setSkill(person.getData().getUser().getSkill());
					user.setTarget(person.getData().getUser().getTarget());
					user.setIdentity(person.getData().getUser().getIdentity());
					user.setInterests(person.getData().getUser().getInterests());
					user.setSignature(person.getData().getUser().getSignature());
				}
			}
			break;
		case ANDPASSWORD:
			MxPersonalDataResult dataResult = (MxPersonalDataResult) data;
			if (dataResult.getCode() == 1) {
				// 获取个人信息
				isLoginSuccess = true;
				App.mInstance.setUser(dataResult.getData().getUser());
//				SharePrefUtils.setLogin(this,true);
				Intent i = new Intent(this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				chatLogin("" + dataResult.getData().getUser().getMxid(),
						"123456");
				finish();
				// addTask(this, new IHttpTask(UrlLink.PERSONALDATA_IRL,
				// paramsinit2("" + dataResult.getData().getUser().getMxid()),
				// MxPersonalDataResult.class), HttpConfig.GET, PERSON);
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
//						LoginActivity.setUserHearder(username.getMxid() + "",
//								username);

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
		default:
			break;
		}
	}*/

	String currentUsername;
	String currentPassword;
	boolean issuccess = false;
	int num = 0;
	Handler handler = new Handler();

	public void chatLogin(final String currentUsername,
			final String currentPassword) {
		this.currentUsername = currentUsername;
		this.currentPassword = currentPassword;

		handler.postDelayed(loginrun, 500);
		// 调用sdk登陆方法登陆聊天服务器

	}

	Runnable loginrun = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (issuccess == false && num < 10) {
//				Login(currentUsername, currentPassword);
				num++;
				handler.postDelayed(loginrun, 1000 * num);

			}
		}
	};

//	public void Login(final String currentUsername, final String currentPassword) {
//		EMChatManager.getInstance().login(currentUsername, currentPassword,
//				new EMCallBack() {
//
//					@Override
//					public void onSuccess() {
//						MXLog.d("chat", "Success");
//						issuccess = true;
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
//						MXLog.d("chat", "Progress");
//					}
//
//					@Override
//					public void onError(final int code, final String message) {
//						MXLog.d("chat", "Error");
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
//	}
//
//	private void initializeContacts() throws EaseMobException {
//		Map<String, UserCoach> userlist = new HashMap<String, UserCoach>();
//		UserDao dao = new UserDao(this);
//		userlist = dao.getContactList();
//		final List<String> usernames = EMContactManager.getInstance()
//				.getContactUserNames();
//		if (userlist != null && userlist.size() != 0) {
//			// 存入内存
//			BaseApp.mInstance.setContactList(userlist);
//		} else {
//			userlist = new HashMap<String, UserCoach>();
//			for (String username : usernames) {
//				UserCoach UserCoach = new UserCoach();
//				UserCoach.setUsername(username);
//				LoginActivity.setUserHearder(username, UserCoach);
//				userlist.put(username, UserCoach);
//			}
//			BaseApp.mInstance.setContactList(userlist);
//		}
//
//		addTask(this, new IHttpTask(UrlLink.FRIENDS_URL,
//				friendsParamsinit(usernames), FriendsResult.class),
//				HttpConfig.GET, FRIENDS_CODE);
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

	/*@Override
	public void onError(String reason, int requestCode) {

	}*/

	private class NoLineClickSpan extends ClickableSpan {
		String text;

		public NoLineClickSpan(String text) {
			super();
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Color.TRANSPARENT);
			ds.setUnderlineText(false);
		}

		@Override
		public void onClick(View v) {
			Log.e(TAG, "color font has been clicked");
			// processHyperLinkClick(text);
		}
	}
}
