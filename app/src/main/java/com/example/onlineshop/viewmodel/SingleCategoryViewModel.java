package com.example.onlineshop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlineshop.model.Category;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

import java.util.List;

public class SingleCategoryViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private SingleLiveEvent<Integer> mCategoryIdSingleLiveEvent = new SingleLiveEvent<>();
    private LiveData<List<Category>> mCategoryListLiveData;

    public SingleCategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = ProductRepository.getInstance(getApplication());
        mCategoryListLiveData = mRepository.getCategoryListMutableLiveData();
    }

    public SingleLiveEvent<Integer> getCategoryIdSingleLiveEvent() {
        return mCategoryIdSingleLiveEvent;
    }

    public LiveData<List<Category>> getCategoryListLiveData() {
        return mCategoryListLiveData;
    }

    public void getCategory(int page) {
        mRepository.getCategory(page);
    }
}
