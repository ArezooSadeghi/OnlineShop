package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CategoryAdapterItemBinding;
import com.example.onlineshop.model.Category;
import com.example.onlineshop.viewmodel.SingleCategoryViewModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private Context mContext;
    private SingleCategoryViewModel mViewModel;
    private List<Category> mCategories;

    public CategoryAdapter(Context context, SingleCategoryViewModel viewModel, List<Category> categories) {
        mContext = context;
        mViewModel = viewModel;
        mCategories = categories;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.category_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.bindCategory(mCategories.get(position));
        holder.mBinding.btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getCategoryIdSingleLiveEvent().setValue(mCategories.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private CategoryAdapterItemBinding mBinding;

        public CategoryHolder(CategoryAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindCategory(Category category) {
            mBinding.setCategory(category);
        }
    }
}
