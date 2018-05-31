package com.leyuan.aidong.http.subscriber;


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

import com.leyuan.aidong.R;
import com.leyuan.aidong.utils.GlideLoader;

public class ProgressDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
        View view = View.inflate(inflater.getContext(), R.layout.progressdialogfragment, null);

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
            win.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );

            WindowManager.LayoutParams params = win.getAttributes();
            params.gravity = Gravity.CENTER;

            params.width = (int) getResources().getDimension(R.dimen.x300);
            params.height = (int) getResources().getDimension(R.dimen.x140);
            win.setAttributes(params);
        }


    }

}
