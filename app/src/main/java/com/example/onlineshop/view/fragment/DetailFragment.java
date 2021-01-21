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

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.SliderAdapter;
import com.example.onlineshop.databinding.FragmentDetailBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SingleSharedDetailViewModel;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding mBinding;
    private SingleSharedDetailViewModel mViewModel;
    private Product mProduct;

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedDetailViewModel.class);
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detail,
                container,
                false);

        mBinding.fabAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.setIsSold(true);
                mViewModel.getProducts().add(mProduct);
                mViewModel.getProductListMutableLiveData().setValue(mViewModel.getProducts());
                mViewModel.getPrices().add(mProduct.getPrice());
                mViewModel.getPriceMutableLiveData().setValue(mViewModel.getPrices());
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        int id = args.getId();
        mViewModel.retrieveProduct(id);
    }

    private void setObserver() {
        mViewModel.getRetrieveProductLiveData().observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProduct = product;
                initViews(product);
            }
        });
    }

    private void initViews(Product product) {
        mBinding.setProduct(product);
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), product.getImageUrl());
        mBinding.imgProductSlider.setSliderAdapter(sliderAdapter);
    }
}