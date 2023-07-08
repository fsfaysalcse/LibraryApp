package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.ui.utli.Utility;
import me.fsfaysalcse.ezylib.databinding.FragmentUserRegistrationBinding;
import me.fsfaysalcse.ezylib.ui.model.Admin;
import me.fsfaysalcse.ezylib.ui.model.Student;


public class UserRegistrationFragment extends Fragment {

    private FragmentUserRegistrationBinding binding;
    private NavController navController;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private ProgressDialog progressDialog;

    private String userType;

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

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please wait...");

        navController = ((MainActivity) requireActivity()).getNav();
    }

    private void setupView() {
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());


        binding.spinnerUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 1) {
                    binding.etStudentId.setVisibility(View.VISIBLE);
                } else {
                    binding.etStudentId.setVisibility(View.GONE);
                }

                userType = parentView.getItemAtPosition(position).toString();
                binding.toolbar.titleTextView.setText(userType + " Registration");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });


        binding.btnRegister.setOnClickListener(v -> {
            String fullName = binding.etFullName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String studentId = binding.etStudentId.getText().toString().trim();

            if (validateInput(fullName, email, phone, password)) {
                progressDialog = ProgressDialog.show(requireContext(), "Registering", "Please wait...", true);
                if (userType.equals("User")) {
                    signupStudent(studentId, fullName, email, phone, password);
                } else {
                    signupAdmin(fullName, email, phone, password);
                }
                Utility.hideKeyboard(requireContext(), binding.getRoot());
            }
        });

    }

    private boolean validateInput(String fullName, String email, String phone, String password) {
        if (fullName.isEmpty()) {
            binding.etFullName.setError("Enter full name");
            return false;
        }
        if (email.isEmpty()) {
            binding.etEmail.setError("Enter email");
            return false;
        }
        if (phone.isEmpty()) {
            binding.etPhone.setError("Enter phone");
            return false;
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("Enter password");
            return false;
        }
        return true;
    }

    private void signupAdmin(String fullName, String email, String phone, String password) {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String adminId = firebaseAuth.getCurrentUser().getUid();
                        Admin admin = new Admin(adminId, fullName, email, phone);
                        firestore.collection("admins")
                                .document(adminId)
                                .set(admin)
                                .addOnCompleteListener(docTask -> {
                                    if (docTask.isSuccessful()) {
                                        navController.navigateUp();
                                        Toast.makeText(requireActivity(), "Admin Registration Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireActivity(), "Failed Something Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(requireActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                });

                    } else {
                        progressDialog.dismiss(); // Dismiss the progress dialog here
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(requireActivity(), "Email is already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "Failed to create admin account", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss(); // Dismiss the progress dialog here
                });


    }

    private void signupStudent(String studentId, String fullName, String email, String phone, String password) {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String studentUserId = firebaseAuth.getCurrentUser().getUid();
                        Student student = new Student(studentUserId, studentId, fullName, email, phone);
                        firestore.collection("students")
                                .document(studentUserId)
                                .set(student)
                                .addOnCompleteListener(docTask -> {
                                    progressDialog.dismiss();

                                    if (docTask.isSuccessful()) {
                                        navController.navigateUp();
                                        Toast.makeText(requireActivity(), "Student Registration Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireActivity(), "Failed Something Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(requireActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                    } else {
                        progressDialog.dismiss(); // Dismiss the progress dialog here
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(requireActivity(), "Email is already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "Failed to create admin account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
