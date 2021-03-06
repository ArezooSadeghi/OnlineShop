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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ProductAdapter;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.eventbus.event.AddEvent;
import com.example.onlineshop.eventbus.event.DeleteEvent;
import com.example.onlineshop.eventbus.event.PostOrder;
import com.example.onlineshop.eventbus.event.RemoveEvent;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.SingleSharedDetailViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding mBinding;
    private SingleSharedDetailViewModel mViewModel;

    private static final String TAG = CartFragment.class.getSimpleName();


    public static CartFragment newInstance() {
        Bundle args = new Bundle();
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedDetailViewModel.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_cart,
                container,
                false);

        mBinding.setSingleSharedDetailViewModel(mViewModel);

        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(sticky = true)
    public void postOrderEvent(PostOrder postOrder) {
        mViewModel.getProducts().clear();
        mViewModel.getProductListMutableLiveData().setValue(mViewModel.getProducts());
        mViewModel.getPrices().clear();
        mViewModel.getPriceListMutableLiveData().setValue(mViewModel.getPrices());
        EventBus.getDefault().removeStickyEvent(postOrder);
    }


    private void initRecyclerView() {
        mBinding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void setObserver() {
        mViewModel.getItemClickedMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer productId) {
                CartFragmentDirections.ActionNavigationCartToDetailFragment action =
                        CartFragmentDirections.actionNavigationCartToDetailFragment();
                action.setId(productId);
                NavHostFragment.findNavController(CartFragment.this).navigate(action);
            }
        });


        mViewModel.getProductListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                setupAdapter(products);
            }
        });


        mViewModel.getPriceListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> prices) {
                Double totalPrice = mViewModel.calculateTotalPrice(prices);
                mViewModel.getTotalAmountPaidMutableLiveData().setValue(String.valueOf(totalPrice));
                mBinding.setTotalPrice(String.valueOf(totalPrice));
            }
        });


        mViewModel.getAddClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                EventBus.getDefault().post(new AddEvent());
                mViewModel.getPrices().add(product.getPrice());
                mViewModel.getPriceListMutableLiveData().setValue(mViewModel.getPrices());
            }
        });


        mViewModel.getDeleteClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                EventBus.getDefault().post(new DeleteEvent());
                mViewModel.getPrices().remove(product.getPrice());
                mViewModel.getPriceListMutableLiveData().setValue(mViewModel.getPrices());
                mViewModel.getProducts().remove(product);
                mViewModel.getProductListMutableLiveData().setValue(mViewModel.getProducts());
            }
        });


        mViewModel.getRemoveClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                EventBus.getDefault().post(new RemoveEvent());
                mViewModel.getPrices().remove(product.getPrice());
                mViewModel.getPriceListMutableLiveData().setValue(mViewModel.getPrices());
            }
        });


        mViewModel.getOkClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOkClicked) {
                if (Preferences.getIsLogin(getContext())) {
                    CartFragmentDirections.ActionNavigationCartToAddressFragment action =
                            CartFragmentDirections.actionNavigationCartToAddressFragment();
                    action.setEmail(Preferences.getEmail(getContext()));
                    NavHostFragment.findNavController(CartFragment.this).navigate(action);
                } else {
                    NavHostFragment.findNavController(CartFragment.this)
                            .navigate(R.id.action_navigation_cart_to_loginFragment);
                }
            }
        });


        mViewModel.getContinueClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isContinueClicked) {
                AlertDialogFragment fragment = AlertDialogFragment.newInstance(
                        mViewModel.getTotalAmountPaidMutableLiveData().getValue());
                fragment.show(getParentFragmentManager(), TAG);
            }
        });
    }


    private void setupAdapter(List<Product> products) {
        ProductAdapter productAdapter = new ProductAdapter(getContext(), mViewModel, 2, products);
        mBinding.recyclerViewCart.setAdapter(productAdapter);
    }
}
