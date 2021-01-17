package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.ProductAdapterItemBinding;
import com.example.onlineshop.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context mContext;
    private List<Product> mProducts;

    public ProductAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.product_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.bindProduct(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private ProductAdapterItemBinding mBinding;

        public ProductHolder(ProductAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }
}
