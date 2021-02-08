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
    private Review mReview;

    private static final String TAG = EditReviewDialogFragment.class.getSimpleName();

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObserver();
    }

    private void setObserver() {
        mViewModel.getReviewListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupAdapter(reviews);
            }
        });

        mViewModel.getDeleteReviewClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                mViewModel.deleteReview(review.getId());
                mViewModel.getReviews().remove(review);
                mViewModel.getReviewListMutableLiveData().setValue(mViewModel.getReviews());
            }
        });

        mViewModel.getEditReviewClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                mReview = review;
                EditReviewDialogFragment fragment = EditReviewDialogFragment.newInstance(review);
                fragment.show(getParentFragmentManager(), TAG);
            }
        });


        mViewModel.getDialogReviewMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                mViewModel.updateReview(review.getId(), review.getReviewContent(), review.getReviewerName(), review.getRating());
            }
        });

        mViewModel.getUpdateReviewLiveData().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                if (mReview != null) {
                    mViewModel.getReviews().remove(mReview);
                }
                if (mViewModel.isValidReview(review, mViewModel.getReviews())) {
                    mViewModel.getReviews().add(review);
                    mViewModel.getReviewListMutableLiveData().setValue(mViewModel.getReviews());
                }
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