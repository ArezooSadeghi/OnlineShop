package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.NotificationSettingDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NotificationSettingDialogFragment extends BottomSheetDialogFragment {

    public static NotificationSettingDialogFragment newInstance() {
        NotificationSettingDialogFragment fragment = new NotificationSettingDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        NotificationSettingDialogBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.notification_setting_dialog,
                container,
                false);
        return binding.getRoot();
    }
}