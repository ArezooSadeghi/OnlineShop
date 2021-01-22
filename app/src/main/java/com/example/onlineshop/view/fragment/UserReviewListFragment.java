package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.UserReviewListAdapter;
import com.example.onlineshop.databinding.FragmentUserReviewListBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.viewmodel.SingleSharedReviewViewModel;

import java.util.List;


public class UserReviewListFragment extends Fragment {
    private FragmentUserReviewListBinding mBinding;
    private SingleSharedReviewViewModel mViewModel;
    private UserReviewListAdapter mAdapter;

    public static UserReviewListFragment newInstance() {
        UserReviewListFragment fragment = new UserReviewListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedReviewViewModel.class);
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_user_review_list,
                container,
                false);

        initRecyclerView();

        return mBinding.getRoot();
    }

    private void setObserver() {
        mViewModel.getReviewListMutableLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupAdapter(reviews);
            }
        });

        mViewModel.getDeleteClickedSingleLiveEvent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDeleteClicked) {

            }
        });

        mViewModel.getReviewMutableLiveData().observe(this, new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                mViewModel.deleteReview(review.getId());
                mViewModel.getReviews().remove(review);
                mViewModel.getReviewListMutableLiveData().setValue(mViewModel.getReviews());
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerViewUserListReview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Review> reviews) {
        if (mAdapter == null) {
            mAdapter = new UserReviewListAdapter(getContext(), mViewModel, reviews);
            mBinding.recyclerViewUserListReview.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}