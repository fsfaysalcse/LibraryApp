package me.fsfaysalcse.ezylib.ui.fragment.universal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import me.fsfaysalcse.ezylib.MainActivity;
import me.fsfaysalcse.ezylib.R;
import me.fsfaysalcse.ezylib.databinding.FragmentSearchBookBinding;
import me.fsfaysalcse.ezylib.ui.adapter.BorrowAdapter;
import me.fsfaysalcse.ezylib.ui.model.BorrowItem;


public class SearchBookFragment extends Fragment implements BorrowAdapter.OnItemClickListener {

    private FragmentSearchBookBinding binding;
    private BorrowAdapter borrowAdapter;
    private List<BorrowItem> bookList;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBookBinding.inflate(inflater, container, false);
        init(binding.getRoot());
        setupViews();
        return binding.getRoot();
    }


    private void init(LinearLayout root) {

        navController = ((MainActivity) getActivity()).getNav();

        bookList = new ArrayList<>();
        bookList.add(new BorrowItem("JavaScript for beginner", "Bill Gates", true));
        bookList.add(new BorrowItem("Java Expert", "Yons Topn", false));
        bookList.add(new BorrowItem("Python for beginner", "Mark Zuckerburg", true));
        bookList.add(new BorrowItem("C++ for beginner", "Thomas", false));
        bookList.add(new BorrowItem("C# for beginner", "Mark Adfin", true));

    }

    private void setupViews() {

        binding.toolbar.titleTextView.setText("Search Book");
        binding.toolbar.backButton.setOnClickListener(v -> navController.navigateUp());


        // Setup RecyclerView
        borrowAdapter = new BorrowAdapter(getActivity(), bookList, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(borrowAdapter);

        // Setup Search Button
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform search based on input
                // Update bookList accordingly and call borrowAdapter.notifyDataSetChanged();
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
        navController.navigate(R.id.action_searchBookFragment_to_borrowFragment);
    }
}