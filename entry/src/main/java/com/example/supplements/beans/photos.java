package com.example.supplements.beans;

public class photos {
    private int id;
    private int photoid;
    private String img;

    @Override
    public String toString() {
        return "photos{" +
                "id=" + id +
                ", photoid=" + photoid +
                ", img='" + img + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public photos(int id, int photoid, String img) {
        this.id = id;
        this.photoid = photoid;
        this.img = img;
    }

    public photos() {
    }
}
