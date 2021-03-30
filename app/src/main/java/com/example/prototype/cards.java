package com.example.prototype;

import java.util.List;

public class cards {
    private String placeId;
    private String name;
    private List<String> imageUrl;
    public cards (String name, List<String> imageUrl, String placeId) {
        this.placeId = placeId;
        this.name = name;
        this.imageUrl = imageUrl;

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

}
