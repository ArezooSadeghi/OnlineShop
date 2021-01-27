package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ProductOfEachCategoryAdapterItemBinding;
import com.example.onlineshop.model.Product;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProducts;

    public SearchAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int i) {
        return mProducts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SearchHolder searchHolder;
        if (view == null) {
            ProductOfEachCategoryAdapterItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.product_of_each_category_adapter_item,
                    viewGroup,
                    false);

            searchHolder = new SearchHolder(binding);
            searchHolder.mView = binding.getRoot();
            searchHolder.mView.setTag(searchHolder);
        } else {
            searchHolder = (SearchHolder) view.getTag();
        }

        searchHolder.bindProduct((Product) getItem(i));
        return searchHolder.mView;
    }

    public class SearchHolder {
        private ProductOfEachCategoryAdapterItemBinding mBinding;
        private View mView;

        public SearchHolder(ProductOfEachCategoryAdapterItemBinding binding) {
            mBinding = binding;
            mView = binding.getRoot();
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }
}
