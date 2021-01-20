package com.example.onlineshop.model;

public class Order {
    private String mEmail;

    public Order(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
