package com.leyuan.aidong.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

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

	/**
	 * 关键字高亮显示
	 *
	 * @param context 上下文
	 * @param text    需要显示的文字
	 * @param target  需要高亮的关键字
	 * @param color   高亮颜色
	 * @param start   头部增加高亮文字个数
	 * @param end     尾部增加高亮文字个数
	 * @return 处理完后的结果
	 */
	public static SpannableStringBuilder  highlight(Context context, String text, String target,
											String color, int start, int end) {
		SpannableStringBuilder spannableString = new SpannableStringBuilder (text);

		Pattern pattern = Pattern.compile(target);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			ClickableSpan clickableSpan = new ClickableSpan() {
				@Override
				public void onClick(View view) {
					ToastGlobal.showShortConsecutive("点点点");
				}
			};

			spannableString.setSpan(clickableSpan, matcher.start() - start, matcher.end() + end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));
			spannableString.setSpan(span, matcher.start() - start, matcher.end() + end,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}
}
