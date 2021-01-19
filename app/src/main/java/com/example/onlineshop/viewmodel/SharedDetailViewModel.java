package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SharedDetailViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Product> mProductLiveData;
    private List<Product> mProducts = new ArrayList<>();
    private List<String> mPrices = new ArrayList<>();
    private MutableLiveData<List<String>> mPriceMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProductListMutableLiveData = new MutableLiveData<>();

    public SharedDetailViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mProductLiveData = mRepository.getProductMutableLiveData();
    }

    public LiveData<Product> getProductLiveData() {
        return mProductLiveData;
    }

    public void retrieveProduct(int id) {
        mRepository.retrieveProduct(id);
    }

    public void addToCartClicked(View view) {
        view.setVisibility(View.GONE);
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
}
