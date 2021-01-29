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
    private LiveData<List<Product>> mSearchSortProductsLiveData;

    public SearchableViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mSearchProductLiveData = mRepository.getSearchProductMutableLiveData();
        mSearchSortProductsLiveData = mRepository.getSearchSortProductsMutableLiveData();
    }

    public LiveData<List<Product>> getSearchProductLiveData() {
        return mSearchProductLiveData;
    }

    public LiveData<List<Product>> getSearchSortProductsLiveData() {
        return mSearchSortProductsLiveData;
    }

    public void searchSortProducts(String search, String orderby, String order) {
        mRepository.searchSortProducts(search, orderby, order);
    }

    public void searchProducts(String search) {
        mRepository.searchProducts(search);
    }
}
