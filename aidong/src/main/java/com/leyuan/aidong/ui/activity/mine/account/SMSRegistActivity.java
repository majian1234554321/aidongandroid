package com.leyuan.aidong.ui.activity.mine.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.result.CaptchaResult;
import com.leyuan.aidong.http.HttpConfig;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.common.UrlLink;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SMSRegistActivity extends BaseActivity implements IHttpCallback {
	private static final int VERCODE = 0;
	private static final int BANGDING = 1;
	private Button mbtnGetActiveCode;
	private TimeCount time;
	private EditText meditPhonenum, mEditActiveCode;
	private RelativeLayout layout_regist_sms = null;
	private String mobile;
	private String BingToken;
	private Intent intent;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_regist_sms);
		init();
	}

	protected void initData() {
		intent = new Intent();
		time = new TimeCount(60000, 1000);
		onClick();
	}

	private void init() {
		layout_regist_sms = (RelativeLayout) findViewById(R.id.layout_regist_sms);
		mbtnGetActiveCode = (Button) findViewById(R.id.btnGetActiveCode);
		mEditActiveCode = (EditText) findViewById(R.id.editActiveCode);
		meditPhonenum = (EditText) findViewById(R.id.editPhonenum);
	}

	private void onClick() {
//		layout_regist_sms.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//				return imm.hideSoftInputFromWindow(getCurrentFocus()
//						.getWindowToken(), 0);
//			}
//		});
		findViewById(R.id.btnBack).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		findViewById(R.id.btnNext).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (mEditActiveCode.getText().toString().trim()
								.length() > 0) {
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("token", BingToken);
							addTask(SMSRegistActivity.this, new IHttpTask(
									UrlLink.CAPTCHACHECK_URL, map,
									paramsinit2(mEditActiveCode),
									CaptchaResult.class), HttpConfig.POST,
									BANGDING);
						} else {
							ToastUtil.show(getResources().getString(
									R.string.error_no_activecode),SMSRegistActivity.this);
						}
					}
				});
		mbtnGetActiveCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (meditPhonenum.getText().toString().trim().length() == 0) {
					ToastUtil.show(getResources().getString(
							R.string.input_phone),SMSRegistActivity.this);
				} else {
					mobile = meditPhonenum.getText().toString().trim();
					addTask(SMSRegistActivity.this, new IHttpTask(
							UrlLink.CAPTCHA_URL, paramsinit(mobile),
							CaptchaResult.class), HttpConfig.POST, VERCODE);
				}

			}
		});
	}

	public List<BasicNameValuePair> paramsinit(String mobile) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("mobile", mobile));
		return paramsaaa;
	}

	public List<BasicNameValuePair> paramsinit2(EditText mEditActiveCode) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("captcha",
				mEditActiveCode.getText().toString());
		paramsaaa.add(pair1);
		return paramsaaa;
	}

	@Override
	public void onGetData(Object data, int requestCode, String response) {
		switch (requestCode) {
		case VERCODE:
			CaptchaResult captchaResult = (CaptchaResult) data;
			if (time != null) {
				time.cancel();
				time = null;
			}
			mbtnGetActiveCode.setClickable(true);
			mbtnGetActiveCode.setText(R.string.getActiveCode);
			if (captchaResult.getCode() == 1) {
				BingToken = captchaResult.getData().getToken();
				if (time == null) {
					time = new TimeCount(60000, 1000);
				}
				time.start();
			}
			break;
		case BANGDING:
			CaptchaResult captchaResult1 = (CaptchaResult) data;
			if (captchaResult1.getCode() == 1) {
				intent.setClass(SMSRegistActivity.this,
						PasswordRegistActivity.class);
				intent.putExtra(Constant.BUNDLE_TOKEN, captchaResult1.getData().getToken());
				startActivity(intent);
				finish();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onError(String reason, int requestCode) {

	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mbtnGetActiveCode.setClickable(true);
			mbtnGetActiveCode.setText(R.string.getActiveCode);
			meditPhonenum.setClickable(true);
			meditPhonenum.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mbtnGetActiveCode.setClickable(false);
			mbtnGetActiveCode.setText(millisUntilFinished / 1000 + "S");
			meditPhonenum.setClickable(false);
			meditPhonenum.setEnabled(false);
		}
	}
}
