package com.example.onlineshop.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.onlineshop.model.Category;
import com.example.onlineshop.singleliveevent.SingleLiveEvent;

public class SingleCategoryViewModel extends ViewModel {

    private LiveData<PagedList<Category>> mPagedListLiveData;
    private LiveData<PageKeyedDataSource<Integer, Category>> mPageKeyedDataSourceLiveData;
    private MutableLiveData<Integer> mCategoryIdLiveData = new MutableLiveData<>();
    private SingleLiveEvent<Boolean> mItemClickedSingleLiveEvent = new SingleLiveEvent<>();

    public SingleCategoryViewModel() {
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

    public MutableLiveData<Integer> getCategoryIdLiveData() {
        return mCategoryIdLiveData;
    }

    public SingleLiveEvent<Boolean> getItemClickedSingleLiveEvent() {
        return mItemClickedSingleLiveEvent;
    }
}
