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
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.databinding.FragmentProductOfEachCategoryBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SingleProductOfEachCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductOfEachCategoryFragment extends Fragment {
    private FragmentProductOfEachCategoryBinding mBinding;
    private SingleProductOfEachCategoryViewModel mViewModel;
    private int mCurrentPage = 1;
    private int mId;
    private ProductAdapter mProductAdapter;
    private List<Product> mProducts = new ArrayList<>();


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

        mBinding.recyclerViewProductOfEachCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!mBinding.recyclerViewProductOfEachCategory.canScrollVertically(1)) {
                    if (mCurrentPage <= mViewModel.getTotalPageLiveData().getValue()) {
                        mViewModel.getProductByCategory(mId, mCurrentPage);
                        mCurrentPage++;
                    }
                }
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductOfEachCategoryFragmentArgs args = ProductOfEachCategoryFragmentArgs.fromBundle(getArguments());
        mId = args.getId();
        setupAdapter(mProducts);
        mViewModel.getProductByCategory(mId, mCurrentPage);
        mCurrentPage++;
    }

    private void setObserver() {
        mViewModel.getProductByCategoryLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                int positionStart = mProducts.size();
                mProducts.addAll(products);
                int itemCount = mProducts.size();
                mProductAdapter.notifyItemRangeInserted(positionStart, itemCount);
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
        mProductAdapter = new ProductAdapter(getContext(), mViewModel, 3, products);
        mBinding.recyclerViewProductOfEachCategory.setAdapter(mProductAdapter);
    }
}