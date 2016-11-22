package com.leyuan.aidong.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCountUtil extends CountDownTimer {
    private Button btn;//按钮
    private static long lastClickTime;

    // 在这个构造方法里需要传入三个参数，一个是Activity，一个是总的时间millisInFuture，一个是countDownInterval，然后就是你在哪个按钮上做这个是，就把这个按钮传过来就可以了
    public TimeCountUtil(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }


    @SuppressLint("NewApi")
    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);//设置不能点击
        btn.setText("重发("+millisUntilFinished / 1000+"秒)");//设置倒计时时间

//设置按钮为灰色，这时是不能点击的
//        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.senf_code_gry));
//        Spannable span = new SpannableString(btn.getText().toString());//获取按钮的文字
//        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//讲倒计时时间显示为红色
//        btn.setText(span);

    }


    @SuppressLint("NewApi")
    @Override
    public void onFinish() {
        btn.setText("重发");
//        btn.setTextColor(Color.BLACK);
        btn.setClickable(true);//重新获得点击
//        btn.setBackground(mActivity.getResources().getDrawable(R.drawable.button_transparent_boxes));//还原背景色

    }


    //    防止按钮多点
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


}