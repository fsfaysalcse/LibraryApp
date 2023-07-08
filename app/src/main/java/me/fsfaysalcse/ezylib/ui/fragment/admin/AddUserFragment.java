package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentAddUserBinding;


public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();

        binding.toolbar.titleTextView.setText("Add User");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());
    }

    private void setupView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}