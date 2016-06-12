package com.leyuan.lovesport.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

/**
 * Image compress factory class
 * 
 * @author 
 *
 */
public class ImageFactory {

	/**
	 * Get bitmap from specified image path
	 * 
	 * @param imgPath
	 * @return
	 */
	public Bitmap getBitmap(String imgPath) {
		// Get bitmap through image path
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = false;
		newOpts.inPurgeable = true;
		newOpts.inInputShareable = true;
		// Do not compress
		newOpts.inSampleSize = 1;
		newOpts.inPreferredConfig = Config.RGB_565;
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}
	
	/**
	 * Store bitmap into specified image path
	 * 
	 * @param bitmap
	 * @param outPath
	 * @throws FileNotFoundException 
	 */
	public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream(outPath);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
	}
	
	/**
	 * Compress image by pixel, this will modify image width/height. 
	 * Used to get thumbnail
	 * 
	 * @param imgPath image path
	 * @param pixelW target pixel of width
	 * @param pixelH target pixel of height
	 * @return
	 */
	public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        // �?始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内�?
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        // Get bitmap info, but notice that bitmap is null now  
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath,newOpts);
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        // 想要缩放的目标尺�?
        float hh = pixelH;// 设置高度�?240f时，可以明显看到图片缩小�?
	    float ww = pixelW;// 设置宽度�?120f，可以明显看到图片缩小了
        // 缩放比�?�由于是固定比例缩放，只用高或�?�宽其中�?个数据进行计算即�?  
        int be = 1;//be=1表示不缩�?  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩�?  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩�?  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0) be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例
        // �?始压缩图片，注意此时已经把options.inJustDecodeBounds 设回false�?
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压�?
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而�?�资源，删除
        
        int degree = getExifOrientation(imgPath);
        bitmap = rotateBitmap(bitmap,degree);
        
        return bitmap;
	}
	
	public static int getExifOrientation(String filepath) {
	       int degree = 0;
	       ExifInterface exif = null;

	       try {
	           exif = new ExifInterface(filepath);
	       } catch (IOException ex) {
	          // MmsLog.e(ISMS_TAG, "getExifOrientation():", ex);
	       }

	       if (exif != null) {
	           int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
	           if (orientation != -1) {
	               // We only recognize a subset of orientation tag values.
	               switch (orientation) {
	               case ExifInterface.ORIENTATION_ROTATE_90:
	                   degree = 90;
	                   break;

	               case ExifInterface.ORIENTATION_ROTATE_180:
	                   degree = 180;
	                   break;

	               case ExifInterface.ORIENTATION_ROTATE_270:
	                   degree = 270;
	                   break;
	               default:
	                   break;
	               }
	           }
	       }

	       return degree;
	   }
	
	public static Bitmap rotateBitmap(Bitmap bitmap,int degress) {  
        if (bitmap != null) {  
            Matrix m = new Matrix();  
            m.postRotate(degress);   
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),  
                    bitmap.getHeight(), m, true);  
            return bitmap;  
        }  
        return bitmap;  
    }  
	
	/**
	 * Compress image by size, this will modify image width/height. 
	 * Used to get thumbnail
	 * 
	 * @param image
	 * @param pixelW target pixel of width
	 * @param pixelH target pixel of height
	 * @return
	 */
	public static Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
	    image.compress(Bitmap.CompressFormat.JPEG, 100, os);
	    if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
	        os.reset();//重置baos即清空baos  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos�?  
	    }  
	    ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    //�?始读入图片，此时把options.inJustDecodeBounds 设回true�?  
	    newOpts.inJustDecodeBounds = true;
	    newOpts.inPreferredConfig = Config.RGB_565;
	    Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    float hh = pixelH;// 设置高度�?240f时，可以明显看到图片缩小�?
	    float ww = pixelW;// 设置宽度�?120f，可以明显看到图片缩小了
	    //缩放比�?�由于是固定比例缩放，只用高或�?�宽其中�?个数据进行计算即�?  
	    int be = 1;//be=1表示不缩�?  
	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩�?  
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩�?  
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0) be = 1;  
	    newOpts.inSampleSize = be;//设置缩放比例  
	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false�?  
	    is = new ByteArrayInputStream(os.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(is, null, newOpts);
	    //压缩好比例大小后再进行质量压�?
//	    return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而�?�资源，删除
	    return bitmap;
	}
	
	/**
	 * Compress by quality,  and generate image to the path specified
	 * 
	 * @param image
	 * @param outPath
	 * @param maxSize target will be compressed to be smaller than this size.(kb)
	 * @throws IOException 
	 */
	public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// scale
		int options = 100;
		// Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);  
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
        	os.reset();
        	// interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        
        // Generate compressed image file
        FileOutputStream fos = new FileOutputStream(outPath);  
        fos.write(os.toByteArray());  
        fos.flush();  
        fos.close();  
	}
	
	
	
	/**
	 * Compress by quality,  and generate image to the path specified
	 * 
	 * @param imgPath
	 * @param outPath
	 * @param maxSize target will be compressed to be smaller than this size.(kb)
	 * @param needsDelete Whether delete original file after compress
	 * @throws IOException 
	 */
	public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete) throws IOException {
		compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
		
		// Delete original file
		if (needsDelete) {
			File file = new File (imgPath);
			if (file.exists()) {
				file.delete();
			}
		}
	}
	
	public static ByteArrayOutputStream compressAndGenImageOs(Bitmap image, int maxSize) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		// scale
		int options = 100;
		// Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);  
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
        	os.reset();
        	// interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }
        
        // Generate compressed image file
        return os;
	}
	
	/**
	 * Ratio and generate thumb to the path specified
	 * 
	 * @param image
	 * @param outPath
	 * @param pixelW target pixel of width
	 * @param pixelH target pixel of height
	 * @throws FileNotFoundException
	 */
	public void ratioAndGenThumb(Bitmap image, String outPath, float pixelW, float pixelH) throws FileNotFoundException {
		Bitmap bitmap = ratio(image, pixelW, pixelH);
		storeImage( bitmap, outPath);
	}
	
	/**
	 * Ratio and generate thumb to the path specified
	 * 
	 * @param image
	 * @param outPath
	 * @param pixelW target pixel of width
	 * @param pixelH target pixel of height
	 * @param needsDelete Whether delete original file after compress
	 * @throws FileNotFoundException
	 */
	public void ratioAndGenThumb(String imgPath, String outPath, float pixelW, float pixelH, boolean needsDelete) throws FileNotFoundException {
		Bitmap bitmap = ratio(imgPath, pixelW, pixelH);
		storeImage( bitmap, outPath);
		
		// Delete original file
				if (needsDelete) {
					File file = new File (imgPath);
					if (file.exists()) {
						file.delete();
					}
				}
	}
	
	// 将Bitmap转换成InputStream  
    public static InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    } 
    
    
	
}
