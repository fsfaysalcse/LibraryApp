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

        FAQItem androidFAQ = new FAQItem("What is Android OS?", "Android is an operating system designed primarily for mobile devices such as smartphones and tablets. It is based on the Linux kernel and developed by Google. Android provides a platform for developers to create applications and offers a range of features, including a customizable user interface, access to various apps through the Google Play Store, and integration with Google services.");
        faqList.add(androidFAQ);

        FAQItem iOSFAQ = new FAQItem("What is iOS?", "iOS is the operating system developed by Apple Inc. exclusively for its mobile devices, including iPhones, iPads, and iPod Touch. It offers a secure and user-friendly environment with a wide range of built-in features, including the App Store for downloading applications, iCloud for cloud storage, Siri for voice assistance, and seamless integration with other Apple devices and services.");
        faqList.add(iOSFAQ);

        FAQItem appleFAQ = new FAQItem("What is Apple?", "Apple Inc. is a multinational technology company headquartered in Cupertino, California. It designs, develops, and sells a range of consumer electronics, software, and online services. Apple is best known for its products, including the iPhone, iPad, Mac, Apple Watch, and Apple TV. The company also offers services like the App Store, iCloud, Apple Music, and Apple Pay.");
        faqList.add(appleFAQ);

        FAQItem windowsFAQ = new FAQItem("What is Windows?", "Windows is an operating system developed by Microsoft. It is used on a wide range of devices, including desktop computers, laptops, tablets, and smartphones. Windows provides a graphical user interface, multitasking capabilities, and support for a vast number of software applications. The latest version of Windows is Windows 10, which offers regular updates, improved security features, and integration with Microsoft services.");
        faqList.add(windowsFAQ);

        FAQItem macFAQ = new FAQItem("What is Mac?", "Mac refers to the line of computers developed by Apple Inc. that runs on macOS, the operating system exclusively designed for Macintosh computers. Macs are known for their sleek design, user-friendly interface, and seamless integration with other Apple devices and services. Mac computers offer powerful performance, advanced features, and a wide range of software applications optimized for macOS.");
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