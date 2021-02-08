package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.AddressAdapter;
import com.example.onlineshop.databinding.FragmentAddressBinding;
import com.example.onlineshop.eventbus.event.PostOrder;
import com.example.onlineshop.model.Customer;
import com.example.onlineshop.viewmodel.LocatrViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class AddressFragment extends Fragment {
    private FragmentAddressBinding mBinding;
    private LocatrViewModel mViewModel;
    private Customer mCustomer;
    private boolean mFlag;


    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mViewModel = new ViewModelProvider(requireActivity()).get(LocatrViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_address,
                container,
                false);

        mBinding.setLocatrViewModel(mViewModel);
        initToolbar();
        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddressFragmentArgs args = AddressFragmentArgs.fromBundle(getArguments());
        String email = args.getEmail();
        mCustomer = mViewModel.getCustomer(email);
        setupAdapter(mViewModel.getAddresses(mCustomer.getAddress()));
        setObserver();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.address_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_post_order:
                if (!mFlag) {
                    mFlag = true;
                    mViewModel.postOrder(mCustomer.getEmail());
                    EventBus.getDefault().postSticky(new PostOrder());
                } else {
                    Toast.makeText(getContext(), R.string.empty_cart, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.bottomAppBar);
    }


    private void initRecyclerView() {
        mBinding.recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setupAdapter(List<String> addresses) {
        AddressAdapter adapter = new AddressAdapter(getContext(), addresses);
        mBinding.recyclerViewAddress.setAdapter(adapter);
    }


    private void setObserver() {
        mViewModel.getStatusCodePostOrderLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer statusCode) {
                if (mFlag) {
                    showReult(statusCode);
                }
            }
        });


        mViewModel.getFinalAddressMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String address) {
                mCustomer.getAddress().add(address);
                mViewModel.updateCustomer(mCustomer);
                setupAdapter(mViewModel.getAddresses(mCustomer.getAddress()));
            }
        });


        mViewModel.getAddLocationClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAddLocationClicked) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.locatrFragment);
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
}