package com.leyuan.aidong.utils.common;

import android.os.Environment;
import android.provider.MediaStore;

import com.leyuan.aidong.utils.FileUtil;

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










	public static final int REPORT_USER = 1;
	public static final int REPORT_DYNAMIC = 2;
	public static final int REPORT_GROUP = 3;
	public static final String[] THUMB_COLUMNS = {
			MediaStore.Video.Thumbnails.DATA,
			MediaStore.Video.Thumbnails.VIDEO_ID };
	public static final short RS_SELFFILTER = 101;
	public static final short RC_FITTYPE = 102;
	public static final short RS_FITTYPE = 103;
	public static final short RC_TAKEVIDEO = 104;
	public static final short RC_TAKEPHOTO = 106;
	public static final short RC_SELECTABLUMN = 108;
	public static final short RC_SELECTVIDEO = 110;
	public static final String BUNDLE_PHOTOWALL_POSITION = "BUNDLE_PHOTOWALL_POSITION";
	public static final String BUNDLE_CLASS = "BUNDLE_CLASS";
	public static final String BUNDLE_GENDER = "BUNDLE_GENDER";
	public static final String BUNDLE_SEARCH_KEY_WORD = "BUNDLE_SEARCH_KEY_WORD";
	public static final String ACTION_ADD_OFFICAL = "ACTION_ADD_OFFICAL";
	public static final String ACTION_ADD_FRIEND = "ACTION_ADD_FRIEND";
	public static final String ACTION_ADD_FRIEND_BY_ECHAT = "ACTION_ADD_FRIEND_BY_ECHAT";
	public static final String ACTION_ADD_GROUP = "ACTION_ADD_GROUP";
	public static final String BUNDLE_IDNETITY = "BUNDLE_IDNETITY";
	public static final String BUNDLE_CHAT_TYPE = "BUNDLE_CHAT_TYPE";
	public static final String BUNDLE_CHAT_SUB_TYPE = "BUNDLE_CHAT_SUB_TYPE";
	public static final String BUNDLE_USER = "BUNDLE_USER";
	public static final String ACTION_EDIT_GROUP_INFO = "ACTION_EDIT_GROUP_INFO";
	public static final String ACTION_EDIT_GROUP_PHOTO_AND_INFO = "ACTION_EDIT_GROUP_PHOTO_AND_INFO";
	public static final String BUNDLE_BIGIMAGEITEM = "BUNDLE_BIGIMAGEITEM";
	public static final String BUNDLE_VIDEO_URL = "BUNDLE_VIDEO_URL";
	public static final String BUNDLE_GROUP_ID = "BUNDLE_GROUP_ID";
	public static final String ACTION_DEL_GROUP = "ACTION_DEL_GROUP";
	public static final String BUNDLE_GROUP_NAME = "BUNDLE_GROUP_NAME";
	public static final String BUNDLE_FIT_INTEREST = "BUNDLE_FIT_INTEREST";
	public static final String ACTION_EDIT_GROUP_PHOTO = "ACTION_EDIT_GROUP_PHOTO";
	public static final String BUNDLE_FILM = "BUNDLE_FILM";
	public static final String BUNDLE_TOKEN = "BUNDLE_TOKEN";
	public static final String ACTION_EDIT_GROUP_FRIEND = "ACTION_EDIT_GROUP_INFO";
	public static final byte CHATSUBTYPE_USER = 1;
	public static final byte CHATSUBTYPE_SERVICES = 2;
	public static final String BUNDLE_VIDEO_PATH = "BUNDLE_VIDEO_PATH";
	public static FileUtil MxFileUtil;
	//	protected LoadingDialog loadingDialog;
	public static final byte MAX_PHOTO_WALL_COUNT = 8;
	public static final byte MAX_PHOTO_DYNAMIC_COUNT = 6;
	public static final short RC_BIND = 112;
	public static byte Num = MAX_PHOTO_DYNAMIC_COUNT;
	public static final byte RIGHT = 0;
	public static final byte LEFT = 1;
	public static final byte MAX_INDIC = 3;
	public static final String BUNDLE_URL = "BUNDLE_URL";
	public static final String BUNDLE_BIGIMAGEITEM_INDEX = "BUNDLE_BIGIMAGEITEM_INDEX";

}
