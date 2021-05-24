package com.example.sherwoodsuitesaigon.Model;

import android.media.Image;

public class AnUongModel {
    private String imageLink;
    private String name;
    private String typeFood;
    private String price;
    private String opening;
    private String location;

    public AnUongModel(String imageLink, String name, String typeFood, String price, String opening, String location) {
        this.imageLink = imageLink;
        this.name = name;
        this.typeFood = typeFood;
        this.price = price;
        this.opening = opening;
        this.location = location;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(String typeFood) {
        this.typeFood = typeFood;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
