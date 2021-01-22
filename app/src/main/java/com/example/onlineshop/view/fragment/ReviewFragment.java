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
import com.example.onlineshop.databinding.FragmentReviewBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.utilities.Preferences;
import com.example.onlineshop.viewmodel.SharedReviewViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ReviewFragment extends Fragment {
    private FragmentReviewBinding mBinding;
    private SharedReviewViewModel mViewModel;
    private int mId;


    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(SharedReviewViewModel.class);
        setObserver();

        ReviewFragmentArgs args = ReviewFragmentArgs.fromBundle(getArguments());
        mId = args.getId();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_review,
                container,
                false);

        setListener();

        return mBinding.getRoot();
    }


    private void setObserver() {
        mViewModel.getReviewLiveData().observe(this, new Observer<Review>() {
            @Override
            public void onChanged(Review review) {
                showPostReviewResult(review);
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
            }
            Toast.makeText(getContext(), R.string.successful_post_review, Toast.LENGTH_LONG).show();
        }
    }

    private void setListener() {
        mBinding.btnRegisterReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.txtFirstAndLastName.getText().toString().isEmpty() ||
                        mBinding.txtYourReview.getText().toString().isEmpty() ||
                        mBinding.txtScore.getText().toString().isEmpty()) {
                    Snackbar.make(view, R.string.enter_request_information, Snackbar.LENGTH_LONG).show();
                } else {
                    String content = mBinding.txtYourReview.getText().toString();
                    String name = mBinding.txtFirstAndLastName.getText().toString();
                    String email = Preferences.getEmail(getContext());
                    int rating = Integer.valueOf(mBinding.txtScore.getText().toString());
                    mViewModel.postReview(mId, content, name, email, rating);
                }
            }
        });
    }
}