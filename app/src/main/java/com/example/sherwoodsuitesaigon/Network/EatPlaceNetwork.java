package com.example.sherwoodsuitesaigon.Network;

import android.renderscript.Allocation;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

@IgnoreExtraProperties
public class EatPlaceNetwork implements Serializable {
    private String Description;
    private String address;
    private String categoryName;
    private List<String> imageUrls;
    private Location location;
    private String phone;
    private String price;
    private String url;
    private String state;
    private String title;
    private Double totalScore;
    private String website;

    public EatPlaceNetwork() {
    }

    public EatPlaceNetwork(String description, String address, String categoryName, List<String> imageUrls, Location location, String phone, String price, String url, String state, String title, Double totalScore, String website) {
        this.Description = description;
        this.address = address;
        this.categoryName = categoryName;
        this.imageUrls = imageUrls;
        this.location = location;
        this.phone = phone;
        this.price = price;
        this.url = url;
        this.state = state;
        this.title = title;
        this.totalScore = totalScore;
        this.website = website;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}


class Location implements Serializable {
    private Double lat;
    private Double lng;

    public Location() {

    }

    public Location(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}