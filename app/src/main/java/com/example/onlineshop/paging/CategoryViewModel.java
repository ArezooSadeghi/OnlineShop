package com.example.onlineshop.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.onlineshop.model.Category;

public class CategoryViewModel extends ViewModel {

    private LiveData<PagedList<Category>> mPagedListLiveData;
    private LiveData<PageKeyedDataSource<Integer, Category>> mPageKeyedDataSourceLiveData;

    public CategoryViewModel() {
        CategoryDataSourceFactory categoryDataSourceFactory = new CategoryDataSourceFactory();
        mPageKeyedDataSourceLiveData = categoryDataSourceFactory.getPageKeyedDataSourceMutableLiveData();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(CategoryDataSource.PER_PAGE).build();

        mPagedListLiveData = (new LivePagedListBuilder(categoryDataSourceFactory, pagedListConfig))
                .build();
    }

    public LiveData<PagedList<Category>> getPagedListLiveData() {
        return mPagedListLiveData;
    }

    public LiveData<PageKeyedDataSource<Integer, Category>> getPageKeyedDataSourceLiveData() {
        return mPageKeyedDataSourceLiveData;
    }
}
