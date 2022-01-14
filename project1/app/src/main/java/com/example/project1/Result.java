package com.example.project1;

import java.util.List;
//class that used to convert jason to java object
public class Result {
    private String name;
    private String place_id;
    private Geometry geometry;
    private List<Photo> photos;

    public Result (String name, String place_id, Geometry geometry, List<Photo> photos){
        this.name = name;
        this.place_id = place_id;
        this.geometry = geometry;
        this.photos = photos;
    }
    public double getLat() {
        return geometry.getLat();
    }

    public double getLng() {
        return geometry.getLng();
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public List<Photo> getPhotosList() {
        return photos;
    }
    public Photo getPhotos() {
        return photos.get(0);
    }
    public Photo getPhotos(int position) {
        return photos.get(position);
    }

}
