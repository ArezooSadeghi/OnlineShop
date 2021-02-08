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
import com.example.onlineshop.databinding.FragmentReviewBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.SingleSharedReviewViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ReviewFragment extends Fragment {
    private FragmentReviewBinding mBinding;
    private SingleSharedReviewViewModel mViewModel;
    private int mProductId;


    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
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
                R.layout.fragment_review,
                container,
                false);

        mBinding.setSingleSharedReviewViewModel(mViewModel);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewFragmentArgs args = ReviewFragmentArgs.fromBundle(getArguments());
        mProductId = args.getId();
        setObserver();
    }


    private void setObserver() {
        mViewModel.getReviewLiveData().observe(getViewLifecycleOwner(), new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                showPostReviewResult(review);
            }
        });


        mViewModel.getSubmitClickedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSubmitClicked) {
                if (mBinding.txtFirstAndLastName.getText().toString().isEmpty() ||
                        mBinding.txtYourReview.getText().toString().isEmpty() ||
                        mBinding.txtScore.getText().toString().isEmpty()) {
                    Snackbar.make(getView(), R.string.enter_request_information, Snackbar.LENGTH_LONG).show();
                } else {
                    String content = mBinding.txtYourReview.getText().toString();
                    String name = mBinding.txtFirstAndLastName.getText().toString();
                    String email = Preferences.getEmail(getContext());
                    int rating = Integer.valueOf(mBinding.txtScore.getText().toString());
                    mViewModel.postReview(mProductId, content, name, email, rating);
                }
            }
        });
    }


    private void showPostReviewResult(Review review) {
        if (review == null) {
            Toast.makeText(getContext(), R.string.failed_post_review, Toast.LENGTH_LONG).show();
        } else {
            if (mViewModel.isValidReview(review, mViewModel.getReviews())) {
                List<Review> reviews = mViewModel.getReviews();
                reviews.add(review);
                mViewModel.getReviewListMutableLiveData().setValue(reviews);
                Toast.makeText(getContext(), R.string.successful_post_review, Toast.LENGTH_LONG).show();
            }
        }
    }
}