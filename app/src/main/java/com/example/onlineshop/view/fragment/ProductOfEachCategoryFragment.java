package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.databinding.FragmentProductOfEachCategoryBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.ProductOfEachCategoryViewModel;

import java.util.List;

public class ProductOfEachCategoryFragment extends Fragment {
    private FragmentProductOfEachCategoryBinding mBinding;
    private ProductOfEachCategoryViewModel mViewModel;


    public static ProductOfEachCategoryFragment newInstance() {
        ProductOfEachCategoryFragment fragment = new ProductOfEachCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ProductOfEachCategoryViewModel.class);
        setObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_product_of_each_category,
                container,
                false);

        initRecyclerView();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductOfEachCategoryFragmentArgs args = ProductOfEachCategoryFragmentArgs.fromBundle(getArguments());
        int id = args.getId();
        mViewModel.getProductByCategory(id);
    }

    private void setObserver() {
        mViewModel.getProductByCategoryLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerViewProductOfEachCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(getContext(), 3, products);
        mBinding.recyclerViewProductOfEachCategory.setAdapter(productAdapter);
    }
}