package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class SingleSharedDetailViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Product> mRetrieveProductLiveData;
    private List<Product> mProducts = new ArrayList<>();
    private List<String> mPrices = new ArrayList<>();
    private MutableLiveData<List<String>> mPriceMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mAddClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mDeleteClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mRemoveClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mItemClickedMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> mProductMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mTotalAmountPaidMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mOkClickedSingleLiveEvent = new SingleLiveEvent<>();
    private LiveData<List<Review>> mReviewListLiveData;
    private List<Review> mReviews = new ArrayList<>();

    public SingleSharedDetailViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mRetrieveProductLiveData = mRepository.getProductMutableLiveData();
        mReviewListLiveData = mRepository.getReviewListMutableLiveData();
    }

    public LiveData<Product> getRetrieveProductLiveData() {
        return mRetrieveProductLiveData;
    }

    public void retrieveProduct(int id) {
        mRepository.retrieveProduct(id);
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public List<String> getPrices() {
        return mPrices;
    }

    public MutableLiveData<List<Product>> getProductListMutableLiveData() {
        return mProductListMutableLiveData;
    }

    public MutableLiveData<List<String>> getPriceMutableLiveData() {
        return mPriceMutableLiveData;
    }

    public MutableLiveData<Product> getProductMutableLiveData() {
        return mProductMutableLiveData;
    }

    public SingleLiveEvent<Boolean> getAddClickedSingleLiveEvent() {
        return mAddClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getDeleteClickedSingleLiveEvent() {
        return mDeleteClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getRemoveClickedSingleLiveEvent() {
        return mRemoveClickedSingleLiveEvent;
    }

    public MutableLiveData<Boolean> getItemClickedMutableLiveData() {
        return mItemClickedMutableLiveData;
    }

    public MutableLiveData<String> getTotalAmountPaidMutableLiveData() {
        return mTotalAmountPaidMutableLiveData;
    }

    public SingleLiveEvent<Boolean> getOkClickedSingleLiveEvent() {
        return mOkClickedSingleLiveEvent;
    }

    public LiveData<List<Review>> getReviewListLiveData() {
        return mReviewListLiveData;
    }

    public List<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    public void getReviews(int id) {
        mRepository.getReviews(id);
    }
}
