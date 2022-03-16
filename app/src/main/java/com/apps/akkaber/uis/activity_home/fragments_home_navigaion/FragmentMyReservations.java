package com.apps.akkaber.uis.activity_home.fragments_home_navigaion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.akkaber.R;
import com.apps.akkaber.uis.activity_base.BaseFragment;
import com.apps.akkaber.adapter.MyPagerAdapter;
import com.apps.akkaber.databinding.FragmentMyReservationsBinding;
import com.apps.akkaber.uis.activity_home.HomeActivity;
import com.apps.akkaber.uis.activity_login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentMyReservations extends BaseFragment {
    private FragmentMyReservationsBinding binding;
    private List<String> titles;
    private List<Fragment> fragments;
    private MyPagerAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private HomeActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                    titles.add(getString(R.string.current));
                    titles.add(getString(R.string.prev));
                    fragments.add(FragmentCurrentReservation.newInstance());
                    fragments.add(FragmentPreviousReservation.newInstance());

                    adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, titles);

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_reservations, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();

        if (getUserModel() != null) {
            titles.add(getString(R.string.current));
            titles.add(getString(R.string.prev));
            fragments.add(FragmentCurrentReservation.newInstance());
            fragments.add(FragmentPreviousReservation.newInstance());

            adapter = new MyPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, titles);
        } else {
            Intent intent = new Intent(activity, LoginActivity.class);
            launcher.launch(intent);

        }


    }
}