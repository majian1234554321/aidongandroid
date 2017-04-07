package com.leyuan.aidong.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;


@Deprecated
public class DialogUtils {

    private static Dialog dialog;

    /**
     * you must call releaseDialog when view destroyed
     */
    public static void showDialog(Context context, String message, boolean cancelable) {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
        dialog = wattingDialog(context, message, cancelable);
        dialog.show();
    }

    private static Dialog wattingDialog(Context context, String message, boolean cancelable) {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.progress_dialog, null);
        TextView tv_message = (TextView) view
                .findViewById(R.id.tv_message);
        if (null == message || "".equals(message)) {
            tv_message.setVisibility(View.GONE);
        } else {
            tv_message.setText(message);
        }
        Dialog m_pDialog = new Dialog(context,
                R.style.MyDialog);
        m_pDialog.setContentView(view);
        m_pDialog.setCancelable(cancelable);
        return m_pDialog;
    }

    public static void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void releaseDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }
}
