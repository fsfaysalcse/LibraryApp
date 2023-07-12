package me.arvin.ezylib.ui.fragment.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.gson.Gson;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentPaymentBinding;
import me.arvin.ezylib.ui.model.BorrowItem;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;

    private NavController navController;

    private ProgressDialog dialog;

    private SharedPreferenceManager preferenceManager;


    private PaymentFragmentArgs args;
    BorrowItem borrowItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        navController = ((MainActivity) requireActivity()).getNav();
        args = PaymentFragmentArgs.fromBundle(getArguments());
        borrowItem = new Gson().fromJson(args.getParams(), BorrowItem.class);

        init();
        setupView();
        return binding.getRoot();
    }

    private void init() {
        preferenceManager = new SharedPreferenceManager(requireContext());
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        binding.toolbar.titleTextView.setText("Payment And Invoice");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());


        binding.spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    binding.loginLayout.setVisibility(View.GONE);
                    binding.cashLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.loginLayout.setVisibility(View.VISIBLE);
                    binding.cashLayout.setVisibility(View.GONE);

                    String title = binding.spinnerPaymentMethod.getSelectedItem().toString();
                    binding.payingBy.setText(title);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setupView() {
        binding.payBtn.setOnClickListener(v -> {
            performPayment();
        });

        binding.payCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(PaymentFragmentDirections.actionPaymentFragmentToInvoiceFragment(args.getParams()));

            }
        });

    }

    private void performPayment() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            binding.etEmail.setError("Phone number is required");
            binding.etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError("Pin is required");
            binding.etPassword.requestFocus();
            return;
        }
        navController.navigate(PaymentFragmentDirections.actionPaymentFragmentToInvoiceFragment(args.getParams()));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }
}