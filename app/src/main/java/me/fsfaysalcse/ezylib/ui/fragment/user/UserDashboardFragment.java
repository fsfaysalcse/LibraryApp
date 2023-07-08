package me.fsfaysalcse.ezylib.ui.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.databinding.FragmentAdminDashboardBinding;
import me.fsfaysalcse.ezylib.databinding.FragmentUserDashboardBinding;
import me.fsfaysalcse.ezylib.ui.utli.SharedPreferenceManager;

public class UserDashboardFragment extends Fragment {

    private FragmentUserDashboardBinding binding;
    private NavController navController;

    private SharedPreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        preferenceManager = new SharedPreferenceManager(requireContext());
        navController = ((MainActivity) requireActivity()).getNav();
    }

    private void setupView() {

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

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setLoggedIn(false);
                preferenceManager.setUserType("");
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.action_userDashboardFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}