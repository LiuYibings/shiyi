package com.example.supplements.beans;

public class user {
    private int userid;
    private String name;
    private int password;
    private int telemphone;
    private int qq;
    private String emil;
    private String address;
    private String weixing;

    @Override
    public String toString() {
        return "user{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", telemphone=" + telemphone +
                ", qq=" + qq +
                ", emil='" + emil + '\'' +
                ", address='" + address + '\'' +
                ", weixing='" + weixing + '\'' +
                '}';
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getTelemphone() {
        return telemphone;
    }

    public void setTelemphone(int telemphone) {
        this.telemphone = telemphone;
    }

    public int getQq() {
        return qq;
    }

    public void setQq(int qq) {
        this.qq = qq;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeixing() {
        return weixing;
    }

    public void setWeixing(String weixing) {
        this.weixing = weixing;
    }

    public user(int userid, String name, int password, int telemphone, int qq, String emil, String address, String weixing) {
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.telemphone = telemphone;
        this.qq = qq;
        this.emil = emil;
        this.address = address;
        this.weixing = weixing;
    }

    public user() {
    }
}
