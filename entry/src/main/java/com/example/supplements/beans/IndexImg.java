package com.example.supplements.beans;


public class IndexImg {
   private int id;
   private String image;

    @Override
    public String toString() {
        return "IndexImg{" +
                "id=" + id +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public IndexImg(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public IndexImg() {
    }
}