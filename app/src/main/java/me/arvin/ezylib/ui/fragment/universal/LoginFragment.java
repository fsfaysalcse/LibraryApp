package me.arvin.ezylib.ui.fragment.universal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentLoginBinding;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;
import me.arvin.ezylib.ui.utli.Utility;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private NavController navController;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    SharedPreferenceManager preferenceManager;

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
        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        preferenceManager = new SharedPreferenceManager(requireContext());

        if (preferenceManager.isLoggedIn()) {
            if (preferenceManager.getUserType().equals("admin")) {
                navController.navigate(R.id.action_loginFragment_to_adminHomeFragment);
            } else {
                navController.navigate(R.id.action_loginFragment_to_userHomeFragment);
            }
        }

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Please wait...");

    }

    private void setupView() {
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            loginWithEmailAndPassword(email, password);
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_userRegistrationFragment);
                Utility.hideKeyboard(requireContext(), binding.getRoot());
            }
        });

        binding.tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
                Utility.hideKeyboard(requireContext(), binding.getRoot());
            }
        });
    }

    private void loginWithEmailAndPassword(String email, String password) {
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        checkUserType(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkUserType(FirebaseUser user) {
        String userId = user.getUid();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference userRef = firestore.collection("admins").document(userId);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                navController.navigate(R.id.action_loginFragment_to_adminHomeFragment);
                preferenceManager.setUserType("admin");
                preferenceManager.setLoggedIn(true);
            } else {
                navController.navigate(R.id.action_loginFragment_to_userHomeFragment);
                preferenceManager.setUserType("user");
                preferenceManager.setLoggedIn(true);
            }
            progressDialog.dismiss();
        }).addOnFailureListener(e -> {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
