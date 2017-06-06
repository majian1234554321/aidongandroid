package com.leyuan.aidong.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.leyuan.aidong.R;


/**
 * Created by user on 2017/1/12.
 */

public abstract class BaseDialog extends Dialog {
    protected Context context;

    protected BaseDialog(Context context) {
        this(context, R.style.selectorDialog);
    }

    protected BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        setContentView(inflateView(LayoutInflater.from(context)));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initData();
    }

    protected abstract void initData();

    protected abstract View inflateView(LayoutInflater inflater);


}
