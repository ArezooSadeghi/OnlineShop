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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.AddressAdapter;
import com.example.onlineshop.databinding.FragmentAddressBinding;
import com.example.onlineshop.model.Customer;
import com.example.onlineshop.viewmodel.LocatrViewModel;

import java.util.List;

public class AddressFragment extends Fragment {
    private FragmentAddressBinding mBinding;
    private LocatrViewModel mViewModel;
    private Customer mCustomer;


    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(LocatrViewModel.class);
        setObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_address,
                container,
                false);

        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddressFragmentArgs args = AddressFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        setListener(email);
        mCustomer = mViewModel.getCustomer(email);
        setupAdapter(mCustomer.getAddress());
    }


    private void setObserver() {
        mViewModel.getStatusCodePostOrderLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer statusCode) {
                showReult(statusCode);
            }
        });

        mViewModel.getFinalAddressMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String address) {
                mCustomer.getAddress().add(address);
                mViewModel.updateCustomer(mCustomer);
                setupAdapter(mCustomer.getAddress());
            }
        });
    }


    private void showReult(Integer statusCode) {
        if (statusCode == 400) {
            Toast.makeText(getContext(), R.string.failed_final_registration_order, Toast.LENGTH_LONG).show();
        }
        if (statusCode == 201) {
            Toast.makeText(getContext(), R.string.successful_final_registration_order, Toast.LENGTH_LONG).show();
        }
    }

    private void setupAdapter(List<String> addresses) {
        AddressAdapter adapter = new AddressAdapter(getContext(), addresses);
        mBinding.recyclerViewAddress.setAdapter(adapter);
    }

    private void initRecyclerView() {
        mBinding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setListener(String email) {
        mBinding.btnFinalRegistrationOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.postOrder(email);
            }
        });

        mBinding.fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.locatrFragment);
            }
        });
    }
}