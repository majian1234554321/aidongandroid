package com.leyuan.aidong.ui.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.result.MsgResult;
import com.leyuan.aidong.http.HttpConfig;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Utils;
import com.leyuan.aidong.utils.common.UrlLink;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class TabMineFeedBackActivity extends BaseActivity implements
		IHttpCallback {
	private Button mlayout_feedback_btn_commit;
	private EditText mlayout_tab_mine_feedback_edit,
			mlayout_tab_mine_feedback_input_edit;
	private RelativeLayout feedback = null;
	private static final int FEEDBACK = 0;
	private ImageView layout_tab_mine_feedback_img_back;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}

	protected void setupView() {
		setContentView(R.layout.layout_tab_mine_feedback);
		layout_tab_mine_feedback_img_back = (ImageView) findViewById(R.id.layout_tab_mine_feedback_img_back);
	}

	protected void initData() {
		layout_tab_mine_feedback_img_back
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mlayout_feedback_btn_commit = (Button) findViewById(R.id.layout_feedback_btn_commit);
		mlayout_tab_mine_feedback_edit = (EditText) findViewById(R.id.layout_tab_mine_feedback_edit);
		feedback = (RelativeLayout) findViewById(R.id.feedback);
		mlayout_tab_mine_feedback_input_edit = (EditText) findViewById(R.id.layout_tab_mine_feedback_input_edit);
		feedback.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		mlayout_feedback_btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String feedback_edit = mlayout_tab_mine_feedback_edit.getText()
						.toString();
				String feedback_input_edit = mlayout_tab_mine_feedback_input_edit
						.getText().toString();
				if (feedback_edit.equals("")) {
					ToastUtil.show(getResources().getString(
							R.string.please_feekback),TabMineFeedBackActivity.this);
				} else if (feedback_input_edit.equals("")) {
					ToastUtil.show(getResources().getString(
							R.string.please_emailnumber),TabMineFeedBackActivity.this);
				} else if (!Utils.isMobileNO(feedback_input_edit)
						&& !Utils.isEmail(feedback_input_edit)) {
					ToastUtil.show(getResources().getString(
							R.string.please_emailnumber),TabMineFeedBackActivity.this);
				} else if (feedback_edit != "" && feedback_input_edit != "") {
					addTask(TabMineFeedBackActivity.this,
							new IHttpTask(UrlLink.FEEDBACK, paramsinit(
									feedback_edit, feedback_input_edit),
									MsgResult.class), HttpConfig.POST, FEEDBACK);
				}
			}

		});
	}

	public List<BasicNameValuePair> paramsinit(String content, String contact) {
		List<BasicNameValuePair> paramsaaa = new ArrayList<BasicNameValuePair>();
		paramsaaa.add(new BasicNameValuePair("content", content));
		paramsaaa.add(new BasicNameValuePair("contact", "" + contact));
		return paramsaaa;
	}

	@Override
	public void onGetData(Object data, int requestCode, String response) {
		switch (requestCode) {
		case FEEDBACK:
			MsgResult res = (MsgResult) data;
			if (res.getCode() == 1) {
				ToastUtil.show(getResources().getString(
						R.string.tip_feedback),TabMineFeedBackActivity.this);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.FLAG_CANCELED) {
			finish(); // 返回键的物理代码
		}
		return super.onKeyDown(keyCode, event);
	}
}
