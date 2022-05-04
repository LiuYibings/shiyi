package com.example.supplements.beans;

import java.util.List;

public class Product {
    private int id;                     //id
    private String title;               //标题
    private String label;               //标签
    private String date;                //时间
    private String address;             //地址
    private String remark;              //备注
    private String price;               //价格
    private int userid;                 //所属商品的用户id
    private int catchuserid;
    private int photoid;
    private List<photos> photos;        //所属商品的图片

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", label='" + label + '\'' +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", remark='" + remark + '\'' +
                ", price='" + price + '\'' +
                ", userid=" + userid +
                ", catchuserid=" + catchuserid +
                ", photoid=" + photoid +
                ", photos=" + photos +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCatchuserid() {
        return catchuserid;
    }

    public void setCatchuserid(int catchuserid) {
        this.catchuserid = catchuserid;
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public List<com.example.supplements.beans.photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<com.example.supplements.beans.photos> photos) {
        this.photos = photos;
    }

    public Product(int id, String title, String label, String date, String address, String remark, String price, int userid, int catchuserid, int photoid, List<com.example.supplements.beans.photos> photos) {
        this.id = id;
        this.title = title;
        this.label = label;
        this.date = date;
        this.address = address;
        this.remark = remark;
        this.price = price;
        this.userid = userid;
        this.catchuserid = catchuserid;
        this.photoid = photoid;
        this.photos = photos;
    }

    public Product() {
    }
}
