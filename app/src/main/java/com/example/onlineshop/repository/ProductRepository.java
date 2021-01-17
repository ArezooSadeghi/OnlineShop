package com.example.onlineshop.repository;

import android.content.Context;

public class ProductRepository {
    private Context mContext;
    private static ProductRepository sInstance;

    private ProductRepository(Context context) {
        mContext = context;
    }

    public static ProductRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProductRepository(context);
        }
        return sInstance;
    }
}
