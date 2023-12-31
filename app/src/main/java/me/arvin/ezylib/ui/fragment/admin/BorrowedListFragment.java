package me.arvin.ezylib.ui.fragment.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.databinding.FragmentBorrowedBookBinding;
import me.arvin.ezylib.ui.adapter.BorrowedBookAdapter;
import me.arvin.ezylib.ui.model.BorrowItem;

public class BorrowedListFragment extends Fragment implements BorrowedBookAdapter.OnItemClickListener {

    private FragmentBorrowedBookBinding binding;
    private BorrowedBookAdapter borrowedBookAdapter;

    private NavController navController;

    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBorrowedBookBinding.inflate(inflater, container, false);
        navController = ((MainActivity) requireActivity()).getNav();
        init();
        setupView();
        return binding.getRoot();
    }

    private void init() {
        dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        binding.toolbarBorrow.titleTextView.setText("Borrowed Books");
        binding.toolbarBorrow.backButton.setOnClickListener(v -> navController.navigateUp());

        borrowedBookAdapter = new BorrowedBookAdapter(getActivity(), this);
    }

    private void setupView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(borrowedBookAdapter);


        binding.reload.setOnClickListener(v -> getAllTheBorrowedBooks());
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllTheBorrowedBooks();
    }

    private void getAllTheBorrowedBooks() {

        dialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("borrows")
                .get()
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        List<BorrowItem> borrowedBooks = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String bookId = document.getString("bookId");
                            String bookTitle = document.getString("bookTitle");
                            String studentId = document.getString("studentId");
                            String borrowedDate = document.getString("borrowDate");
                            String returnDate = document.getString("returnDate");
                            String returnStatus = document.getString("returnStatus");
                            String borrowedId = document.getString("borrowedId");

                            BorrowItem borrowItem = new BorrowItem(bookId, bookTitle, borrowedDate, returnDate, studentId, returnStatus, borrowedId);
                            borrowedBooks.add(borrowItem);
                        }
                        if (borrowedBooks.size() > 0) {
                            binding.llHeader.setVisibility(View.VISIBLE);
                            borrowedBookAdapter.setBorrowItemList(borrowedBooks);
                        } else {
                            binding.llHeader.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "No borrowed books", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        binding.llHeader.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Failed to retrieve borrowed books", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        binding.llHeader.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Failed to retrieve borrowed books", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }

    @Override
    public void onItemClick(BorrowItem borrowItem) {

    }

    @Override
    public void onReturnClick(BorrowItem borrowItem) {

    }
}
