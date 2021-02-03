package com.example.prototype;

public class Card {
    private int picture;
    private String location;
    public Card(){

    }
    public Card(int p,String l){
        picture = p;
        location = l;
    }
    public int getPicture(){
        return picture;
    }
    public String getLocation(){
        return location;
    }
    public void setPicture(int p){
        picture = p;
    }
    public void setLocation(String l){
        location = l;
    }
}
