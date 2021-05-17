package com.example.sherwoodsuitesaigon.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String name;
    private String password;
    private String phone_number;
    private String user_name;

    public User(String email, String name, String password, String phone_number, String user_name) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone_number = phone_number;
        this.user_name = user_name;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
