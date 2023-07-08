package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentAdminEditProfileBinding;


public class AdminEditProfileFragment extends Fragment {

    private FragmentAdminEditProfileBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();
    }

    private void setupView() {
        binding.toolbar.titleTextView.setText("Edit Profile");
        binding.toolbar.backButton.setOnClickListener(v -> navController.popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}