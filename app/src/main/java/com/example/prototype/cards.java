package com.example.prototype;

import java.util.List;

public class cards {
    private String placeId;
    private String name;
    private List<String> imageUrl;
    private List<Double> latlon;
    private String address;
    public cards (String name, List<String> imageUrl, String placeId, List<Double> latlon, String address) {
        this.placeId = placeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.latlon = latlon;
        this.address = address;

    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlaceId() { return placeId; }


    public void setPlaceId(String placeId) { this.placeId = placeId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getLatlon() {
        return latlon;
    }

    public void setLatlon(List<Double> latlon) {
        this.latlon = latlon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
