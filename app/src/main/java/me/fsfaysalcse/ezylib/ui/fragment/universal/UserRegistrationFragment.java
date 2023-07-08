package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentUserRegistrationBinding;


public class UserRegistrationFragment extends Fragment {

    private FragmentUserRegistrationBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserRegistrationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();
        String userType = binding.spinnerUserType.getSelectedItem().toString();

        binding.toolbar.titleTextView.setText("User Registration");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());

        binding.btnRegister.setOnClickListener(v -> navController.navigateUp());

        if (userType.equals("User")) {
            binding.etStudentId.setVisibility(View.VISIBLE);
        } else {
            binding.etStudentId.setVisibility(View.GONE);
        }


    }

    private void setupView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}