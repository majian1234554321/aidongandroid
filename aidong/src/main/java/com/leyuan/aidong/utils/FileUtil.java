package com.leyuan.aidong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	private static final String SHARE_GOOD_INFO = "SHARE_GOOD_INFO";
	private static final String SHARE_GOOD_PERSON_INFO = "SHARE_GOOD_PERSON_INFO";
	private static final String SHARE_GOOD_Video_INFO = "SHARE_GOOD_Video_INFO";
	private static FileUtil instance;
	public static final String VIDEO_MP4 = "video/mp4";
	public static final String VIDEO_3GPP = "video/3gpp";
	public static final String MX_CACHE = Constant.FILE_FOLDER + "/" + "cache";
	public static final String MX_BIGIMAGE = Constant.FILE_FOLDER + "/"
			+ "image";
	public static final String TAKEPHOTO_SDPATH = Constant.FILE_FOLDER + "/"
			+ "photo" + "/";
	public static FileUtil getInstance() {
		if (instance == null) {
			instance = new FileUtil();
		}
		return instance;
	}
	public File loadFile(String path) {
		File file = null;
		try {
			file = new File(path);
			FileInputStream is = new FileInputStream(file);
			byte[] b = new byte[is.available()];
			is.read(b);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * 通过后缀获取文件类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getMimeType(File file) {
		String extension = getExtension(file);
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
	}
	public static String stringPath(Bitmap bitmap, String FilePath,
			String FileName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] photodata = baos.toByteArray();
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), FilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, FileName);
		if (file.exists()) {
			return file.getAbsolutePath();
		}
		try {
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file.getAbsolutePath()));
			bos.write(photodata);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return FilePath;
	}
	/**
	 * 获取文件后缀
	 * 
	 * @param file
	 * @return
	 */
	private static String getExtension(File file) {
		String suffix = "";
		String name = file.getName();
		final int idx = name.lastIndexOf(".");
		if (idx > 0) {
			suffix = name.substring(idx + 1);
		}
		return suffix;
	}

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(TAKEPHOTO_SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(TAKEPHOTO_SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	public File saveBitmap(Bitmap bm, String picName) {
		File f = null;
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			f = new File(TAKEPHOTO_SDPATH, picName + ".JPEG");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

public File getTakePhotoFileFolder() {
		File dir = null;
		if (SDCardUtil.isExsitSDCard()) {
			dir = SDCardUtil.getSDPath(TAKEPHOTO_SDPATH);
			if (dir == null) {
				return null;
			} else if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return dir;
	}

	public File getTakePhotoFile(File folder, String FileName) {
		File f = new File(folder, FileName + ".jpg");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return f;
	}
	public int loadGoodVideoInfo(Context c, String videoNo) {
		SharedPreferences mySharedPreferences = c.getSharedPreferences(
				SHARE_GOOD_Video_INFO, Activity.MODE_PRIVATE);
		int mxid = mySharedPreferences.getInt(videoNo, 0);
		return mxid;
	}
	public int loadGoodInfo(Context c, String dynamicNo) {
		SharedPreferences mySharedPreferences = c.getSharedPreferences(
				SHARE_GOOD_INFO, Activity.MODE_PRIVATE);
		int mxid = mySharedPreferences.getInt(dynamicNo, 0);
		return mxid;
	}
	public Bitmap getBigImageBitmap(String FileName) {
		Bitmap bitmap = null;
		File dir = null;
		if (SDCardUtil.isExsitSDCard()) {
			dir = SDCardUtil.getSDPath(MX_BIGIMAGE);
			if (dir == null) {
				return null;
			} else if (!dir.exists()) {
				dir.mkdirs();
			}
			File imageFile = new File(dir, FileName);
			if (!imageFile.exists()) {
				try {
					imageFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			}
		}
		return bitmap;
	}

	public boolean saveBigBitmapInSD(String fileName, Bitmap bm) {
		boolean saveSuccess = false;
		File dir = null;
		if (SDCardUtil.isExsitSDCard()) {
			dir = SDCardUtil.getSDPath(MX_BIGIMAGE);
			if (dir == null) {
				return false;
			} else if (!dir.exists()) {
				dir.mkdirs();
			}
			File imageFile = new File(dir, fileName);
			if (!imageFile.exists()) {
				try {
					imageFile.createNewFile();
					saveSuccess = writeBigBitmapInSD(imageFile, bm);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				saveSuccess = writeBigBitmapInSD(imageFile, bm);
			}
		}
		return saveSuccess;
	}
	public void saveGoodInfo(Context c, int mxid, String dynamicNo) {
		SharedPreferences mySharedPreferences = c.getSharedPreferences(
				SHARE_GOOD_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putInt(dynamicNo, mxid);
		editor.commit();
	}
	public boolean writeBigBitmapInSD(File imageFile, Bitmap bm) {
		boolean saveSuccess = false;
		try {
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
			// baos.close();

			FileOutputStream out = new FileOutputStream(imageFile);
			bm.compress(Bitmap.CompressFormat.JPEG, 80, out);
			out.flush();
			out.close();
			saveSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveSuccess;
	}
	public int loadGoodPersonInfo(Context c, int otherPersonMxid) {
		SharedPreferences mySharedPreferences = c.getSharedPreferences(
				SHARE_GOOD_PERSON_INFO, Activity.MODE_PRIVATE);
		int mxid = mySharedPreferences.getInt(String.valueOf(otherPersonMxid),
				0);
		return mxid;
	}

}
