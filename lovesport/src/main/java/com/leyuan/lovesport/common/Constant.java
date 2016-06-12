package com.leyuan.lovesport.common;

import android.os.Environment;

public class Constant {

	public static final String device = "Android";

	public static final String base_path = Environment
			.getExternalStorageDirectory().getPath() + "/aidong";
	public static final String db_path = base_path + "/database";

	public static final String file_path = base_path + "/file";
	public static final String libary_path = base_path + "/file/libary";
	public static final String download_path = base_path + "/download";

	public static final String headimg_path = base_path + "/headimg";
	public static final String update_path = base_path + "/update";
	public static final String photo_path = base_path + "/photo";

	public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
	public static final String GROUP_USERNAME = "item_groups";
	public static final String CHAT_ROOM = "item_chatroom";
	public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
	public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
	public static final String ACCOUNT_REMOVED = "account_removed";
	public static final String CHAT_ROBOT = "item_robots";
	public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";

	public static final byte ID_VIP = 0;
	public static final byte ID_OFFICAL = 1;
	public static final byte ID_COACH = 2;

	public static final String ACTION_MSG_RECEIVER = "ACTION_MSG_RECEIVER";
	public static final String BUNDLE_MSG_COUNT = "BUNDLE_MSG_COUNT";

	public static final String STR_SPLITE_DOT = ",";

	private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CONTEXT_MENU = 3;
	private static final int REQUEST_CODE_MAP = 4;
	public static final int REQUEST_CODE_TEXT = 5;
	public static final int REQUEST_CODE_VOICE = 6;
	public static final int REQUEST_CODE_PICTURE = 7;
	public static final int REQUEST_CODE_LOCATION = 8;
	public static final int REQUEST_CODE_NET_DISK = 9;
	public static final int REQUEST_CODE_FILE = 10;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
	public static final int REQUEST_CODE_PICK_VIDEO = 12;
	public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
	public static final int REQUEST_CODE_VIDEO = 14;
	public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
	public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
	public static final int REQUEST_CODE_SEND_USER_CARD = 17;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
	public static final int REQUEST_CODE_GROUP_DETAIL = 21;
	public static final int REQUEST_CODE_SELECT_VIDEO = 23;
	public static final int REQUEST_CODE_SELECT_FILE = 24;
	public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;

	public static final int RESULT_CODE_COPY = 1;
	public static final int RESULT_CODE_DELETE = 2;
	public static final int RESULT_CODE_FORWARD = 3;
	public static final int RESULT_CODE_OPEN = 4;
	public static final int RESULT_CODE_DWONLOAD = 5;
	public static final int RESULT_CODE_TO_CLOUD = 6;
	public static final int RESULT_CODE_EXIT_GROUP = 7;

	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_GROUP = 2;
	public static final int CHATTYPE_CHATROOM = 3;

	public static final String COPY_IMAGE = "EASEMOBIMG";

}
