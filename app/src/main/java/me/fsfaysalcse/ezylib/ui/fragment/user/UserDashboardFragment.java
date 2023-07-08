package me.fsfaysalcse.ezylib.ui.fragment.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.databinding.FragmentUserDashboardBinding;
import me.fsfaysalcse.ezylib.ui.utli.SharedPreferenceManager;

public class UserDashboardFragment extends Fragment {

    private FragmentUserDashboardBinding binding;
    private NavController navController;

    private SharedPreferenceManager preferenceManager;

    private FirebaseFirestore firestore;

    private ProgressDialog progressDialog;

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
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");

    }

    private void setupView() {

        binding.btnSearchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_userDashboardFragment_to_searchBookFragment);
            }
        });

        binding.btnBorrowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_userDashboardFragment_to_studentBorrowedListFragment);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.setLoggedIn(false);
                preferenceManager.setUserType("");
                preferenceManager.setStudentId("");
                FirebaseAuth.getInstance().signOut();
                navController.navigate(R.id.action_userDashboardFragment_to_loginFragment);
            }
        });

        if (preferenceManager.getStudentId().equals("")) {
            getStudentInformation();
        }
    }

    private void getStudentInformation() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        progressDialog.show();
        firestore.collection("students").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    progressDialog.dismiss();
                    if (documentSnapshot.exists()) {
                        String studentId = documentSnapshot.getString("studentId");
                        preferenceManager.setStudentId(studentId);
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