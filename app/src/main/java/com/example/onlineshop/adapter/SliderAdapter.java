package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.SliderAdapterItemBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {
    private Context mContext;
    private List<String> mUrls;

    public SliderAdapter(Context context, List<String> urls) {
        mContext = context;
        mUrls = urls;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public void setUrls(List<String> urls) {
        mUrls = urls;
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        return new SliderHolder(DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.slider_adapter_item,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        viewHolder.bindUrl(mUrls.get(position));
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    public class SliderHolder extends SliderViewAdapter.ViewHolder {
        private SliderAdapterItemBinding mBinding;

        public SliderHolder(SliderAdapterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bindUrl(String url) {
            mBinding.setUrl(url);
        }
    }
}
