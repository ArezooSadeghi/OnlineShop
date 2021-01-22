package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Review;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class SingleSharedReviewViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private List<Review> mReviews = new ArrayList<>();
    private MutableLiveData<List<Review>> mReviewListMutableLiveData = new MutableLiveData<>();
    private LiveData<Review> mReviewLiveData;
    private SingleLiveEvent<Boolean> mDeleteClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Review> mReviewMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mEditClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Review> mEditReviewMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Review> mDialogReviewMutableLiveData = new MutableLiveData<>();
    private LiveData<Review> mUpdateReviewLiveData;

    public SingleSharedReviewViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mReviewLiveData = mRepository.getReviewMutableLiveData();
        mUpdateReviewLiveData = mRepository.getUpdateReviewMutableLiveData();
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

    public SingleLiveEvent<Boolean> getDeleteClickedSingleLiveEvent() {
        return mDeleteClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getEditClickedSingleLiveEvent() {
        return mEditClickedSingleLiveEvent;
    }

    public MutableLiveData<Review> getReviewMutableLiveData() {
        return mReviewMutableLiveData;
    }

    public MutableLiveData<Review> getEditReviewMutableLiveData() {
        return mEditReviewMutableLiveData;
    }

    public MutableLiveData<Review> getDialogReviewMutableLiveData() {
        return mDialogReviewMutableLiveData;
    }

    public LiveData<Review> getUpdateReviewLiveData() {
        return mUpdateReviewLiveData;
    }

    public void updateReview(int id, String content, String name, int rating) {
        mRepository.updateReview(id, content, name, rating);
    }

    public void deleteReview(int id) {
        mRepository.deleteReview(id);
    }

    public boolean isValidReview(Review myReview, List<Review> reviews) {
        for (Review review : reviews) {
            if (review.getId() == myReview.getId()) {
                return false;
            }
        }
        return true;
    }
}
