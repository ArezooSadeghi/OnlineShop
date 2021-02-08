package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentLoginBinding;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding mBinding;
    private LoginViewModel mViewModel;


    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false);

        mBinding.setLoginViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void setObserver() {
        mViewModel.getSignUpClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSignUpClicked) {
                NavHostFragment
                        .findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });


        mViewModel.getLoginClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoginClicked) {
                if (mBinding.txtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), R.string.enter_email, Toast.LENGTH_LONG).show();
                } else {
                    boolean isValidCustomer = mViewModel.isValidCustomer(
                            mBinding.txtEmail.getText().toString(),
                            mViewModel.getCustomers());
                    if (isValidCustomer) {
                        Preferences.setIsLogin(getContext(), true);
                        Preferences.setEmail(getContext(), mBinding.txtEmail.getText().toString());
                        LoginFragmentDirections.ActionLoginFragmentToAddressFragment action =
                                LoginFragmentDirections.actionLoginFragmentToAddressFragment();
                        action.setEmail(mBinding.txtEmail.getText().toString());
                        NavHostFragment.findNavController(LoginFragment.this).navigate(action);

                    } else {
                        Toast.makeText(getContext(), R.string.no_exist_account, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}