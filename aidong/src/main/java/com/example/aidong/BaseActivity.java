package com.example.aidong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.http.Logic;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

public class BaseActivity extends AppCompatActivity implements IHttpToastCallBack {
    protected Logic logic;
    protected TextView txtTopTitle, txtTopLeft, txtTopRight;
    protected ImageView btnBack;
    protected ImageButton btnTopRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logic = new Logic();
    }

    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode) {
        logic.doLogic(callBack, tsk, method, requestCode, this);
    }

    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode, IHttpToastCallBack base) {
        logic.doLogic(callBack, tsk, method, requestCode, base);
    }

    @Override
    public void showToastOnUiThread(CharSequence msg) {
        //show msg when net error
    }

    protected void initTop(String title) {
        initTop(title, false);
    }

    protected void initTop(String title, boolean hasback) {
        initTop(title, hasback, null);
    }

    protected void initTop(String title, boolean hasback, String rightTxt) {
        initTop(title, hasback, null, rightTxt, -1);
    }

    protected void initTop(String title, boolean hasback, int rightImgaeId) {
        initTop(title, hasback, null, null, rightImgaeId);
    }

    protected void initTop(String title, String leftTxt) {
        initTop(title, leftTxt, null);
    }

    protected void initTop(String title, String leftTxt, String rightTxt) {
        initTop(title, false, leftTxt, rightTxt, -1);
    }

    protected void initTop(String title, String leftTxt, int rightImgaeId) {
        initTop(title, false, leftTxt, null, rightImgaeId);
    }

    private void initTop(String title, boolean hasback, String leftTxt,
                         String rightTxt, int rightImgaeId) {
        txtTopTitle = (TextView) findViewById(R.id.txtTopTitle);
        txtTopLeft = (TextView) findViewById(R.id.txtTopLeft);
        txtTopRight = (TextView) findViewById(R.id.txtTopRight);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnTopRight = (ImageButton) findViewById(R.id.btnTopRight);

        txtTopTitle.setText(title);

        if (hasback) {
            btnBack.setVisibility(View.VISIBLE);
            btnBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mfinish();
                }
            });
        }

        if (leftTxt != null) {
            txtTopLeft.setVisibility(View.VISIBLE);
            txtTopLeft.setText(leftTxt);
            txtTopLeft.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mfinish();
                }
            });
        }

        if (rightTxt != null) {
            txtTopRight.setVisibility(View.VISIBLE);
            txtTopRight.setText(rightTxt);
            txtTopRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    rightTxtOnClick();
                }
            });
        }else{
            txtTopRight.setVisibility(View.GONE);
        }

        if (rightImgaeId > 0) {
            btnTopRight.setVisibility(View.VISIBLE);
            btnTopRight.setImageResource(rightImgaeId);
            btnTopRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    rightImageOnClick();
                }
            });
        }

    }

    protected void mfinish() {
        finish();
    }

    protected void rightTxtOnClick() {

    }

    protected void rightImageOnClick() {

    }
}
