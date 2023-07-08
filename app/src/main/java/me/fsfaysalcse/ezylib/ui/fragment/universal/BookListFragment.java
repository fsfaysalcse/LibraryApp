package me.fsfaysalcse.ezylib.ui.fragment.universal;

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

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentBookListBinding;
import me.fsfaysalcse.ezylib.ui.adapter.BookAdapter;
import me.fsfaysalcse.ezylib.ui.model.Book;

public class BookListFragment extends Fragment implements BookAdapter.OnItemClickListener {

    private BookAdapter bookAdapter;
    private List<Book> bookList;

    private FragmentBookListBinding binding;
    private NavController navController;

    private FirebaseFirestore firestore;

    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookListBinding.inflate(inflater, container, false);
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

        binding.toolbar.titleTextView.setText("Book List");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());

        firestore = FirebaseFirestore.getInstance();
    }

    public void setupView() {
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(getActivity(), this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(bookAdapter);

        dialog.show();
        firestore.collection("books")
                .get()
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get book details from Firestore document
                            String book_id = document.getString("book_id");
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
                            bookAdapter.submitList(bookList);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed to fetch books", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to fetch books", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(Book book) {

    }

    @Override
    public void onDeleteClick(Book book) {
        firestore.collection("books")
                .document(book.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        bookList.remove(book);
                        bookAdapter.notifyItemRemoved(bookList.indexOf(book));
                        Toast.makeText(requireContext(), "Book deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to delete book", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
