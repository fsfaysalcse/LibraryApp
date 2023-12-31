package me.arvin.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import me.arvin.ezylib.databinding.FragmentAdminHomeBinding;
import me.arvin.ezylib.ui.adapter.ViewPagerAdapter;
import me.ibrahimsn.lib.OnItemSelectedListener;


public class AdminHomeFragment extends Fragment {
    private FragmentAdminHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
    }

    private void setupView() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                binding.viewPager.setCurrentItem(i, true);
                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}