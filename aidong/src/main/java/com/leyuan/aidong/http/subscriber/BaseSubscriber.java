package com.leyuan.aidong.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.http.api.exception.NotLoginException;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogSingleButton;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;


public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Context context;

    public BaseSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, R.string.connect_timeout, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, R.string.connect_break, Toast.LENGTH_SHORT).show();
        } else if (e instanceof NotLoginException) {
            showLoginDialog(e);
        } else {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompleted() {

    }

    private void showLoginDialog(Throwable e) {
        new DialogSingleButton(context).setContentDesc(e.getMessage())
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        App.mInstance.exitLogin();
                        UiManager.activityJump(context, LoginActivity.class);
                    }
                }).show();
    }

}
