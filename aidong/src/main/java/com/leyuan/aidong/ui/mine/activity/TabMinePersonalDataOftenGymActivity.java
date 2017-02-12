package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.ToastUtil;


public class TabMinePersonalDataOftenGymActivity extends BaseActivity {
	private ImageView mlayout_tab_mine_personal_data_often_go_to_the_gym_img;
	private TextView mlayout_tab_mine_personal_data_often_go_to_the_gym_txt;
	private EditText medittext_the_often_go_to_the_gym;
	private TextView mtextview_the_often_go_to_the_gym_zishu;
	private static final int MIN_COUNT = 10;
	private RelativeLayout gotothegym = null;
	private String often;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupView();
		initData();
	}


	protected void setupView() {
		setContentView(R.layout.layout_tab_mine_personal_data_often_go_to_the_gym);
		init();

	}

	protected void initData() {
		onClick();
		setLeftCount();
	}

	private void init() {
		mlayout_tab_mine_personal_data_often_go_to_the_gym_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_often_go_to_the_gym_img);
		medittext_the_often_go_to_the_gym = (EditText) findViewById(R.id.edittext_the_often_go_to_the_gym);
		mlayout_tab_mine_personal_data_often_go_to_the_gym_txt = (TextView) findViewById(R.id.layout_tab_mine_personal_data_often_go_to_the_gym_txt);
		mtextview_the_often_go_to_the_gym_zishu = (TextView) findViewById(R.id.textview_the_often_go_to_the_gym_zishu);
		gotothegym = (RelativeLayout) findViewById(R.id.gotothegym);
		Intent intent = getIntent();
		String gym = intent.getStringExtra("gym");
		medittext_the_often_go_to_the_gym.setText(gym);
		medittext_the_often_go_to_the_gym
				.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
						setLeftCount();
					}
				});
		medittext_the_often_go_to_the_gym
				.setFilters(new InputFilter[] { filter });
	}

	private final int maxLen = 10;
	InputFilter filter = new InputFilter() {

		@Override
		public CharSequence filter(CharSequence src, int start, int end,
				Spanned dest, int dstart, int dend) {
			int dindex = 0;
			double count = 0;

			while (count <= maxLen && dindex < dest.length()) {
				char c = dest.charAt(dindex++);
				if (c < 128) {
					count = count + 0.5;
				} else {
					count = count + 1;
				}
			}

			if (count > maxLen) {
				ToastUtil.show(getResources().getString(R.string.yourlength),TabMinePersonalDataOftenGymActivity.this);
				return dest.subSequence(0, dindex - 1);
			}

			int sindex = 0;
			while (count <= maxLen && sindex < src.length()) {
				char c = src.charAt(sindex++);
				if (c < 128) {
					count = count + 0.5;
				} else {
					count = count + 1;
				}
			}

			if (count > maxLen) {
				sindex--;
				ToastUtil.show(getResources().getString(R.string.yourlength),TabMinePersonalDataOftenGymActivity.this);
			}

			return src.subSequence(0, sindex);
		}
	};

	private void onClick() {
		gotothegym.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				return imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
			}
		});
		mlayout_tab_mine_personal_data_often_go_to_the_gym_img
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		mlayout_tab_mine_personal_data_often_go_to_the_gym_txt
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String often = medittext_the_often_go_to_the_gym
								.getText().toString();
						Intent data = new Intent();
						data.putExtra("often", often);
						setResult(5, data);
						finish();
					}
				});
	}

	/**
	 * 计算分享内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
	 * 
	 * @param c
	 * @return
	 */
	private long calculateLength(CharSequence c) {
		double len = 0;
		for (int i = 0; i < c.length(); i++) {
			int tmp = (int) c.charAt(i);
			if (tmp > 0 && tmp < 127) {
				len += 0.5;
			} else {
				len++;
			}
		}
		return Math.round(len);
	}

	/**
	 * 刷新剩余输入字数,最大值新浪微博是140个字，人人网是200个字
	 */
	private void setLeftCount() {
		mtextview_the_often_go_to_the_gym_zishu.setText(String
				.valueOf((MIN_COUNT - getInputCount())));
	}

	/**
	 * 获取用户输入的分享内容字数
	 * 
	 * @return
	 */
	private long getInputCount() {
		return calculateLength(medittext_the_often_go_to_the_gym.getText()
				.toString());
	}

}
