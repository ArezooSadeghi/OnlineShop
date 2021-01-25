package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.AddressAdapterItemBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private Context mContext;
    private List<String> mAddresses;

    public AddressAdapter(Context context, List<String> addresses) {
        mContext = context;
        mAddresses = addresses;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.address_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        holder.bindAddress(mAddresses.get(position));
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {
        private AddressAdapterItemBinding mBinding;

        public AddressHolder(AddressAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindAddress(String address) {
            mBinding.setAddress(address);
        }
    }
}
