package com.example.aidong.http.subscriber;


import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong.utils.GlideLoader;

public class Progress3DialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        View view = View.inflate(inflater.getContext(), R.layout.progress3dialogfragment, null);

        ImageView imageView = view.findViewById(R.id.loading);
        GlideLoader.getInstance().displayDrawableGifImage(R.drawable.loading, imageView);
        return view;
    }





    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        if (win != null) {
            win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            WindowManager.LayoutParams params = win.getAttributes();
            params.gravity = Gravity.CENTER;

           // params.alpha = R.style.progress3Dialog;


            params.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
            params.height = WindowManager.LayoutParams.MATCH_PARENT;

            win.setAttributes(params);
            //win.setWindowAnimations(R.style.progress3Dialog);
        }


    }

}
