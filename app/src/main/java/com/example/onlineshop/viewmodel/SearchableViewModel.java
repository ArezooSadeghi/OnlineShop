package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;

import java.util.List;

public class SearchableViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mSearchProductLiveData;

    public SearchableViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mSearchProductLiveData = mRepository.getSearchProductMutableLiveData();
    }

    public LiveData<List<Product>> getSearchProductLiveData() {
        return mSearchProductLiveData;
    }

    public void searchProducts(String search) {
        mRepository.searchProducts(search);
    }
}
