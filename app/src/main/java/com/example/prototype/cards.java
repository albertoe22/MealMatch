package com.example.prototype;

public class cards {
    //private String userId;
    private String name;
    private String imageUrl;
    public cards ( String name,String imageUrl) {
        //this.userId = userId;
        this.name = name;
        this.imageUrl = imageUrl;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
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
