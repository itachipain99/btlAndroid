package com.example.sherwoodsuitesaigon.Model;

import java.io.Serializable;

public class Detail implements Serializable {
    private String ticketPrice;
    private String opentime;
    private String attention;

    public Detail() {
    }

    public Detail(String ticketPrice, String opentime, String attention) {
        this.ticketPrice = ticketPrice;
        this.opentime = opentime;
        this.attention = attention;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }
}
