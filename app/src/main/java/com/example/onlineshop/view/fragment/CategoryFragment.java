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
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentCategoryBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.paging.CategoryListAdapter;
import com.example.onlineshop.paging.SingleCategoryViewModel;

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
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(getContext(), mViewModel);
        mViewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), new Observer<PagedList<Category>>() {
            @Override
            public void onChanged(PagedList<Category> categories) {
                categoryListAdapter.submitList(categories);
            }
        });

        mViewModel.getItemClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isClicked) {
                if (isClicked) {
                    mViewModel.getCategoryIdLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer id) {
                            CategoryFragmentDirections.ActionNavigationCategoryToProductOfEachCategoryFragment action =
                                    CategoryFragmentDirections.actionNavigationCategoryToProductOfEachCategoryFragment();
                            action.setId(id);
                            NavHostFragment.findNavController(CategoryFragment.this).navigate(action);
                        }
                    });
                }
            }
        });

        mBinding.recyclerViewCategory.setAdapter(categoryListAdapter);

        return mBinding.getRoot();
    }

    private void initRecyclerView() {
        mBinding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}