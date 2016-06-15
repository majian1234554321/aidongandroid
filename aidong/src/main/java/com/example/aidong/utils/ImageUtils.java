package com.example.aidong.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtils {
	
	 public static final String AUTHORITY = "media";

	    private static final String CONTENT_AUTHORITY_SLASH = "content://" + AUTHORITY + "/";
	
	public static final Uri EXTERNAL_CONTENT_URI =
            getContentUri("external");
	
	public static Uri getContentUri(String volumeName) {
        return Uri.parse(CONTENT_AUTHORITY_SLASH + volumeName +
                "/video/media");
    }
	private static ImageFreshListener mImageFreshListener;
	
	public void setImageFreshListener(ImageFreshListener imageFreshListener) {
		mImageFreshListener = imageFreshListener;
	}
	public interface ImageFreshListener {
		public void onADAdpaterFresh();
	}

	public static final String insertImage(ContentResolver cr, Bitmap source,
			String title, String description, String imagePath) {
//		ContentValues values = new ContentValues();
//		values.put(Images.Media.TITLE, title);
//		values.put(Images.Media.DESCRIPTION, description);
//		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		File file = new File(imagePath);
		

        ContentValues values = new ContentValues(6);
        values.put(Images.Media.TITLE,
        		file.getName());
        values.put(Images.Media.DISPLAY_NAME,
        		file.getName());
        values.put(Images.Media.DATA, file.getPath());
        values.put(Images.Media.DATE_MODIFIED,
                System.currentTimeMillis() / 1000);
        values.put(Images.Media.SIZE, file.length());
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
         
//      result = getContentResolver().insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		Uri url = null;
		String stringUrl = null; /* value to be returned */

		try {
			url = cr.insert(EXTERNAL_CONTENT_URI, values);

			if (source != null) {
				OutputStream imageOut = cr.openOutputStream(url);
				try {
					source.compress(CompressFormat.JPEG, 50, imageOut);
				} finally {
					imageOut.close();
				}

				long id = ContentUris.parseId(url);
				// Wait until MINI_KIND thumbnail is generated.
				Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id,
						Images.Thumbnails.MINI_KIND, null);
				// This is for backward compatibility.
				Bitmap microThumb = StoreThumbnail(cr, miniThumb, id, 50F, 50F,
						Images.Thumbnails.MICRO_KIND);
			} else {
				Log.e("image", "Failed to create thumbnail, removing original");
				cr.delete(url, null, null);
				url = null;
			}
		} catch (Exception e) {
			Log.e("image", "Failed to insert image", e);
			if (url != null) {
				cr.delete(url, null, null);
				url = null;
			}
		}

		if (url != null) {
			stringUrl = url.toString();
		}

		return stringUrl;
	}

	public static final String insertImage(ContentResolver cr,
			String imagePath, String name, String description)
			throws FileNotFoundException {
		// Check if file exists with a FileInputStream
		FileInputStream stream = new FileInputStream(imagePath);
		try {
			Bitmap bm = BitmapFactory.decodeFile(imagePath);
			String ret = insertImage(cr, bm, name, description, imagePath);
			bm.recycle();
			return ret;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}
	
	private static final Bitmap StoreThumbnail(
            ContentResolver cr,
            Bitmap source,
            long id,
            float width, float height,
            int kind) {
        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                                           source.getWidth(),
                                           source.getHeight(), matrix,
                                           true);

        ContentValues values = new ContentValues(4);
        values.put(Images.Thumbnails.KIND,     kind);
        values.put(Images.Thumbnails.IMAGE_ID, (int)id);
        values.put(Images.Thumbnails.HEIGHT,   thumb.getHeight());
        values.put(Images.Thumbnails.WIDTH,    thumb.getWidth());

        Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);

            thumb.compress(CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        }
        catch (FileNotFoundException ex) {
            return null;
        }
        catch (IOException ex) {
            return null;
        }
    }
	
	public static final String saveImage(String imagePath)
			throws FileNotFoundException {
		// Check if file exists with a FileInputStream
		FileInputStream stream = new FileInputStream(imagePath);
		try {
			Bitmap bm = BitmapFactory.decodeFile(imagePath);
			String ret = saveImage(bm).getAbsolutePath();
			bm.recycle();
			return ret;
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static File saveImage(Bitmap bmp) {
	    File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(appDir, fileName);
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        bmp.compress(CompressFormat.JPEG, 50, fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return file;
	}
	 

}
