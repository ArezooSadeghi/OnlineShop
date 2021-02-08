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

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSignupBinding;
import com.example.onlineshop.model.Customer;
import com.example.onlineshop.viewmodel.SignupViewModel;

public class SignupFragment extends Fragment {
    private FragmentSignupBinding mBinding;
    private SignupViewModel mViewModel;


    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_signup,
                container,
                false);

        mBinding.setSignupViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    private void setObserver() {
        mViewModel.getStatusCodePostCustomerLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer statusCode) {
                showSignUpStatus(statusCode);
            }
        });


        mViewModel.getRegisterClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isRegisterClicked) {
                Toast.makeText(getContext(), R.string.enter_email, Toast.LENGTH_LONG).show();
                if (mBinding.txtEmail.getText().toString().isEmpty()) {
                } else {
                    mViewModel.postCustomer(mBinding.txtEmail.getText().toString());
                }
            }
        });
    }


    private void showSignUpStatus(Integer statusCode) {
        if (statusCode == 400) {
            Toast.makeText(getContext(), R.string.account_exist, Toast.LENGTH_LONG).show();
        }
        if (statusCode == 201) {
            mViewModel.insert(new Customer(mBinding.txtEmail.getText().toString()));
            Toast.makeText(getContext(), R.string.successful_register, Toast.LENGTH_LONG).show();
        }
    }
}