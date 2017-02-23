package com.leyuan.aidong.widget;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
/**
 * 可上下滚动的linearlayout，你必须为它设置3个子view，拖动第二个子view可带动所有子view滚动
 * @author huyouni
 *
 */
public class LinearLayoutVerticalScrolling extends LinearLayout {
	private ViewDragHelper mDragHelper;
	private Status mStatus = Status.Open;
	private onDragStatusChangedListener mListener;
	

	public LinearLayoutVerticalScrolling(Context context) {
		this(context,null);
	}

	public LinearLayoutVerticalScrolling(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public LinearLayoutVerticalScrolling(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mDragHelper = ViewDragHelper.create(this,1.0f, mCallback );
	}

	
	public static enum Status{
		Close,Open,Draging;
	}
	
	public interface onDragStatusChangedListener{
		public void onClose();
		public void onOpen();
		public void OnDraging();
	}
	
	public Status getStatus() {
		return mStatus;
	}

	public void setStatus(Status mStatus) {
		this.mStatus = mStatus;
	}

	public void setDragStatusListener(onDragStatusChangedListener mListener){
		this.mListener = mListener;
	}
	
	private Callback mCallback = new Callback() {
		
		

		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			if(arg0 == secondView)
			{
				return true;
			}
			return false;
		}
		
		public int clampViewPositionVertical(View child, int top, int dy) {
			
			int newTop = top;
			if(top < 0)
			{
				newTop = 0;
			}
			else if(top > firstViewHeight)
			{
				newTop = firstViewHeight;
			}
			if(child == secondView)
			{
				return newTop;
			}
			return newTop;
		};
		
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			
			if(changedView == secondView)
			{
			    second_top = top;
				requestLayout();
				if (second_top == 0) {
					mStatus = Status.Close;
					if (listener_close != null) {
						listener_close.onStatusClose();
					}
				}
				else if (second_top == firstViewHeight) {
					mStatus = Status.Open;
					if (listener_open != null) {
						listener_open.onStatusOpen();
					}
				}
				else{
					mStatus = Status.Draging;
				}
			}
			
//			Log.i("linearlayout", "onviewPositionChanged被调用");
			
			
		};
		
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if(releasedChild == secondView)
			{
				if(secondView.getTop() <= firstViewHeight / 2)
				{
					close();
				}
			}
			
			
		};
	};
	private View firstView;
	private View secondView;
	private View thirdView;
	private int firstViewHeight;
	private int secondViewHeight;
	private int thirdiewHeight;
	private int all_height;
	private int second_top;
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if(getChildCount() != 3)
		{
			throw new IllegalStateException("你必须且只能有3个子孩子才能使用该控件");
		}
		firstView = getChildAt(0);
		secondView = getChildAt(1);
		thirdView = getChildAt(2);
		
		
	};
	/** 关闭 */
	public  void close() {
		if (mDragHelper.smoothSlideViewTo(secondView, 0, 0)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}
	/** 打开 */
	public void open() {
		if (mDragHelper.smoothSlideViewTo(secondView, 0, firstViewHeight)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
		
	}
	
	@Override
	public void computeScroll() {
		super.computeScroll();
		if(mDragHelper.continueSettling(true))
		{
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragHelper.shouldInterceptTouchEvent(ev);
		
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		try {
			mDragHelper.processTouchEvent(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		firstViewHeight = firstView.getMeasuredHeight();
		secondViewHeight = secondView.getMeasuredHeight();
		thirdiewHeight = thirdView.getMeasuredHeight();
		all_height = getMeasuredHeight();
		second_top = firstViewHeight;
	}
	
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		
//		Log.i("linearlayout", "secondtop =  " + second_top +",firstViewHeight = " +firstViewHeight);
//		if (mStatus == Status.Draging) {
			firstView.layout(0, second_top - firstViewHeight, r,  second_top);
			secondView.layout(0, second_top, r,second_top +secondViewHeight);
			thirdView.layout(0, second_top + secondViewHeight, r, b);
//			Log.i("linearlayout","3个layout方法被调用");
//		}
	}
	
	private OnStatusOpenListener listener_open;
	public void setOnStatusOpenListener(OnStatusOpenListener listener_open){
		this.listener_open = listener_open;
	}
	public void removeOnStatusOpenListener(){
		this.listener_open = null;
	}
	
	private OnStatusCloseListener listener_close;
	public void setOnStatusCloseListener(OnStatusCloseListener listener_close){
		this.listener_close = listener_close;
	}
	public void removeOnStatusCloseListener(){
		this.listener_close = null;
	}
	
	public interface OnStatusOpenListener{
		public void onStatusOpen();
	}
	public interface OnStatusCloseListener{
		public void onStatusClose();
	}
}
