package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSortDialogBinding;
import com.example.onlineshop.viewmodel.SearchableViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortDialogFragment extends BottomSheetDialogFragment {
    private FragmentSortDialogBinding mBinding;
    private SearchableViewModel mViewModel;

    public static SortDialogFragment newInstance() {
        SortDialogFragment fragment = new SortDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(SearchableViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sort_dialog,
                container,
                false);

        mBinding.setSearchableViewModel(mViewModel);
        setObserver();

        return mBinding.getRoot();
    }

    private void setObserver() {
        mViewModel.getSortNewMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isNewSortClicked) {
                dismiss();
            }
        });

        mViewModel.getSortBestSellerMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isBestSellerClicked) {
                dismiss();
            }
        });
    }
}