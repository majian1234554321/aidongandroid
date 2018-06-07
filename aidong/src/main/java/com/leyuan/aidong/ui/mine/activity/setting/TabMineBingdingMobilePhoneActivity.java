package com.leyuan.aidong.ui.mine.activity.setting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.ActivityTool;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TabMineBingdingMobilePhoneActivity extends BaseActivity
{
	private ImageView mlayout_tab_mine_binding_mobile_phone_img_back;
	private Button mlayout_tab_mine_submit_binding_btn;
	private Button layout_tab_mine_input_phone_number_btn;
	private EditText layout_tab_mine_bingding_dianhua;
	private EditText layout_tab_mine_enter_the_verification_code_edit;
	private String BingToken;
	private RelativeLayout bingding_mobile_phone = null;
	private static final int PASSWORD = 0;
	private static final int BANGDING = 1;
	private Intent intent;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_tab_mine_bingding_mobile_phone);
		init();
	}

	protected void initData() {
		onClick();
	}

	private void init() {
		mlayout_tab_mine_binding_mobile_phone_img_back = (ImageView) findViewById(R.id.layout_tab_mine_binding_mobile_phone_img_back);
		mlayout_tab_mine_submit_binding_btn = (Button) findViewById(R.id.layout_tab_mine_submit_binding_btn);
		layout_tab_mine_input_phone_number_btn = (Button) findViewById(R.id.layout_tab_mine_input_phone_number_btn);
		layout_tab_mine_bingding_dianhua = (EditText) findViewById(R.id.layout_tab_mine_bingding_dianhua);
		bingding_mobile_phone = (RelativeLayout) findViewById(R.id.bingding_mobile_phone);
		layout_tab_mine_enter_the_verification_code_edit = (EditText) findViewById(R.id.layout_tab_mine_enter_the_verification_code_edit);
		intent = new Intent();
	}

	public void stoploadingDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	public void setLoadingDialog(String string) {
		dialog = ProgressDialog.show(this, "提示", string);
	}
	public void setLoadingDialog(int string) {
		dialog = ProgressDialog.show(this, "提示", getResources().getString(string));
	}

	private void onClick() {
		bingding_mobile_phone.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		mlayout_tab_mine_binding_mobile_phone_img_back
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mlayout_tab_mine_submit_binding_btn
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
					 if (layout_tab_mine_enter_the_verification_code_edit
								.getText().toString().trim().length() == 0) {
							//ToastUtil.show(getResources().getString(
								//	R.string.pleasecode),TabMineBingdingMobilePhoneActivity.this);
						} else if (layout_tab_mine_bingding_dianhua.getText()
								.toString().trim().length() == 0) {
						// ToastUtil.show(getResources().getString(
								// R.string.pleasenumber),TabMineBingdingMobilePhoneActivity.this);
						} else {
							setLoadingDialog(R.string.tip_getVerificationcode);
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("token", BingToken);
							/*addTask(TabMineBingdingMobilePhoneActivity.this,
									new IHttpTask(
											UrlLink.CAPTCHACHECK_URL,map,
											paramsinit2(
													layout_tab_mine_enter_the_verification_code_edit),
											Captcha.class), HttpConfig.POST,
											BANGDING);*/
						}
					}
				});
		layout_tab_mine_input_phone_number_btn
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (layout_tab_mine_bingding_dianhua.getText()
								.toString().trim().length() == 0) {
							//ToastUtil.show(getResources().getString(
									//R.string.pleasenumber),TabMineBingdingMobilePhoneActivity.this);
						} else {
							if (!ActivityTool
									.isMobileNO(layout_tab_mine_bingding_dianhua
											.getText().toString().trim())) {
							//	ToastUtil.show(getResources().getString(
									//	R.string.input_phone),TabMineBingdingMobilePhoneActivity.this);
							} else {
								setLoadingDialog(R.string.tip_getVerificationcode);								
								/*addTask(TabMineBingdingMobilePhoneActivity.this,
										new IHttpTask(
												UrlLink.BINGDINGIPHONE,
												paramsinit(layout_tab_mine_bingding_dianhua),
												Captcha.class),
										HttpConfig.POST, PASSWORD);*/
							}

						}
					}
				});

	}

	/*@Override
	public void onGetData(Object data, int requestCode, String response) {
		stoploadingDialog();
		switch (requestCode) {
		case PASSWORD:
			Captcha cres = (Captcha) data;
			if (cres.getCode() == 1) {
				BingToken = cres.getToken();
				
			}
			break;
		case BANGDING:
			Captcha rres = (Captcha) data;
			if (rres.getCode() == 1) {
				ToastUtil.show(getResources().getString(
						R.string.bingding_success),TabMineBingdingMobilePhoneActivity.this);
				setResult(Constant.RC_BIND);
				finish();
			}
			break;

		default:
			break;
		}

	}*/

	public List<BasicNameValuePair> paramsinit(
			EditText layout_tab_mine_bingding_dianhua) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("mobile",
				layout_tab_mine_bingding_dianhua.getText().toString());
		paramsaaa.add(pair1);
		return paramsaaa;
	}

	public List<BasicNameValuePair> paramsinit2(
			EditText layout_tab_mine_enter_the_verification_code_edit) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair pair1 = new BasicNameValuePair("captcha",
				layout_tab_mine_enter_the_verification_code_edit.getText().toString());
		paramsaaa.add(pair1);
		return paramsaaa;
	}
	/*@Override
	public void onError(String reason, int requestCode) {
		// TODO Auto-generated method stub
		stoploadingDialog();
	}*/
}
