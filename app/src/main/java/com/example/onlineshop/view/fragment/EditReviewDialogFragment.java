package com.example.onlineshop.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentEditReviewDialogBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.viewmodel.SingleSharedReviewViewModel;

public class EditReviewDialogFragment extends DialogFragment {
    private FragmentEditReviewDialogBinding mBinding;
    private Review mReview;
    private SingleSharedReviewViewModel mViewModel;

    private static final String ARGS_REVIEW = "review";

    public static EditReviewDialogFragment newInstance(Review review) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_REVIEW, review);
        EditReviewDialogFragment fragment = new EditReviewDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(SingleSharedReviewViewModel.class);
        mReview = (Review) getArguments().getSerializable(ARGS_REVIEW);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getContext()),
                R.layout.fragment_edit_review_dialog,
                null,
                false);

        mBinding.setReview(mReview);

        mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReview.setReviewerName(mBinding.txtFirstAndLastName.getText().toString());
                mReview.setReviewContent(mBinding.txtContent.getText().toString());
                mReview.setRating(Integer.valueOf(mBinding.txtRating.getText().toString()));
                mViewModel.getDialogReviewMutableLiveData().setValue(mReview);
                dismiss();
            }
        });

        return new AlertDialog.Builder(getContext()).setView(mBinding.getRoot()).create();
    }
}
