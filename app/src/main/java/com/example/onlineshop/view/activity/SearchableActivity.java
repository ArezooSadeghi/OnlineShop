package com.example.onlineshop.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.SearchAdapter;
import com.example.onlineshop.databinding.ActivitySearchableBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.view.fragment.SortDialogFragment;
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

        mViewModel = new ViewModelProvider(this).get(SearchableViewModel.class);
        mBinding.setSearchableViewModel(mViewModel);
        setObserver();

        handleIntent();
    }

    private void setObserver() {
        mViewModel.getSearchProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });

        mViewModel.getSortClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSortClicked) {
                if (isSortClicked) {
                    SortDialogFragment dialogFragment = SortDialogFragment.newInstance();
                    dialogFragment.show(getSupportFragmentManager(), TAG);
                }
            }
        });

        mViewModel.getSortNewMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNewSortClicked) {
                mViewModel.searchSortProducts(mQuery, "date", "desc");
            }
        });

        mViewModel.getSearchSortProductsLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });

        mViewModel.getSortBestSellerMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isBestSellerClicked) {
                mViewModel.searchSortProducts(mQuery, "popularity", "desc");
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