package com.leyuan.aidong.ui.mine.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.ToastUtil;


/**
 * Created by user on 2015/8/10.
 */
public class XiuGaiGeXinQianMing extends BaseActivity {
    private ImageView imageView_back;
    private EditText editText11;
    private TextView at_finish;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_signture);
       // content = getIntent().getExtras().getString(Constant.BUNDLE_CONTENT);
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        editText11 = (EditText) findViewById(R.id.editText11);
        if (content != null || !content.equals("")) {
            editText11.setText(content);
            Editable etext = editText11.getText();
            Selection.setSelection(etext, etext.length());
        }
        at_finish = (TextView) findViewById(R.id.at_finish);
        at_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sign = editText11.getText().toString().trim();
                if (TextUtils.isEmpty(sign)) {
                    ToastUtil.showConsecutiveShort("内容为空");
//                    ToastUtil.showToast(XiuGaiGeXinQianMing.this, "内容为空");
                } else {
                    if (sign.length() > 20) {
                        ToastUtil.showConsecutiveShort("最多20个字");
//                        ToastTools.showToast(XiuGaiGeXinQianMing.this, "最多20个字");
                    } else {
                        Intent intent = new Intent();
                       /* intent.putExtra(Constant.PERSION_SIGN,sign);
                         setResult(Constant.RESULT_CODE_SIGN,intent);*/
                        finish();
                    }

                }
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
