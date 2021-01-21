package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.repository.ProductRepository;

public class SharedReviewViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Integer> mStatusCodePostReviewLiveData;

    public SharedReviewViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mStatusCodePostReviewLiveData = mRepository.getStatusCodePostReviewMutableLiveData();
    }

    public LiveData<Integer> getStatusCodePostReviewLiveData() {
        return mStatusCodePostReviewLiveData;
    }

    public void postReview(int productId, String content, String name, String email, int rating) {
        mRepository.postReview(productId, content, name, email, rating);
    }
}
