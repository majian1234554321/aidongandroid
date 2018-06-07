package com.leyuan.aidong.http.subscriber.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.example.aidong.R;
import com.leyuan.aidong.widget.CustomProgressDialog;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener progressCancelListener;

    public interface ProgressCancelListener {
        void onCancelProgress();
    }

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,boolean cancelable) {
        super();
        this.context = context;
        this.progressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(){
        if (pd == null) {
            pd = new CustomProgressDialog(context, R.style.progressDialog);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        progressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }


    public void dismissProgressDialog2(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
        ((Activity)context).finish();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }


}
