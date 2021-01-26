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
import com.example.onlineshop.adapter.CategoryAdapter;
import com.example.onlineshop.databinding.FragmentCategoryBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.viewmodel.SingleCategoryViewModel;

import java.util.List;

public class CategoryFragment extends Fragment {
    private FragmentCategoryBinding mBinding;
    private SingleCategoryViewModel mViewModel;


    public static CategoryFragment newInstance() {
        Bundle args = new Bundle();
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SingleCategoryViewModel.class);
        mViewModel.getCategory(1);
        setObserver();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setObserver();
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

        return mBinding.getRoot();
    }


    private void setObserver() {
        mViewModel.getCategoryListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                setupAdapter(categories);
            }
        });

        mViewModel.getCategoryIdSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer categoryId) {
                CategoryFragmentDirections.ActionNavigationCategoryToProductOfEachCategoryFragment action =
                        CategoryFragmentDirections.actionNavigationCategoryToProductOfEachCategoryFragment();
                action.setId(categoryId);
                NavHostFragment.findNavController(CategoryFragment.this).navigate(action);
            }
        });
    }


    private void initRecyclerView() {
        mBinding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setupAdapter(List<Category> categories) {
        CategoryAdapter adapter = new CategoryAdapter(getContext(), mViewModel, categories);
        mBinding.recyclerViewCategory.setAdapter(adapter);
    }
}
