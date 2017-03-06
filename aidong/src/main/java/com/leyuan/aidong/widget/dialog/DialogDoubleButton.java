package com.leyuan.aidong.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leyuan.aidong.R;


/**
 * Created by user on 2017/1/12.
 */

public class DialogDoubleButton extends BaseDialog {
    private TextView txtTitle;
    private TextView txtContent;
    private Button btnCancle;
    private Button btnOk;

    public DialogDoubleButton(Context context) {
        super(context);
    }

    @Override
    protected View inflateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_common_base, null, false);
        txtTitle = (TextView) view.findViewById(R.id.txt_dialog_title);
        txtContent = (TextView) view.findViewById(R.id.txt_dialog_content);
        btnCancle = (Button) view.findViewById(R.id.btn_dialog_cancle);
        btnOk = (Button) view.findViewById(R.id.btn_dialog_ok);
        return view;
    }

    protected void initData() {
//        btnCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//
//            }
//        });
    }

    public DialogDoubleButton setCommonTilte(String title) {
        txtTitle.setText(title);
        return this;
    }

    public DialogDoubleButton setLeftButton(String content) {
        btnCancle.setText(content);
        return this;
    }

    public DialogDoubleButton setRightButton(String content) {
        btnOk.setText(content);
        return this;
    }

    public DialogDoubleButton setContentDesc(String title) {
        txtContent.setText(title);
        return this;
    }

    public DialogDoubleButton setBtnCancelListener(final ButtonCancelListener listener) {
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DialogDoubleButton.this);

            }
        });
        return this;
    }

    public DialogDoubleButton setBtnOkListener(final ButtonOkListener listener) {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DialogDoubleButton.this);
            }
        });
        return this;
    }


}
