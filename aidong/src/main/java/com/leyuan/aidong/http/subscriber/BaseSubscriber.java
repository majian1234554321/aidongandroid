package com.leyuan.aidong.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.http.api.exception.NotLoginException;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.account.LoginActivity;
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
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, R.string.connect_timeout, Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, R.string.connect_break, Toast.LENGTH_SHORT).show();
        } else if (e instanceof NotLoginException) {
            new DialogSingleButton(context).setContentDesc(e.getMessage())
                    .setBtnOkListener(new ButtonOkListener() {
                        @Override
                        public void onClick(BaseDialog dialog) {
                            App.getInstance().exitLogin();
                            UiManager.activityJump(context, LoginActivity.class);
                        }
                    }).show();

//            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
