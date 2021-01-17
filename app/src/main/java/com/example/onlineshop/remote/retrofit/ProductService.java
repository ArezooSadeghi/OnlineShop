package com.example.onlineshop.remote.retrofit;

import com.example.onlineshop.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getTotalProduct();

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getBestProduct(@Query("orderby") String orderby, @Query("order") String order, @Query("per_page") int per_page);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getLatestProduct(@Query("orderby") String orderby, @Query("order") String order, @Query("per_page") int per_page);

    @GET("products?consumer key=ck_3bc440296ce055c52b5b7e421a0d48654ce215dd & " +
            "consumer secret=cs_a1062b3fc4224055d557cbc90a4323ca633f35aa")
    Call<List<Product>> getMostVisitedProduct(@Query("orderby") String orderby, @Query("order") String order, @Query("per_page") int per_page);
}
