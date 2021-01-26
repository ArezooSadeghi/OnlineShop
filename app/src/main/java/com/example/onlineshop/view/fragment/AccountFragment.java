package com.example.onlineshop.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentAccountBinding;
import com.example.onlineshop.databinding.NoAccountBinding;
import com.example.onlineshop.utilities.Preferences;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding mBinding;
    private NoAccountBinding mNoAccountBinding;


    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (!Preferences.getIsLogin(getContext())) {
            mNoAccountBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.no_account,
                    container,
                    false);
            return mNoAccountBinding.getRoot();
        } else {
            mBinding = DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_account,
                    container,
                    false);

            initToolbar();
            setListener();

            return mBinding.getRoot();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.account_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.settingFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mBinding.accountToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
    }


    private void setListener() {
        mBinding.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(AccountFragment.this)
                        .navigate(R.id.action_navigation_account_to_userReviewListFragment);
            }
        });
    }
}
