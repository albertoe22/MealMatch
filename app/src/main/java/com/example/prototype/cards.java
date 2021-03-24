package com.example.prototype;

import java.util.List;

public class cards {
    //private String userId;
    private String name;
    private List<String> imageUrl;
    public cards ( String name, List<String> imageUrl) {
        //this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;

    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

/*    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
