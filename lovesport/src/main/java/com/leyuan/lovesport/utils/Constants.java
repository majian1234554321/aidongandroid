package com.leyuan.lovesport.utils;

import java.util.HashMap;


public class Constants {
	public static final String NO_STR = "";
	public static final String STR_SPACE = " ";
	public static final String STR_SPLITE_DOT = ",";
	public static final String FILE_FOLDER = "MX";
	public static final int IDENTITY_RES[] = {
//		R.string.identity_user,
//		R.string.identity_coach,
//		R.string.identity_service,
	};

	public static final HashMap<Integer, Boolean> FILTER_GENDER_ORI = new HashMap<Integer, Boolean>() {
		{
//			put(R.id.textGenderAll, false);
//			put(R.id.textMan, false);
//			put(R.id.textWomen, false);
		}
	};
	public static final HashMap<Integer, Boolean> FILTER_USERID_ORI = new HashMap<Integer, Boolean>() {
		{
//			put(R.id.textUserIdAll, false);
//			put(R.id.textCoach, false);
//			put(R.id.textFan, false);
		}
	};

	public static final HashMap<Integer, String> FILTER_GENDER_CONTENT = new HashMap<Integer, String>() {
		{
//			put(R.id.textGenderAll, null);
//			put(R.id.textMan, BaseApp.MAN);
//			put(R.id.textWomen, BaseApp.WOMAN);
		}
	};
	public static final HashMap<Integer, String> FILTER_ID_CONTENT = new HashMap<Integer, String>() {
		{
//			put(R.id.textUserIdAll, null);
//			put(R.id.textCoach, BaseApp.ID_COACH);
//			put(R.id.textFan, BaseApp.ID_HOBBY);
		}
	};
}
