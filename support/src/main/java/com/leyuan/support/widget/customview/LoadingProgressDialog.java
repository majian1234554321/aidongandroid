package com.leyuan.support.widget.customview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;

import com.leyuan.support.R;

/**
 * 加载中的Dialog
 * Created by song on 2016/8/25.
 */
public class LoadingProgressDialog extends ProgressDialog {

    public LoadingProgressDialog(Context context) {
        super(context, R.style.Progress_Dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_progress_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
