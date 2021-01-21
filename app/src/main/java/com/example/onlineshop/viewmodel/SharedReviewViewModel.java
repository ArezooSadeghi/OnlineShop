package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Review;
import com.example.onlineshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SharedReviewViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Integer> mStatusCodePostReviewLiveData;
    private List<Review> mReviews = new ArrayList<>();
    private MutableLiveData<List<Review>> mReviewListMutableLiveData = new MutableLiveData<>();
    private LiveData<Review> mReviewLiveData;

    public SharedReviewViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mStatusCodePostReviewLiveData = mRepository.getStatusCodePostReviewMutableLiveData();
        mReviewLiveData = mRepository.getReviewMutableLiveData();
    }

    public LiveData<Integer> getStatusCodePostReviewLiveData() {
        return mStatusCodePostReviewLiveData;
    }

    public MutableLiveData<List<Review>> getReviewListMutableLiveData() {
        return mReviewListMutableLiveData;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public LiveData<Review> getReviewLiveData() {
        return mReviewLiveData;
    }

    public void postReview(int productId, String content, String name, String email, int rating) {
        mRepository.postReview(productId, content, name, email, rating);
    }
}
