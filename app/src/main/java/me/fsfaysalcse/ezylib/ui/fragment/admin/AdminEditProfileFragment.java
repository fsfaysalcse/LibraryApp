package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentAdminEditProfileBinding;


public class AdminEditProfileFragment extends Fragment {

    private FragmentAdminEditProfileBinding binding;
    private NavController navController;

    private ProgressDialog progressDialog;

    private FirebaseFirestore firestore;


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
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

    }

    private void setupView() {
        binding.toolbar.titleTextView.setText("Edit Profile");
        binding.toolbar.backButton.setOnClickListener(v -> navController.popBackStack());
        getAdminData();

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAdminData();
            }
        });
    }

    private void updateAdminData() {
        String fullName = binding.etFullName.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            binding.etFullName.setError("Enter your full name");
            binding.etFullName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            binding.etPhone.setError("Enter your phone number");
            binding.etPhone.requestFocus();
            return;
        }

        progressDialog.show();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firestore.collection("admins").document(userId)
                .update("fullName", fullName, "phone", phone)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    navController.popBackStack();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void getAdminData() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        progressDialog.show();
        firestore.collection("admins").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    progressDialog.dismiss();
                    if (documentSnapshot.exists()) {
                        String fullName = documentSnapshot.getString("fullName");
                        String phone = documentSnapshot.getString("phone");

                        binding.etFullName.setText(fullName);
                        binding.etPhone.setText(phone);
                    } else {
                        Toast.makeText(requireContext(), "User not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}