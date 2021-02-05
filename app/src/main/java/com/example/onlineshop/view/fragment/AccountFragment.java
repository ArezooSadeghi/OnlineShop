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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.databinding.NoAccountBinding;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.SingleAccountViewModel;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding mBinding;
    private NoAccountBinding mNoAccountBinding;
    private SingleAccountViewModel mViewModel;


    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SingleAccountViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (!Preferences.getIsLogin(getContext())) {
            mNoAccountBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.no_account,
                    container,
                    false);
            return mNoAccountBinding.getRoot();
        } else {
            mBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_account,
                    container,
                    false);

            mBinding.setSingleAccountViewModel(mViewModel);
            mBinding.setEmail(Preferences.getEmail(getContext()));

            return mBinding.getRoot();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void setObserver() {
        mViewModel.getReviewClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isReviewClicked) {
                NavHostFragment
                        .findNavController(AccountFragment.this)
                        .navigate(R.id.action_navigation_account_to_userReviewListFragment);
            }
        });


        mViewModel.getSettingClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSettingClicked) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.settingFragment);
            }
        });
    }
}
