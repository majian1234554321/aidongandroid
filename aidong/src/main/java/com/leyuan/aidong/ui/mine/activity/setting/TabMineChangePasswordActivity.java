package com.leyuan.aidong.ui.mine.activity.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.leyuan.aidong.ui.BaseActivity;


public class TabMineChangePasswordActivity extends BaseActivity {
    private static final int CHANGEPASSWORD = 0;
    private Button mlayout_tab_mine_change_password_btn;
    private EditText mverification_edittext;
    private EditText minput_new_edittext;
    private EditText mrepeat_new_edittext;
    private RelativeLayout change_password = null;
    private ImageView layout_tab_mine_change_password_img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        setContentView(R.layout.layout_tab_mine_change_password);
        layout_tab_mine_change_password_img_back = (ImageView) findViewById(R.id.layout_tab_mine_change_password_img_back);
    }

    protected void initData() {
        layout_tab_mine_change_password_img_back
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        mlayout_tab_mine_change_password_btn = (Button) findViewById(R.id.layout_tab_mine_change_password_btn);
        mverification_edittext = (EditText) findViewById(R.id.verification_edittext);
        change_password = (RelativeLayout) findViewById(R.id.change_password);
        minput_new_edittext = (EditText) findViewById(R.id.input_new_edittext);
        mrepeat_new_edittext = (EditText) findViewById(R.id.repeat_new_edittext);
        mverification_edittext
                .setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
        minput_new_edittext
                .setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
        mrepeat_new_edittext
                .setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
        change_password.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), 0);
            }
        });
        mverification_edittext
                .setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        return false;
                    }
                });
        minput_new_edittext
                .setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        return false;
                    }
                });
        mrepeat_new_edittext
                .setCustomSelectionActionModeCallback(new ActionMode.Callback() {

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        return false;
                    }
                });
        mlayout_tab_mine_change_password_btn
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chagePassword(mverification_edittext,
                                minput_new_edittext, mrepeat_new_edittext)) {

                        }

                    }
                });
    }

    private boolean chagePassword(EditText mverification_edittext,
                                  EditText minput_new_edittext, EditText mrepeat_new_edittext) {
        if (mverification_edittext.getText().toString() == null
                || mverification_edittext.getText().toString().equals("")) {
            mverification_edittext.setError(Html
                    .fromHtml("<font color=#808183>"
                            + getResources().getString(
                            R.string.pleaseinputpassword) + "</font>"));
            return false;
        }
        if (minput_new_edittext.getText().toString() == null
                || minput_new_edittext.getText().toString().equals("")) {
            minput_new_edittext.setError(Html.fromHtml("<font color=#808183>"
                    + getResources().getString(R.string.pleasenewpassword)
                    + "</font>"));
            return false;
        }
        if (mrepeat_new_edittext.getText().toString() == null
                || mrepeat_new_edittext.getText().toString().equals("")) {
            mrepeat_new_edittext.setError(Html.fromHtml("<font color=#808183>"
                    + getResources().getString(R.string.pleasenewpassword)
                    + "</font>"));
            return false;

        }
        if (!minput_new_edittext.getText().toString()
                .equals(mrepeat_new_edittext.getText().toString())) {
            mrepeat_new_edittext.setError(Html.fromHtml("<font color=#808183>"
                    + getResources().getString(R.string.repeatpassword)
                    + "</font>"));
            return false;
        }
        if (minput_new_edittext.getText().toString().length() < 6
                || mrepeat_new_edittext.getText().toString().length() < 6) {
            mrepeat_new_edittext.setError(Html.fromHtml("<font color=#808183>"
                    + getResources().getString(R.string.six_password)
                    + "</font>"));
            return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.FLAG_CANCELED) {
            finish(); // 返回键的物理代码
        }
        return super.onKeyDown(keyCode, event);
    }
}
