package me.arvin.ezylib.ui.fragment.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.databinding.FragmentReturnsBinding;
import me.arvin.ezylib.ui.adapter.ReturnsAdapter;
import me.arvin.ezylib.ui.model.Returned;


public class ReturnsFragment extends Fragment implements ReturnsAdapter.OnItemClickListener {

    private ReturnsAdapter returnAdapter;
    private List<Returned> returnList;

    private FragmentReturnsBinding binding;
    private NavController navController;

    private FirebaseFirestore firestore;

    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReturnsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        dialog = new ProgressDialog(requireContext());
        dialog.setTitle("Loading Books");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

        navController = ((MainActivity) requireActivity()).getNav();

        binding.toolbarReturns.titleTextView.setText("Returned Books");
        binding.toolbarReturns.backButton.setOnClickListener(v -> navController.navigateUp());

        firestore = FirebaseFirestore.getInstance();
    }

    public void setupView() {
        returnList = new ArrayList<>();
        returnAdapter = new ReturnsAdapter(getActivity(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(returnAdapter);

        binding.reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBooks();
            }
        });

        loadBooks();

    }

    private void loadBooks() {
        dialog.show();
        firestore.collection("returns")
                .get()
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get book details from Firestore document
                            String book_id = document.getString("bookId");
                            String title = document.getString("bookTitle");
                            String borrowedId = document.getString("borrowedId");
                            String returnTimestamp = document.getString("returnTimestamp");
                            String studentId = document.getString("studentId");
                            String returnedId = document.getString("returnedId");

                            Returned returned = new Returned(
                                    returnedId,
                                    book_id,
                                    borrowedId,
                                    title,
                                    studentId,
                                    returnTimestamp);
                            returnList.add(returned);
                        }

                        if (returnList.size() == 0) {
                            Toast.makeText(getActivity(), "No returns found", Toast.LENGTH_SHORT).show();
                        } else {
                            returnAdapter.setReturnedList(returnList);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to fetch returns books", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to fetch returns books", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onItemClick(Returned returned) {

    }


    @Override
    public void onAcceptClick(Returned returned) {
        deleteReturned(returned);
    }

    private void deleteReturned(Returned returned) {
        dialog.show();
        firestore.collection("returns")
                .document(returned.getReturnedId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        deleteBorrowItem(returned);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to delte borrowItem book", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteBorrowItem(Returned returned) {
        firestore.collection("borrows")
                .document(returned.getBorrowedId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateBook(returned);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to delte borrowItem book", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateBook(Returned returned) {
        firestore.collection("books")
                .document(returned.getBookId())
                .update("isBorrowed", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        returnList.clear();
                        returnAdapter.setReturnedList(returnList);
                        loadBooks();
                        Toast.makeText(requireActivity(), "Return book has been accepted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to accept return", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}