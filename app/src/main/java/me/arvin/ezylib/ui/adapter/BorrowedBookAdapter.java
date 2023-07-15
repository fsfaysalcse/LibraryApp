package me.arvin.ezylib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.arvin.ezylib.R;
import me.arvin.ezylib.ui.model.BorrowItem;
import me.arvin.ezylib.ui.utli.SharedPreferenceManager;

public class BorrowedBookAdapter extends RecyclerView.Adapter<BorrowedBookAdapter.BorrowedBookViewHolder> {

    private Context context;
    private List<BorrowItem> borrowItemList;
    private SharedPreferenceManager preferenceManager;

    public interface OnItemClickListener {
        void onItemClick(BorrowItem borrowItem);

        void onReturnClick(BorrowItem borrowItem);
    }

    private OnItemClickListener listener;

    public BorrowedBookAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.borrowItemList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public BorrowedBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_borrowed_book, parent, false);
        preferenceManager = new SharedPreferenceManager(context);
        return new BorrowedBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowedBookViewHolder holder, int position) {
        BorrowItem borrowItem = borrowItemList.get(position);

        holder.tvStudentId.setText(borrowItem.getStudentId());
        holder.tvBookName.setText(borrowItem.getBookTitle());
        holder.tvBorrowedDate.setText(borrowItem.getBorrowDate());
        holder.tvReturnDate.setText(borrowItem.getReturnDate());

        holder.tvRetrunStatus.setText(borrowItem.getReturnStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(borrowItem);
            }
        });

        if (preferenceManager.getStudentId().isEmpty()) {
            holder.llReturnBook.setVisibility(View.GONE);
        }

        holder.btnReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (borrowItem.getReturnStatus().equals("Return Pending")) {
                    Toast.makeText(context, "This book has already been returned to the library. Please wait for admin approval.", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onReturnClick(borrowItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return borrowItemList.size();
    }

    public void setBorrowItemList(List<BorrowItem> borrowItemList) {
        this.borrowItemList.clear();
        if (borrowItemList != null) {
            this.borrowItemList.addAll(borrowItemList);
        }
        notifyDataSetChanged();
    }

    public class BorrowedBookViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentId, tvBookName, tvBorrowedDate, tvReturnDate, btnReturnBook, tvRetrunStatus;
        LinearLayout llReturnBook;

        public BorrowedBookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentId = itemView.findViewById(R.id.tvStudentId);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBorrowedDate = itemView.findViewById(R.id.tvBorrowedDate);
            tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
            btnReturnBook = itemView.findViewById(R.id.btnReturnBook);
            tvRetrunStatus = itemView.findViewById(R.id.tvRetrunStatus);
            llReturnBook = itemView.findViewById(R.id.llReturnBook);
        }
    }
}
