package com.leyuan.aidong.entity.model.location;

import android.graphics.Bitmap;

import java.io.Serializable;


public class RecorderVideoInformation implements Serializable{
private Bitmap bitmap;
private String path;
private String durarion;


public RecorderVideoInformation() {
	super();
}
public RecorderVideoInformation(Bitmap bitmap, String path, String durarion) {
	super();
	this.bitmap = bitmap;
	this.path = path;
	this.durarion = durarion;
}
public Bitmap getBitmap() {
	return bitmap;
}
public void setBitmap(Bitmap bitmap) {
	this.bitmap = bitmap;
}
public String getPath() {
	return path;
}
public void setPath(String path) {
	this.path = path;
}
public String getDurarion() {
	return durarion;
}
public void setDurarion(String durarion) {
	this.durarion = durarion;
}

	
	
}
