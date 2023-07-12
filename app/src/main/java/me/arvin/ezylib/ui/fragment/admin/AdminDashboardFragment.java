package me.arvin.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentAdminDashboardBinding;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;


public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;
    private NavController navController;

    private SharedPreferenceManager preferenceManager;

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
        preferenceManager = new SharedPreferenceManager(requireContext());
        navController = ((MainActivity) requireActivity()).getNav();

        binding.btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_addUserFragment);
            }
        });

        binding.btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_addBookFragment);
            }
        });

        binding.btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_searchBookFragment);
            }
        });

        binding.btnBorrowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_borrowedListFragment);
            }
        });

        binding.tvFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_faqFragment);
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