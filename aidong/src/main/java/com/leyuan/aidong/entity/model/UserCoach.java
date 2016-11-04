package com.leyuan.aidong.entity.model;

import com.leyuan.aidong.entity.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class UserCoach extends User implements Serializable{
	protected String token; // '用户token值',
	protected String name; // '昵称'，
	protected int mxid; // 美型号,
	protected String avatar; // 头像,
	protected int gender; // 性别 0:男，1：女
	protected int age; // 年龄
	protected int true_age; // 年龄
	protected String signature;
	protected int identity;//是否是服务号
	protected ArrayList<Integer> tags;// 图标
	protected String mobile;
	private String address;
	//	private int new;//完善资料使用字段：1为跳完善资料，0为去应用主界面
	private String tip;
	private int courses;
	private Coordinate coordinate;


	private String target;
	private String skill;
	private String interests;
	private String often;
	private String birthday;
	//	private Contact1 contact;
	private boolean isBindMobile;


	private int unreadMsgCount;
	private String header;
	private String username;
	private String nick;
	private String city;

	protected int likes;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/** 照片墙*/
	private ArrayList<AttributeImages> photowall;

	/** 兴趣*/
	private ArrayList<String> fpg;

	public ArrayList<String> getFpg() {
		return fpg;
	}

	public void setFpg(ArrayList<String> fpg) {
		this.fpg = fpg;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getCourses() {
		return courses;
	}

	public void setCourses(int courses) {
		this.courses = courses;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public int getTrue_age() {
		return true_age;
	}

	public void setTrue_age(int true_age) {
		this.true_age = true_age;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getOften() {
		return often;
	}

	public void setOften(String often) {
		this.often = often;
	}

	public boolean isBindMobile() {
		return isBindMobile;
	}

	public void setBindMobile(boolean isBindMobile) {
		this.isBindMobile = isBindMobile;
	}

	//	public Contact1 getContact() {
	//		return contact;
	//	}
	//
	//	public void setContact(Contact1 contact) {
	//		this.contact = contact;
	//	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMxid() {
		return mxid;
	}

	public void setMxid(int mxid) {
		this.mxid = mxid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	/**
	 * 已弃用
	 * @return
	 */
	@Deprecated
	public ArrayList<Integer> getTags() {
		//		if(tags == null){
		tags = new ArrayList<Integer>();
		switch (identity) {
			case 0:
				tags.add(0);
				tags.add(0);
				tags.add(0);
				break;
			case 1:
				tags.add(0);
				tags.add(0);
				tags.add(1);
				break;
			case 2:
				tags.add(0);
				tags.add(1);
				tags.add(0);
				break;

			default:
				break;
		}
		//		}
		return tags;
	}

	public void setTags(ArrayList<Integer> tags) {
		this.tags = tags;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public class Contact1 {
		private String name;
		private String photo;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

	}

	public ArrayList<AttributeImages> getPhotowall() {
		return photowall;
	}
	public void setPhotowall(ArrayList<AttributeImages> photowall) {
		this.photowall = photowall;
	}




	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserCoach() {
	}

	public UserCoach(String username) {
		this.username = username;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}

	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}

	//	@Override
	//	public void writeToParcel(Parcel out, int flags) {
	//		out.writeString(token);
	//		out.writeString(name);
	//		out.writeInt(mxid);
	//		out.writeString(avatar);
	//		out.writeInt(gender);
	//		out.writeInt(age);
	//		out.writeString(signature);
	//		out.writeInt(identity);
	//		out.writeArray(tags.toArray(new Integer[tags.size()]));
	//		out.writeString(mobile);
	//		out.writeString(address);
	//		out.writeString(target);
	//		out.writeString(skill);
	//		out.writeString(often);
	//	}
	//	public static final Parcelable.Creator<UserCoach> CREATOR = new Creator<UserCoach>() {
	//		@Override
	//		public UserCoach[] newArray(int size) {
	//			return new UserCoach[size];
	//		}
	//
	//		@Override
	//		public UserCoach createFromParcel(Parcel in) {
	//			return new UserCoach(in);
	//		}
	//	};
	//
	//	public UserCoach(Parcel in) {
	//		token = in.readString();
	//		name = in.readString();
	//		mxid = in.readInt();
	//		avatar = in.readString();
	//		gender = in.readInt();
	//		signature = in.readString();
	//		identity = in.readInt();
	//		tags = in.readArrayList(ArrayList.class.getClassLoader());
	//		mobile = in.readString();
	//		address = in.readString();
	//		target = in.readString();
	//		skill = in.readString();
	//		often = in.readString();
	//	}

	@Override
	public int hashCode() {
		return 17 * getUsername().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		try {
			if (o == null || !(o instanceof UserCoach)) {
				return false;
			}
			return getUsername().equals(((UserCoach) o).getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String toString() {
		return nick == null ? username : nick;
	}


	//	@Override
	//	public int describeContents() {
	//		// TODO Auto-generated method stub
	//		return 0;
	//	}



}
