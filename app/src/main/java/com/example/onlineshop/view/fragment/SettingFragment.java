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

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSettingBinding;
import com.example.onlineshop.viewmodel.SettingViewModel;

public class SettingFragment extends Fragment {
    private FragmentSettingBinding mBinding;
    private SettingViewModel mViewModel;

    private static final String TAG = SettingFragment.class.getSimpleName();


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_setting,
                container,
                false);

        mBinding.setSettingViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void setObserver() {
        mViewModel.getSettingNotificationClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSettingNotificationClicked) {
                NotificationSettingDialogFragment fragment = NotificationSettingDialogFragment.newInstance();
                fragment.show(getParentFragmentManager(), TAG);
            }
        });


        mViewModel.getLogOutClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLogOutClicked) {
                LogOutFragment fragment = LogOutFragment.newInstance();
                fragment.show(getParentFragmentManager(), TAG);
            }
        });
    }
}