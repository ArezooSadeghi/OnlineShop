package com.example.onlineshop.model;

public class Review {
    private int mId;
    private int mProductId;
    private String mReviewContent;
    private String mReviewerName;
    private String mReviewerEmail;
    private int mRating;

    public Review(int id, int productId, String reviewContent, String reviewerName, String reviewerEmail, int rating) {
        mId = id;
        mProductId = productId;
        mReviewContent = reviewContent;
        mReviewerName = reviewerName;
        mReviewerEmail = reviewerEmail;
        mRating = rating;
    }

    public Review(int productId, String reviewContent, String reviewerName, String reviewerEmail, int rating) {
        mProductId = productId;
        mReviewContent = reviewContent;
        mReviewerName = reviewerName;
        mReviewerEmail = reviewerEmail;
        mRating = rating;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }

    public String getReviewContent() {
        return mReviewContent;
    }

    public void setReviewContent(String reviewContent) {
        mReviewContent = reviewContent;
    }

    public String getReviewerName() {
        return mReviewerName;
    }

    public void setReviewerName(String reviewerName) {
        mReviewerName = reviewerName;
    }

    public String getReviewerEmail() {
        return mReviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        mReviewerEmail = reviewerEmail;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }
}
