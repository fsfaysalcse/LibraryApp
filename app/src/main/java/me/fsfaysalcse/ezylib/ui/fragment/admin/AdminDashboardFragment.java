package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.databinding.FragmentAdminDashboardBinding;


public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();

        binding.btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_addUserFragment);
            }
        });

        binding.btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_addBookFragment);
            }
        });

        binding.btnBookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_bookListFragment);
            }
        });

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_adminEditProfileFragment);
            }
        });

        binding.btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_searchBookFragment);
            }
        });

        binding.btnBorrowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminDashboardFragment_to_borrowedListFragment);
            }
        });
    }

    private void setupView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}