package com.leyuan.aidong.entity.model;

import org.json.JSONObject;

/**
 * Created by user on 2015/6/19.
 */
public class Curriculum {

    String endTime;
    String startTime;
    String courseChName;
    String courseID;
    String gpPic;
    String autoId;
    String  courseEnName;
    String  price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCourseEnName() {
		return courseEnName;
	}

	public void setCourseEnName(String courseEnName) {
		this.courseEnName = courseEnName;
	}

	public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCourseChName() {
        return courseChName;
    }

    public void setCourseChName(String courseChName) {
        this.courseChName = courseChName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getGpPic() {
        return gpPic;
    }

    public void setGpPic(String gpPic) {
        this.gpPic = gpPic;
    }

    public void parseJaon(JSONObject jsonObject) {
        endTime = jsonObject.optString("endTime");
        startTime = jsonObject.optString("startTime");
        courseChName = jsonObject.optString("courseChName");
        courseID = jsonObject.optString("courseID");
        gpPic = jsonObject.optString("gpPic");
        autoId = jsonObject.optString("autoId");
        courseEnName=jsonObject.optString("courseEnName");
        price=jsonObject.optString("price");

    }
}
