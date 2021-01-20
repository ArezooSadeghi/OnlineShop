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

public class SingleProductOfEachCategoryViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private LiveData<List<Product>> mProductByCategoryLiveData;
    private SingleLiveEvent<Boolean> mItemClickedSingleLiveEvent = new SingleLiveEvent<>();
    private MutableLiveData<Integer> mProductIdMutableLiveData = new MutableLiveData<>();
    private LiveData<Integer> mTotalPageLiveData;

    public SingleProductOfEachCategoryViewModel(@NonNull Application application) {
        super(application);

        mRepository = ProductRepository.getInstance(getApplication());
        mProductByCategoryLiveData = mRepository.getProductByCategoryMutableLiveData();
        mTotalPageLiveData = mRepository.getTotalPageMutableLiveData();
    }

    public LiveData<List<Product>> getProductByCategoryLiveData() {
        return mProductByCategoryLiveData;
    }

    public SingleLiveEvent<Boolean> getItemClickedSingleLiveEvent() {
        return mItemClickedSingleLiveEvent;
    }

    public MutableLiveData<Integer> getProductIdMutableLiveData() {
        return mProductIdMutableLiveData;
    }

    public LiveData<Integer> getTotalPageLiveData() {
        return mTotalPageLiveData;
    }

    public void getProductByCategory(int categoryId, int page) {
        mRepository.getProductByCategory(categoryId, page);
    }
}