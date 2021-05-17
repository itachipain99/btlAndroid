package com.example.sherwoodsuitesaigon.Network;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class HaveFunNetwork  implements Serializable {
    private String description;
    private String address;
    private String categoryName;
    private ArrayList<String> imageUrls;
    private Location location;
    private String phone;
    private String price;
    private String url;
    private String title;
    private Double totalScore;
    private String website;

    public HaveFunNetwork() {
    }

    public HaveFunNetwork(String description, String address, String categoryName, ArrayList<String> imageUrls, Location location, String phone, String price, String url, String title, Double totalScore, String website) {
        this.description = description;
        this.address = address;
        this.categoryName = categoryName;
        this.imageUrls = imageUrls;
        this.location = location;
        this.phone = phone;
        this.price = price;
        this.url = url;
        this.title = title;
        this.totalScore = totalScore;
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
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
}
