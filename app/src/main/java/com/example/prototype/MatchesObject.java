package com.example.prototype;

import java.util.List;

public class MatchesObject {
    private String placeId;
//    private String name;
//    private List<String> imageUrl;
    public MatchesObject (String placeId) {
        this.placeId = placeId;

    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
