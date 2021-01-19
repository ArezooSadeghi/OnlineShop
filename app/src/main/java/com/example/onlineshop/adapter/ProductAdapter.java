package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.CartProductAdapterItemBinding;
import com.example.onlineshop.databinding.HomeProductAdapterItemBinding;
import com.example.onlineshop.databinding.ProductOfEachCategoryAdapterItemBinding;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.viewmodel.SingleHomeViewModel;
import com.example.onlineshop.viewmodel.SingleProductOfEachCategoryViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private SingleHomeViewModel mHomeViewModel;
    private SingleProductOfEachCategoryViewModel mProductOfEachCategoryViewModel;
    private int mViewType;
    private List<Product> mProducts;

    public ProductAdapter(Context context, SingleHomeViewModel homeViewModel, int viewType, List<Product> products) {
        mContext = context;
        mHomeViewModel = homeViewModel;
        mViewType = viewType;
        mProducts = products;
    }

    public ProductAdapter(Context context, SingleProductOfEachCategoryViewModel singleProductOfEachCategoryViewModel, int viewType, List<Product> products) {
        mContext = context;
        mProductOfEachCategoryViewModel = singleProductOfEachCategoryViewModel;
        mViewType = viewType;
        mProducts = products;
    }

    public ProductAdapter(Context context, int viewType, List<Product> products) {
        mContext = context;
        mViewType = viewType;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 3) {
            return new ProductOfEachCategoryHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.product_of_each_category_adapter_item,
                    parent,
                    false));

        } else if (viewType == 2) {
            return new CartProductHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.cart_product_adapter_item,
                    parent,
                    false));
        } else {
            return new HomeProductHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(mContext),
                    R.layout.home_product_adapter_item,
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = mProducts.get(position);
        if (holder instanceof HomeProductHolder) {
            ((HomeProductHolder) holder).bindProduct(product);
            ((HomeProductHolder) holder).mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHomeViewModel.getItemClickedSingleLiveEvent().setValue(true);
                    mHomeViewModel.getProductIdLiveData().setValue(product.getId());
                }
            });
        }

        if (holder instanceof CartProductHolder) {
            ((CartProductHolder) holder).bindProduct(product);
        }

        if (holder instanceof ProductOfEachCategoryHolder) {
            ((ProductOfEachCategoryHolder) holder).bindProduct(product);
            ((ProductOfEachCategoryHolder) holder).mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mProductOfEachCategoryViewModel.getItemClickedSingleLiveEvent().setValue(true);
                    mProductOfEachCategoryViewModel.getProductIdMutableLiveData().setValue(product.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViewType;
    }

    public class HomeProductHolder extends RecyclerView.ViewHolder {
        private HomeProductAdapterItemBinding mBinding;

        public HomeProductHolder(HomeProductAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }

    public class CartProductHolder extends RecyclerView.ViewHolder {
        private CartProductAdapterItemBinding mBinding;

        public CartProductHolder(CartProductAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }

    public class ProductOfEachCategoryHolder extends RecyclerView.ViewHolder {
        private ProductOfEachCategoryAdapterItemBinding mBinding;

        public ProductOfEachCategoryHolder(ProductOfEachCategoryAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindProduct(Product product) {
            mBinding.setProduct(product);
        }
    }
}
