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
import com.google.firebase.firestore.FirebaseFirestore;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentAddBookBinding;
import me.fsfaysalcse.ezylib.ui.model.Book;
import me.fsfaysalcse.ezylib.ui.utli.Utility;


public class AddBookFragment extends Fragment {


    private FragmentAddBookBinding binding;
    private NavController navController;
    private FirebaseFirestore firestore;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Saving Book");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        firestore = FirebaseFirestore.getInstance();
        navController = ((MainActivity) requireActivity()).getNav();

        binding.toolbar.titleTextView.setText("Add Book");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());


    }

    private void setupView() {
        binding.btnSave.setOnClickListener(v -> performSaveBook());
    }

    private void performSaveBook() {

        String bookTitle = binding.etBookTitle.getText().toString().trim();
        String publishYear = binding.etPublishYear.getText().toString().trim();
        String author = binding.etAuthor.getText().toString().trim();


        if (bookTitle.isEmpty()) {
            binding.etBookTitle.setError("Please enter the book title");
            binding.etBookTitle.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (publishYear.isEmpty()) {
            binding.etPublishYear.setError("Please enter the publish year");
            binding.etPublishYear.requestFocus();
            progressDialog.dismiss();
            return;
        }

        if (author.isEmpty()) {
            binding.etAuthor.setError("Please enter the author");
            binding.etAuthor.requestFocus();
            progressDialog.dismiss();
            return;
        }

        Utility.hideKeyboard(requireActivity(), binding.getRoot());

        String bookId = firestore.collection("books").document().getId();
        Book book = new Book(bookId,bookTitle, publishYear, author, false);

        progressDialog.show();
        firestore.collection("books")
                .document(bookId)
                .set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        navController.navigateUp();
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), "Book saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(requireContext(), "Failed to save book data", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}