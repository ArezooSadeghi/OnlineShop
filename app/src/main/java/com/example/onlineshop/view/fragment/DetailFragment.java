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
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.ReviewAdapter;
import com.example.onlineshop.adapter.SliderAdapter;
import com.example.onlineshop.databinding.FragmentDetailBinding;
import com.example.onlineshop.eventbus.event.AddToCartEvent;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.service.PollService;
import com.example.onlineshop.viewmodel.SingleSharedDetailViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding mBinding;
    private SingleSharedDetailViewModel mViewModel;
    private ReviewAdapter mAdapter;
    private int mProductId;

    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedDetailViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detail,
                container,
                false);

        mBinding.setSingleSharedDetailViewModel(mViewModel);
        initRecyclerView();

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int id = getArguments().getInt(PollService.BUNDLE_LAST_ID);
        mViewModel.retrieveProduct(id);
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        mProductId = args.getId();
        mViewModel.retrieveProduct(mProductId);
        mViewModel.getReviews(mProductId);
        setObserver();
    }


    private void initRecyclerView() {
        mBinding.recyclerViewReview.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.HORIZONTAL,
                true));
    }


    private void setObserver() {
        mViewModel.getRetrieveProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    mBinding.setProduct(product);
                    initViews(product);
                }
            }
        });


        mViewModel.getReviewListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupAdapter(reviews);
            }
        });


        mViewModel.getAddReviewClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean addReviewClicked) {
                DetailFragmentDirections.ActionDetailFragmentToReviewFragment action =
                        DetailFragmentDirections.actionDetailFragmentToReviewFragment();
                action.setId(mProductId);
                NavHostFragment.findNavController(DetailFragment.this).navigate(action);
            }
        });


        mViewModel.getAddToCartClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean addToCartClicked) {
                EventBus.getDefault().post(new AddToCartEvent());
                mViewModel.getProducts().add(mBinding.getProduct());
                mViewModel.getProductListMutableLiveData().setValue(mViewModel.getProducts());
                mViewModel.getPrices().add(mBinding.getProduct().getPrice());
                mViewModel.getPriceListMutableLiveData().setValue(mViewModel.getPrices());
            }
        });
    }


    private void initViews(Product product) {
        SliderAdapter sliderAdapter = new SliderAdapter(getContext(), product.getImageUrl());
        mBinding.imgProductSlider.setSliderAdapter(sliderAdapter);
    }


    private void setupAdapter(List<Review> reviews) {
        if (mAdapter == null) {
            mAdapter = new ReviewAdapter(getContext(), reviews);
            mBinding.recyclerViewReview.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}