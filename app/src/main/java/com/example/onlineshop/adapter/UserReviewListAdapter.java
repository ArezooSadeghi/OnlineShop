package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.UserReviewListAdapterItemBinding;
import com.example.onlineshop.model.Review;
import com.example.onlineshop.viewmodel.SingleSharedReviewViewModel;

import java.util.List;

public class UserReviewListAdapter extends RecyclerView.Adapter<UserReviewListAdapter.UserReviewListHolder> {
    private Context mContext;
    private SingleSharedReviewViewModel mViewModel;
    private List<Review> mReviews;

    public UserReviewListAdapter(Context context, SingleSharedReviewViewModel viewModel, List<Review> reviews) {
        mContext = context;
        mViewModel = viewModel;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public UserReviewListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserReviewListHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.user_review_list_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserReviewListHolder holder, int position) {
        holder.bindReview(mReviews.get(position));
        holder.mBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getDeleteReviewClickedSingleLiveEvent().setValue(mReviews.get(position));
            }
        });

        holder.mBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getEditReviewClickedSingleLiveEvent().setValue(mReviews.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class UserReviewListHolder extends RecyclerView.ViewHolder {
        private UserReviewListAdapterItemBinding mBinding;

        public UserReviewListHolder(UserReviewListAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindReview(Review review) {
            mBinding.setReview(review);
        }
    }
}
