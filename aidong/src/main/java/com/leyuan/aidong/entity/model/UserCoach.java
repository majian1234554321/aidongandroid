package com.leyuan.aidong.entity.model;


import java.io.Serializable;
import java.util.Arrays;

public class UserCoach implements Serializable {
    private String token; // '用户token值',
    private int id; // 美型号,
    private String header;
    private String username;
    private String name;
    private  String avatar;
    private  int gender;
    private  String birthday;
    private  int age;
    private   String zodiac;
    private  String signature;
    private String province;
    private  String city;
    private  String area;
    private   String mobile;
    private  int height;
    private   int weight;
    private  int bmi;
    private  String bust;
    private  String waist;
    private  boolean hip;
    private  String charm_site;
    private  String frequency;

    private  String[] sport;
    private   UserTag tag[];

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }


    public String getCharm_site() {
        return charm_site;
    }

    public void setCharm_site(String charm_site) {
        this.charm_site = charm_site;
    }

    public String[] getSport() {
        return sport;
    }

    public void setSport(String[] sport) {
        this.sport = sport;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserTag[] getTag() {
        return tag;
    }

    public void setTag(UserTag[] tag) {
        this.tag = tag;
    }

    public class UserTag {
        String name;
        int color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getWaist() {
        return waist;
    }

    public void setWaist(String waist) {
        this.waist = waist;
    }

    public boolean isHip() {
        return hip;
    }

    public void setHip(boolean hip) {
        this.hip = hip;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        if (username == null)
            username = getName();
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
        return "UserCoach{" +
                "token='" + token + '\'' +
                ", id=" + id +
                ", header='" + header + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender=" + gender +
                ", birthday='" + birthday + '\'' +
                ", age=" + age +
                ", zodiac='" + zodiac + '\'' +
                ", signature='" + signature + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", mobile='" + mobile + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", bmi=" + bmi +
                ", bust='" + bust + '\'' +
                ", waist='" + waist + '\'' +
                ", hip=" + hip +
                ", charm_site='" + charm_site + '\'' +
                ", frequency='" + frequency + '\'' +
                ", sport=" + Arrays.toString(sport) +
                ", tag=" + Arrays.toString(tag) +
                '}';
    }
}
