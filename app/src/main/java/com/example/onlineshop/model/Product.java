package com.example.onlineshop.model;

import java.util.List;

public class Product {

    private int mId;
    private String mName;
    private String mPrice;
    private int mRatingCount;
    private String mDescription;
    private String mStockStatus;
    private List<String> mImageUrl;

    public Product(int id, String name, String price, int ratingCount,
                   String description, String stockStatus, List<String> imageUrl) {
        mId = id;
        mName = name;
        mPrice = price;
        mRatingCount = ratingCount;
        mDescription = description;
        mStockStatus = stockStatus;
        mImageUrl = imageUrl;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public void setRatingCount(int ratingCount) {
        mRatingCount = ratingCount;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getStockStatus() {
        return mStockStatus;
    }

    public void setStockStatus(String stockStatus) {
        mStockStatus = stockStatus;
    }

    public List<String> getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        mImageUrl = imageUrl;
    }
}
