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

        mViewModel.getItemClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isItemClicked) {
                if (isItemClicked) {
                    mViewModel.getProductIdMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer id) {
                            ProductOfEachCategoryFragmentDirections.ActionProductOfEachCategoryFragmentToDetailFragment action =
                                    ProductOfEachCategoryFragmentDirections.actionProductOfEachCategoryFragmentToDetailFragment();
                            action.setId(id);
                            NavHostFragment.findNavController(ProductOfEachCategoryFragment.this).navigate(action);
                        }
                    });
                }
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerViewProductOfEachCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(getContext(), mViewModel, 3, products);
        mBinding.recyclerViewProductOfEachCategory.setAdapter(productAdapter);
    }
}