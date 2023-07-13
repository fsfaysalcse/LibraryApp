package me.arvin.ezylib.ui.fragment.universal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentFaqBinding;
import me.arvin.ezylib.ui.adapter.FAQAdapter;
import me.arvin.ezylib.ui.model.FAQItem;


public class FaqFragment extends Fragment {


    private FragmentFaqBinding binding;

    private List<FAQItem> faqList;
    private FAQAdapter faqAdapter;
    private RecyclerView faqRecyclerView;

    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFaqBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        navController = ((MainActivity) requireActivity()).getNav();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {

        binding.toolbar.titleTextView.setText("FAQs");
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });

        faqList = new ArrayList<>();

        FAQItem androidFAQ = new FAQItem("What is a library app?", "A library app is a mobile application that allows users to access a library's catalog and resources from their mobile devices. It provides a convenient way for users to search for books, check their availability, and place holds on items. It also allows users to manage their accounts, renew items, and pay fines.");
        faqList.add(androidFAQ);

        FAQItem iOSFAQ = new FAQItem("How can a library app benefit users?", "A library app can benefit users by providing them with a convenient way to access the library's catalog and resources from their mobile devices. It also allows users to manage their accounts, renew items, and pay fines.");
        faqList.add(iOSFAQ);

        FAQItem appleFAQ = new FAQItem("What features can a library app have?", "A library app can have a variety of features, including a catalog search, account management, and a way to pay fines. It can also have a feature that allows users to place holds on items and renew items.");
        faqList.add(appleFAQ);


        FAQItem macFAQ = new FAQItem("Can I use a library app to request or reserve books", "Yes, most library apps allow users to place holds or requests on books or other materials. When an item is not currently available, users can request it through the app, and they will be notified when the item becomes available for pickup. This feature helps users secure popular items and ensures that they do not miss out on any new releases.");
        faqList.add(macFAQ);


        faqRecyclerView = view.findViewById(R.id.faqRecyclerView);
        faqAdapter = new FAQAdapter(faqList);
        faqRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        faqRecyclerView.setAdapter(faqAdapter);


    }

    private void setupView() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}