package com.example.onlineshop.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.onlineshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Product {

    private int mId;
    private String mName;
    private String mPrice;
    private String mAverageRating;
    private String mDescription;
    private String mStockStatus;
    private List<String> mImageUrl;

    public Product(int id, String name, String price, String averageRating,
                   String description, String stockStatus, List<String> imageUrl) {
        mId = id;
        mName = name;
        mPrice = price;
        mAverageRating = averageRating;
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

    public String getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
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

    @BindingAdapter({"imageUrl"})
    public static void setProductImage(ImageView imageView, String url) {
        if (url == null) {
            Picasso.get().load(R.drawable.image_place_holder).into(imageView);
        } else {
            Picasso.get().load(url).into(imageView);
        }
    }
}
