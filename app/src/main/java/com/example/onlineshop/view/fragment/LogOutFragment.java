package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLogOutBinding;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.LogOutViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LogOutFragment extends BottomSheetDialogFragment {
    private FragmentLogOutBinding mBinding;
    private LogOutViewModel mViewModel;


    public static LogOutFragment newInstance() {
        LogOutFragment fragment = new LogOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(LogOutViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_log_out,
                container,
                false);

        mBinding.setLogOutViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setObserver();
    }


    private void setObserver() {
        mViewModel.getCommonClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isCommonClicked) {
                dismiss();
            }
        });


        mViewModel.getLogOutClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogOutClicked) {
                Preferences.setIsLogin(getContext(), false);
                Preferences.setEmail(getContext(), null);
                dismiss();
            }
        });
    }
}