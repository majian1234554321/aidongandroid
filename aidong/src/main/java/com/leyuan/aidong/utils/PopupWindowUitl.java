package com.leyuan.aidong.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.leyuan.aidong.R;


/**
 * popupWindow 相关的工具类,如果要设置点击监听，用get方法取得控件，然后setonclick
 */
public class PopupWindowUitl {
	private Context mContext;
	private PopupWindow window;
	private Button btn_first;
	private Button btn_second;
	private Button btn_third;
	private Button btn_pop_myshow_cancel;
	private View view;
	private Button btn_fourth;
	private Button btn_five;

	/**
	 * 默认显示取消，并已实现点击事件，其他点击请调对应几口
	 * 
	 * @param context
	 * @param firstDesc
	 *            只有一行的文字描述
	 * @param backgroud
	 *            可选的参数，背景
	 */
	public PopupWindowUitl(Context context, String firstDesc,
			final View backgroud) {
		this(context, firstDesc, null, backgroud);
	}

	/**
	 * 默认显示取消，并已实现点击事件，其他点击请调对应几口
	 * 
	 * @param context
	 * @param firstDesc
	 *            第一行的文字描述
	 * @param SecondDesc
	 *            第二行的文字描述
	 * @param backgroud
	 *            可选的参数，背景
	 */
	public PopupWindowUitl(Context context, String firstDesc,
			String SecondDesc, final View backgroud) {
		this(context, firstDesc, SecondDesc, null, backgroud);
	}

	
	public PopupWindowUitl(Context context, String firstDesc,
			String SecondDesc, String thirdDesc, final View backgroud) {

	this(context, firstDesc, SecondDesc, thirdDesc, null, backgroud);

	}
	
	public PopupWindowUitl(Context context, String firstDesc,
			String SecondDesc, String thirdDesc,String fourthDesc,final View backgroud){
		this(context, firstDesc, SecondDesc, thirdDesc, fourthDesc, null, backgroud);
	}
	
	/**
	 * 
	 * 默认显示"取消"按钮，并已实现点击事件，其他点击请调对应几口
	 * 
	 * @param context
	 * @param firstDesc
	 *            第一行的文字描述
	 * @param SecondDesc
	 *            第二行的文字描述
	 * @param thirdDesc
	 *            第三行的文字描述
	 * @param fourthDesc
	 *            第四行的文字描述
	* @param fiveDesc
	 *            第五行的文字描述               
	 * @param backgroud
	 *            可选的参数，背景
	 */

	public PopupWindowUitl(Context context, String firstDesc,
			String SecondDesc, String thirdDesc,String fourthDesc,String fiveDesc , View backgroud){
		mContext = context;

		view = initPopView();
		window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		window.setAnimationStyle(R.style.popuStyle);
		window.setBackgroundDrawable(new BitmapDrawable());
		this.backgroud = backgroud;

		setClickEvent(firstDesc, SecondDesc, thirdDesc, fourthDesc,fiveDesc,backgroud);
		
		
	}
	
	private View backgroud;
	private LinearLayout ll_popup;

	private View initPopView() {
		View view = View.inflate(mContext, R.layout.popmenu_report, null);
		ll_popup= (LinearLayout) view.findViewById(R.id.ll_popup);
		btn_first = (Button) view.findViewById(R.id.popmenu_report_1);
		btn_second = (Button) view.findViewById(R.id.popmenu_report_2);
		btn_third = (Button) view.findViewById(R.id.popmenu_report_3);
		btn_fourth = (Button) view.findViewById(R.id.popmenu_report_4);
		btn_five = (Button) view.findViewById(R.id.popmenu_report_5);
		btn_pop_myshow_cancel = (Button) view
				.findViewById(R.id.popmenu_report_6);
		return view;
	}

	private void setClickEvent(String firstDesc, String secondDesc,
			String thirdDesc, String fourthDesc, String fiveDesc, final View backgroud) {
		if (firstDesc != null) {
			btn_first.setVisibility(View.VISIBLE);
			btn_first.setText(firstDesc);
			btn_first.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					window.dismiss();
					if (listener != null) {
						listener.onFirstItemClick();
					}
				}
			});
		}else{
			btn_first.setVisibility(View.GONE);
		}

		if (secondDesc != null) {
			btn_second.setVisibility(View.VISIBLE);
			btn_second.setText(secondDesc);
			btn_second.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					window.dismiss();
					if (listener != null) {
						listener.onSecondItemClick();
					}
				}
			});

		}else{
			btn_second.setVisibility(View.GONE);
		}

		if (thirdDesc != null) {
			btn_third.setVisibility(View.VISIBLE);
			btn_third.setText(thirdDesc);
			btn_third.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					window.dismiss();
					if (listener != null) {
						listener.onThirdItemClick();
					}

				}
			});
		}else{
			btn_third.setVisibility(View.GONE);
		}
		
		
		if (fourthDesc != null) {
			btn_fourth.setVisibility(View.VISIBLE);
			btn_fourth.setText(fourthDesc);
			btn_fourth.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					window.dismiss();
					if (listener != null) {
						listener.onFourthItemClick();
					}

				}
			});
		}else{
			btn_fourth.setVisibility(View.GONE);
		}
		
		if (fiveDesc != null) {
			btn_five.setVisibility(View.VISIBLE);
			btn_five.setText(fiveDesc);
			btn_five.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					window.dismiss();
					if (listener != null) {
						listener.onFirstItemClick();
					}

				}
			});
		}else{
			btn_five.setVisibility(View.GONE);
		}
		

		btn_pop_myshow_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});

		window.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
//				ll_popup.clearAnimation();
				if (backgroud != null) {
					backgroud.setVisibility(View.GONE);
				}
				if (listener != null) {
					listener.onDismissListener();
				}
			}
		});
	}
/** 默认显示方法，从底部弹出*/
	public void show() {
		
//		ll_popup.startAnimation(AnimationUtils.loadAnimation(
//				mContext,
//				R.anim.activity_translate_in));
		window.showAtLocation(view.findViewById(R.id.popmenu_report),
				Gravity.BOTTOM, 0, 0);
		if (backgroud != null) {
			backgroud.setVisibility(View.VISIBLE);
		}
	}
	/** 依赖于父控件*/
	public void show(View parentView) {
	
//		ll_popup.startAnimation(AnimationUtils.loadAnimation(
//				mContext,
//				R.anim.activity_translate_in));
		window.showAtLocation(parentView,
				Gravity.BOTTOM, 0, 0);
		if (backgroud != null) {
			backgroud.setVisibility(View.VISIBLE);
		}
	}
	
	/** 显示在某个view下面*/
	public void show(View view, int xoff, int yoff) {
		if (view != null) {
			if (backgroud != null) {
				backgroud.setVisibility(View.VISIBLE);
			}
			window.showAsDropDown(view, xoff, yoff);
		}
	}
	
	
	
	
	
	
	public Button getBtn_first() {
		return btn_first;
	}

	public void setBtn_first(Button btn_first) {
		this.btn_first = btn_first;
	}

	public Button getBtn_second() {
		return btn_second;
	}

	public void setBtn_second(Button btn_second) {
		this.btn_second = btn_second;
	}

	public Button getBtn_third() {
		return btn_third;
	}

	public void setBtn_third(Button btn_third) {
		this.btn_third = btn_third;
	}

	public Button getBtn_pop_myshow_cancel() {
		return btn_pop_myshow_cancel;
	}

	public void setBtn_pop_myshow_cancel(Button btn_pop_myshow_cancel) {
		this.btn_pop_myshow_cancel = btn_pop_myshow_cancel;
	}

	public Button getBtn_fourth() {
		return btn_fourth;
	}

	public void setBtn_fourth(Button btn_fourth) {
		this.btn_fourth = btn_fourth;
	}

	public Button getBtn_five() {
		return btn_five;
	}

	public void setBtn_five(Button btn_five) {
		this.btn_five = btn_five;
	}

	private OnPopupWindowItemClickListener listener;
	public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener l){
		listener = l;
	}
	
	/** popop里面点击监听，如果没有某个item，请忽略*/
	public interface OnPopupWindowItemClickListener{
		public void onFirstItemClick();
		public void onSecondItemClick();
		public void onThirdItemClick();
		public void onFourthItemClick();
		public void onFiveItemClick();
		public void onDismissListener();
		
	}
}
