package com.example.onlineshop.repository;

import android.content.Context;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.remote.retrofit.ProductListDeserializer;
import com.example.onlineshop.remote.retrofit.ProductService;
import com.example.onlineshop.remote.retrofit.RetrofitInstance;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ProductRepository {
    private Context mContext;
    private ProductService mProductService;
    private static ProductRepository sInstance;

    private ProductRepository(Context context) {
        mProductService = RetrofitInstance.getRetrofitInstance(
                new TypeToken<List<Product>>() {
                }.getType(),
                new ProductListDeserializer()).create(ProductService.class);
        mContext = context;
    }

    public static ProductRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProductRepository(context);
        }
        return sInstance;
    }
}
