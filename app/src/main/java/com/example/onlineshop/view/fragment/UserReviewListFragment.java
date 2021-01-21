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
import com.example.onlineshop.adapter.ReviewAdapter;
import com.example.onlineshop.databinding.FragmentUserReviewListBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.viewmodel.SharedReviewViewModel;

import java.util.List;


public class UserReviewListFragment extends Fragment {
    private FragmentUserReviewListBinding mBinding;
    private SharedReviewViewModel mViewModel;
    private ReviewAdapter mAdapter;

    public static UserReviewListFragment newInstance() {
        UserReviewListFragment fragment = new UserReviewListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SharedReviewViewModel.class);
        mViewModel.getReviewListMutableLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupAdapter(reviews);
            }
        });
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

    private void initRecyclerView() {
        mBinding.recyclerViewUserListReview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupAdapter(List<Review> reviews) {
        if (mAdapter == null) {
            mAdapter = new ReviewAdapter(getContext(), reviews);
            mBinding.recyclerViewUserListReview.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}