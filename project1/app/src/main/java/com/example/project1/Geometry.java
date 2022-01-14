package com.example.project1;

//class that used to convert jason to java object
public class Geometry {
    private Location location;
    public Geometry(Location location){
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    public double getLat() {
        return location.getLat();
    }

    public double getLng() {
        return location.getLng();
    }
}
