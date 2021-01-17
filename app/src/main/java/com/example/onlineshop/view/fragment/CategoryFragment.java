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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCategoryBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.paging.CategoryAdapter;
import com.example.onlineshop.paging.CategoryViewModel;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding mBinding;
    private CategoryViewModel mViewModel;

    public static CategoryFragment newInstance() {
        Bundle args = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_category,
                container,
                false);

        initRecyclerView();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<Category>>() {
            @Override
            public void onChanged(PagedList<Category> categories) {
                categoryAdapter.submitList(categories);
            }
        });

        mBinding.recyclerViewCategory.setAdapter(categoryAdapter);

        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}