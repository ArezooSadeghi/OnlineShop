package com.example.onlineshop.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CategoryAdapterItemBinding;
import com.example.onlineshop.model.Category;

public class CategoryListAdapter extends PagedListAdapter<Category, CategoryListAdapter.CategoryHolder> {
    private Context mContext;
    private SingleCategoryViewModel mViewModel;

    private static DiffUtil.ItemCallback<Category> sCategoryItemCallback =
            new DiffUtil.ItemCallback<Category>() {
                @Override
                public boolean areItemsTheSame(Category oldCategory, Category newCategory) {
                    return true;
                }

                @Override
                public boolean areContentsTheSame(Category oldCategory, Category newCategory) {
                    int result = oldCategory.compareTo(newCategory);
                    if (result == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            };

    public CategoryListAdapter(Context context, SingleCategoryViewModel viewModel) {
        super(sCategoryItemCallback);
        mContext = context;
        mViewModel = viewModel;
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
        Category category = getItem(position);
        holder.bindCategory(category);
        holder.mBinding.btnSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.getItemClickedSingleLiveEvent().setValue(true);
                mViewModel.getCategoryIdLiveData().setValue(category.getId());
            }
        });
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
