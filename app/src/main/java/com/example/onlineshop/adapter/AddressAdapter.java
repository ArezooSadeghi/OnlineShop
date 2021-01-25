package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.AddressAdapterItemBinding;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private Context mContext;
    private List<String> mAddresses;
    private CompoundButton mLastCheckedRB = null;

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
        holder.mBinding.radioBtnAddress.setTag(new Integer(position));
        CompoundButton.OnCheckedChangeListener ls = (new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int tag = (int) compoundButton.getTag();
                if (mLastCheckedRB == null) {
                    mLastCheckedRB = compoundButton;
                } else if (tag != (int) mLastCheckedRB.getTag()) {
                    mLastCheckedRB.setChecked(false);
                    mLastCheckedRB = compoundButton;
                }
            }
        });
        holder.mBinding.radioBtnAddress.setOnCheckedChangeListener(ls);
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
