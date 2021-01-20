package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSignupBinding;
import com.example.onlineshop.model.Customer;
import com.example.onlineshop.viewmodel.SignupViewModel;
import com.google.android.material.snackbar.Snackbar;

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
        setObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_signup,
                container,
                false);

        setListener();

        return mBinding.getRoot();
    }


    private void setObserver() {
        mViewModel.getStatusCodePostCustomerLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer statusCode) {
                showSignupStatus(statusCode);
            }
        });
    }


    private void showSignupStatus(Integer statusCode) {
        if (statusCode == 400) {
            Toast.makeText(getContext(), R.string.account_exist, Toast.LENGTH_LONG).show();
        }
        if (statusCode == 201) {
            mViewModel.insert(new Customer(mBinding.txtEmail.getText().toString()));
            Toast.makeText(getContext(), R.string.successful_register, Toast.LENGTH_LONG).show();
        }
    }


    private void setListener() {
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.txtEmail.getText().toString().isEmpty()) {
                    Snackbar.make(view, R.string.enter_email, Snackbar.LENGTH_LONG).show();
                } else {
                    mViewModel.postCustomer(mBinding.txtEmail.getText().toString());
                }
            }
        });
    }
}