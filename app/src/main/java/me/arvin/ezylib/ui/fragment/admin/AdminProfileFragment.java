package me.arvin.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import me.arvin.ezylib.databinding.FragmentAdminProfileBinding;

public class AdminProfileFragment extends Fragment {


    private FragmentAdminProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
    }

    private void setupView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}