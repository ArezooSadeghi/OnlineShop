package com.example.onlineshop.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.onlineshop.model.Category;
import com.example.onlineshop.remote.retrofit.CategoryListDeserializer;
import com.example.onlineshop.remote.retrofit.ProductService;
import com.example.onlineshop.remote.retrofit.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDataSource extends PageKeyedDataSource<Integer, Category> {
    private ProductService mCategoryService;
    private static final int FIRST_PAGE = 1;
    private MutableLiveData<Integer> mTotalMutableLiveData = new MutableLiveData<>();
    public static final int PER_PAGE = 10;

    private static final String TAG = CategoryDataSource.class.getSimpleName();

    {
        mCategoryService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Category>>() {
                }.getType(),
                new CategoryListDeserializer()).create(ProductService.class);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, Category> callback) {
        mCategoryService.getCategory(FIRST_PAGE).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.body() != null) {
                    mTotalMutableLiveData.setValue(Integer.valueOf(response.headers().values("x-wp-totalpages").get(0)));
                    callback.onResult(response.body(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Category> callback) {
        mCategoryService.getCategory(params.key).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Integer adjacentPageKey = (params.key > 1) ? params.key - 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Category> callback) {
        mCategoryService.getCategory(params.key).enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                Integer adjacentPageKey = params.key < mTotalMutableLiveData.getValue() ? params.key + 1 : null;
                if (response.body() != null) {
                    callback.onResult(response.body(), adjacentPageKey);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}
