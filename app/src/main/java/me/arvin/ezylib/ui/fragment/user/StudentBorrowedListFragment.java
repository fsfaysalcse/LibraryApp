package me.arvin.ezylib.ui.fragment.user;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.databinding.FragmentBorrowedBookBinding;
import me.arvin.ezylib.ui.adapter.BorrowedBookAdapter;
import me.arvin.ezylib.ui.model.BorrowItem;
import me.arvin.ezylib.ui.model.Returned;
import me.arvin.ezylib.ui.utli.DateUtils;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;

public class StudentBorrowedListFragment extends Fragment implements BorrowedBookAdapter.OnItemClickListener {

    private FragmentBorrowedBookBinding binding;
    private BorrowedBookAdapter borrowedBookAdapter;

    private NavController navController;

    private ProgressDialog dialog;

    private SharedPreferenceManager preferenceManager;

    private FirebaseFirestore firestore;

    private List<BorrowItem> borrowedBooks;


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
        borrowedBooks = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        preferenceManager = new SharedPreferenceManager(requireContext());
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
        String stdId = preferenceManager.getStudentId();
        dialog.show();
        firestore.collection("borrows")
                .whereEqualTo("studentId", stdId)
                .get()
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String borrowedId = document.getString("borrowedId");
                            String bookId = document.getString("bookId");
                            String bookTitle = document.getString("bookTitle");
                            String studentId = document.getString("studentId");
                            String borrowedDate = document.getString("borrowDate");
                            String returnDate = document.getString("returnDate");
                            String returnStatus = document.getString("returnStatus");

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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            LocalDate parsedReturnDate = LocalDate.parse(borrowItem.getReturnDate());

            if (currentDate.isAfter(parsedReturnDate)) {
                String params = new Gson().toJson(borrowItem);
                navController.navigate(StudentBorrowedListFragmentDirections.actionStudentBorrowedListFragmentToPaymentFragment(params));
            } else {
                String borrowedDate = DateUtils.getCurrentDate();
                String returnedId = firestore.collection("returns").document().getId();

                Returned returned = new Returned(
                        returnedId,
                        borrowItem.getBookId(),
                        borrowItem.getBorrowedId(),
                        borrowItem.getBookTitle(),
                        borrowItem.getStudentId(),
                        borrowedDate);

                dialog.show();
                firestore.collection("returns")
                        .document(returnedId)
                        .set(returned)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateBorrowedBook(borrowItem);
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(requireActivity(), "Failed to borrow book", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }

    private void updateBorrowedBook(BorrowItem borrowItem) {
        dialog.show();
        firestore.collection("borrows")
                .document(borrowItem.getBorrowedId())
                .update("returnStatus", "Return Pending")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        Toast.makeText(requireActivity(), "Book returned successfully", Toast.LENGTH_SHORT).show();
                        borrowedBooks.clear();
                        borrowedBookAdapter.setBorrowItemList(borrowedBooks);
                        getAllTheBorrowedBooks();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to return book", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
