package com.example.prototype;

public class MapMarkerObject {
    private String name;
    private double lat;
    private double lng;
    //private LatLng latlng;

    public MapMarkerObject(String n, double lat, double lng)
    {
        this.name = n;
        this.lat = lat;
        this.lng = lng;
    }

    public MapMarkerObject()
    {

    }

    public double getLat(){
        return this.lat;
    }
    public double getLng(){
        return this.lng;
    }
    public String getName()
    {
        return this.name;
    }

    public void setName(String n){
        this.name = n;
    }

    public void setLat(double lat){
        this.lat = lat;
    }

    public void setLng(double lng){
        this.lng = lng;
    }
}
