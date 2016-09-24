package com.example.aidong.entity.model.location;

import android.graphics.Bitmap;

import com.example.aidong.utils.photo.Bimp;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;


public class ImageItem implements Serializable {
	public String imageId;
	public String imageserId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	private File file;
	private boolean isHttpImageItem;
	public boolean isSelected = false;
	
	
	
	public String getImageserId() {
		return imageserId;
	}
	public void setImageserId(String imageserId) {
		this.imageserId = imageserId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Bitmap getBitmap() {		
		if(bitmap == null && isHttpImageItem == false){
			try {
				bitmap = Bimp.revitionImageSize(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	public boolean getHttpImageItem() {
		return isHttpImageItem;
	}
	public void setHttpImageItem(boolean isHttpImageItem) {
		this.isHttpImageItem = isHttpImageItem;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		ImageItem item  = (ImageItem)o;
//		return this.imagePath.equals(item.getImagePath());
//	}
//	@Override
//	public int hashCode() {
//		return this.imagePath.hashCode();
//	}
	
	
}
