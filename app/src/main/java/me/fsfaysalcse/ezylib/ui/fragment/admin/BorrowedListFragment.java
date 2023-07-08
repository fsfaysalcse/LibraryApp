package me.fsfaysalcse.ezylib.ui.fragment.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.databinding.FragmentBorrowedBookBinding;
import me.fsfaysalcse.ezylib.ui.adapter.BorrowedBookAdapter;
import me.fsfaysalcse.ezylib.ui.model.BorrowedBookItem;

public class BorrowedListFragment extends Fragment {

    private FragmentBorrowedBookBinding binding;
    private BorrowedBookAdapter borrowedBookAdapter;
    private List<BorrowedBookItem> borrowedBookList;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBorrowedBookBinding.inflate(inflater, container, false);
        navController = ((MainActivity) requireActivity()).getNav();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Setup Toolbar
        binding.toolbar.titleTextView.setText("Borrowed Books");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());

        // Initialize borrowed book list
        borrowedBookList = new ArrayList<>();
        borrowedBookList.add(new BorrowedBookItem("S001", "Book Title 1", "2022-07-01", "2022-07-14"));
        borrowedBookList.add(new BorrowedBookItem("S002", "Book Title 2", "2022-06-15", "2022-06-30"));
        // Add more borrowed book items as needed

        // Setup RecyclerView
        borrowedBookAdapter = new BorrowedBookAdapter(getActivity(), borrowedBookList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(borrowedBookAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }
}
