package com.leyuan.aidong.widget.card;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class AAA extends RecyclerView {
    public AAA(@NonNull Context context) {
        super(context);
    }

    public AAA(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public float xDistance,yDistance,xStart,yStart,xEnd,yEnd;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

//        switch (ev.getAction()) {
//
//        }
//
//
//        if (){
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }else {
//            getParent().requestDisallowInterceptTouchEvent(true);
//        }
//
//
//        return super.dispatchTouchEvent(ev);




        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                xDistance = yDistance = 0f;

                xStart = ev.getX();

                yStart = ev.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                xEnd = ev.getX();

                yEnd = ev.getY();




                xDistance = Math.abs(xEnd-xStart);
                yDistance = Math.abs(yEnd-yStart);


                Log.i("YAG","xStart:"+xStart+"xEnd:"+xEnd);
                Log.i("YAG","yStart:"+yStart+"yEnd:"+yEnd);

                Log.i("YAG","xDistance:"+xDistance);
                Log.i("YAG","yDistance:"+yDistance);



                if(xDistance>yDistance){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }


                break;




            case MotionEvent.ACTION_UP:
                xDistance = yDistance = 0f;
                xEnd = yEnd = 0f;


            default:

                break;

        }








        return super.dispatchTouchEvent(ev);//默认值为false




    }

}