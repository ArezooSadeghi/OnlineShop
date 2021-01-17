package com.example.onlineshop.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.onlineshop.model.Category;

public class CategoryDataSourceFactory extends DataSource.Factory<Integer, Category> {
    private MutableLiveData<PageKeyedDataSource<Integer, Category>> mPageKeyedDataSourceMutableLiveData = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Category> create() {
        CategoryDataSource categoryDataSource = new CategoryDataSource();
        mPageKeyedDataSourceMutableLiveData.postValue(categoryDataSource);
        return categoryDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Category>> getPageKeyedDataSourceMutableLiveData() {
        return mPageKeyedDataSourceMutableLiveData;
    }
}
