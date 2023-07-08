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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentAddUserBinding;
import me.fsfaysalcse.ezylib.ui.model.Student;


public class AddUserFragment extends Fragment {

    private FragmentAddUserBinding binding;
    private NavController navController;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;

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

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please wait...");

    }

    private void setupView() {
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {
        String fullName = binding.etFullName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String phone = binding.etPhone.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String studentId = binding.etStudentId.getText().toString().trim();

        if (studentId.isEmpty()) {
            binding.etStudentId.setError("Enter Student Id");
            return;
        }

        if (fullName.isEmpty()) {
            binding.etFullName.setError("Enter full name");
            return;
        }
        if (email.isEmpty()) {
            binding.etEmail.setError("Enter email");
            return;
        }
        if (phone.isEmpty()) {
            binding.etPhone.setError("Enter phone");
            return;
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("Enter password");
            return;
        }

        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Student student = new Student(authResult.getUser().getUid(), studentId, fullName, email, phone);
                    firestore.collection("students")
                            .document(authResult.getUser().getUid())
                            .set(student)
                            .addOnSuccessListener(aVoid -> {
                                progressDialog.dismiss();
                                navController.navigateUp();
                                Toast.makeText(requireContext(), "User added successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                firebaseAuth.getCurrentUser().delete();
                                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(requireContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}