package com.example.sherwoodsuitesaigon.Model;

import java.util.ArrayList;
import java.util.List;

public class HaveFunPlace {
    private String namePlace;
    private String place;
    private String introduct;
    private List<String> imageList;
    private Detail detail;

    public HaveFunPlace(String namePlace, String place, String introduct, List<String> imageList, Detail detail) {
        this.namePlace = namePlace;
        this.place = place;
        this.introduct = introduct;
        this.imageList = imageList;
        this.detail = detail;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntroduct() {
        return introduct;
    }

    public void setIntroduct(String introduct) {
        this.introduct = introduct;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
}


