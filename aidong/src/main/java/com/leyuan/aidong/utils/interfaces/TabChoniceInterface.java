package com.leyuan.aidong.utils.interfaces;


import com.leyuan.aidong.R;

public interface TabChoniceInterface {
	public static final byte TAB_NEAR = 0;
	public static final byte TAB_FOUND = 1;
	public static final byte TAB_MSG = 2;
	public static final byte TAB_CONTACTOR = 3;
	public static final byte TAB_MINE = 4;

	public static final int[][] TAB_INDEX = {
			{ R.id.tabNearImageView, R.id.tabNearTextView , R.id.tabNearLayout, },
			{ R.id.tabFoundImageView, R.id.tabFoundTextView , R.id.tabFoundLayout},
//			{ R.id.tabMsgImageView, R.id.tabMsgTextView , R.id.tabMsgLayout},
			{ R.id.tabContactorImageView, R.id.tabContactorTextView, R.id.tabContactorLayout },
			{ R.id.tabMineImageView, R.id.tabMineTextView , R.id.tabMineLayout}, };

}
