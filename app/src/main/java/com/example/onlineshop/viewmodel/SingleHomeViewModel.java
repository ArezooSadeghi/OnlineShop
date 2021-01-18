package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

public class SingleHomeViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mBestProductLiveData,
            mLatestProductLiveData, mMostVisitedProductLiveData, mSpecialProductLiveData;
    private LiveData<Integer> mTotalProductLiveData;
    private MutableLiveData<Integer> mProductIdLiveData = new MutableLiveData<>();

    private SingleLiveEvent<Boolean> mItemClickedSingleLiveEvent = new SingleLiveEvent<>();

    public SingleHomeViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mBestProductLiveData = mRepository.getBestProductMutableLiveData();
        mLatestProductLiveData = mRepository.getLatestProductMutableLiveData();
        mMostVisitedProductLiveData = mRepository.getMostVisitedProductMutableLiveData();
        mTotalProductLiveData = mRepository.getTotalProductMutableLiveData();
        mSpecialProductLiveData = mRepository.getSpecialProductMutableLiveData();
    }

    public LiveData<Integer> getTotalProductLiveData() {
        return mTotalProductLiveData;
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

    public SingleLiveEvent<Boolean> getItemClickedSingleLiveEvent() {
        return mItemClickedSingleLiveEvent;
    }

    public MutableLiveData<Integer> getProductIdLiveData() {
        return mProductIdLiveData;
    }

    public void getTotalProduct() {
        mRepository.getTotalProduct();
    }

    public void getBestProduct(String orderby, String order, int per_page) {
        mRepository.getBestProduct(orderby, order, per_page);
    }

    public void getLatestProduct(String orderby, String order, int per_page) {
        mRepository.getLatestProduct(orderby, order, per_page);
    }

    public void getMostVisitedProduct(String orderby, String order, int per_page) {
        mRepository.getMostVisitedProduct(orderby, order, per_page);
    }

    public void getSpecialProduct(boolean featured, int per_page) {
        mRepository.getSpecialProduct(featured, per_page);
    }

    public List<String> getUrl(List<Product> products) {
        List<String> urls = new ArrayList<>();
        for (Product product : products) {
            if (product.getImageUrl().size() != 0) {
                urls.add(product.getImageUrl().get(0));
            }
        }
        return urls;
    }
}
