package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentSearchBookBinding;
import me.fsfaysalcse.ezylib.ui.adapter.BorrowAdapter;
import me.fsfaysalcse.ezylib.ui.model.Book;


public class SearchBookFragment extends Fragment implements BorrowAdapter.OnItemClickListener {

    private FragmentSearchBookBinding binding;
    private BorrowAdapter borrowAdapter;

    private NavController navController;

    private FirebaseFirestore firestore;
    private CollectionReference booksCollection;

    private ProgressDialog progressDialog;

    private List<Book> bookList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBookBinding.inflate(inflater, container, false);
        init(binding.getRoot());
        setupViews();
        return binding.getRoot();
    }


    private void init(LinearLayout root) {

        binding.toolbarSearchBook.titleTextView.setText("Search Book");
        binding.toolbarSearchBook.backButton.setOnClickListener(v -> navController.navigateUp());

        navController = ((MainActivity) getActivity()).getNav();
        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Loading Books");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        // Setup RecyclerView
        borrowAdapter = new BorrowAdapter(getActivity(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(borrowAdapter);


    }

    private void setupViews() {
        // Setup Search Button
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchQuery = s.toString().trim();

                if (searchQuery.isEmpty()) {
                    // Clear the error and retain the previous list
                    binding.etSearch.setError(null);
                    borrowAdapter.submitList(bookList);
                } else {
                    List<Book> searchResults = new ArrayList<>();
                    for (Book book : bookList) {
                        if (book.getBookTitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                            searchResults.add(book);
                        }
                    }
                    borrowAdapter.submitList(searchResults);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllBooks();
    }

    private void getAllBooks() {
        borrowAdapter.getCurrentList().clear();
        binding.etSearch.setText("");
        progressDialog.show();
        bookList = new ArrayList<>();
        firestore.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("bookTitle");
                            String author = document.getString("author");
                            String publishYear = document.getString("publishYear");
                            boolean isBorrowed = document.getBoolean("isBorrowed");

                            // Create a Book object and add it to the bookList
                            Book book = new Book(document.getId(), title, author, publishYear, isBorrowed);
                            bookList.add(book);
                        }

                        if (bookList.size() == 0) {
                            Toast.makeText(getActivity(), "No books found", Toast.LENGTH_SHORT).show();
                        } else {
                            borrowAdapter.submitList(bookList);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to fetch books", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to fetch books", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Book borrowItem) {

        String params = new Gson().toJson(borrowItem);

        me.fsfaysalcse.ezylib.ui.fragment.universal.SearchBookFragmentDirections.ActionSearchBookFragmentToBorrowFragment action =
                SearchBookFragmentDirections.actionSearchBookFragmentToBorrowFragment(params);

        navController.navigate(action);
    }
}