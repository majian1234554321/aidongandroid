package com.example.aidong.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aidong.R;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .utils.Logger;


public class DialogImageIdentify extends Dialog {

    private static final java.lang.String TAG = "DialogImageIdentify";
    private ImageView imgIdentify;
    private TextView txtRefresh;
    private ImageView imgClose;
    private OnInputCompleteListener listener;
    private Context context;


    public DialogImageIdentify(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    private EditText getEditFirst() {
        return (EditText) findViewById(R.id.edit_first);
    }

    private EditText getEditSecond() {
        return (EditText) findViewById(R.id.edit_second);
    }

    private EditText getEditThird() {
        return (EditText) findViewById(R.id.edit_third);
    }

    private EditText getEditFourth() {
        return (EditText) findViewById(R.id.edit_fourth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_identify);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    private void initView() {
        imgIdentify = (ImageView) findViewById(R.id.img_identify);
        txtRefresh = (TextView) findViewById(R.id.txt_refresh);
        imgClose = (ImageView) findViewById(R.id.img_close);
    }

    private void initData() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txtRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.refreshImage();
                }
            }
        });

    }

    public void refreshImage(String mobile) {
        Logger.i(TAG,"refreshImage mobile = " +mobile);
        Glide.with(context).load(ConstantUrl.CAPTCHA_IMAGE + mobile + "?time=" + System.currentTimeMillis())
                .into(imgIdentify);
    }

    private void initEvent() {
        getEditFirst().requestFocus();

        getEditFirst().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                String sFirst = getEditFirst().getText().toString().trim();
                callBackListnerByIndentify();
                if (!TextUtils.isEmpty(sFirst))
                    getEditSecond().requestFocus();
            }
        });

        getEditSecond().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                String sFirst = getEditSecond().getText().toString().trim();
                callBackListnerByIndentify();
                if (!TextUtils.isEmpty(sFirst))
                    getEditThird().requestFocus();
            }
        });
        getEditThird().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                String sFirst = getEditThird().getText().toString().trim();
                callBackListnerByIndentify();
                if (!TextUtils.isEmpty(sFirst))
                    getEditFourth().requestFocus();
            }
        });

        getEditFourth().addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                callBackListnerByIndentify();
            }


        });

        getEditSecond().setOnKeyListener(new View.OnKeyListener() {
            int num = 0;

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Logger.i("imageIdentify", "KeyEvent.KEYCODE_DEL");
                    num++;
                    if (num % 2 == 0) {
                        String s = getEditSecond().getText().toString().trim();
                        if (s.length() > 0) {
                            getEditSecond().setText("");
                        } else {
                            getEditFirst().requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        getEditThird().setOnKeyListener(new View.OnKeyListener() {
            int num = 0;

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Logger.i("imageIdentify", "KeyEvent.KEYCODE_DEL");
                    num++;
                    if (num % 2 == 0) {
                        String s = getEditThird().getText().toString().trim();
                        if (s.length() > 0) {
                            getEditThird().setText("");
                        } else {
                            getEditSecond().requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        getEditFourth().setOnKeyListener(new View.OnKeyListener() {
            int num = 0;

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    Logger.i("imageIdentify", "KeyEvent.KEYCODE_DEL");
                    num++;
                    if (num % 2 == 0) {
                        String s = getEditFourth().getText().toString().trim();
                        if (s.length() > 0) {
                            getEditFourth().setText("");
                        } else {
                            getEditThird().requestFocus();
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void callBackListnerByIndentify() {
        String sFirst = getEditFirst().getText().toString().trim();
        String sSecond = getEditSecond().getText().toString().trim();
        String sThird = getEditThird().getText().toString().trim();
        String sFourth = getEditFourth().getText().toString().trim();

        if (!TextUtils.isEmpty(sFirst) && !TextUtils.isEmpty(sSecond) && !TextUtils.isEmpty(sThird) && !TextUtils.isEmpty(sFourth)) {
            if (listener != null) {
                listener.inputIdentify(sFirst + sSecond + sThird + sFourth);
            }

        }

    }


    public void setOnInputCompleteListener(OnInputCompleteListener listener) {
        this.listener = listener;
    }

    public void clearContent() {
        getEditFirst().setText("");
        getEditSecond().setText("");
        getEditThird().setText("");
        getEditFourth().setText("");
        getEditFirst().requestFocus();

    }

    public interface OnInputCompleteListener {
        void inputIdentify(String imageIndentify);

        void refreshImage();
    }


    class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //              Logger.i("imageIdentify","s = " +  s.toString());
        }
    }
}

