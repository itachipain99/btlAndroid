package com.example.demorecomment;

import java.io.Serializable;

public class SearchModel implements Serializable {
    private String title;

    public SearchModel(String title) {
        this.title = title;
    }

    public SearchModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
