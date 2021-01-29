package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class SingleHomeViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mBestProductLiveData,
            mLatestProductLiveData, mMostVisitedProductLiveData, mSpecialProductLiveData;
    private SingleLiveEvent<Integer> mProductIdSingleLiveEvent = new SingleLiveEvent<>();

    public SingleHomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mBestProductLiveData = mRepository.getBestProductMutableLiveData();
        mLatestProductLiveData = mRepository.getLatestProductMutableLiveData();
        mMostVisitedProductLiveData = mRepository.getMostVisitedProductMutableLiveData();
        mSpecialProductLiveData = mRepository.getSpecialProductMutableLiveData();
    }

    public LiveData<List<Product>> getBestProductLiveData() {
        return mBestProductLiveData;
    }

    public LiveData<List<Product>> getLatestProductLiveData() {
        return mLatestProductLiveData;
    }

    public LiveData<List<Product>> getMostVisitedProductLiveData() {
        return mMostVisitedProductLiveData;
    }

    public LiveData<List<Product>> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }

    public SingleLiveEvent<Integer> getProductIdSingleLiveEvent() {
        return mProductIdSingleLiveEvent;
    }

    public void getTotalProduct() {
        mRepository.getTotalProduct();
    }

    public void getBestProduct(String orderby, String order) {
        mRepository.getBestProduct(orderby, order);
    }

    public void getLatestProduct(String orderby, String order) {
        mRepository.getLatestProduct(orderby, order);
    }

    public void getMostVisitedProduct(String orderby, String order) {
        mRepository.getMostVisitedProduct(orderby, order);
    }

    public void getSpecialProduct(boolean featured) {
        mRepository.getSpecialProduct(featured);
    }

    public List<String> getUrls(List<Product> products) {
        List<String> urls = new ArrayList<>();
        for (Product product : products) {
            if (product.getImageUrl() != null) {
                urls.addAll(product.getImageUrl());
            }
        }
        return urls;
    }
}
