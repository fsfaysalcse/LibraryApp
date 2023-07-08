package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentBorrowBinding;


public class BorrowFragment extends Fragment {


    private FragmentBorrowBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBorrowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();

        binding.toolbar.titleTextView.setText("Borrow");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());


    }

    private void setupView() {
        binding.btnGiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.borrowLayout.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}