package com.example.aidong.http.subscriber;

import android.content.Context;

import com.example.aidong .http.api.exception.NotLoginException;
import com.example.aidong .ui.App;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .utils.UiManager;
import com.example.aidong .widget.dialog.BaseDialog;
import com.example.aidong .widget.dialog.ButtonOkListener;
import com.example.aidong .widget.dialog.DialogSingleButton;


public abstract class IsLoginSubscriber<T> extends BaseSubscriber<T> {


    public IsLoginSubscriber(Context context) {
        super(context);
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof NotLoginException) {
            showLoginDialog(e);
        }
    }


    private void showLoginDialog(Throwable e) {
        new DialogSingleButton(context).setContentDesc(e.getMessage())
                .setBtnOkListener(new ButtonOkListener() {
                    @Override
                    public void onClick(BaseDialog dialog) {
                        App.mInstance.exitLogin();
                        UiManager.activityJump(context, LoginActivity.class);
                        dialog.dismiss();
                    }
                }).show();
    }

}
