package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLogOutBinding;
import com.example.onlineshop.utilities.Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LogOutFragment extends BottomSheetDialogFragment {
    private FragmentLogOutBinding mBinding;


    public static LogOutFragment newInstance() {
        LogOutFragment fragment = new LogOutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

        mBinding.btnCommon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.setIsLogin(getContext(), false);
                Preferences.setEmail(getContext(), null);
                dismiss();
            }
        });

        return mBinding.getRoot();
    }
}