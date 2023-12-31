package me.arvin.ezylib.ui.fragment.admin;

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

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentAdminProfileBinding;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;

public class AdminProfileFragment extends Fragment {


    private FragmentAdminProfileBinding binding;


    private SharedPreferenceManager preferenceManager;

    private NavController navController;


    private FirebaseFirestore firestore;


    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) requireActivity()).getNav();
        preferenceManager = new SharedPreferenceManager(requireContext());
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
    }

    private void setupView() {

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setLoggedIn(false);
                preferenceManager.setUserType("");
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.action_adminHomeFragment_to_loginFragment);
            }
        });

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_adminHomeFragment_to_adminEditProfileFragment);
            }
        });

        getAdminData();

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
                        String email = documentSnapshot.getString("email");

                        binding.fullNameValueTextView.setText(fullName);
                        binding.phoneValueTextView.setText(phone);
                        binding.emailValueTextView.setText(email);

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