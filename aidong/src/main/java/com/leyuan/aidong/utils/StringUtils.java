package com.leyuan.aidong.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	private static final String TAG = "StringUtils";
	private final static Pattern RegexEmail = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	private final static Pattern RegexTel = Pattern
//			.compile("^(13[0-9]|15[0|1|2|3|5|6|7|8|9]|18[0-9]|14[57]|17[0|7])\\d{8}$");
			.compile("^(1)\\d{10}$");

	private final static Pattern RegexPwd = Pattern.compile("[\\d\\w]{6,16}");

	public static boolean isMatchEmail(String e) {
		return !(e == null || e.trim().length() == 0) && RegexEmail.matcher(e).matches();
	}

	public static boolean isMatchTel(String tel) {
		return !(tel == null || tel.trim().length() == 0) && RegexTel.matcher(tel).matches();
	}

	public static boolean isMatchPasswd(String pwd) {
		return !(pwd == null || pwd.trim().length() == 0) && RegexPwd.matcher(pwd).matches();
	}

	public static boolean isNumber(String str){
		String reg = "^\\d+$";
		return Pattern.compile(reg).matcher(str).find();
	}

	public static boolean isNameExceedLimit(String name) {
		int chineseCount = 0;
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(name);
		while (m.find()) {
			chineseCount = m.group(0).length();
		}
		if (name.length() + chineseCount > 16) {
			return true;
		} else {
			return false;
		}
	}
}
