package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
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
        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(this).get(SingleHomeViewModel.class);
        mViewModel.getBestProduct("rating", "desc");
        mViewModel.getLatestProduct("date", "desc");
        mViewModel.getMostVisitedProduct("popularity", "desc");
        setObserver();
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

        initToolbar();
        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.homeToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }

    private void initRecyclerView() {
        mBinding.recyclerViewBestProduct.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        mBinding.recyclerViewLatestProduct.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
        mBinding.recyclerViewMostVisitedProduct.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true));
    }

    private void setObserver() {
        mViewModel.getBestProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mBinding.setBestProductTitle(getString(R.string.best_product_title));
                setupBestProductAdapter(products);
            }
        });

        mViewModel.getLatestProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                Preferences.setLastId(getContext(), products.get(0).getId());
                mBinding.setLatestProductTitle(getString(R.string.latest_product_title));
                setupLatestProductAdapter(products);
            }
        });

        mViewModel.getMostVisitedProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                mBinding.setMostVisitedProductTitle(getString(R.string.most_visited_product_title));
                setupMostVisitedProductAdapter(products);
            }
        });

        mViewModel.getItemClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isItemClicked) {
                if (isItemClicked) {
                    mViewModel.getProductIdLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer id) {
                            HomeFragmentDirections.ActionNavigationHomeToDetailFragment action =
                                    HomeFragmentDirections.actionNavigationHomeToDetailFragment();
                            action.setId(id);
                            NavHostFragment.findNavController(HomeFragment.this).navigate(action);
                        }
                    });
                }
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
}