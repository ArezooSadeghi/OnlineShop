package com.example.onlineshop.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.SearchAdapter;
import com.example.onlineshop.databinding.ActivitySearchableBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SearchableViewModel;

import java.util.List;

public class SearchableActivity extends AppCompatActivity {
    private ActivitySearchableBinding mBinding;
    private SearchableViewModel mViewModel;
    private String mQuery;

    private static final String TAG = SearchableActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_searchable);

        initToolbar();

        mViewModel = new ViewModelProvider(this).get(SearchableViewModel.class);
        setObserver();

        handleIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchable_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sort_new:
                mViewModel.searchSortProducts(mQuery, "date", "desc");
                return true;
            case R.id.item_sort_best_sellers:
                mViewModel.searchSortProducts(mQuery, "popularity", "desc");
                return true;
            case R.id.item_sort_price_low_high:
                mViewModel.searchSortProducts(mQuery, "price", "asc");
                return true;
            case R.id.item_sort_price_high_low:
                mViewModel.searchSortProducts(mQuery, "price", "desc");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initToolbar() {
        setSupportActionBar(mBinding.searchableToolbar);
    }


    private void setObserver() {
        mViewModel.getSearchProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });

        mViewModel.getSearchSortProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }


    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(mQuery);
        }
    }


    private void doMySearch(String query) {
        mViewModel.searchProducts(query);
    }


    private void setupAdapter(List<Product> products) {
        SearchAdapter adapter = new SearchAdapter(this, products);
        mBinding.listViewSearchResult.setAdapter(adapter);
    }
}