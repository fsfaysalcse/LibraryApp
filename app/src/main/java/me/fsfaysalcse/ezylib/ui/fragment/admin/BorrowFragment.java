package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentBorrowBinding;
import me.fsfaysalcse.ezylib.ui.model.Book;
import me.fsfaysalcse.ezylib.ui.model.BorrowItem;
import me.fsfaysalcse.ezylib.ui.model.Student;
import me.fsfaysalcse.ezylib.ui.utli.DateUtils;


public class BorrowFragment extends Fragment {


    private FragmentBorrowBinding binding;
    private NavController navController;

    private BorrowFragmentArgs args;

    private FirebaseFirestore firestore;

    private ProgressDialog progressDialog;

    private Student student;

    private Book bookInfo;

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
        navController = ((MainActivity) getActivity()).getNav();
        args = BorrowFragmentArgs.fromBundle(getArguments());

        binding.toolbar.titleTextView.setText("Borrow Book");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());

        bookInfo = new Gson().fromJson(args.getParams(), Book.class);

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Searching Student");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        firestore = FirebaseFirestore.getInstance();
    }

    private void setupView() {
        binding.btnGiveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performStudentSearchById();
            }
        });

        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBorrowItem();
            }
        });
    }

    private void performStudentSearchById() {
        String studentId = binding.etStudentId.getText().toString().trim();
        if (studentId.isEmpty()) {
            binding.etStudentId.setError("Please enter student id");
            return;
        }

        progressDialog.show();
        firestore.collection("students")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot userDocument = querySnapshot.getDocuments().get(0);
                            // Extract the user data
                            String userId = userDocument.getId();
                            String fullName = userDocument.getString("fullName");
                            String phone = userDocument.getString("phone");
                            String email = userDocument.getString("email");
                            student = new Student(userId, studentId, fullName, email, phone);

                            if (fullName != null) {
                                binding.borrowLayout.setVisibility(View.VISIBLE);
                                binding.tvStudentName.setText("Student Name : " + student.getFullName());
                                binding.tvBorrowedDate.setText("Borrowed Date : " + DateUtils.getCurrentDate());
                                binding.tvReturnDate.setText("Return Date : " + DateUtils.getDateInFuture(7));
                                binding.tvCharge.setText("Charge : RM 5.00");
                            }

                        } else {
                            Toast.makeText(requireActivity(), "No user found with the provided student ID", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                });


    }

    private void saveBorrowItem() {

        String bookId = bookInfo.getId();
        String bookTitle = bookInfo.getBookTitle();
        String studentId = student.getStudentId();
        String borrowedDate = DateUtils.getCurrentDate();
        String returnDate = DateUtils.getDateInFuture(7);

        BorrowItem borrowItem = new BorrowItem(
                bookId,
                bookTitle,
                studentId,
                borrowedDate,
                returnDate,
                studentId
        );

        progressDialog.show();
        String borrowId = firestore.collection("borrows").document().getId();
        firestore.collection("borrows")
                .document(borrowId)
                .set(borrowItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        performBookUpdate();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to borrow book", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void performBookUpdate() {
        firestore.collection("books")
                .document(bookInfo.getId())
                .update("isBorrowed", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Borrowed Successfully.This book was given to " + student.getFullName(), Toast.LENGTH_SHORT).show();
                        navController.navigateUp();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to borrow book", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}