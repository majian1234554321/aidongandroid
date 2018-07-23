package com.example.aidong.http.subscriber.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong.utils.GlideLoader;


public class ProgressDialogHandler extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener progressCancelListener;

    public interface ProgressCancelListener {
        void onCancelProgress();
    }

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.context = context;
        this.progressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null) {
            //  pd = new CustomProgressDialog(context, R.style.progressDialog);


            View view = View.inflate(context, R.layout.progress_dialog, null);

            ImageView imageView = view.findViewById(R.id.loading);
            GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, imageView);

            pd = new Dialog(context,
                    R.style.MyDialog2);
            pd.setContentView(view);


            WindowManager.LayoutParams params = pd.getWindow().getAttributes();
// 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些
// 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            pd.getWindow().setAttributes(params);


            pd.setCancelable(cancelable);


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

    private void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }


    public void dismissProgressDialog2() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
        ((Activity) context).finish();
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
