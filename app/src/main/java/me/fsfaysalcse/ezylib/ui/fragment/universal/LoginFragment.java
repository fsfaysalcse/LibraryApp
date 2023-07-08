package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {


    private FragmentLoginBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();
        String userType = binding.spinnerUserType.getSelectedItem().toString();

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_adminDashboardFragment);
            }
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_userRegistrationFragment);
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