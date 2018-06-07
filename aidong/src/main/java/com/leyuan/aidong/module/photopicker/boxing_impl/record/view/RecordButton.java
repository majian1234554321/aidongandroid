package com.leyuan.aidong.module.photopicker.boxing_impl.record.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aidong.R;

/**
 * Record
 * Created by song on 2017/3/2.
 */
public class RecordButton extends RelativeLayout implements RingProgressBar.ProgressListener {
    private RingProgressBar progressBar;
    private ImageView ivInner;
    private ImageView ivOuter;
    private int innerImageResId;
    private int outerImageResId;
    private boolean isRecord = false;
    private RecordButtonListener recordListener;

    public RecordButton(Context context) {
        this(context,null);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecordButton);
        innerImageResId = a.getResourceId(R.styleable.RecordButton_record_inner_image,R.drawable.record_inner);
        outerImageResId = a.getResourceId(R.styleable.RecordButton_record_outer_image,R.drawable.record_outer);
        a.recycle();
        setAttr(context);
    }

    private void setAttr(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_record,this,true);
        ivInner = (ImageView) view.findViewById(R.id.iv_inner);
        ivOuter = (ImageView)view.findViewById(R.id.iv_outer);
        progressBar = (RingProgressBar) view.findViewById(R.id.progress);
        ivInner.setImageResource(innerImageResId);
        ivOuter.setImageResource(outerImageResId);
        progressBar.setListener(this);
    }

    public void setProgress(double progress){
        progressBar.setProgress(progress);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(!isRecord) {
                    setTouchStatus();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setNormalStatus();
                break;
        }
        return true;
    }

    private void setNormalStatus(){
        isRecord = false;
        if(recordListener != null){
            recordListener.onReleaseRecordButton();
        }
        progressBar.setProgress(0);
        ivInner.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
        ivOuter.animate().scaleX(1.0f).scaleY(1.0f).setDuration(300).start();
    }

    private void setTouchStatus(){
        isRecord = true;
        if(recordListener != null){
            recordListener.onPressRecordButton();
        }
        ivOuter.animate().scaleX(1.25f).scaleY(1.25f).setDuration(300).start();
        ivInner.animate().scaleX(0.75f).scaleY(0.75f).setDuration(300).start();
    }

    public void setRecordListener(RecordButtonListener recordListener) {
        this.recordListener = recordListener;
    }

    @Override
    public void onProgressMax() {
        setNormalStatus();
        if(recordListener != null){
            recordListener.onProgressMax();
        }
    }

    interface RecordButtonListener {
        void onPressRecordButton();
        void onReleaseRecordButton();
        void onProgressMax();
    }
}
