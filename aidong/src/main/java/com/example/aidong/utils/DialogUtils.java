package com.example.aidong.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;


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
        if (context == null) return;
        dialog = wattingDialog(context, message, cancelable);


        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
// 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些
// 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    private static Dialog wattingDialog(Context context, String message, boolean cancelable) {
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.progress_dialog, null);

        ImageView imageView = view.findViewById(R.id.loading);
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, imageView);

        Dialog m_pDialog = new Dialog(context,
                R.style.MyDialog2);
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
