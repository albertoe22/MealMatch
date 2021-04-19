package com.example.prototype;

import java.util.List;

public class MatchesObject {
    private String placeId;
    private String name;
    private String address;
    public MatchesObject (String placeId, String name, String address) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;


    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
