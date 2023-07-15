package me.arvin.ezylib.ui.fragment.user;

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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import me.arvin.ezylib.MainActivity;
import me.arvin.ezylib.R;
import me.arvin.ezylib.databinding.FragmentInvoiceBinding;
import me.arvin.ezylib.ui.model.BorrowItem;
import me.arvin.ezylib.ui.model.Returned;
import me.arvin.ezylib.ui.utli.DateUtils;

public class InvoiceFragment extends Fragment {


    private FragmentInvoiceBinding binding;
    private NavController navController;

    private InvoiceFragmentArgs args;

    private FirebaseFirestore firestore;

    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInvoiceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        init(view);
        setupView();
        return view;
    }

    private void init(View view) {
        navController = ((MainActivity) getActivity()).getNav();
        args = InvoiceFragmentArgs.fromBundle(getArguments());


        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Searching Student");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        firestore = FirebaseFirestore.getInstance();
    }

    private void setupView() {
        saveBorrowItem();

        binding.btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_invoiceFragment_to_userHomeFragment);
            }
        });
    }

    private void saveBorrowItem() {

        BorrowItem borrowItem = new Gson().fromJson(args.getParams(), BorrowItem.class);
        progressDialog.show();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("borrows")
                .whereEqualTo("borrowedId", borrowItem.getBorrowedId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            firestore.collection("borrows")
                                    .document(document.getId())
                                    .update("returnStatus", "Returned Pending")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            binding.tvInvoice.setText(borrowItem.toString());
                                            perfromReturn(borrowItem);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(requireActivity(), "Failed to update borrow item", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to retrieve borrow item", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void perfromReturn(BorrowItem borrowItem) {
        String borrowedDate = DateUtils.getCurrentDate();
        String returnedId = firestore.collection("returns").document().getId();

        Returned returned = new Returned(
                returnedId,
                borrowItem.getBookId(),
                borrowItem.getBorrowedId(),
                borrowItem.getBookTitle(),
                borrowItem.getStudentId(),
                borrowedDate);

        progressDialog.show();
        firestore.collection("returns")
                .document(returnedId)
                .set(returned)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireActivity(), "This book has returned successfully please wait for admin approval", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(requireActivity(), "Failed to borrow book", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}