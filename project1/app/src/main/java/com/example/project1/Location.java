package com.example.project1;
//class that used to convert jason to java object
public class Location {
    private double lat;
    private double lng;
    public Location(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
