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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.adapter.SliderAdapter;
import com.example.onlineshop.databinding.FragmentHomeBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.SingleHomeViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private SingleHomeViewModel mViewModel;


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SingleHomeViewModel.class);

        mViewModel.getBestProduct("rating", "desc");
        mViewModel.getLatestProduct("date", "desc");
        mViewModel.getMostVisitedProduct("popularity", "desc");
        mViewModel.getSpecialProduct(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false);

        mBinding.setIsLoading(true);
        mBinding.setSingleHomeViewModel(mViewModel);

        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void initRecyclerView() {
        mBinding.recyclerViewBestProduct.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        mBinding.recyclerViewLatestProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBinding.recyclerViewMostVisitedProduct.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
    }


    private void setObserver() {
        mViewModel.getBestProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mBinding.setIsLoading(false);
                mBinding.setBestProductTitle(getString(R.string.best_product_title));
                setupBestProductAdapter(products);
            }
        });


        mViewModel.getLatestProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Preferences.setLastId(getContext(), products.get(0).getId());
                mBinding.setLatestProductTitle(getString(R.string.latest_product_title));
                setupLatestProductAdapter(products);
            }
        });


        mViewModel.getMostVisitedProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mBinding.setMostVisitedProductTitle(getString(R.string.most_visited_product_title));
                setupMostVisitedProductAdapter(products);
            }
        });


        mViewModel.getSpecialProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupSpecialProductAdapter(products);
            }
        });


        mViewModel.getProductIdSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer productId) {
                HomeFragmentDirections.ActionNavigationHomeToDetailFragment action =
                        HomeFragmentDirections.actionNavigationHomeToDetailFragment();
                action.setId(productId);
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });


        mViewModel.getSearchClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSearchClicked) {
                getActivity().onSearchRequested();
            }
        });
    }


    private void setupBestProductAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(getContext(), mViewModel, 1, products);
        mBinding.recyclerViewBestProduct.setAdapter(adapter);
    }


    private void setupLatestProductAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(getContext(), mViewModel, 1, products);
        mBinding.recyclerViewLatestProduct.setAdapter(adapter);
    }


    private void setupMostVisitedProductAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(getContext(), mViewModel, 1, products);
        mBinding.recyclerViewMostVisitedProduct.setAdapter(adapter);
    }


    private void setupSpecialProductAdapter(List<Product> products) {
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), mViewModel.getUrls(products));
        mBinding.imgProductSlider.setSliderAdapter(sliderAdapter);
    }
}