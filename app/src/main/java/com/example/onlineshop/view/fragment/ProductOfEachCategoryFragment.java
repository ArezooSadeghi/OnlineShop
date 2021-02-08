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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.databinding.FragmentProductOfEachCategoryBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SingleProductOfEachCategoryViewModel;

import java.util.List;

public class ProductOfEachCategoryFragment extends Fragment {
    private FragmentProductOfEachCategoryBinding mBinding;
    private SingleProductOfEachCategoryViewModel mViewModel;


    public static ProductOfEachCategoryFragment newInstance() {
        ProductOfEachCategoryFragment fragment = new ProductOfEachCategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SingleProductOfEachCategoryViewModel.class);
        ProductOfEachCategoryFragmentArgs args = ProductOfEachCategoryFragmentArgs.fromBundle(getArguments());
        int categoryId = args.getId();
        mViewModel.getProductByCategory(categoryId, 1);
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
        setObserver();
    }


    private void initRecyclerView() {
        mBinding.recyclerViewProductOfEachCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setObserver() {
        mViewModel.getProductIdSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer productId) {
                ProductOfEachCategoryFragmentDirections.ActionProductOfEachCategoryFragmentToDetailFragment action =
                        ProductOfEachCategoryFragmentDirections.actionProductOfEachCategoryFragmentToDetailFragment();
                action.setId(productId);
                NavHostFragment.findNavController(ProductOfEachCategoryFragment.this).navigate(action);
            }
        });


        mViewModel.getProductByCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });
    }


    private void setupAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(getContext(), mViewModel, 3, products);
        mBinding.recyclerViewProductOfEachCategory.setAdapter(adapter);
    }
}