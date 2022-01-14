package com.example.project1;
//class that used to convert jason to java object
public class Photo {
    private int height;
    private int width;
    private String photo_reference;
    public Photo(int height,int width,String photo_reference){
        this.height = height;
        this.width = width;
        this.photo_reference = photo_reference;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    public String getPhoto_reference() {
        return photo_reference;
    }
}