package com.leyuan.aidong.http.subscriber;

import android.content.Context;

import com.leyuan.aidong.http.api.exception.NotLoginException;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.dialog.BaseDialog;
import com.leyuan.aidong.widget.dialog.ButtonOkListener;
import com.leyuan.aidong.widget.dialog.DialogSingleButton;


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
                    }
                }).show();
    }

}
