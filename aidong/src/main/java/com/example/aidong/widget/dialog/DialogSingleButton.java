package com.example.aidong.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aidong.R;


/**
 * Created by user on 2017/1/12.
 */

public class DialogSingleButton extends BaseDialog {
    private TextView txtTitle;
    private TextView txtContent;
    private Button btnOk;

    public DialogSingleButton(Context context) {
        super(context);
    }

    @Override
    protected View inflateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_single_button, null, false);
        txtTitle = (TextView) view.findViewById(R.id.txt_dialog_title);
        txtContent = (TextView) view.findViewById(R.id.dialog_content);
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
//            }
//        });
    }

    public DialogSingleButton setCommonTilte(String title) {
        txtTitle.setText(title);
        return this;
    }

    public DialogSingleButton setContentDesc(String title) {
        txtContent.setText(title);
        return this;
    }

    public DialogSingleButton setBtnOkListener(final ButtonOkListener listener) {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(DialogSingleButton.this);
            }
        });
        return this;
    }


}
