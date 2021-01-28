package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.List;

public class SearchableViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mSearchProductLiveData;
    private SingleLiveEvent<Boolean> mSortClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Boolean> mSortNewMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mSortBestSellerMutableLiveData = new MutableLiveData<>();
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

    public SingleLiveEvent<Boolean> getSortClickedSingleLiveEvent() {
        return mSortClickedSingleLiveEvent;
    }

    public void setSortClickedSingleLiveEvent() {
        mSortClickedSingleLiveEvent.setValue(true);
    }

    public MutableLiveData<Boolean> getSortNewMutableLiveData() {
        return mSortNewMutableLiveData;
    }

    public void setSortNewMutableLiveData() {
        mSortNewMutableLiveData.setValue(true);
    }

    public LiveData<List<Product>> getSearchSortProductsLiveData() {
        return mSearchSortProductsLiveData;
    }

    public MutableLiveData<Boolean> getSortBestSellerMutableLiveData() {
        return mSortBestSellerMutableLiveData;
    }

    public void setSortBestSellerMutableLiveData() {
        mSortBestSellerMutableLiveData.setValue(true);
    }

    public void searchSortProducts(String search, String orderby, String order) {
        mRepository.searchSortProducts(search, orderby, order);
    }

    public void searchProducts(String search) {
        mRepository.searchProducts(search);
    }
}
