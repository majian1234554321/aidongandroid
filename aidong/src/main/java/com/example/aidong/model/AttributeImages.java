package com.example.aidong.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.aidong.utils.ImageUtil;

import java.io.Serializable;

public class AttributeImages implements Serializable, Parcelable {

	private String thumb;// 小图
	private String original;// 原图
	// private int width;
	// private int height;
	private int no;
	private String caption;
	private String url;
	private int id;

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getNo() {
		if (no == 0) {
			return id;
		}
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		if (url == null) {
			return thumb;
		}
		return url;
	}

	public String getUrl(int mode, int width, int height) {
		if (url == null) {
			return thumb;
		}
		return ImageUtil.getImageUrl(this.url, mode, width, height);
	}

	public String getUrl(int width, int height) {
		return getUrl(0, width, height);
	}

	public String getUrl(Context context, int mode, int width, int height) {
		if (url == null) {
			return thumb;
		}
		return ImageUtil.getImageUrl(context, this.url, mode, width, height);
	}

	public String getUrl(Context context, int width, int height) {
		return getUrl(context, 0, width, height);
	}

	public static Creator<AttributeImages> getCreator() {
		return CREATOR;
	}

	// public String getThumb() {
	// return thumb;
	// }
	// public void setThumb(String thumb) {
	// this.thumb = thumb;
	// }
	// public String getOriginal() {
	// return original;
	// }
	// public void setOriginal(String original) {
	// this.original = original;
	// }
	// public int getWidth() {
	// return width;
	// }
	// public void setWidth(int width) {
	// this.width = width;
	// }
	// public int getHeight() {
	// return height;
	// }
	// public void setHeight(int height) {
	// this.height = height;
	// }
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// dest.writeString(thumb);
		// dest.writeString(original);
		// dest.writeInt(width);
		// dest.writeInt(height);
		dest.writeInt(no);
		dest.writeString(caption);
		dest.writeString(url);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Creator<AttributeImages> CREATOR = new Creator<AttributeImages>() {
		@Override
		public AttributeImages[] newArray(int size) {
			return new AttributeImages[size];
		}

		@Override
		public AttributeImages createFromParcel(Parcel in) {
			return new AttributeImages(in);
		}
	};

	public AttributeImages() {
	}

	public AttributeImages(Parcel in) {
		// thumb = in.readString();
		// original = in.readString();
		// width = in.readInt();
		// height = in.readInt();
		no = in.readInt();
		caption = in.readString();
		url = in.readString();
	}
}
