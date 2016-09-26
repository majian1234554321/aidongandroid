package com.leyuan.aidong.utils;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.leyuan.aidong.R;

/**
 * popupWindow 相关的工具类,如果要设置点击监听，用get方法取得控件，然后setonclick
 */
public class PopupWindowUitls {
	private Context mContext;
	private PopupWindow window;
	public PopupWindow getWindow() {
		return window;
	}

	private Button btn_first;
	private Button btn_second;
	private Button btn_third;
	
	public Button getBtn_first() {
		return btn_first;
	}

	

	public Button getBtn_second() {
		return btn_second;
	}


	public Button getBtn_third() {
		return btn_third;
	}

	

	private Button btn_pop_myshow_cancel;
	public PopupWindowUitls(Context context)
	{
		mContext = context;
	}

	/**
	 * 显示弹出窗体
	 * @param firstDesc  第一行内容的描述,如果为空或长度为0，则显示默认内容，其他相同
	 * @param fisShow    是否显示第一行内容
	 * @param SecondDesc 第二行内容的描述
	 * @param sisShow    是否显示第二行内容
	 * @param thirdDesc  第三行内容的描述
	 * @param tisShow    是否显示第三行内容
	 * @param backgroud popupwindow背景，可以为空
	 */
	public  void showPopup(String firstDesc,boolean fisShow,String SecondDesc,boolean sisShow,String thirdDesc,boolean tisShow,final ImageView backgroud)
	{
		if(backgroud != null)
		{
		backgroud.setVisibility(View.VISIBLE);
		}
		LayoutInflater layoutIn = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutIn.inflate(R.layout.popmenu_myshow, null);
		btn_first = (Button) view
				.findViewById(R.id.pop_myshow_video_show);
		btn_second = (Button) view
				.findViewById(R.id.pop_myshow_photo_wall);
		btn_third = (Button) view
				.findViewById(R.id.pop_myshow_dynamic);
		btn_pop_myshow_cancel = (Button) view
				.findViewById(R.id.pop_myshow_cancel);
		if (window == null) {
			window = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, true);
			window.setAnimationStyle(R.style.popuStyle);
			window.setBackgroundDrawable(new BitmapDrawable());
		}
		if(!TextUtils.isEmpty(firstDesc))
		btn_first.setText(firstDesc);
		btn_first.setVisibility(fisShow?View.VISIBLE:View.GONE);
		if(!TextUtils.isEmpty(SecondDesc))
		btn_second.setText(SecondDesc);
		btn_second.setVisibility(sisShow?View.VISIBLE:View.GONE);
		if(!TextUtils.isEmpty(thirdDesc))
		btn_third.setText(thirdDesc);
		btn_third.setVisibility(tisShow?View.VISIBLE:View.GONE);
		
		btn_pop_myshow_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
		window.showAtLocation(view.findViewById(R.id.my_show_popwindow),
				Gravity.BOTTOM, 0, 0);
		window.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				if(backgroud != null)
				{
				backgroud.setVisibility(View.GONE);
				}
			}
		});
	}
}
