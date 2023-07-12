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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentForgotPasswordBinding;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private NavController navController;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    SharedPreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        firebaseAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);


        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Please wait...");

    }

    private void setupView() {


        binding.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    binding.etEmail.setError("Please enter email");
                    return;
                }

                performForgotPassword(email);
            }
        });
    }

    private void performForgotPassword(String email) {
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                        navController.navigateUp();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if (e instanceof FirebaseAuthInvalidUserException) {
                            Toast.makeText(requireContext(), "User account not found", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}