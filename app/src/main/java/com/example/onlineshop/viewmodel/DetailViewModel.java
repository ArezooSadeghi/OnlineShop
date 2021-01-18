package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;

public class DetailViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<Product> mProductLiveData;

    public DetailViewModel(@NonNull Application application) {
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
}
