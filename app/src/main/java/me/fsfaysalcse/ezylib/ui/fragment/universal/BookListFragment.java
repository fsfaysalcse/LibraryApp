package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentBookListBinding;
import me.fsfaysalcse.ezylib.ui.adapter.BookAdapter;
import me.fsfaysalcse.ezylib.ui.model.Book;

public class BookListFragment extends Fragment {

    private BookAdapter bookAdapter;
    private List<Book> bookList;

    private FragmentBookListBinding binding;
    private NavController navController;

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
        navController = ((MainActivity) requireActivity()).getNav();

        binding.toolbar.titleTextView.setText("Book List");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());

    }


    public void setupView() {
        bookList = new ArrayList<>();
        bookList.add(new Book("Kotlin for Java Programmer", "Faysal Hossain", 2021));
        bookList.add(new Book("Groking Algorithm", "Nicola Tesla", 2022));
        bookList.add(new Book("Be your best version", "Elon Mask", 2023));
        bookList.add(new Book("The Art of War", "Sun Tzu", 2020));


        bookAdapter = new BookAdapter(getActivity(), bookList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(bookAdapter);
    }
}

