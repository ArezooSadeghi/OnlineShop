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
    private MutableLiveData<List<String>> mPriceListMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Product> mAddClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Product> mDeleteClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Product> mRemoveClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Integer> mItemClickedMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mTotalAmountPaidMutableLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mOkClickedSingleLiveEvent = new SingleLiveEvent<>();
    private LiveData<List<Review>> mReviewListLiveData;
    private SingleLiveEvent<Boolean> mAddReviewClickedSingleLiveEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<Boolean> mAddToCartClickedSingleLiveEvent = new SingleLiveEvent<>();

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

    public MutableLiveData<List<String>> getPriceListMutableLiveData() {
        return mPriceListMutableLiveData;
    }

    public SingleLiveEvent<Product> getAddClickedSingleLiveEvent() {
        return mAddClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Product> getDeleteClickedSingleLiveEvent() {
        return mDeleteClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Product> getRemoveClickedSingleLiveEvent() {
        return mRemoveClickedSingleLiveEvent;
    }

    public MutableLiveData<Integer> getItemClickedMutableLiveData() {
        return mItemClickedMutableLiveData;
    }

    public MutableLiveData<String> getTotalAmountPaidMutableLiveData() {
        return mTotalAmountPaidMutableLiveData;
    }

    public SingleLiveEvent<Boolean> getOkClickedSingleLiveEvent() {
        return mOkClickedSingleLiveEvent;
    }

    public SingleLiveEvent<Boolean> getAddReviewClickedSingleLiveEvent() {
        return mAddReviewClickedSingleLiveEvent;
    }

    public void setAddReviewClickedSingleLiveEvent() {
        mAddReviewClickedSingleLiveEvent.setValue(true);
    }

    public SingleLiveEvent<Boolean> getAddToCartClickedSingleLiveEvent() {
        return mAddToCartClickedSingleLiveEvent;
    }

    public void setAddToCartClickedSingleLiveEvent() {
        mAddToCartClickedSingleLiveEvent.setValue(true);
    }

    public LiveData<List<Review>> getReviewListLiveData() {
        return mReviewListLiveData;
    }

    public void getReviews(int id) {
        mRepository.getReviews(id);
    }
}
