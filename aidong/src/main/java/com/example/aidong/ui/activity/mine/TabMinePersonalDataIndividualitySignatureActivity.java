package com.example.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.leyuan.commonlibrary.util.ToastUtil;


public class TabMinePersonalDataIndividualitySignatureActivity extends
        BaseActivity {
    private ImageView mlayout_tab_mine_personal_data_individuality_signature_img;
    private TextView mlayout_tab_mine_personal_data_individuality_signature_txt;
    private EditText medittext_the_individuality_signature;
    private static final int MAX_COUNT = 120;
    private TextView mtextview_the_individuality_signature_zishu;
    private RelativeLayout individuality_signature = null;
    private String individualitysignature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_tab_mine_personal_data_individuality_signature);
        init();
    }

    protected void initData() {
        onClick();
        setLeftCount();
    }

    private void init() {
        mlayout_tab_mine_personal_data_individuality_signature_img = (ImageView) findViewById(R.id.layout_tab_mine_personal_data_individuality_signature_img);
        mlayout_tab_mine_personal_data_individuality_signature_txt = (TextView) findViewById(R.id.layout_tab_mine_personal_data_individuality_signature_txt);
        medittext_the_individuality_signature = (EditText) findViewById(R.id.edittext_the_individuality_signature);
        mtextview_the_individuality_signature_zishu = (TextView) findViewById(R.id.textview_the_individuality_signature_zishu);
        individuality_signature = (RelativeLayout) findViewById(R.id.individuality_signature);
        Intent intent = getIntent();
        String singnature = intent.getStringExtra("singnature");
        medittext_the_individuality_signature.setText(singnature);
        medittext_the_individuality_signature
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
        medittext_the_individuality_signature
                .setFilters(new InputFilter[]{filter});
    }

    private final int maxLen = 120;
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
                ToastUtil.show(getResources().getString(R.string.yourlength),TabMinePersonalDataIndividualitySignatureActivity.this);
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
                ToastUtil.show(getResources().getString(R.string.yourlength),TabMinePersonalDataIndividualitySignatureActivity.this);
            }

            return src.subSequence(0, sindex);
        }
    };

    private void onClick() {
        individuality_signature.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), 0);
            }
        });
        mlayout_tab_mine_personal_data_individuality_signature_img
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        mlayout_tab_mine_personal_data_individuality_signature_txt
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        individualitysignature = medittext_the_individuality_signature
                                .getText().toString();
                        Intent data = new Intent();
                        data.putExtra("individualitysignature",
                                individualitysignature);
                        setResult(0, data);
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
        mtextview_the_individuality_signature_zishu.setText(String
                .valueOf((MAX_COUNT - getInputCount())));
    }

    /**
     * 获取用户输入的分享内容字数
     *
     * @return
     */
    private long getInputCount() {
        return calculateLength(medittext_the_individuality_signature.getText()
                .toString());
    }


}
