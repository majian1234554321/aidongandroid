package com.example.aidong.widget.customview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.aidong.R;
import com.example.aidong.utils.common.Constant;

/**
 * 自定义的textview，用来处理复制粘贴的消息
 *
 */
public class PasteEditText extends EditText{
    private Context context;


    public PasteEditText(Context context) {
        super(context);
        this.context = context;
    }

    public PasteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public PasteEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTextContextMenuItem(int id) {
        if(id == android.R.id.paste){
            ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clip == null || clip.getText() == null) {
                return false;
            }
            String text = clip.getText().toString();
            if(text.startsWith(Constant.COPY_IMAGE)){
                //                intent.setDataAndType(Uri.fromFile(new File("/sdcard/mn1.jpg")), "image/*");
                text = text.replace(Constant.COPY_IMAGE, "");
                Intent intent = new Intent(context,AlertDialog.class);
                String str = context.getResources().getString(R.string.Send_the_following_pictures);
                intent.putExtra("title", str);
                intent.putExtra("forwardImage", text);
                intent.putExtra("cancel", true);
                ((Activity)context).startActivityForResult(intent,Constant.REQUEST_CODE_COPY_AND_PASTE);
                //                clip.setText("");
            }
        }
        return super.onTextContextMenuItem(id);
    }



    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(!TextUtils.isEmpty(text) && text.toString().startsWith(Constant.COPY_IMAGE)){
            setText("");
        }
        //        else if(!TextUtils.isEmpty(text)){
        //        	setText(SmileUtils.getSmiledText(getContext(), text),BufferType.SPANNABLE);
        //        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }


}
